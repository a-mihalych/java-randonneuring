package ru.mihalych.randonneuring.telegram.service;

import ru.mihalych.randonneuring.telegram.model.Telegram;

public interface TelegramService {

     Telegram saveTelegram(Telegram telegram);

     Telegram telegramByTelegramUserChatId(Long telegramUserId);
}
