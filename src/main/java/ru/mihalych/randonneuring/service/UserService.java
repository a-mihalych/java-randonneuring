package ru.mihalych.randonneuring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mihalych.randonneuring.model.User;
import ru.mihalych.randonneuring.storage.interfaces.UserStorage;

import java.util.List;

@Service
public class UserService {

    UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> users() {
        return userStorage.getUsers();
    }

    public User user(Integer id) {
        return userStorage.getUser(id);
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User saveUser(User user) {
        return userStorage.saveUser(user);
    }
}
