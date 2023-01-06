package ru.mihalych.randonneuring.storage;

import ru.mihalych.randonneuring.model.User;
import ru.mihalych.randonneuring.storage.interfaces.UserStorage;

import java.util.List;

public class UserDbStorage implements UserStorage {

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User getUser(Integer id) {
        return null;
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public Integer deleteUser(Integer id) {
        return null;
    }
}
