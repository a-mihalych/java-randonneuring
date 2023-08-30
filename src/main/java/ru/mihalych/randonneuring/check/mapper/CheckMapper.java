package ru.mihalych.randonneuring.check.mapper;

import ru.mihalych.randonneuring.check.dto.CheckResult;
import ru.mihalych.randonneuring.user.model.User;

public class CheckMapper {

    public static CheckResult toCheckResult(User user, String result) {
        return CheckResult.builder()
                          .nameFirst(user.getEnNameI())
                          .nameLast(user.getEnNameF())
                          .result(result)
                          .build();
    }
}
