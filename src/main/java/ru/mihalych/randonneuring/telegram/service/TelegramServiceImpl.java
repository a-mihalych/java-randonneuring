package ru.mihalych.randonneuring.telegram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mihalych.randonneuring.telegram.model.Telegram;
import ru.mihalych.randonneuring.telegram.repository.TelegramRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TelegramServiceImpl implements TelegramService {

    private final TelegramRepository telegramRepository;

    @Override
    @Transactional
    public Telegram saveTelegram(Telegram telegram) {
        return telegramRepository.save(telegram);
    }

    @Override
    public Telegram telegramByTelegramUserChatId(Long telegramUserChatId) {
        return telegramRepository.findByTelegramUserChatId(telegramUserChatId);
    }

    @Override
    public Telegram telegramByUserId(Integer userId) {
        return telegramRepository.findByUserId(userId);
    }
}
