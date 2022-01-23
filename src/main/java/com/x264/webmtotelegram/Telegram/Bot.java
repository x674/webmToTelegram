package com.x264.webmtotelegram.Telegram;

import com.x264.webmtotelegram.ImageBoard.GetWebmFrom2ch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setText("link");
            messageEntity.setUrl("example.com");
            sendMessage.setEntities(Arrays.asList(messageEntity));
//            sendMessage.enableMarkdown(true);
//            //sendMessage.setParseMode("[inline URL](http://www.example.com/)");
            sendMessage.setText("new line");
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

    public ArrayDeque<Message> messageArrayDeque = new ArrayDeque<>();

    private CompletableFuture completableSentMessage = new CompletableFuture();

    //@Scheduled(initialDelay = 5000)
    private void AsyncSentMessages() {
        if (!messageArrayDeque.isEmpty() && completableSentMessage.isDone()) {
            completableSentMessage = executeAsync(sendMedia(messageArrayDeque.getLast()));
        }
    }

    private ArrayList<InputMedia> GetListInputMedia(List<String> listUrls) {
        ArrayList<InputMedia> mediaArrayList = new ArrayList<>();
        listUrls.forEach(urlVideo ->
                mediaArrayList.add(new InputMediaVideo(urlVideo)));
        return mediaArrayList;
    }

    public SendMediaGroup sendMedia(Message message) {
        var builder = SendMediaGroup.builder();
        builder.medias(GetListInputMedia(message.URLVideos));
        SendMediaGroup sendMediaGroup = builder.build();
        return sendMediaGroup;
    }
}
