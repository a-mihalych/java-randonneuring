package ru.mihalych.randonneuring.storage;

import org.springframework.stereotype.Repository;
import ru.mihalych.randonneuring.model.User;
import ru.mihalych.randonneuring.storage.interfaces.UserStorage;

import java.util.List;

@Repository
public class UserDbStorage implements UserStorage {

    @Override
    public List<User> getUsers() {
        System.out.println("getUsers");
        return List.of(new User(1, "qw", "er", "as", "df", "zx"));
    }

    @Override
    public User getUser(Integer id) {
        System.out.println("getUser");
        return new User();
    }

    @Override
    public User createUser(User user) {
        System.out.println("createUsers");
        return new User();
    }

    @Override
    public User saveUser(User user) {
        System.out.println("saveUsers");
        return new User();
    }
}
