package ru.mihalych.randonneuring.telegram.model;

import lombok.Getter;

@Getter
public enum StatusBotForUser {

    START("Бот ожидает ввода команды: /start"),
    EN_NAME_I("Бот ожидает ввода вашего имени латинмцей (например: Ivan):"),
    EN_NAME_F("Бот ожидает ввода вашей фамилии латинмцей (например: Ivanov):"),
    VALID("Бот ожидает подтверждения введённых данные"),
    CHECK("Бот ожидает отправку фотоподтверждения для отметки на КП "),
    FINISH("Бот ожидает отправку фотоподтверждения для отметки на финише"),
    CLOSE("Для вас бревет завершён");

    private String description;

    StatusBotForUser(String description) {
        this.description = description;
    }
}
