package com.x264.webmtotelegram.telegram;

import com.x264.webmtotelegram.DvachProperties;
import com.x264.webmtotelegram.entities.MediaFile;
import com.x264.webmtotelegram.imageboard.dvach.Requests;
import com.x264.webmtotelegram.imageboard.dvach.URLBuilder;
import com.x264.webmtotelegram.imageboard.dvach.rest.Thread;
import com.x264.webmtotelegram.repositories.MediaRepository;
import com.x264.webmtotelegram.telegram.entities.TelegramPost;
import com.x264.webmtotelegram.telegram.entities.VideoThumbnail;
import com.x264.webmtotelegram.videoutils.Converter;
import com.x264.webmtotelegram.videoutils.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ws.schild.jave.EncoderException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Component
public class DvachBot extends TelegramLongPollingBot {
    private static final Logger log = LoggerFactory.getLogger(DvachBot.class);
    private final List<String> fileExtensions = Arrays.asList("webm", "mp4", "mov");
    private final DvachProperties dvachProperties;
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

    public DvachBot(ApplicationContext applicationContext, MediaRepository mediaRepository, Requests dvachRequests,
                    DvachProperties dvachProperties) {
        this.applicationContext = applicationContext;
        this.mediaRepository = mediaRepository;
        this.dvachRequests = dvachRequests;
        this.dvachProperties = dvachProperties;
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
                    // threadFilterWords = filterWords;
                    nowSetFilters = false;
                    DeleteMessage deleteMessage = new DeleteMessage(message.getChatId().toString(),
                            message.getMessageId());
                    execute(deleteMessage);
                    // execute(CallbackHandlers.OnSettedFilter(setFilterCallbackQuery,
                    // threadFilterWords));
                    return;
                }
                // When receiveing link to a thread, download it
                // TODO Refine the filter
                else if (message.getText().contains("2ch.hk")) {
                    //
                }

            }
            else if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                String callbackCommand = callbackQuery.getData();

                if (callbackCommand.contains("StatusService"))
                    execute(CallbackHandlers.sendInlineKeyboardStatusService(callbackQuery, downloadsStatus, telegramPostArrayDeque.size()));

                else if (callbackCommand.contains("mainMenu"))
                    execute(CallbackHandlers.ReturnToMainMenu(callbackQuery));

                else if (callbackCommand.contains("downloadAllThreads"))
                    execute(CallbackHandlers.DownloadSettingsMessage(callbackQuery, downloadsStatus));

                else if (callbackCommand.contains("filterSettings")) {
                    setFilterCallbackQuery = callbackQuery;
                    nowSetFilters = true;
                    // execute(CallbackHandlers.filterSettingsMessage(callbackQuery,
                    // threadFilterWords));
                } else if (callbackCommand.contains("toggleDownload")) {
                    if (downloadsStatus) {
                        toggleDownloadStatus(false);
                        SleepBySecond(2);
                        removeTempFiles();
                        log.info("Stopped downloading");
                    } else {
                        toggleDownloadStatus(true);
                        startService();
                    }
                    execute(CallbackHandlers.DownloadSettingsMessage(callbackQuery, downloadsStatus));
                }
            } else if (update.hasMyChatMember()) {
                ChatMemberUpdated chatMember = update.getMyChatMember();
                if (chatMember.getNewChatMember().getStatus().equals("administrator")) {
                    execute(new SendMessage(chatMember.getChat().getId().toString(),
                            MessageFormat.format("Welcome to \"{0}\"\nChat ID: {1}", chatMember.getChat().getTitle(), chatMember.getChat().getId().toString())));
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
        CompletableFuture.runAsync(() -> {
            while (downloadsStatus) {
                try {
                    restartUpdate = false;
                    dvachProperties.getBoards().keySet().forEach(board -> {
                        var catalog = dvachRequests.getCatalog(board);
                        if (catalog.isPresent()) {
                            List<Thread> threads = catalog.get().getThreads();
                            threads.stream()
                                    .filter(thread -> {
                                        if (!dvachProperties.getBoards().get(board).getFilterWords().isEmpty()) {
                                            return dvachProperties.getBoards().get(board).getFilterWords().stream()
                                                    .anyMatch(
                                                            filterWord -> Pattern
                                                                    .compile(filterWord, Pattern.CASE_INSENSITIVE)
                                                                    .matcher(thread.getSubject()).find());
                                        } else
                                            return true;
                                    })
                                    .forEach(thread -> {
                                        if (downloadsStatus) {
                                            log.info("Check thread: {}", thread.getSubject());
                                            var threadPosts = dvachRequests.getThreadPosts(board, thread.getNum());
                                            if (threadPosts.isPresent())
                                                threadPosts.get().getPosts().stream()
                                                        .forEach(post -> {
                                                            post.getFiles()
                                                                    .stream()
                                                                    .filter(file -> fileExtensions.stream()
                                                                            .anyMatch(extension -> file.getName()
                                                                                    .toLowerCase().endsWith(extension))
                                                                            && file.getSize() < 100000)
                                                                    .forEach(file -> {
                                                                        var newPost = new TelegramPost(
                                                                                file,
                                                                                thread.getSubject(),
                                                                                URLBuilder.buildPostURL(board, thread, post));

                                                                        if (!mediaRepository.existsById(file.getMd5())
                                                                                && !telegramPostArrayDeque.contains(newPost)) {
                                                                            mediaRepository.save(new MediaFile(file.getMd5(),
                                                                                    file.getFullname()));
                                                                            return;
                                                                        }

                                                                        if (mediaRepository.existsById(file.getMd5())) {
                                                                            var media = mediaRepository.findById(file.getMd5())
                                                                                    .get();
                                                                            if (!media.isUploaded() && !media.isBadFile()
                                                                                    && !telegramPostArrayDeque
                                                                                    .contains(newPost)) {
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
                    });

                } catch (Exception e) {
                    // TODO ignore bad items
                    e.printStackTrace();
                }
            }
        });
    }

    private void sentMessages() {
        CompletableFuture.runAsync(() -> {
            int retryCount = 0;
            TelegramPost telegramPost = null;
            MediaFile mediaFile = null;
            VideoThumbnail videoThumbnail = null;
            while (downloadsStatus) {
                while (retryCount < 3) {
                    if (Objects.isNull(telegramPost)) {
                        try {
                            telegramPost = telegramPostArrayDeque.takeFirst();
                        } catch (InterruptedException e) {
                            log.warn("Interrupted arrayDeque");
                        }
                        videoThumbnail = telegramPost.getVideoThumbnail();
                        mediaFile = mediaRepository.findById(videoThumbnail.getMd5()).get();
                        log.info("Post gotten");
                    }

                    if (videoThumbnail.getUrlVideo().startsWith("http")) {

                        if (videoThumbnail.getUrlVideo().endsWith(".webm")) {
                            try {
                                File convertedFile = converter.convertWebmToMP4(videoThumbnail.getUrlVideo());
                                videoThumbnail.setUrlVideo(convertedFile.getAbsolutePath());
                            } catch (IllegalArgumentException | IOException | EncoderException e) {
                                log.error("Convertion failed");
                                e.printStackTrace();
                                retryCount++;
                                break;
                            }

                        } else if (videoThumbnail.getUrlVideo().endsWith(".mp4")) {
                            try {
                                boolean correctCodec = converter.CheckFileCodecs(videoThumbnail.getUrlVideo());
                                if (!correctCodec) {
                                    File convertedFile = converter.convertWebmToMP4(videoThumbnail.getUrlVideo());
                                    videoThumbnail.setUrlVideo(convertedFile.getAbsolutePath());
                                }
                            } catch (EncoderException | IllegalArgumentException | IOException e) {
                                log.error("Convertion failed");
                                e.printStackTrace();
                                retryCount++;
                                break;
                            }
                        }
                    }
                    if (videoThumbnail.getUrlThumbnail().contains("http")) {
                        File thumbnail;
                        try {
                            thumbnail = Downloader.downloadFile(videoThumbnail.getUrlThumbnail());
                        } catch (IOException e) {
                            log.error("Thumbnail download failed");
                            e.printStackTrace();
                            retryCount++;
                            break;
                        }
                        videoThumbnail.setUrlThumbnail(thumbnail.getAbsolutePath());
                        
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
                    mediaFile.setUploaded(true);
                    mediaRepository.save(mediaFile);
                    removeFilesFromVideoThumbnails(videoThumbnail);

                    retryCount = 0;
                    telegramPost = null;
                    SleepBySecond(10);
                }
                if (retryCount >= 3) {
                    mediaFile.setBadFile(true);
                    mediaRepository.save(mediaFile);
                    removeFilesFromVideoThumbnails(telegramPost.getVideoThumbnail());
                    telegramPost = null;
                    retryCount = 0;
                }

            }
        });
    }

    private void removeFilesFromVideoThumbnails(VideoThumbnail videoThumbnail) {
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

    @PreDestroy
    private void removeTempFiles() {

        File file = new File(System.getProperty("user.dir"));
        File[] listFiles = file.listFiles((dir, name) -> name.endsWith("jpg") || name.endsWith("mp4"));
        for (File f : listFiles)
            f.delete();
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
        if (videoThumbnail.getDurationSecs().isPresent())
            sendVideo.setDuration(videoThumbnail.getDurationSecs().get());

        sendVideo.setSupportsStreaming(true);
        sendVideo.setParseMode(ParseMode.HTML);
        String caption = MessageFormat.format("<a href=\"{0}\">{1}</a>\n{2}",
                telegramPost.getMessageURL(), telegramPost.getThreadName(), videoThumbnail.getFilename());
        sendVideo.setCaption(caption);

        return sendVideo;
    }
}
