package ru.mihalych.randonneuring.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mihalych.randonneuring.user.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
