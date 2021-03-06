package com.x264.webmtotelegram.telegram;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class CallbackHandlers {

    public static EditMessageText sendInlineKeyboardStatusService(CallbackQuery callbackQuery, boolean statusService, int countMessages) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setChatId(callbackQuery.getMessage().getChatId().toString());
        String messageFormat = MessageFormat.format("Статус сервиса: {0}\nСообщений в очереди: {1}", statusService ? "Выкачка запущена" : "Выкачка остановлена", countMessages);
        editMessageText.setText(messageFormat);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        //Vertical rows
        List<InlineKeyboardButton> row1 = new ArrayList<>();

        InlineKeyboardButton followBack = new InlineKeyboardButton("Вернуться назад");
        followBack.setCallbackData("mainMenu");
        row1.add(followBack);


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

    public static EditMessageText OnSettedFilter(CallbackQuery callbackQuery, List listFilter) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setChatId(callbackQuery.getMessage().getChatId().toString());
        String message = "Установлены слова:\n" + listFilter.stream().collect(Collectors.joining("\n"));
        editMessageText.setText(message);


        editMessageText.setReplyMarkup(ButtonsTemplates.MoveBackKeyboard("mainMenu"));
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

    public static EditMessageText filterSettingsMessage(CallbackQuery callbackQuery, List<String> listFilter) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setChatId(callbackQuery.getMessage().getChatId().toString());
        String message = "";
        if (listFilter.isEmpty())
            message = "Ключевые слова не заданы\n" +
                    "Отправь список ключевых слов, через запятую";
        else
            message = "Текущие ключевые слова\n" +
                    listFilter.stream().collect(Collectors.joining("\n")) +
                    "\nОтправь список ключевых слов, через запятую";
        editMessageText.setText(message);

        editMessageText.setReplyMarkup(ButtonsTemplates.MoveBackKeyboard("downloadAllThreadsSettings"));
        return editMessageText;
    }

//    public static EditMessageText ListThreadsMenu(CallbackQuery callbackQuery, ArrayList<ImageBoardThread> listThreads) {
//        EditMessageText editMessageText = new EditMessageText();
//        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
//        editMessageText.setChatId(callbackQuery.getMessage().getChatId().toString());
//        editMessageText.enableHtml(true);
//        String stringThreads = "Список тредов \n\n"+ listThreads.stream().map(thread->thread.getTitle()).collect(Collectors.joining("\n"));
//        editMessageText.setText(stringThreads.substring(0,1000));
//
//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//
//        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
//        //Vertical rows
//        List<InlineKeyboardButton> row1 = new ArrayList<>();
//
//        InlineKeyboardButton statusService = new InlineKeyboardButton("Вернуться назад");
//        statusService.setCallbackData("mainMenu");
//        row1.add(statusService);
//
//        keyboard.add(row1);
//
//        inlineKeyboardMarkup.setKeyboard(keyboard);
//        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
//        return editMessageText;
//    }
}
