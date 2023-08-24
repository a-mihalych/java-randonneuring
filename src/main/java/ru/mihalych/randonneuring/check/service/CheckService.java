package ru.mihalych.randonneuring.check.service;

import ru.mihalych.randonneuring.check.model.Check;

public interface CheckService {

    Check saveCheck(Check check);

    Check checkById(Integer id);

    Integer maxKP(Integer userId);
}
