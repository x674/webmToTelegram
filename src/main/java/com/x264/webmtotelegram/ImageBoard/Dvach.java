package com.x264.webmtotelegram.ImageBoard;

import com.x264.webmtotelegram.Entities.ImageBoardThread;
import com.x264.webmtotelegram.Entities.MediaFile;
import com.x264.webmtotelegram.Repositories.MediaRepository;
import com.x264.webmtotelegram.Repositories.ThreadRepository;
import com.x264.webmtotelegram.Telegram.Bot;
import com.x264.webmtotelegram.Entities.TelegramPost;
import com.x264.webmtotelegram.VideoUtils.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class Dvach {
    private final RestTemplate restTemplate;
    private static final String host2ch = "https://2ch.hk";
    private static final Logger log = LoggerFactory.getLogger(Dvach.class);
    private Bot telegramBot;
    private Converter converter;
    private ArrayList<ImageBoardThread> listImageBoardThreads;
    private ThreadRepository threadRepository;
    private MediaRepository mediaRepository;
    private boolean parseStatus = false;

    public Dvach(RestTemplateBuilder restTemplateBuilder, ApplicationContext applicationContext,
            ThreadRepository threadRepository, MediaRepository mediaRepository, Converter converter, Bot telegramBot) {
        this.telegramBot = telegramBot;
        this.converter = converter;
        this.restTemplate = restTemplateBuilder.build();
        this.threadRepository = threadRepository;
        this.mediaRepository = mediaRepository;

        var currentThreads = GetListThreads();
        var idsRepo = threadRepository.findAllByIdThread();
        threadRepository.saveAll(
                currentThreads.stream().filter(e -> !idsRepo.contains(e.getIdThread())).collect(Collectors.toList()));
        var currentIdsThreads = currentThreads.stream().map(e1 -> e1.getIdThread()).collect(Collectors.toList());
        var toRemoveList = threadRepository.findAllByIdThread().stream().filter(e -> !currentIdsThreads.contains(e))
                .collect(Collectors.toList());
        threadRepository.deleteAllByIdInBatch(toRemoveList);
        listImageBoardThreads = threadRepository.findAll();
        UpdateThreads();
    }

    private ArrayList<ImageBoardThread> GetListThreads() {
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

    private void CheckThread(ImageBoardThread imageBoardThread) {
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
                        String fileType = (String) file.get("name");
                        String hash = (String) file.get("md5");
                        String name = (String) file.get("name");
                        String fullName = (String) file.get("fullname");
                        if (mediaRepository.existsById(hash))
                            return;
                        if (fileType.contains("mp4")) {
                            var mediaFile = new MediaFile(hash, name, fullName);
                            mediaRepository.save(mediaFile);
                            imageBoardThread.addMediaFile(mediaFile);
                            urlFiles.add(host2ch + file.get("path"));
                            // urlFiles.add(Downloader.DownloadFile(host2ch + file.get("path")));
                        } else if (fileType.contains("webm")) {
                            var mediaFile = new MediaFile(hash, name, fullName);
                            var filePath = converter.ConvertWebmToMP4(host2ch + file.get("path"));
                            mediaRepository.save(mediaFile);
                            imageBoardThread.addMediaFile(mediaFile);
                            urlFiles.add(filePath.getAbsolutePath());
                        }
                    });

                    imageBoardThread.setLastCheckedMessage(numMessage);

                    if (!urlFiles.isEmpty()) {
                        TelegramPost telegramPost = new TelegramPost();
                        telegramPost.ThreadName = imageBoardThread.getTitle();
                        telegramPost.MessageURL = host2ch + "/b/res/" + imageBoardThread.getIdThread() + ".html" + "#" + numMessage;
                        telegramPost.URLVideos = urlFiles;
                        telegramBot.telegramPostArrayDeque.add(telegramPost);
                    }
                }
        );
        threadRepository.save(imageBoardThread);
    }

    private void UpdateThreads() {
        CompletableFuture.supplyAsync(() -> {
            while (true) {
                if (parseStatus) {
                    listImageBoardThreads.stream()
                            // .filter(e -> Pattern.compile(Pattern.quote("webm"), Pattern.CASE_INSENSITIVE)
                            //         .matcher(e.getTitle()).find())
                            .forEach(imageBoardThread -> {
                                try {
                                    if (parseStatus) {
                                        CheckThread(imageBoardThread);
                                        TimeUnit.SECONDS.sleep(4);
                                    } else {
                                        return;
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                }
            }
        });
    }

    /**
     * @return Bot return the telegramBot
     */
    public Bot getTelegramBot() {
        return telegramBot;
    }

    /**
     * @param telegramBot the telegramBot to set
     */
    public void setTelegramBot(Bot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * @return Converter return the converter
     */
    public Converter getConverter() {
        return converter;
    }

    /**
     * @param converter the converter to set
     */
    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    /**
     * @return ArrayList<ImageBoardThread> return the listImageBoardThreads
     */
    public ArrayList<ImageBoardThread> getListImageBoardThreads() {
        return listImageBoardThreads;
    }

    /**
     * @param listImageBoardThreads the listImageBoardThreads to set
     */
    public void setListImageBoardThreads(ArrayList<ImageBoardThread> listImageBoardThreads) {
        this.listImageBoardThreads = listImageBoardThreads;
    }

    /**
     * @return ThreadRepository return the threadRepository
     */
    public ThreadRepository getThreadRepository() {
        return threadRepository;
    }

    /**
     * @param threadRepository the threadRepository to set
     */
    public void setThreadRepository(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    /**
     * @return MediaRepository return the mediaRepository
     */
    public MediaRepository getMediaRepository() {
        return mediaRepository;
    }

    /**
     * @param mediaRepository the mediaRepository to set
     */
    public void setMediaRepository(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }


    /**
     * @return boolean return the parseStatus
     */
    public boolean isParseStatus() {
        return parseStatus;
    }

    /**
     * @param parseStatus the parseStatus to set
     */
    public void setParseStatus(boolean parseStatus) {
        this.parseStatus = parseStatus;
    }

}
