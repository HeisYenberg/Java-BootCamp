package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component("messagesService")
public class MessagesServiceImpl implements MessagesService {
    private final MessagesRepository messagesRepository;

    @Autowired
    public MessagesServiceImpl(
            @Qualifier("messagesRepository") MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    @Override
    public void sendMessage(Chatroom room, User user, String text) {

        Long id = (long) (messagesRepository.findAll().size() + 1);
        Message message = new Message(
                id, user, room, text, LocalDateTime.now());
        messagesRepository.save(message);
    }

    @Override
    public List<Message> getAllMessages() {
        return messagesRepository.findAll();
    }

    @Override
    public List<Message> getLast30FromChatroom(Long id) {
        List<Message> messages = messagesRepository.findLast30FromRoomId(id);
        Collections.reverse(messages);
        return messages;
    }
}
