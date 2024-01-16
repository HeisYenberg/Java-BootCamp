package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;

import java.util.List;

public interface MessagesService {
    void sendMessage(Chatroom room, User user, String text);

    List<Message> getAllMessages();

    List<Message> getLast30FromChatroom(Long id);
}
