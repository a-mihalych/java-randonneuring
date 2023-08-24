package ru.mihalych.randonneuring.message.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mihalych.randonneuring.message.model.Message;
import ru.mihalych.randonneuring.message.repository.MessageRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message messageById(Integer id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        Message message = null;
        if (messageOptional.isPresent()) {
            message = messageOptional.get();
        }
        return message;
    }
}
