package com.x264.webmtotelegram.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class CommandHandlers {
    public static SendMessage sendInlineKeyboardMainMenu(String chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Главное меню");
        // Add it to the message
        message.setReplyMarkup(ButtonsTemplates.mainMenuKeyboard());

        return message;
    }

}
