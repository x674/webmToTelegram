package com.x264.webmtotelegram.telegram;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    @Value("${dvach.threadFilterWords}")
    private List<String> threadFilterWords;
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
    private final BlockingDeque<TelegramPost> telegramPostArrayDeque = new LinkedBlockingDeque<>();
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
                    threadFilterWords = filterWords;
                    nowSetFilters = false;
                    DeleteMessage deleteMessage = new DeleteMessage(message.getChatId().toString(), message.getMessageId());
                    execute(deleteMessage);
                    execute(CallbackHandlers.OnSettedFilter(setFilterCallbackQuery, threadFilterWords));
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

                else if (callbackCommand.contains("filterSettings")) {
                    setFilterCallbackQuery = callbackQuery;
                    nowSetFilters = true;
                    execute(CallbackHandlers.filterSettingsMessage(callbackQuery, threadFilterWords));
                } else if (callbackCommand.contains("toggleDownload")) {
                    if (downloadsStatus) {
                        toggleDownloadStatus(false);
                        log.info("Stopped downloading");
                    } else {
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

    private void toggleDownloadStatus(boolean status) {
        downloadsStatus = status;
        if (!status)
            telegramPostArrayDeque.clear();
    }

    @PostConstruct
    private void startService() {
        getPosts();
        sentMessages();
        log.info("Resume downloadings");
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
                            if (downloadsStatus) {
                                log.info("Check thread: {}", thread.getSubject());
                                dvachRequests.getThreadPosts("b", thread.getNum()).getPosts().stream()
                                        .forEach(post -> {
                                            post.getFiles()
                                                    .stream().filter(file -> fileExtensions.stream().anyMatch(extension -> file.getName().contains(extension)) && file.getSize() < 48800)
                                                    .forEach(file ->
                                                    {
                                                        var newPost = new TelegramPost(
                                                                file,
                                                                thread.getSubject(),
                                                                URLBuilder.buildPostURL(board, thread, post)
                                                        );

                                                        if (!mediaRepository.existsById(file.getMd5()) && !telegramPostArrayDeque.contains(newPost)) {
                                                            mediaRepository.save(new MediaFile(file.getMd5(), file.getFullname()));
                                                            telegramPostArrayDeque.addLast(newPost);
                                                            return;
                                                        }

                                                        if (mediaRepository.existsById(file.getMd5())) {
                                                            var media = mediaRepository.findById(file.getMd5()).get();
                                                            if (!media.isUploaded() && !telegramPostArrayDeque.contains(newPost)) {
                                                                telegramPostArrayDeque.addLast(newPost);
                                                            }
                                                        }
                                                    });
                                        });
                                log.info("Added new posts, current size {}", telegramPostArrayDeque.size());
                                try {
                                    TimeUnit.SECONDS.sleep(3);
                                } catch (InterruptedException e) {
                                    log.warn("Interrupted! {0}", e);
                                }
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
            int retryCount = 0;
            TelegramPost telegramPost = null;
            while (downloadsStatus) {
                while (retryCount < 3) {
                    if (Objects.isNull(telegramPost)) {
                        try {
                            telegramPost = telegramPostArrayDeque.takeFirst();
                            log.info("Post gotten");
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    VideoThumbnail videoThumbnail = telegramPost.getVideoThumbnail();
                    if (videoThumbnail.getUrlVideo().endsWith(".webm") ||
                            (videoThumbnail.getUrlVideo().contains("http") && converter.CheckFileCodecs(videoThumbnail.getUrlVideo()))) {
                        Optional<File> convertedFile = converter.convertWebmToMP4(videoThumbnail.getUrlVideo());
                        if (convertedFile.isPresent()) {
                            File filePath = convertedFile.get();
                            videoThumbnail.setUrlVideo(filePath.getAbsolutePath());
                        } else {
                            log.error("Convertion failed");
                            retryCount++;
                            break;
                        }

                    }
                    if (videoThumbnail.getUrlThumbnail().contains("http")) {
                        Optional<File> thumbnail = Downloader.downloadFile(videoThumbnail.getUrlThumbnail());
                        if (thumbnail.isPresent()) {
                            videoThumbnail.setUrlThumbnail(thumbnail.get().getAbsolutePath());
                        } else {
                            log.error("Thumbnail not found");
                            retryCount++;
                            break;
                        }
                    }
                    log.info("Try send");
                    try {
                        SendVideo sendVideo = sendVideo(telegramPost);
                        execute(sendVideo);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                        if (e.getMessage().contains("Too Many Requests"))
                            SleepBySecond(30);
                        retryCount++;
                        break;
                    }
                    MediaFile mediaFile = mediaRepository.findById(videoThumbnail.getMd5()).get();
                    mediaFile.setUploaded(true);
                    mediaRepository.save(mediaFile);
                    RemoveTempFiles(videoThumbnail);

                    retryCount = 0;
                    telegramPost = null;
                    SleepBySecond(10);
                }
                if (retryCount >= 3) {
                    RemoveTempFiles(telegramPost.getVideoThumbnail());
                    telegramPost = null;
                    retryCount = 0;
                }
            }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }

    private void RemoveTempFiles(VideoThumbnail videoThumbnail) {
        if (!videoThumbnail.getUrlVideo().contains("http")) {
            try {
                Files.delete(Path.of(videoThumbnail.getUrlVideo()));

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        try {
            if (Files.exists(Path.of(videoThumbnail.getUrlThumbnail())))
                Files.delete(Path.of(videoThumbnail.getUrlThumbnail()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        sendVideo.setVideo(inputVideo);
        sendVideo.setThumb(new InputFile(new File(videoThumbnail.getUrlThumbnail())));
        sendVideo.setDuration(videoThumbnail.getDurationSecs());
        sendVideo.setHeight(videoThumbnail.getHeight());
        sendVideo.setWidth(videoThumbnail.getWidth());

        sendVideo.setSupportsStreaming(true);
        sendVideo.setParseMode(ParseMode.HTML);
        String caption = MessageFormat.format("<a href=\"{0}\">{1}</a>\n{2}",
                telegramPost.getMessageURL(), telegramPost.getThreadName(), videoThumbnail.getFilename());
        sendVideo.setCaption(caption);

        return sendVideo;
    }
}
