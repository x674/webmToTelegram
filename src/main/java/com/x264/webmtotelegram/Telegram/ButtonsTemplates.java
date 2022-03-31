package com.x264.webmtotelegram.Telegram;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ButtonsTemplates {
    public static InlineKeyboardMarkup mainMenuKeyboard() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        //Vertical rows
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

        InlineKeyboardButton downloadAllThreads = new InlineKeyboardButton("3. Скачать медиа из всех тредов");
        downloadAllThreads.setCallbackData("downloadAllThreads");
        row3.add(downloadAllThreads);

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
}
