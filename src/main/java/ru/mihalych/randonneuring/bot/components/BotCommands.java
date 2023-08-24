package ru.mihalych.randonneuring.bot.components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {

    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "запуск"),
            new BotCommand("/help", "информация")
    );

    String HELP_TEXT = "Это бот предназначен для отметки на контрольных пунктах бревета.\n" +
                       "Для взаимодействия с ботом доступны следующие команды:\n\n" +
                       "/start - стартовать бота\n" +
                       "/help - помощь";
}
