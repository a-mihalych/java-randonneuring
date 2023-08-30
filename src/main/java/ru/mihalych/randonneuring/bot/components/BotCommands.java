package ru.mihalych.randonneuring.bot.components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {

    // todo планируется добавить ещё несколько команд

    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "запуск бота"),
            new BotCommand("/help", "информация"),
//            new BotCommand("/next", "отменить отметку"),
//            new BotCommand("/prev", "пропустить отметку"),
//            new BotCommand("/dnf", "завершить (сойти)"),
            new BotCommand("/results", "АДМИН, результаты")
    );

    String HELP_TEXT = "Это бот предназначен для отметки на контрольных пунктах бревета.\n" +
                       "Для взаимодействия с ботом доступны следующие команды:\n\n" +
                       "/start - стартовать бота\n" +
                       "/help - показать помощь\n" +
//                       "/next - пропуск КП, отметка на следующем\n" +
//                       "/prev - отмена отметки, для повторной отметки\n" +
//                       "/dnf - завершить бревет, сойти\n" +
                       "/results - получить результаты, для админа\n\n" +
                       "Для отметки на КП и на финише - нужно просто отправить боту фото с КП";
}
