package ru.mihalych.randonneuring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mihalych.randonneuring.model.User;
import ru.mihalych.randonneuring.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
