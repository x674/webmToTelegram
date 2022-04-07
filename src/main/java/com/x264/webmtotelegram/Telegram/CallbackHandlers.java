package com.x264.webmtotelegram.Telegram;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.x264.webmtotelegram.Entities.ImageBoardThread;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class CallbackHandlers {

    public static EditMessageText sendInlineKeyboardStatusService(CallbackQuery callbackQuery) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setChatId(callbackQuery.getMessage().getChatId().toString());
        editMessageText.setText("Статус сервиса \r\nВремя работы \r\nТекущие задачи");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        //Vertical rows
        List<InlineKeyboardButton> row1 = new ArrayList<>();

        InlineKeyboardButton statusService = new InlineKeyboardButton("Вернуться назад");
        statusService.setCallbackData("mainMenu");
        row1.add(statusService);


        keyboard.add(row1);

        inlineKeyboardMarkup.setKeyboard(keyboard);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);

        return editMessageText;
    }

    public static EditMessageText ReturnToMainMenu(CallbackQuery callbackQuery) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setChatId(callbackQuery.getMessage().getChatId().toString());
        editMessageText.setText("Главное меню");

        editMessageText.setReplyMarkup(ButtonsTemplates.mainMenuKeyboard());
        return editMessageText;
    }

    public static EditMessageText DownloadSettingsMessage(CallbackQuery callbackQuery, boolean dowloadsStatus) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setChatId(callbackQuery.getMessage().getChatId().toString());
        editMessageText.setText("Управление выгрузкой в канал");

        editMessageText.setReplyMarkup(ButtonsTemplates.DownloadSettingsKeyboard(dowloadsStatus));
        return editMessageText;
    }

    public static EditMessageText FilterSettingsMessage(CallbackQuery callbackQuery) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setChatId(callbackQuery.getMessage().getChatId().toString());
        editMessageText.setText("Отправь список ключевых слов, через запятую");

        editMessageText.setReplyMarkup(ButtonsTemplates.FilterSettingsKeyboard());
        return editMessageText;
    }

    public static EditMessageText ListThreadsMenu(CallbackQuery callbackQuery, ArrayList<ImageBoardThread> listThreads) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setChatId(callbackQuery.getMessage().getChatId().toString());
        editMessageText.enableHtml(true);
        String stringThreads = "Список тредов \n\n"+ listThreads.stream().map(thread->thread.getTitle()).collect(Collectors.joining("\n\n "));
        editMessageText.setText(stringThreads.substring(0,1000));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        //Vertical rows
        List<InlineKeyboardButton> row1 = new ArrayList<>();

        InlineKeyboardButton statusService = new InlineKeyboardButton("Вернуться назад");
        statusService.setCallbackData("mainMenu");
        row1.add(statusService);


        keyboard.add(row1);

        inlineKeyboardMarkup.setKeyboard(keyboard);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }
}
