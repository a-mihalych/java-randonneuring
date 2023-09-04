package ru.mihalych.randonneuring.check.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mihalych.randonneuring.bot.config.ParametersBrevet;
import ru.mihalych.randonneuring.check.dto.CheckResult;
import ru.mihalych.randonneuring.check.mapper.CheckMapper;
import ru.mihalych.randonneuring.check.model.Check;
import ru.mihalych.randonneuring.check.repository.CheckRepository;
import ru.mihalych.randonneuring.telegram.model.StatusBotForUser;
import ru.mihalych.randonneuring.telegram.model.Telegram;
import ru.mihalych.randonneuring.telegram.service.TelegramService;
import ru.mihalych.randonneuring.user.service.UserService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CheckServiceImpl implements CheckService {

    private final CheckRepository checkRepository;
    private final UserService userService;
    private final TelegramService telegramService;

    @Override
    @Transactional
    public Check saveCheck(Check check) {
        return checkRepository.save(check);
    }

    @Override
    public Check checkById(Integer id) {
        Optional<Check> checkOptional = checkRepository.findById(id);
        Check check = null;
        if (checkOptional.isPresent()) {
            check = checkOptional.get();
        }
        return check;
    }

    @Override
    public Integer maxKP(Integer userId) {
        return checkRepository.maxKP(userId);
    }

    @Override
    @Transactional
    public List<CheckResult> resultsBrevet() {
        return userService.idUsers().stream()
                                    .map(userId -> CheckMapper.toCheckResult(userService.userById(userId),
                                                                             userResult(userId)))
                                    .sorted()
                                    .toList();
    }

    @Override
    @Transactional
    public String userResult(Integer userId) {
        String result = "DNF";
        int kpFinish = ParametersBrevet.getCountKP() + 1;
        Check check = checkRepository.userResult(userId, kpFinish);
        if (check != null) {
            if (check.getKp() > 0) {
                Duration duration = Duration.between(ParametersBrevet.getStart(), check.getCheckTime());
                int minutesResult = duration.toMinutesPart();
                result = String.format("%d:%s", duration.toHoursPart(), ((minutesResult > 9 ? "" : "0") + minutesResult));
            }
        } else {
            saveCheck(Check.builder()
                           .user(userService.userById(userId))
                           .checkTime(LocalDateTime.now())
                           .kp(-kpFinish)
                           .build());
            Telegram telegram = telegramService.telegramByUserId(userId);
            telegram.setStatusBot(StatusBotForUser.CLOSE);
            telegramService.saveTelegram(telegram);
        }
        return result;
    }
}
