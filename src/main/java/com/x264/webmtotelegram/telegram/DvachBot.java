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
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

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
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;
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
    private boolean parseStatus = true;
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
                var callbackQuery = update.getCallbackQuery();
                var callbackCommand = callbackQuery.getData();

                if (callbackCommand.contains("StatusService"))
                    execute(CallbackHandlers.sendInlineKeyboardStatusService(callbackQuery));

                else if (callbackCommand.contains("mainMenu"))
                    execute(CallbackHandlers.ReturnToMainMenu(callbackQuery));

//                else if (callbackCommand.contains("downloadAllThreads"))
//                    execute(CallbackHandlers.DownloadSettingsMessage(callbackQuery, isDownloadsStatus()));

                // else if (callbackCommand.contains("filterSettings"))
                // {
                //     setFilterCallbackQuery = callbackQuery;
                //     nowSetFilters = true;
                //     execute(CallbackHandlers.FilterSettingsMessage(callbackQuery, dvachRequests.getFilterWords()));
                // }

                // else if (callbackCommand.contains("listThreads"))
                //     execute(CallbackHandlers.ListThreadsMenu(callbackQuery,dvachRequests.getListImageBoardThreads()));

//                else if (callbackCommand.contains("toggleDownload"))
//                {
//                    if (isDownloadsStatus())
//                    {
//                        setDownloadsStatus(false);
//                        //applicationContext.getBean(Requests.class).setParseStatus(false);
//                    }
//                    else if (!isDownloadsStatus())
//                    {
//                        setDownloadsStatus(true);
//                        //applicationContext.getBean(Requests.class).setParseStatus(true);
//                    }
//                    execute(CallbackHandlers.DownloadSettingsMessage(callbackQuery, isDownloadsStatus()));
//                }
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    private void startBot() {
        getPosts();
        sentMessages();
    }

    private void getPosts() {
        CompletableFuture.runAsync(() ->
        {
            if (parseStatus) {
                restartUpdate = false;
                dvachRequests.getCatalog(board).getThreads().stream()
                        .filter(thread -> threadFilterWords.stream()
                                .anyMatch(filterWord -> Pattern.compile(filterWord, Pattern.CASE_INSENSITIVE).matcher(thread.getSubject()).find()))
                        .forEach(thread ->
                        {
                            log.info("Check thread: {}", thread.getSubject());
                            dvachRequests.getThreadPosts("b", thread.getNum()).getPosts().stream()
                                    .filter(post -> post.getFiles().stream().anyMatch(file -> fileExtensions.stream().anyMatch(extension -> file.getFullname().contains(extension))))
                                    .forEach(post -> {
                                        var newPost = new TelegramPost(
                                                post.getFiles(),
                                                thread.getSubject(),
                                                URLBuilder.buildPostURL(board, thread, post)
                                        );
                                        telegramPostArrayDeque.addLast(newPost);

                                    });
                            log.info("Added new posts, current size {}", telegramPostArrayDeque.size());
                            try {
                                TimeUnit.SECONDS.sleep(3);
                            } catch (InterruptedException e) {
                                log.warn("Interrupted! {0}", e);
                                Thread.currentThread().interrupt();
                            }
                        });
                try {
                    TimeUnit.SECONDS.sleep(12);
                } catch (InterruptedException e) {
                    log.warn("Interrupted! {0}", e);
                    Thread.currentThread().interrupt();
                }
            }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }

    private void sentMessages() {
        CompletableFuture.runAsync(() -> {
            while (true) {
                if (downloadsStatus) {
                    TelegramPost telegramPost = null;
                    telegramPost = telegramPostArrayDeque.peekFirst();
                    if (telegramPost != null){
                    log.info("Post gotten");
                    for (int index = 0; index < telegramPost.getVideoThumbnails().size(); index++) {
                        if (telegramPost.getVideoThumbnails().get(index).getUrlVideo().contains("webm")) {
                            File filePath = converter.convertWebmToMP4(telegramPost.getVideoThumbnails().get(index).getUrlVideo());
                            String thumbnail = Downloader.DownloadFile(telegramPost.getVideoThumbnails().get(index).getUrlThumbnail());
                            telegramPost.getVideoThumbnails().get(index).setUrlVideo(filePath.getAbsolutePath());
                            telegramPost.getVideoThumbnails().get(index).setUrlThumbnail(thumbnail);
                        }
                    }

                        if (telegramPost.getVideoThumbnails().size() == 1) {
                            log.info("Length urls == 1, try send");
                            executeAsync(sendVideo(telegramPost)).exceptionally(e -> {
                                log.error(e.getMessage());
                                if (e.getMessage().contains("Too Many Requests"))
                                    SleepBySecond(30);
                                return null;
                            }).join();
                        } else if (telegramPost.getVideoThumbnails().size() > 1) {
                            log.info("Length urls == {}, try send", telegramPost.getVideoThumbnails().size());
                            executeAsync(SendMediaGroup(telegramPost)).exceptionally(e -> {
                                log.error(e.getMessage());
                                if (e.getMessage().contains("Too Many Requests"))
                                    SleepBySecond(30);
                                return null;
                            }).join();
                        }
                        telegramPostArrayDeque.remove(telegramPost);
                        telegramPost.getVideoThumbnails().forEach(videoThumbnail -> {
                            if (!videoThumbnail.getUrlVideo().contains("http"))
                                try {
                                    Files.delete(Path.of(videoThumbnail.getUrlVideo()));
                                    Files.delete(Path.of(videoThumbnail.getUrlThumbnail()));
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                        });
                        SleepBySecond(10);
                    }
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

    public SendMediaGroup SendMediaGroup(TelegramPost telegramPost) {
        SendMediaGroup sendMediaGroup = new SendMediaGroup();
        sendMediaGroup.setChatId(chatId);
        List<InputMedia> listMedia = telegramPost.getVideoThumbnails().stream().map(videoThumbnail -> {
            var inputMediaVideo = new InputMediaVideo();
            if (videoThumbnail.getUrlVideo().contains("http")) {
                inputMediaVideo.setMedia(videoThumbnail.getUrlVideo());
            } else {
                File mediaName = new File(videoThumbnail.getUrlVideo());
                inputMediaVideo = new InputMediaVideo();
                inputMediaVideo.setMedia(mediaName, mediaName.getName());
            }
            inputMediaVideo.setThumb(new InputFile(new File(videoThumbnail.getUrlThumbnail())));
            inputMediaVideo.setDuration(videoThumbnail.getDurationSecs());
            inputMediaVideo.setHeight(videoThumbnail.getHeight());
            inputMediaVideo.setWidth(videoThumbnail.getWidth());
            inputMediaVideo.setMediaName(videoThumbnail.getFilename());
            inputMediaVideo.setSupportsStreaming(true);
            return inputMediaVideo;
        }).collect(Collectors.toList());
        listMedia.get(0).setParseMode(ParseMode.HTML);
        String caption = MessageFormat.format("<a href=\"{0}\">{1}</a>\n{2}",telegramPost.getMessageURL(),telegramPost.getThreadName(),
                telegramPost.getVideoThumbnails().stream().map(VideoThumbnail::getFilename).collect(Collectors.joining("\n")));
        listMedia.get(0).setCaption(caption);
        sendMediaGroup.setMedias(listMedia);
        return sendMediaGroup;
    }

    public SendVideo sendVideo(TelegramPost telegramPost) {
        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(chatId);
        InputFile inputVideo;
        var videoThumbnail = telegramPost.getVideoThumbnails().get(0);
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
