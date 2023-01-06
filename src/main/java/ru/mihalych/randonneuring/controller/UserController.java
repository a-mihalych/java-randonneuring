package ru.mihalych.randonneuring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mihalych.randonneuring.model.User;
import ru.mihalych.randonneuring.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> users() {
        return userService.users();
    }

    @GetMapping("/{id}")
    public User user(@PathVariable Integer id) {
        return userService.user(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
