package com.x264.webmtotelegram.Telegram;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ButtonsTemplates {

    public static InlineKeyboardMarkup mainMenuKeyboard() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        // Vertical rows
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        List<InlineKeyboardButton> row4 = new ArrayList<>();

        InlineKeyboardButton statusService = new InlineKeyboardButton("1. Статус сервиса");
        statusService.setCallbackData("StatusService");
        row1.add(statusService);

        InlineKeyboardButton addThread = new InlineKeyboardButton("2. Добавить тред в очередь скачивания");
        addThread.setCallbackData("addThread");
        row2.add(addThread);

        InlineKeyboardButton downloadAllThreadsSettings = new InlineKeyboardButton("3. Скачивание медиа из всех тредов");
        downloadAllThreadsSettings.setCallbackData("downloadAllThreadsSettings");
        row3.add(downloadAllThreadsSettings);

        InlineKeyboardButton listThreads = new InlineKeyboardButton("4. Список тредов");
        listThreads.setCallbackData("listThreads");
        row4.add(listThreads);

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup DownloadSettingsKeyboard(boolean dowloadsStatus) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();

        String downloadStatusButton = "";
        if (dowloadsStatus)
            downloadStatusButton = "Остановить скачивание";
        else
            downloadStatusButton = "Запустить выкачивание медиа в канал";
        InlineKeyboardButton toggleDownload = new InlineKeyboardButton(downloadStatusButton);
        toggleDownload.setCallbackData("toggleDownload");
        row1.add(toggleDownload);

        InlineKeyboardButton filterThreads = new InlineKeyboardButton("Изменить фильтр заголовков тредов");
        filterThreads.setCallbackData("filterSettings");
        row2.add(filterThreads);
        row3.add(MoveBack("mainMenu"));


        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup MoveBackKeyboard(String CallbackData) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();

        row1.add(MoveBack(CallbackData));

        keyboard.add(row1);

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardButton MoveBack(String CallbackData){
        InlineKeyboardButton moveBack = new InlineKeyboardButton("Вернуться назад");
        moveBack.setCallbackData(CallbackData);
        return moveBack;
    }
}
