package ru.mihalych.randonneuring.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mihalych.randonneuring.user.model.User;
import ru.mihalych.randonneuring.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User userById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = null;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        return user;
    }
}
