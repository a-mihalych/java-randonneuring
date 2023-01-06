package ru.mihalych.randonneuring.storage.interfaces;

import ru.mihalych.randonneuring.model.User;

import java.util.List;

public interface UserStorage {

    List<User> getUsers();

    User getUser(Integer id);

    User createUser(User user);

    User saveUser(User user);

//    Integer deleteUser(Integer id);
}
