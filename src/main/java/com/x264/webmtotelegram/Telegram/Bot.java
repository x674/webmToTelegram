package com.x264.webmtotelegram.Telegram;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.x264.webmtotelegram.Entities.TelegramPost;
import com.x264.webmtotelegram.ImageBoard.Dvach;
import com.x264.webmtotelegram.Repositories.MediaRepository;
import com.x264.webmtotelegram.VideoUtils.Converter;

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
public class Bot extends TelegramLongPollingBot {
    private static final Logger log = LoggerFactory.getLogger(Bot.class);
    @Value("${bot.name}")
    private String username;
    @Value("${bot.token}")
    private String token;
    @Value("${bot.chatId}")
    private String chatId;
    final ApplicationContext applicationContext;
    final MediaRepository mediaRepository;
    private Converter converter;
    private Dvach dvach;
    private boolean downloadsStatus = true;
    private boolean nowSetFilters = false;
    private CallbackQuery setFilterCallbackQuery;
    private ConcurrentLinkedDeque<TelegramPost> telegramPostArrayDeque;

    public Bot(ApplicationContext applicationContext, MediaRepository mediaRepository, Converter converter, Dvach dvach) {
        this.applicationContext = applicationContext;
        this.mediaRepository = mediaRepository;
        this.dvach = dvach;
        this.converter = converter;
        telegramPostArrayDeque = dvach.getTelegramPostArrayDeque();
        AsyncSentMessages();
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
                }
                if (nowSetFilters)
                {
                    List<String> filterWords = Arrays.asList(message.getText().split(" "));
                    dvach.setThreadFilter(new ArrayList<>(filterWords));
                    nowSetFilters = false;
                    DeleteMessage deleteMessage = new DeleteMessage(message.getChatId().toString(),message.getMessageId());
                    execute(deleteMessage);
                    execute(CallbackHandlers.OnSettedFilter(setFilterCallbackQuery, dvach.getThreadFilter()));
                    return;
                }
            }
            if (update.hasCallbackQuery()) {
                var callbackQuery = update.getCallbackQuery();
                var callbackCommand = callbackQuery.getData();

                if (callbackCommand.contains("StatusService"))
                    execute(CallbackHandlers.sendInlineKeyboardStatusService(callbackQuery));

                else if (callbackCommand.contains("mainMenu"))
                    execute(CallbackHandlers.ReturnToMainMenu(callbackQuery));

                else if (callbackCommand.contains("downloadAllThreads"))
                    execute(CallbackHandlers.DownloadSettingsMessage(callbackQuery, isDownloadsStatus()));

                else if (callbackCommand.contains("filterSettings"))
                {
                    setFilterCallbackQuery = callbackQuery;
                    nowSetFilters = true;
                    execute(CallbackHandlers.FilterSettingsMessage(callbackQuery, dvach.getThreadFilter()));
                }

                else if (callbackCommand.contains("listThreads"))
                    execute(CallbackHandlers.ListThreadsMenu(callbackQuery,dvach.getListImageBoardThreads()));

                else if (callbackCommand.contains("toggleDownload"))
                {
                    if (isDownloadsStatus())
                    {
                        setDownloadsStatus(false);
                        applicationContext.getBean(Dvach.class).setParseStatus(false);
                    }
                    else if (!isDownloadsStatus())
                    {
                        setDownloadsStatus(true);
                        applicationContext.getBean(Dvach.class).setParseStatus(true);
                    }
                    execute(CallbackHandlers.DownloadSettingsMessage(callbackQuery, isDownloadsStatus()));
                }
            }
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void AsyncSentMessages() {
        CompletableFuture.runAsync(() -> {
            while (true) {
                if (downloadsStatus) {
                    if (!telegramPostArrayDeque.isEmpty()) {
                        TelegramPost telegramPost = telegramPostArrayDeque.getFirst();
                        for (int index = 0; index < telegramPost.URLVideos.size(); index++) {
                            if (telegramPost.URLVideos.get(index).contains("webm")) {
                                File filePath = converter.ConvertWebmToMP4(telegramPost.URLVideos.get(index));
                                telegramPost.URLVideos.set(index, filePath.getAbsolutePath());
                            }
                        }

                        if (telegramPost.URLVideos.size() == 1)
                            executeAsync(SendVideo(telegramPost)).exceptionally(e -> {
                                log.error(e.getMessage());
                                if (e.getMessage().contains("Too Many Requests"))
                                    SleepBySecond(30);
                                return null;
                            }).join();
                        else if (telegramPost.URLVideos.size() > 1) {
                            executeAsync(SendMediaGroup(telegramPost)).exceptionally(e -> {
                                log.error(e.getMessage());
                                if (e.getMessage().contains("Too Many Requests"))
                                    SleepBySecond(30);
                                return null;
                            }).join();
                        }
                        telegramPostArrayDeque.remove(telegramPost);
                        telegramPost.URLVideos.forEach(e -> {
                            if (!e.contains("http"))
                                try {
                                    Files.delete(Path.of(e));
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                        });
                        SleepBySecond(10);
                    }
                }
            }
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
        List<InputMedia> listMedia = telegramPost.URLVideos.stream().map(e -> {
            var inputMediaVideo = new InputMediaVideo();
            if (e.contains("http")) {
                inputMediaVideo.setMedia(e);
            } else {
                File mediaName = new File(e);
                inputMediaVideo = new InputMediaVideo();
                inputMediaVideo.setMedia(mediaName, mediaName.getName());
            }
            inputMediaVideo.setSupportsStreaming(true);
            return inputMediaVideo;
        }).collect(Collectors.toList());
        listMedia.get(0).setParseMode(ParseMode.HTML);
        String caption = "<a href=\"" + telegramPost.MessageURL + "\">" + telegramPost.ThreadName + "</a>";
        listMedia.get(0).setCaption(caption);
        sendMediaGroup.setMedias(listMedia);
        return sendMediaGroup;
    }

    public SendVideo SendVideo(TelegramPost telegramPost) {
        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(chatId);
        InputFile inputVideo;
        var urlVideo = telegramPost.URLVideos.get(0);
        if (urlVideo.contains("http")) {
            inputVideo = new InputFile(urlVideo);
        } else {
            inputVideo = new InputFile(new File(urlVideo));
        }
        sendVideo.setVideo(inputVideo);
        sendVideo.setSupportsStreaming(true);
        sendVideo.setParseMode(ParseMode.HTML);
        String caption = "<a href=\"" + telegramPost.MessageURL + "\">" + telegramPost.ThreadName + "</a>";
        sendVideo.setCaption(caption);

        return sendVideo;
    }

    public SendVideo SendVideoByFile_id(TelegramPost telegramPost) {
        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(chatId);
        InputFile inputVideo;
        var urlVideo = telegramPost.URLVideos.get(0);
        if (urlVideo.contains("http")) {
            inputVideo = new InputFile(urlVideo);
        } else {
            inputVideo = new InputFile(new File(urlVideo));
        }
        var ee = new InputFile();
        sendVideo.setVideo(inputVideo);
        sendVideo.setSupportsStreaming(true);
        sendVideo.setCaption(telegramPost.MessageURL);
        return sendVideo;
    }

    /**
     * @return String return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return String return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return boolean return the dowloadsStatus
     */
    public boolean isDownloadsStatus() {
        return downloadsStatus;
    }

    /**
     * @param downloadsStatus the dowloadsStatus to set
     */
    public void setDownloadsStatus(boolean downloadsStatus) {
        this.downloadsStatus = downloadsStatus;
    }

}
