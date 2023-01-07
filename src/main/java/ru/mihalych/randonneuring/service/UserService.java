package ru.mihalych.randonneuring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mihalych.randonneuring.model.User;
import ru.mihalych.randonneuring.storage.interfaces.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;
    private final ValidationService validationService;

    public List<User> users() {
        return userStorage.getUsers();
    }

    public User user(Integer id) {
        id = validationService.validationPositive(id);
        return userStorage.getUser(id);
    }

    public User createUser(User user) {
        user = validationService.validationUser(user);
        return userStorage.createUser(user);
    }

    public User saveUser(User user) {
        user = validationService.validationUser(user);
        validationService.validationPositive(user.getId());
        validationService.validationNotFoundUser(user.getId());
        return userStorage.saveUser(user);
    }
}
