package ru.mihalych.randonneuring.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mihalych.randonneuring.message.model.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}
