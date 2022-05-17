package com.x264.webmtotelegram.telegram;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import com.x264.webmtotelegram.entities.MediaFile;
import com.x264.webmtotelegram.imageboard.dvach.rest.Catalog;
import com.x264.webmtotelegram.imageboard.dvach.rest.Thread;
import com.x264.webmtotelegram.repositories.MediaRepository;
import com.x264.webmtotelegram.telegram.entities.TelegramPost;
import com.x264.webmtotelegram.telegram.entities.VideoThumbnail;
import com.x264.webmtotelegram.videoutils.Converter;
import com.x264.webmtotelegram.imageboard.dvach.Requests;
import com.x264.webmtotelegram.imageboard.dvach.URLBuilder;

import com.x264.webmtotelegram.videoutils.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class DvachBot extends TelegramLongPollingBot {
    private static final Logger log = LoggerFactory.getLogger(DvachBot.class);
    private String board = "b";
    private List<String> fileExtensions = Arrays.asList("webm", "mp4");
    private List<String> threadFilterWords = Arrays.asList("webm", "mp4", "тикток", "тик-ток");
    @Value("${bot.name}")
    private String username;
    @Value("${bot.token}")
    private String token;
    @Value("${bot.chatId}")
    private String chatId;
    final ApplicationContext applicationContext;
    final MediaRepository mediaRepository;
    private final Converter converter = new Converter();
    private final Requests dvachRequests;
    private boolean downloadsStatus = true;
    private boolean nowSetFilters = false;
    private CallbackQuery setFilterCallbackQuery;
    private final ConcurrentLinkedDeque<TelegramPost> telegramPostArrayDeque = new ConcurrentLinkedDeque<>();
    private boolean restartUpdate;

    public DvachBot(ApplicationContext applicationContext, MediaRepository mediaRepository, Requests dvachRequests) {
        this.applicationContext = applicationContext;
        this.mediaRepository = mediaRepository;
        this.dvachRequests = dvachRequests;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                var message = update.getMessage();
                if (message.isCommand()) {
                    var textMessage = message.getText();
                    if (textMessage.contains("Start")) {
                        execute(CommandHandlers.sendInlineKeyboardMainMenu(message.getChatId().toString()));
                        return;
                    }
                } else if (nowSetFilters) {
                    List<String> filterWords = Arrays.asList(message.getText().split(" "));
                    //dvachRequests.setFilterWords(new ArrayList<>(filterWords));
                    nowSetFilters = false;
                    DeleteMessage deleteMessage = new DeleteMessage(message.getChatId().toString(), message.getMessageId());
                    execute(deleteMessage);
                    //execute(CallbackHandlers.OnSettedFilter(setFilterCallbackQuery, dvachRequests.getFilterWords()));
                    return;
                }
                //When receiveing link to a thread, download it
                //TODO Refine the filter
                else if (message.getText().contains("2ch.hk")) {
                    //
                }

            }
            if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                String callbackCommand = callbackQuery.getData();

                if (callbackCommand.contains("StatusService"))
                    execute(CallbackHandlers.sendInlineKeyboardStatusService(callbackQuery));

                else if (callbackCommand.contains("mainMenu"))
                    execute(CallbackHandlers.ReturnToMainMenu(callbackQuery));

               else if (callbackCommand.contains("downloadAllThreads"))
                   execute(CallbackHandlers.DownloadSettingsMessage(callbackQuery, downloadsStatus));

                else if (callbackCommand.contains("filterSettings"))
                {
                    setFilterCallbackQuery = callbackQuery;
                    nowSetFilters = true;
                    execute(CallbackHandlers.filterSettingsMessage(callbackQuery, threadFilterWords));
                }

                // else if (callbackCommand.contains("listThreads"))
                //     execute(CallbackHandlers.ListThreadsMenu(callbackQuery,dvachRequests.getListImageBoardThreads()));

               else if (callbackCommand.contains("toggleDownload"))
               {
                   if (downloadsStatus)
                   {
                    toggleDownloadStatus(false);
                   }
                   else
                   {
                    toggleDownloadStatus(true);
                    startService();
                   }
                   execute(CallbackHandlers.DownloadSettingsMessage(callbackQuery, downloadsStatus));
               }
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void toggleDownloadStatus(boolean status)
    {
        downloadsStatus = status;
        if (!status)
            telegramPostArrayDeque.clear();
    }

    @PostConstruct
    private void startService() {
        getPosts();
        sentMessages();
    }

    private void getPosts() {
        CompletableFuture.runAsync(() ->
        {
            while (downloadsStatus) {
                    restartUpdate = false;
                    Catalog catalog = dvachRequests.getCatalog(board);
                    List<Thread> threads = catalog.getThreads();
                    threads.stream()
                            .filter(thread -> threadFilterWords.stream()
                                    .anyMatch(filterWord -> Pattern.compile(filterWord, Pattern.CASE_INSENSITIVE).matcher(thread.getSubject()).find()))
                            .forEach(thread ->
                            {
                                log.info("Check thread: {}", thread.getSubject());
                                dvachRequests.getThreadPosts("b", thread.getNum()).getPosts().stream()
                                        .forEach(post -> {
                                            post.getFiles()
                                                    .stream().filter(file ->fileExtensions.stream().anyMatch(extension -> file.getName().contains(extension)) && file.getSize() < 48800)
                                                    .forEach(file ->
                                                    {
                                                        var newPost = new TelegramPost(
                                                                file,
                                                                thread.getSubject(),
                                                                URLBuilder.buildPostURL(board, thread, post)
                                                        );

                                                        if (!mediaRepository.existsById(file.getMd5()))
                                                            mediaRepository.save(new MediaFile(file.getMd5(), file.getFullname()));

                                                        if (!telegramPostArrayDeque.contains(newPost))
                                                            telegramPostArrayDeque.addLast(newPost);
                                                    });
                                        });
                                log.info("Added new posts, current size {}", telegramPostArrayDeque.size());
                                try {
                                    TimeUnit.SECONDS.sleep(3);
                                } catch (InterruptedException e) {
                                    log.warn("Interrupted! {0}", e);
                                }
                            });
                    try {
                        TimeUnit.SECONDS.sleep(30);
                    } catch (InterruptedException e) {
                        log.warn("Interrupted! {0}", e);
                    }
                }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });

    }

    private void sentMessages() {
        CompletableFuture.runAsync(() -> {
            while (downloadsStatus) {
                    TelegramPost telegramPost;
                    telegramPost = telegramPostArrayDeque.peekFirst();
                    if (telegramPost != null) {
                        log.info("Post gotten");
                        VideoThumbnail videoThumbnail = telegramPost.getVideoThumbnail();
                        if (videoThumbnail.getUrlVideo().contains("webm")) {
                            File filePath = converter.convertWebmToMP4(videoThumbnail.getUrlVideo());
                            videoThumbnail.setUrlVideo(filePath.getAbsolutePath());
                        }
                        File thumbnail = Downloader.downloadFile(videoThumbnail.getUrlThumbnail());
                        videoThumbnail.setUrlThumbnail(thumbnail.getAbsolutePath());

                        log.info("Try send");
                        executeAsync(sendVideo(telegramPost)).exceptionally(e -> {
                            log.error(e.getMessage());
                            if (e.getMessage().contains("Too Many Requests"))
                                SleepBySecond(30);
                            return null;
                        }).join();
                        MediaFile mediaFile = mediaRepository.findById(videoThumbnail.getMd5()).get();
                        mediaFile.setUploaded(true);
                        mediaRepository.save(mediaFile);
                        telegramPostArrayDeque.remove(telegramPost);
                        if (!videoThumbnail.getUrlVideo().contains("http"))
                        {
                            try {
                                Files.delete(Path.of(videoThumbnail.getUrlVideo()));

                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        try {
                            Files.delete(Path.of(videoThumbnail.getUrlThumbnail()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        SleepBySecond(10);
                    }
                }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }

    // TODO Extract
    public static void SleepBySecond(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public SendVideo sendVideo(TelegramPost telegramPost) {
        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(chatId);
        InputFile inputVideo;
        var videoThumbnail = telegramPost.getVideoThumbnail();
        if (videoThumbnail.getUrlVideo().contains("http")) {
            inputVideo = new InputFile(videoThumbnail.getUrlVideo());
        } else {
            inputVideo = new InputFile(new File(videoThumbnail.getUrlVideo()));
        }
        sendVideo.setThumb(new InputFile(new File(videoThumbnail.getUrlThumbnail())));
        sendVideo.setDuration(videoThumbnail.getDurationSecs());
        sendVideo.setHeight(videoThumbnail.getHeight());
        sendVideo.setWidth(videoThumbnail.getWidth());
        sendVideo.setVideo(inputVideo);
        sendVideo.setSupportsStreaming(true);
        sendVideo.setParseMode(ParseMode.HTML);
        String caption = MessageFormat.format("<a href=\"{0}\">{1}</a>\n{2}",
                telegramPost.getMessageURL(),telegramPost.getThreadName(),videoThumbnail.getFilename());
        sendVideo.setCaption(caption);

        return sendVideo;
    }
}
