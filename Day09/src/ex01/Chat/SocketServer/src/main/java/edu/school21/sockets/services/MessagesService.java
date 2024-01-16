package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;

import java.util.List;

public interface MessagesService {
    boolean sendMessage(String username, String text);

    List<Message> getAllMessages();
}
