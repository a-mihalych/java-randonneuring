package ru.mihalych.randonneuring.message.service;

import ru.mihalych.randonneuring.message.model.Message;

public interface MessageService {

    Message saveMessage(Message message);

    Message messageById(Integer id);
}
