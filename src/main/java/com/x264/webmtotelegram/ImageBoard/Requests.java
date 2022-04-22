package com.x264.webmtotelegram.ImageBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import com.x264.webmtotelegram.Entities.ImageBoardThread;
import com.x264.webmtotelegram.Entities.MediaFile;
import com.x264.webmtotelegram.Entities.TelegramPost;
import com.x264.webmtotelegram.ImageBoard.Dvach.Rest.Catalog;
import com.x264.webmtotelegram.ImageBoard.Dvach.Rest.Thread;
import com.x264.webmtotelegram.ImageBoard.Dvach.Rest.ThreadPosts;
import com.x264.webmtotelegram.Repositories.MediaRepository;
import com.x264.webmtotelegram.Repositories.ThreadRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.constraints.NotNull;

@Component
public class Requests {
private final RestTemplate restTemplate;
    private static final String host2ch = "https://2ch.hk";
    private static final Logger log = LoggerFactory.getLogger(Requests.class);
    private final WebClient webClient;
    private ArrayList<ImageBoardThread> listImageBoardThreads;
    private ThreadRepository threadRepository;
    private MediaRepository mediaRepository;
    private boolean parseStatus = true;
    private boolean restartUpdate = false;
    private volatile ConcurrentLinkedDeque<TelegramPost> telegramPostArrayDeque = new ConcurrentLinkedDeque<>();

    private List<String> filterWords = Arrays.asList("WEBM");

    public Requests(RestTemplateBuilder restTemplateBuilder, ApplicationContext applicationContext,
            ThreadRepository threadRepository, MediaRepository mediaRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.threadRepository = threadRepository;
        this.mediaRepository = mediaRepository;
        webClient = WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
        .codecs(configurer -> configurer
                  .defaultCodecs()
                  .maxInMemorySize(16 * 1024 * 1024))
                .build()).baseUrl(host2ch).build();
        GetCatalog("b");
        // UpdateThreads();
        // CheckThreads();
    }

    private Predicate<Thread> threadFilter = thread -> {
        if (filterWords.size() > 0) {
            for (var word : filterWords) {
                if (Pattern.compile(Pattern.quote(word), Pattern.CASE_INSENSITIVE)
                        .matcher(thread.getSubject()).find())
                    return true;
            }
        } else {
            return true;
        }
        return false;
    };

    private Catalog GetCatalog(@NotNull String board) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{board}/catalog.json").build(board))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, ClientResponse::createException)
                .bodyToMono(Catalog.class).block();
    }

    private ThreadPosts GetThreadPosts(@NotNull String board, @NotNull String numThread) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{board}/res/{idThread}.json").build(board, numThread))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, ClientResponse::createException)
                .bodyToMono(ThreadPosts.class).block();
    }

    // private void UpdateThreads() {
    //     ArrayList<ImageBoardThread> currentThreads = GetListThreads();
    //     ArrayList<Long> idsRepo = threadRepository.findAllByIdThread();
    //     threadRepository.saveAll(
    //             currentThreads.stream().filter(e -> !idsRepo.contains(e.getIdThread())).collect(Collectors.toList()));
    //     List<Long> currentIdsThreads = currentThreads.stream().map(e1 -> e1.getIdThread()).collect(Collectors.toList());
    //     List<Long> toRemoveList = threadRepository.findAllByIdThread().stream().filter(e -> !currentIdsThreads.contains(e))
    //             .collect(Collectors.toList());
    //     threadRepository.deleteAllByIdInBatch(toRemoveList);
    //     listImageBoardThreads = threadRepository.findAll();

    // }

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
                        if (fileType.contains("mp4") || fileType.contains("webm")) {
                            if (mediaRepository.existsById(hash))
                                return;
                            String name = (String) file.get("name");
                            String fullName = (String) file.get("fullname");
                            var mediaFile = new MediaFile(hash, name, fullName);
                            mediaRepository.save(mediaFile);
                            imageBoardThread.addMediaFile(mediaFile);
                            urlFiles.add(host2ch + file.get("path"));

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


    private void CheckThreads() {
        CompletableFuture.runAsync(() -> {
            while (true) {

                if (parseStatus) {
                    restartUpdate = false;
                    listImageBoardThreads.stream()
                            .filter(e -> {
                                if (filterWords.size() > 0) {
                                    for (var word : filterWords) {
                                        if (Pattern.compile(Pattern.quote(word), Pattern.CASE_INSENSITIVE)
                                                .matcher(e.getTitle()).find())
                                            return true;
                                    }
                                } else if (filterWords.size() == 0) {
                                    return true;
                                }
                                return false;
                            })
                            .forEach(imageBoardThread -> {

                                if (parseStatus || !restartUpdate) {
                                    CheckThread(imageBoardThread);
                                    try {
                                        TimeUnit.SECONDS.sleep(4);
                                    } catch (InterruptedException e1) {
                                        e1.printStackTrace();
                                    }
                                } else {
                                    return;
                                }

                            });
                    try {
                        TimeUnit.SECONDS.sleep(15);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    //UpdateThreads();
                }
            }
        });
    }

    public ConcurrentLinkedDeque<TelegramPost> getTelegramPostArrayDeque() {
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

    public List<String> getFilterWords() {
        return filterWords;
    }

    public void setFilterWords(List<String> filterWords) {
        this.filterWords = filterWords;
        restartUpdate = true;
    }
}
