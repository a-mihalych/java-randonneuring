package ru.mihalych.randonneuring.bot.components;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class Buttons {

    private static final InlineKeyboardButton SAVE_BUTTON = new InlineKeyboardButton("Сохранить");
    private static final InlineKeyboardButton UPDATE_BUTTON = new InlineKeyboardButton("Исправить");

    public static InlineKeyboardMarkup getValidButton() {
        SAVE_BUTTON.setCallbackData("save");
        UPDATE_BUTTON.setCallbackData("update");

        List<InlineKeyboardButton> rowInline = List.of(SAVE_BUTTON, UPDATE_BUTTON);
        List<List<InlineKeyboardButton>> rowsInline = List.of(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }
}
