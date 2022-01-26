package com.x264.webmtotelegram.ImageBoard;

import com.x264.webmtotelegram.Telegram.Bot;
import com.x264.webmtotelegram.Telegram.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class GetWebmFrom2ch {
    private final ApplicationContext applicationContext;
    private final RestTemplate restTemplate;
    private static final String host2ch = "https://2ch.hk";
    private static final Logger log = LoggerFactory.getLogger(GetWebmFrom2ch.class);
    private Bot botBean;
    private Converter converter;
    private ArrayList<Thread> ListThreads = new ArrayList<>();

    public GetWebmFrom2ch(RestTemplateBuilder restTemplateBuilder, ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        botBean = (Bot) applicationContext.getBean("bot");
        converter = (Converter) applicationContext.getBean("converter");
        restTemplate = restTemplateBuilder.build();

    }

    @PostConstruct
    public ArrayList<Thread> GetListThreads() {
        LinkedHashMap catalog = this.restTemplate.getForObject(host2ch + "/b/catalog.json", LinkedHashMap.class);
        ArrayList<LinkedHashMap> arrayThreads = (ArrayList) catalog.get("threads");
        arrayThreads.forEach(e -> {
            Thread thread = new Thread();
            thread.setIdThread(Long.parseLong((String) e.get("num")));
            thread.setTitle((String) e.get("subject"));
            ListThreads.add(thread);
        });
        return ListThreads;
    }

    public void CheckThread(Thread thread) {
        LinkedHashMap threadJson;
        try {
            threadJson = restTemplate.getForObject(host2ch + "/b/res/" + thread.getIdThread() + ".json", LinkedHashMap.class);
        } catch (HttpClientErrorException.NotFound exception) {
            log.warn("404 " + exception);
            ListThreads.remove(thread);
            return;
        }
        //Threads->Posts
        ArrayList<LinkedHashMap> threads = (ArrayList<LinkedHashMap>) threadJson.get("threads");
        LinkedHashMap to1 = threads.get(0);
        ArrayList<LinkedHashMap> posts = (ArrayList<LinkedHashMap>) to1.get("posts");
        posts.forEach(
                post -> {
                    ArrayList<String> urlFiles = new ArrayList<>();
                    int numMessage = (int) post.get("num");
                    if (numMessage > thread.getLastCheckedMessage()) {
                        ArrayList<LinkedHashMap> filesList = (ArrayList<LinkedHashMap>) post.get("files");
                        filesList.forEach(file -> {
                            //check that this a video file
                            //type mp4 - 10, webm - 6

                            int fileType = (int) file.get("type");
                            switch (fileType){
                                case 6:
                                {
                                    //convert webm to mp4
                                    var filePath = converter.ConvertWebmToMP4(host2ch + file.get("path"));
                                    urlFiles.add(filePath);

                                }
                                //mp4
                                case 10:
                                {
                                    urlFiles.add(host2ch + file.get("path"));
                                }
                            }
                        });


                    }

                    if (!urlFiles.isEmpty()) {
                        Message message = new Message();
                        message.ThreadName = thread.getTitle();
                        message.MessageURL = host2ch + "/b/res/" + thread.getIdThread() + ".html" + "#" + numMessage;
                        message.URLVideos = urlFiles;
                        botBean.messageArrayDeque.add(message);
                    }
                }
        );
    }

    public void Stop() {

    }
    //@Scheduled(fixedRate = 10 * 60000)
    //@PostConstruct
    public void UpdateThreads() {
        ListThreads.forEach(thread -> {
            CheckThread(thread);
            try {
                TimeUnit.SECONDS.sleep(90);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
