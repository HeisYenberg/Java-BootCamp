package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component("messagesService")
public class MessagesServiceImpl implements MessagesService {
    private final UsersRepository usersRepository;
    private final MessagesRepository messagesRepository;

    @Autowired
    public MessagesServiceImpl(
            @Qualifier("usersRepository") UsersRepository usersRepository,
            @Qualifier("messagesRepository") MessagesRepository messagesRepository) {
        this.usersRepository = usersRepository;
        this.messagesRepository = messagesRepository;
    }

    @Override
    public boolean sendMessage(String username, String text) {
        Optional<User> user = usersRepository.findByUsername(username);
        if (user.isPresent()) {
            Long id = (long) (messagesRepository.findAll().size() + 1);
            messagesRepository.save(
                    new Message(id, user.get(), text, LocalDateTime.now()));
            return true;
        }
        return false;
    }

    @Override
    public List<Message> getAllMessages() {
        return messagesRepository.findAll();
    }
}
