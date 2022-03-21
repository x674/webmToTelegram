package com.x264.webmtotelegram.Telegram;

import com.x264.webmtotelegram.Entities.TelegramPost;
import com.x264.webmtotelegram.ImageBoard.GetWebmFrom2ch;
import com.x264.webmtotelegram.Repositories.MediaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class Bot extends TelegramLongPollingBot {
    private static final Logger log = LoggerFactory.getLogger(GetWebmFrom2ch.class);
    @Value("${bot.name}")
    private String username;
    @Value("${bot.token}")
    private String token;
    @Value("${bot.chatId}")
    private String chatId;
    final ApplicationContext applicationContext;
    final MediaRepository mediaRepository;

    public Bot(ApplicationContext applicationContext, MediaRepository mediaRepository) {
        this.applicationContext = applicationContext;
        this.mediaRepository = mediaRepository;
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
        if (update.hasMessage())
        {
            var message = update.getMessage();
            if (message.isCommand())
            {
                var textMessage = message.getText();
                if (textMessage.contains("Start"))
                    sendInlineKeyboardMainMenu(message.getChatId().toString());
            }
        }
        if (update.hasCallbackQuery())
        {
            var callbackQuery = update.getCallbackQuery();
            var callbackCommand = callbackQuery.getData();

            if (callbackCommand.contains("StatusService"))
                sendInlineKeyboardStatusService(callbackQuery.getMessage());

            else if (callbackCommand.contains("mainMenu"))
                sendInlineKeyboardMainMenu(callbackQuery.getMessage().getChatId().toString());

        }
    }

    public void sendCustomKeyboard(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Custom message text");

        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("Row 1 Button 1");
        row.add("Row 1 Button 2");
        row.add("Row 1 Button 3");
        // Add the first row to the keyboard
        keyboard.add(row);
        // Create another keyboard row
        row = new KeyboardRow();
        // Set each button for the second line
        row.add("Row 2 Button 1");
        row.add("Row 2 Button 2");
        row.add("Row 2 Button 3");
        // Add the second row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(keyboardMarkup);

        try {
            // Send the message
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendInlineKeyboardMainMenu(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Главное меню");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        //Vertical rows
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();

        InlineKeyboardButton statusService = new InlineKeyboardButton("1. Статус сервиса");
        statusService.setCallbackData("StatusService");
        row1.add(statusService);

        InlineKeyboardButton addThread = new InlineKeyboardButton("2. Добавить тред в очередь скачивания");
        addThread.setCallbackData("addThread");
        row2.add(addThread);

        InlineKeyboardButton downloadAllThreads = new InlineKeyboardButton("3. Скачать медиа из всех тредов");
        downloadAllThreads.setCallbackData("downloadAllThreads");
        row3.add(downloadAllThreads);

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        inlineKeyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            // Send the message
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendInlineKeyboardStatusService(Message message) {
        //SendMessage message = new SendMessage();
        //message.setChatId(chatId);
        message.setText("Статус сервиса \r\nВремя работы \r\nТекущие задачи");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        //Vertical rows
        List<InlineKeyboardButton> row1 = new ArrayList<>();

        InlineKeyboardButton statusService = new InlineKeyboardButton("Вернуться назад");
        statusService.setCallbackData("mainMenu");
        row1.add(statusService);


        keyboard.add(row1);

        inlineKeyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(inlineKeyboardMarkup);

//        try {
//            // Send the message
//            execute(message);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
    }

    public ArrayDeque<TelegramPost> telegramPostArrayDeque = new ArrayDeque<>();

    @PostConstruct
    public void AsyncSentMessages() {
        if (!telegramPostArrayDeque.isEmpty()) {
            TelegramPost telegramPost = telegramPostArrayDeque.getFirst();
            CompletableFuture.supplyAsync(() -> {
                if (telegramPost.URLVideos.size() == 1)
                    executeAsync(SendVideo(telegramPost)).exceptionally(e -> {
                        log.error(e.getMessage());
                        //TODO переделать под типы исключений
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
                return null;

            }).thenRun(() -> {
                telegramPostArrayDeque.remove(telegramPost);
                telegramPost.URLVideos.forEach(e->{
                    if (!e.contains("http"))
                        try {
                            Files.delete(Path.of(e));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                });
                SleepBySecond(10);
                AsyncSentMessages();
            });
        } else
            CompletableFuture.runAsync(() -> {
                SleepBySecond(10);
                AsyncSentMessages();
            });
    }

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
}
