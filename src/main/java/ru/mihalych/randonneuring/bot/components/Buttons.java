package ru.mihalych.randonneuring.bot.components;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class Buttons {

    private static final InlineKeyboardButton SAVE_BUTTON = new InlineKeyboardButton("Сохранить");
    private static final InlineKeyboardButton UPDATE_BUTTON = new InlineKeyboardButton("Исправить");
    private static final InlineKeyboardButton PLUS1_BUTTON = new InlineKeyboardButton("+1 мин");
    private static final InlineKeyboardButton MINUS1_BUTTON = new InlineKeyboardButton("-1 мин");
    private static final InlineKeyboardButton PLUS2_BUTTON = new InlineKeyboardButton("+2 мин");
    private static final InlineKeyboardButton MINUS2_BUTTON = new InlineKeyboardButton("-2 мин");
    private static final InlineKeyboardButton PLUS3_BUTTON = new InlineKeyboardButton("+3 мин");
    private static final InlineKeyboardButton PLUS4_BUTTON = new InlineKeyboardButton("+4 мин");
    private static final InlineKeyboardButton PLUS5_BUTTON = new InlineKeyboardButton("+5 мин");
    private static final InlineKeyboardButton MINUS5_BUTTON = new InlineKeyboardButton("-5 мин");
    private static final InlineKeyboardButton PLUS10_BUTTON = new InlineKeyboardButton("+10 мин");
    private static final InlineKeyboardButton MINUS10_BUTTON = new InlineKeyboardButton("-10 мин");
    private static final InlineKeyboardButton PLUS30_BUTTON = new InlineKeyboardButton("+30 мин");
    private static final InlineKeyboardButton PLUS60_BUTTON = new InlineKeyboardButton("+60 мин");

    public static InlineKeyboardMarkup getValidButton() {
        SAVE_BUTTON.setCallbackData("save");
        UPDATE_BUTTON.setCallbackData("update");

        List<InlineKeyboardButton> rowInline = List.of(SAVE_BUTTON, UPDATE_BUTTON);
        List<List<InlineKeyboardButton>> rowsInline = List.of(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }

    public static InlineKeyboardMarkup getChangeTimeButton() {
        PLUS1_BUTTON.setCallbackData("p1");
        PLUS2_BUTTON.setCallbackData("p2");
        PLUS3_BUTTON.setCallbackData("p3");
        PLUS4_BUTTON.setCallbackData("p4");
        PLUS5_BUTTON.setCallbackData("p5");
        PLUS10_BUTTON.setCallbackData("p10");
        PLUS30_BUTTON.setCallbackData("p30");
        PLUS60_BUTTON.setCallbackData("p60");
        MINUS1_BUTTON.setCallbackData("m1");
        MINUS2_BUTTON.setCallbackData("m2");
        MINUS5_BUTTON.setCallbackData("m5");
        MINUS10_BUTTON.setCallbackData("m10");

        List<InlineKeyboardButton> rowInline1 = List.of(PLUS1_BUTTON, PLUS2_BUTTON, PLUS3_BUTTON, PLUS4_BUTTON);
        List<InlineKeyboardButton> rowInline2 = List.of(PLUS5_BUTTON, PLUS10_BUTTON, PLUS30_BUTTON, PLUS60_BUTTON);
        List<InlineKeyboardButton> rowInline3 = List.of(MINUS1_BUTTON, MINUS2_BUTTON, MINUS5_BUTTON, MINUS10_BUTTON);
        List<List<InlineKeyboardButton>> rowsInline = List.of(rowInline1, rowInline2, rowInline3);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }
}
