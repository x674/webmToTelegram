package com.x264.webmtotelegram.ImageBoard;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.x264.webmtotelegram.Entities.ImageBoardThread;
import com.x264.webmtotelegram.Entities.MediaFile;
import com.x264.webmtotelegram.Entities.TelegramPost;
import com.x264.webmtotelegram.Repositories.MediaRepository;
import com.x264.webmtotelegram.Repositories.ThreadRepository;
import com.x264.webmtotelegram.Telegram.Bot;
import com.x264.webmtotelegram.VideoUtils.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class Dvach {
    private final RestTemplate restTemplate;
    private static final String host2ch = "https://2ch.hk";
    private static final Logger log = LoggerFactory.getLogger(Dvach.class);
    private Converter converter;
    private ArrayList<ImageBoardThread> listImageBoardThreads;
    private ThreadRepository threadRepository;
    private MediaRepository mediaRepository;
    private boolean parseStatus = true;
    private boolean restartUpdate = false;

    private ArrayList<String> threadFilter = new ArrayList<>();

    public Dvach(RestTemplateBuilder restTemplateBuilder, ApplicationContext applicationContext,
            ThreadRepository threadRepository, MediaRepository mediaRepository, Converter converter) {
        this.converter = converter;
        this.restTemplate = restTemplateBuilder.build();
        this.threadRepository = threadRepository;
        this.mediaRepository = mediaRepository;

        UpdateThreads();
        CheckThreads();
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

    private void UpdateThreads() {
        var currentThreads = GetListThreads();
        var idsRepo = threadRepository.findAllByIdThread();
        threadRepository.saveAll(
                currentThreads.stream().filter(e -> !idsRepo.contains(e.getIdThread())).collect(Collectors.toList()));
        var currentIdsThreads = currentThreads.stream().map(e1 -> e1.getIdThread()).collect(Collectors.toList());
        var toRemoveList = threadRepository.findAllByIdThread().stream().filter(e -> !currentIdsThreads.contains(e))
                .collect(Collectors.toList());
        threadRepository.deleteAllByIdInBatch(toRemoveList);
        listImageBoardThreads = threadRepository.findAll();

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
                        telegramPostArrayDeque.add(telegramPost);
                    }
                }
        );
        threadRepository.save(imageBoardThread);
    }

    private ArrayDeque<TelegramPost> telegramPostArrayDeque = new ArrayDeque<>();

    private void CheckThreads() {
        CompletableFuture.runAsync(() -> {
            while (true) {
                if (parseStatus) {
                    restartUpdate = false;
                    listImageBoardThreads.stream()
                            .filter(e ->
                            {
                                for (var word : threadFilter) {
                                    if (Pattern.compile(Pattern.quote(word), Pattern.CASE_INSENSITIVE)
                                            .matcher(e.getTitle()).find())
                                        return true;
                                    else if(threadFilter.size() == 0)
                                        return true;
                                }
                                return false;
                            })
                            .forEach(imageBoardThread -> {
                                try {
                                    if (parseStatus || !restartUpdate) {
                                        CheckThread(imageBoardThread);
                                        TimeUnit.SECONDS.sleep(4);
                                    } else {
                                        return;
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                    UpdateThreads();
                }
            }
        });
    }

    public ArrayDeque<TelegramPost> getTelegramPostArrayDeque() {
        return telegramPostArrayDeque;
    }
    /**
     * @return ArrayList<ImageBoardThread> return the listImageBoardThreads
     */
    public ArrayList<ImageBoardThread> getListImageBoardThreads() {
        return listImageBoardThreads;
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

    public ArrayList<String> getThreadFilter() {
        return threadFilter;
    }

    public void setThreadFilter(ArrayList<String> threadFilter) {
        this.threadFilter = threadFilter;
        restartUpdate = true;
    }
}
