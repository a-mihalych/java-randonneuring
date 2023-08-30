package ru.mihalych.randonneuring.telegram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mihalych.randonneuring.telegram.model.Telegram;

public interface TelegramRepository extends JpaRepository<Telegram, Integer> {

    Telegram findByTelegramUserChatId(Long telegramUserChatId);
}
