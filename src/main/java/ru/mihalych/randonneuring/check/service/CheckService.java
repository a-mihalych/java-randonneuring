package ru.mihalych.randonneuring.check.service;

import ru.mihalych.randonneuring.check.dto.CheckResult;
import ru.mihalych.randonneuring.check.model.Check;

import java.util.List;

public interface CheckService {

    Check saveCheck(Check check);

    Check checkById(Integer id);

    Integer maxKP(Integer userId);

    List<CheckResult> resultsBrevet();

    String userResult(Integer userId);
}
