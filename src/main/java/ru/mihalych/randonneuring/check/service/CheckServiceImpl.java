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
import ru.mihalych.randonneuring.user.service.UserService;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CheckServiceImpl implements CheckService {

    private final CheckRepository checkRepository;
    private final UserService userService;

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
    public List<CheckResult> resultsBrevet() {
        List<Check> resultBrevet = checkRepository.resultBrevet(ParametersBrevet.getCountKP() + 1);
        return resultBrevet.stream()
                           .map(check -> {
                                Integer userId = check.getUser().getId();
                                return CheckMapper.toCheckResult(userService.userById(userId), userResult(userId));
                           })
                           .collect(Collectors.toList());
    }

    @Override
    public String userResult(Integer userId) {
        Check check = checkRepository.findByUserIdAndKp(userId, (ParametersBrevet.getCountKP() + 1));
        Duration duration = Duration.between(ParametersBrevet.getStart(), check.getCheckTime());
        int minutesResult = duration.toMinutesPart();
        return String.format("%d:%s", duration.toHoursPart(), ((minutesResult > 9 ? "" : "0") + minutesResult));
    }
}
