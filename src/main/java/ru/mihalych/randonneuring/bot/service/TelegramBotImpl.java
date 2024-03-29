package ru.mihalych.randonneuring.bot.service;

import com.opencsv.CSVWriter;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mihalych.randonneuring.bot.components.BotCommands;
import ru.mihalych.randonneuring.bot.components.Buttons;
import ru.mihalych.randonneuring.bot.config.BotConfig;
import ru.mihalych.randonneuring.bot.config.ParametersBrevet;
import ru.mihalych.randonneuring.check.dto.CheckResult;
import ru.mihalych.randonneuring.check.model.Check;
import ru.mihalych.randonneuring.check.service.CheckService;
import ru.mihalych.randonneuring.message.model.Message;
import ru.mihalych.randonneuring.message.service.MessageService;
import ru.mihalych.randonneuring.telegram.model.Telegram;
import ru.mihalych.randonneuring.telegram.service.TelegramService;
import ru.mihalych.randonneuring.telegram.model.StatusBotForUser;
import ru.mihalych.randonneuring.user.model.User;
import ru.mihalych.randonneuring.user.service.UserService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TelegramBotImpl extends TelegramLongPollingBot implements BotCommands {

    private final BotConfig botConfig;
    private final MessageService messageService;
    private final TelegramService telegramService;
    private final UserService userService;
    private final CheckService checkService;

    public TelegramBotImpl(BotConfig botConfig, MessageService messageService, TelegramService telegramService,
                           UserService userService, CheckService checkService) {
        this.botConfig = botConfig;
        this.messageService = messageService;
        this.telegramService = telegramService;
        this.userService = userService;
        this.checkService = checkService;
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("\n! Косяк в 'class TelegramBotImpl', конструктор:\n!!! {}", e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    private long getGeneralChat() {
        return Long.parseLong(botConfig.getChatId());
    }

    private long getChatAdmin() {
        return Long.parseLong(botConfig.getChatIdAdmin());
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().startsWith("/")) {
                    updateCommand(update);
                } else {
                    updateText(update);
                }
            } else {
                if (update.getMessage().hasPhoto()) {
                    updatePhoto(update);
                }
            }
        } else {
            if (update.hasCallbackQuery()) {
                updateCallback(update);
            }
        }
    }

    private Telegram getTelegram(long chatId, boolean start) {
        Telegram telegram = telegramService.telegramByTelegramUserChatId(chatId);
        if ((telegram == null) && start) {
            sendMessage(chatId, String.format("%s", StatusBotForUser.START.getDescription()));
        }
        return telegram;
    }

    private Message saveMessage(Telegram telegram, long ms, String txt, String callback, String photoFileId) {
        return messageService.saveMessage(Message.builder()
                                          .telegram(telegram)
                                          .messageTime(Instant.ofEpochMilli(ms)
                                                              .atZone(ZoneId.systemDefault())
                                                              .toLocalDateTime())
                                          .text(txt)
                                          .callback(callback)
                                          .photoFileId(photoFileId)
                                          .build());
    }

    private void updateCommand(Update update) {
        long chatId = update.getMessage().getChatId();
        String command = update.getMessage().getText();
        Telegram telegram = getTelegram(chatId, !"/start".equals(command));
        if (telegram == null) {
            if ("/start".equals(command)) {
                telegram = telegramService.saveTelegram(Telegram.builder()
                                .name(update.getMessage().getFrom().getFirstName())
                                .telegramUserChatId(chatId)
                                .telegramAdmin(chatId == getChatAdmin())
                                .user(null)
                                .statusBot(StatusBotForUser.EN_NAME_I)
                                .build());
                sendMessage(chatId, String.format("Запущена команда: %s\nБот готов к работе!", command));
                sendForStatusBot(telegram);
            }
            saveMessage(telegram, (update.getMessage().getDate() * 1000L), command, null, null);
        } else {
            Message message = saveMessage(telegram, (update.getMessage().getDate() * 1000L), command, null, null);
            switch (command) {
                case "/start":
                    sendMessage(chatId, String.format("Не запущена команда: %s", command));
                    break;
                case "/help":
                    sendMessage(chatId, String.format("Запущена команда: %s", command));
                    sendMessage(chatId, String.format("%s", HELP_TEXT));
                    break;
                case "/legend":
                    sendMessage(chatId, String.format("Запущена команда: %s", command));
                    sendMessage(chatId, String.format("%s", ParametersBrevet.getLegend()));
                    break;
                case "/next":
                    // todo сделать для пропуска отметки на КП
                    break;
                case "/prev":
                    // todo сделать для повторной отметки на КП (неудачное фото)
                    break;
                case "/dnf":
                    if ((telegram.getStatusBot() == StatusBotForUser.CHECK) ||
                        (telegram.getStatusBot() == StatusBotForUser.FINISH)) {
                        sendMessage(chatId, String.format("Запущена команда: %s", command));
                        telegram.setStatusBot(StatusBotForUser.CLOSE);
                        telegramService.saveTelegram(telegram);
                        User user = userService.userById(telegram.getUser().getId());
                        checkService.saveCheck(Check.builder()
                                .user(user)
                                .checkTime(message.getMessageTime())
                                .kp(-ParametersBrevet.getCountKP() - 1)
                                .build());
                        String dnf = checkService.userResult(user.getId());
                        sendMessage(chatId, "Сход зарегистрирован");
                        sendMessage(getGeneralChat(), String.format("Сход: %s %s, результат: %s",
                                                                    user.getEnNameI(), user.getEnNameF(), dnf));
                    } else {
                        if (telegram.getStatusBot() == StatusBotForUser.CLOSE) {
                            sendMessage(chatId,
                                String.format("Не запущена команда: %s, нельзя сойти с завершённого бревета", command));
                        } else {
                            sendMessage(chatId,
                                String.format("Не запущена команда: %s, нужно завершить регистрацию", command));
                        }
                    }
                    break;
                case "/time":
                    if (telegram.getTelegramAdmin()) {
                        sendCallback(chatId, "Насколько скорректировать старт?", Buttons.getChangeTimeButton());
                    } else {
                        sendMessage(chatId, "Не выполнена команда: /time\nЭта команда доступна только админу");
                    }
                    break;
                case "/results":
                    if (telegram.getTelegramAdmin()) {
                        List<CheckResult> result = checkService.resultsBrevet();
                        createFileCSV(result);
                        sendMessage(chatId, "Создан файл с результатами");
                    } else {
                        sendMessage(chatId, "Не выполнена команда: /results\nЭта команда доступна только админу");
                    }
                    break;
                default:
                    sendMessage(chatId, String.format("Не найдена команда: %s", command));
            }
            if (!((("/results".equals(command)) || ("/time".equals(command))) && (telegram.getTelegramAdmin()))) {
                sendForStatusBot(telegram);
            }
        }
    }

    private void updateText(Update update) {
        long chatId = update.getMessage().getChatId();
        Telegram telegram = getTelegram(chatId, true);
        String txt = update.getMessage().getText();
        if (telegram != null) {
            StatusBotForUser status = telegram.getStatusBot();
            switch (status) {
                case EN_NAME_I:
                    saveEnNameI(txt, telegram);
                    sendMessage(chatId, String.format("Имя: '%s' записано\n%s",
                                txt, telegram.getStatusBot().getDescription()));
                    break;
                case EN_NAME_F:
                    saveEnNameF(txt, telegram);
                    sendMessage(chatId, String.format("Фамилия: '%s' записана\n%s",
                                txt, telegram.getStatusBot().getDescription()));
                    User user = userService.userById(telegram.getUser().getId());
                    sendCallback(chatId,
                                 String.format("Проверка данных: %s %s", user.getEnNameI(), user.getEnNameF()),
                                 Buttons.getValidButton());
                    break;
                default:
                    sendMessage(chatId, String.format("Сообшения: '%s' не ожидалось\n%s",
                                                      txt, status.getDescription()));
            }
        }
        saveMessage(telegram, (update.getMessage().getDate() * 1000L), txt, null, null);
    }

    private void updateCallback(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        Telegram telegram = getTelegram(chatId, true);
        String txtCallback = update.getCallbackQuery().getData();
        if (telegram.getStatusBot() == StatusBotForUser.VALID) {
            switch (txtCallback) {
                case "save":
                    User user = userService.userById(telegram.getUser().getId());
                    user.setSaveClose(true);
                    userService.saveUser(user);
                    telegram.setStatusBot(StatusBotForUser.CHECK);
                    telegramService.saveTelegram(telegram);
                    Check check = Check.builder()
                            .user(user)
                            .checkTime(ParametersBrevet.getStart())
                            .kp(0)
                            .build();
                    checkService.saveCheck(check);
                    sendMessage(chatId, "Данные сохранены!");
                    break;
                case "update":
                    telegram.setStatusBot(StatusBotForUser.EN_NAME_I);
                    telegramService.saveTelegram(telegram);
                    sendMessage(chatId, "Нужно ввести имя и фамилию заново");
                    break;
                default:
                    sendMessage(chatId, "Не ожидаемое состояние! Для продолжения попробуйте ввести команду: /help");
            }
        }
        if (telegram.getTelegramAdmin()) {
            int correct = 0;
            switch (txtCallback) {
                case "p1":
                    correct = 1;
                    break;
                case "p2":
                    correct = 2;
                    break;
                case "p3":
                    correct = 3;
                    break;
                case "p4":
                    correct = 4;
                    break;
                case "p5":
                    correct = 5;
                    break;
                case "p10":
                    correct = 10;
                    break;
                case "p30":
                    correct = 30;
                    break;
                case "p60":
                    correct = 60;
                    break;
                case "m1":
                    correct = -1;
                    break;
                case "m2":
                    correct = -2;
                    break;
                case "m5":
                    correct = -5;
                    break;
                case "m10":
                    correct = -10;
                    break;
            }
            if (correct != 0) {
                ParametersBrevet.correct(correct);
                sendMessage(chatId, String.format("Новое время старта: %s", ParametersBrevet.getStart()));
            }
        }
        sendForStatusBot(telegram);
        saveMessage(telegram, (update.getCallbackQuery().getMessage().getDate() * 1000L), null, txtCallback, null);
    }

    private void updatePhoto(Update update) {
        long chatId = update.getMessage().getChatId();
        Telegram telegram = getTelegram(chatId, true);
        String file_id = update.getMessage().getPhoto().get(0).getFileId();
        Message message = saveMessage(telegram, (update.getMessage().getDate() * 1000L), null, null, file_id);
        if ((telegram.getStatusBot() == StatusBotForUser.CHECK) || (telegram.getStatusBot() == StatusBotForUser.FINISH)) {
            int kp = checkService.maxKP(telegram.getUser().getId()) + 1;
            User user = userService.userById(telegram.getUser().getId());
            checkService.saveCheck(Check.builder()
                    .user(telegram.getUser())
                    .checkTime(message.getMessageTime())
                    .kp(kp)
                    .build());
            String txtKP = "КП " + kp + ", " + ParametersBrevet.getTxtNameKP(kp);
            if (ParametersBrevet.getCountKP() == kp) {
                telegram.setStatusBot(StatusBotForUser.FINISH);
                telegramService.saveTelegram(telegram);
            }
            if (ParametersBrevet.getCountKP() < kp) {
                telegram.setStatusBot(StatusBotForUser.CLOSE);
                telegramService.saveTelegram(telegram);
                txtKP = "Финиш, " + ParametersBrevet.getTxtNameKP(kp) +
                        ", результат: " + checkService.userResult(user.getId());
            }
            sendPhoto(chatId, file_id, user, txtKP);
            sendPhoto(getGeneralChat(), file_id, user, txtKP);
        } else {
            sendMessage(chatId, telegram.getStatusBot().getDescription());
        }
        sendForStatusBot(telegram);
    }

    private void sendForStatusBot(Telegram telegram) {
        StatusBotForUser statusBot = telegram.getStatusBot();
        long chatId = telegram.getTelegramUserChatId();
        switch (statusBot) {
            case EN_NAME_I:
            case EN_NAME_F:
                sendMessage(chatId, statusBot.getDescription());
                break;
            case CLOSE:
                sendMessage(chatId, statusBot.getDescription());
                String result = checkService.userResult(telegram.getUser().getId());
                String txt = String.format("Ваш результат: %s", result);
                sendMessage(chatId, txt);
                break;
            case VALID:
                sendMessage(chatId, statusBot.getDescription());
                User user = userService.userById(telegram.getUser().getId());
                sendCallback(chatId,
                             String.format("Проверка данных: %s %s", user.getEnNameI(), user.getEnNameF()),
                             Buttons.getValidButton());
                break;
            case CHECK:
                sendMessage(chatId, getCheckText(telegram));
                break;
            case FINISH:
                sendMessage(chatId, (statusBot.getDescription() + ", " +
                                     ParametersBrevet.getTxtNameKP(ParametersBrevet.getCountKP() + 1)));
                break;
        }
    }

    private void sendMessage(long chatId, String txt) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(ParseMode.HTML);
        message.setText(String.format("<code>%s:</code>\n%s", getBotUsername(), txt));
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("\n! Косяк в 'class TelegramBotImpl', метод 'sendMessage':\n!!! {}", e.getMessage());
        }
    }

    private void sendCallback(long chatId, String txt, InlineKeyboardMarkup buttons) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setParseMode(ParseMode.HTML);
        message.setText(String.format("<code>%s:</code>\n%s", getBotUsername(), txt));
        message.setReplyMarkup(buttons);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("\n! Косяк в 'class TelegramBotImpl', метод 'sendCallback':\n!!! {}", e.getMessage());
        }
    }

    private void sendPhoto(long chatId, String fileId, User user, String kp) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(fileId));
        sendPhoto.setCaption(String.format("Отметка: %s %s, %s", user.getEnNameF(), user.getEnNameI(), kp));
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            log.error("\n! Косяк в 'class TelegramBotImpl', метод 'sendPhoto':\n!!! {}", e.getMessage());
        }
    }

    private void saveEnNameI(String nameEn, Telegram telegram) {
        User user;
        if (telegram.getUser() != null) {
            user = userService.userById(telegram.getUser().getId());
        } else {
            user = User.builder()
                    .saveClose(false)
                    .build();
        }
        user.setEnNameI(nameEn);
        user = userService.saveUser(user);
        telegram.setStatusBot(StatusBotForUser.EN_NAME_F);
        telegram.setUser(user);
        telegramService.saveTelegram(telegram);
    }

    private void saveEnNameF(String nameEn, Telegram telegram) {
        User user = userService.userById(telegram.getUser().getId());
        user = user.toBuilder()
                   .enNameF(nameEn)
                   .build();
        user = userService.saveUser(user);
        telegram.setStatusBot(StatusBotForUser.VALID);
        telegram.setUser(user);
        telegramService.saveTelegram(telegram);
    }

    private String getCheckText(Telegram telegram) {
        int userId = telegram.getUser().getId();
        int kp = checkService.maxKP(userId) + 1;
        return (telegram.getStatusBot().getDescription() + kp + ", " + ParametersBrevet.getTxtNameKP(kp));
    }

    private void createFileCSV(List<CheckResult> result) {
        File file = new File(ParametersBrevet.CSV_FILE_PATH);
        try (FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile, ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END)
        ) {
            List<String[]> data = new ArrayList<>();
            data.add(ParametersBrevet.TABLE_HEADERS);
            for (CheckResult checkResult : result) {
                String[] rowdata = {checkResult.getNameLast(),
                                    checkResult.getNameFirst(),
                                    ParametersBrevet.IZHEVSK,
                                    ParametersBrevet.KOD_CLUB,
                                    checkResult.getResult()};
                data.add(rowdata);
            }
            writer.writeAll(data);
        }
        catch (IOException e) {
            log.error("\n! Косяк в 'class TelegramBotImpl', метод 'createFileCSV':\n!!! {}", e.getMessage());
        }
    }
}
