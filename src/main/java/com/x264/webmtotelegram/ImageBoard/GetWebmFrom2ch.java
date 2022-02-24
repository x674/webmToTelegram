package com.x264.webmtotelegram.ImageBoard;

import com.x264.webmtotelegram.JPA.MediaRepository;
import com.x264.webmtotelegram.JPA.ThreadRepository;
import com.x264.webmtotelegram.Telegram.Bot;
import com.x264.webmtotelegram.Telegram.TelegramPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class GetWebmFrom2ch {
    private final RestTemplate restTemplate;
    private static final String host2ch = "https://2ch.hk";
    private static final Logger log = LoggerFactory.getLogger(GetWebmFrom2ch.class);
    private Bot botBean;
    private Converter converter;
    private ArrayList<ImageBoardThread> listImageBoardThreads;
    private ThreadRepository threadRepository;
    private MediaRepository mediaRepository;

    public GetWebmFrom2ch(RestTemplateBuilder restTemplateBuilder, ApplicationContext applicationContext, ThreadRepository threadRepository, MediaRepository mediaRepository) {
        botBean = (Bot) applicationContext.getBean("bot");
        converter = (Converter) applicationContext.getBean("converter");
        restTemplate = restTemplateBuilder.build();
        this.threadRepository = threadRepository;
        this.mediaRepository = mediaRepository;

        var currentThreads = GetListThreads();
        var idsRepo =threadRepository.findAllByIdThread();
        threadRepository.saveAll(currentThreads.stream().filter(e->!idsRepo.contains(e.getIdThread())).collect(Collectors.toList()));
        var currentIdsThreads = currentThreads.stream().map(e1 -> e1.getIdThread()).collect(Collectors.toList());
        var toRemoveList = threadRepository.findAllByIdThread().stream().filter(e -> !currentIdsThreads.contains(e)).collect(Collectors.toList());
        threadRepository.deleteAllByIdInBatch(toRemoveList);
        listImageBoardThreads = threadRepository.findAll();
    }

    public ArrayList<ImageBoardThread> GetListThreads() {
        LinkedHashMap catalog = this.restTemplate.getForObject(host2ch + "/b/catalog.json", LinkedHashMap.class);
        ArrayList<LinkedHashMap> arrayThreads = (ArrayList) catalog.get("threads");
        ArrayList<ImageBoardThread> imageBoardThreads = new ArrayList<>();
        arrayThreads.forEach(e -> {
            ImageBoardThread imageBoardThread = new ImageBoardThread();
            imageBoardThread.setIdThread(Long.parseLong((String) e.get("num")));
            imageBoardThread.setTitle((String) e.get("subject"));
            imageBoardThreads.add(imageBoardThread);
        });
        return imageBoardThreads;
    }

    public void CheckThread(ImageBoardThread imageBoardThread) {
        log.info("Check thread " + imageBoardThread.getTitle());
        LinkedHashMap threadJson;
        try {
            threadJson = restTemplate.getForObject(host2ch + "/b/res/" + imageBoardThread.getIdThread() + ".json", LinkedHashMap.class);
        } catch (HttpClientErrorException.NotFound exception) {
            log.warn("404 " + exception);
            threadRepository.delete(imageBoardThread);
            listImageBoardThreads.remove(imageBoardThread);
            return;
        }
        //Threads->Posts
        ArrayList<LinkedHashMap> threads = (ArrayList<LinkedHashMap>) threadJson.get("threads");
        LinkedHashMap to1 = threads.get(0);
        ArrayList<LinkedHashMap> posts = (ArrayList<LinkedHashMap>) to1.get("posts");
        posts.stream().filter(e -> (Integer) e.get("num") > imageBoardThread.getLastCheckedMessage()).forEach(
                post -> {
                    ArrayList<String> urlFiles = new ArrayList<>();
                    int numMessage = (int) post.get("num");
                    ArrayList<LinkedHashMap> filesList = (ArrayList<LinkedHashMap>) post.get("files");
                    filesList.forEach(file -> {
                        //check that this a video file
                        //type mp4 - 10, webm - 6
                        String fileType = (String) file.get("name");
                        String hash = (String) file.get("md5");
                        String name = (String) file.get("name");
                        String fullName = (String) file.get("fullname");
                        if (mediaRepository.existsById(hash))
                            return;
                        if (fileType.contains("mp4")) {
                            var mediaFile = new MediaFile(hash,name,fullName);

                            mediaRepository.save(mediaFile);
                            imageBoardThread.addMediaFile(mediaFile);
                            //urlFiles.add(host2ch + file.get("path"));
                            urlFiles.add(Downloader.DownloadFile(host2ch + file.get("path")));
                        } else if (fileType.contains("webm")) {
                            var mediaFile = new MediaFile(hash,name,fullName);
                            var filePath =  converter.ConvertWebmToMP4(host2ch + file.get("path"));
                            mediaRepository.save(mediaFile);
                            imageBoardThread.addMediaFile(mediaFile);
                            urlFiles.add(filePath);
                        }
                    });

                    imageBoardThread.setLastCheckedMessage(numMessage);

                    if (!urlFiles.isEmpty()) {
                        TelegramPost telegramPost = new TelegramPost();
                        telegramPost.ThreadName = imageBoardThread.getTitle();
                        telegramPost.MessageURL = host2ch + "/b/res/" + imageBoardThread.getIdThread() + ".html" + "#" + numMessage;
                        telegramPost.URLVideos = urlFiles;
                        botBean.telegramPostArrayDeque.add(telegramPost);
                    }
                }
        );
        threadRepository.save(imageBoardThread);
    }

    @PostConstruct
    public void UpdateThreads() {
        CompletableFuture.supplyAsync(() -> {
            //.filter(e-> Pattern.compile(Pattern.quote("webm mp4"),Pattern.CASE_INSENSITIVE).matcher(e.getTitle()).find())
            listImageBoardThreads.stream()
                    .filter(e-> Pattern.compile(Pattern.quote("фап"),Pattern.CASE_INSENSITIVE).matcher(e.getTitle()).find()||
                            Pattern.compile(Pattern.quote("fap"),Pattern.CASE_INSENSITIVE).matcher(e.getTitle()).find()||
                            Pattern.compile(Pattern.quote("afg"),Pattern.CASE_INSENSITIVE).matcher(e.getTitle()).find())
                    .forEach(imageBoardThread -> {
                        CheckThread(imageBoardThread);
                        try {
                            TimeUnit.SECONDS.sleep(4);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
            return null;
        });
    }
}
