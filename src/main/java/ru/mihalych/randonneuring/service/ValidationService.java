package ru.mihalych.randonneuring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mihalych.randonneuring.exception.NotFoundException;
import ru.mihalych.randonneuring.model.User;
import ru.mihalych.randonneuring.storage.interfaces.BrevetStorage;
import ru.mihalych.randonneuring.storage.interfaces.UserStorage;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final UserStorage userStorage;
    private final BrevetStorage brevetStorage;

    public int validationPositive(int arg) {
        if (arg < 1) {
            throw new NotFoundException("Аргумент должен быть больше 0");
        }
        return arg;
    }

    public User validationUser(User user) {
        if ((user.getNick() == null) || (user.getNick().isBlank())) {
            user.setNick(user.getRuNameI());
        }
        return user;
    }

    public int validationNotFoundUser(int id) {
        if (userStorage.getUser(id) == null) {
            throw new NotFoundException(String.format("Пользователь с id = %d в базе отсутствует", id));
        }
        return id;
    }

    public int validationNotFoundBrevet(int id) {
        if (brevetStorage.getBrevet(id) == null) {
            throw new NotFoundException(String.format("Бревет с id = %d в базе отсутствует", id));
        }
        return id;
    }
}
