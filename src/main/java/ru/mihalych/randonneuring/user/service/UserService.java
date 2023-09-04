package ru.mihalych.randonneuring.user.service;

import ru.mihalych.randonneuring.user.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    User userById(Integer id);

    List<Integer> idUsers();
}
