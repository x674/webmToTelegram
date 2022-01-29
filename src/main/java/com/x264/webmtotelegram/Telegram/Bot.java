package com.x264.webmtotelegram.Telegram;

import com.x264.webmtotelegram.ImageBoard.GetWebmFrom2ch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayDeque;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class Bot extends TelegramLongPollingBot {
    final ApplicationArguments applicationArguments;
    private String username;
    private String token;
    private String chatId;
    final ApplicationContext applicationContext;

    public Bot(ApplicationContext applicationContext, ApplicationArguments applicationArguments) {
        this.applicationContext = applicationContext;
        this.applicationArguments = applicationArguments;
        token = applicationArguments.getOptionValues("bot.token").get(0);
        username = applicationArguments.getOptionValues("bot.name").get(0);
        chatId = applicationArguments.getOptionValues("bot.chatId").get(0);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    private static final Logger log = LoggerFactory.getLogger(GetWebmFrom2ch.class);

    @Override
    public void onUpdateReceived(Update update) {
        log.info(update.toString());
        GetWebmFrom2ch getWebmFrom2ch = applicationContext.getBean(GetWebmFrom2ch.class);
        if (update.hasChannelPost()) {
            getWebmFrom2ch.UpdateThreads();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("UpdateThreads started");
            sendMessage.setChatId(update.getChannelPost().getChatId().toString());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (update.hasMessage()) {
            getWebmFrom2ch.UpdateThreads();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(update.getMessage().getChatId().toString());
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayDeque<TelegramPost> telegramPostArrayDeque = new ArrayDeque<>();

    private CompletableFuture completableSentMessage;

    @Scheduled(fixedRate = 4000)
    public void AsyncSentMessages() {
        if (!telegramPostArrayDeque.isEmpty()) {
            TelegramPost telegramPost = telegramPostArrayDeque.getFirst();
            if (completableSentMessage == null) {
                if (telegramPost.URLVideos.size() == 1)
                    completableSentMessage = executeAsync(SendVideo(telegramPost));
                else if (telegramPost.URLVideos.size() > 1) {
                    List<Message> resultMessages;
                    try {
                        resultMessages = execute(SendMediaGroup(telegramPost));
                        log.warn(resultMessages.toString());
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                telegramPostArrayDeque.remove(telegramPost);
            } else if (completableSentMessage.isDone()) {
                if (telegramPost.URLVideos.size() == 1)
                    completableSentMessage = executeAsync(SendVideo(telegramPost));
                else if (telegramPost.URLVideos.size() > 1)
                    completableSentMessage = executeAsync(SendMediaGroup(telegramPost));
                telegramPostArrayDeque.remove(telegramPost);
            }
        }
    }

    public SendMediaGroup SendMediaGroup(TelegramPost telegramPost) {
        SendMediaGroup sendMediaGroup = new SendMediaGroup();
        sendMediaGroup.setChatId(chatId);
        List<InputMedia> listMedia = telegramPost.URLVideos.stream().map(e -> {
            var inputMediaVideo = new InputMediaVideo();
            inputMediaVideo.setSupportsStreaming(true);
            if (e.contains("http")) {
                inputMediaVideo.setMedia(e);
                return inputMediaVideo;
            } else {
                File mediaName = new File(e);
                inputMediaVideo = new InputMediaVideo();
                inputMediaVideo.setMedia(mediaName, mediaName.getName());
                return inputMediaVideo;
            }
        }).collect(Collectors.toList());
        listMedia.get(0).setCaption(telegramPost.MessageURL);
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
        sendVideo.setCaption(telegramPost.MessageURL);
        return sendVideo;
    }
}
