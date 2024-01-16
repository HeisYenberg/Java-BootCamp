package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.ChatroomsRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("chatroomService")
public class ChatroomServiceImpl implements ChatroomService {
    private final ChatroomsRepository chatroomsRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public ChatroomServiceImpl(
            @Qualifier("chatroomsRepository") ChatroomsRepository chatroomsRepository,
            @Qualifier("usersRepository") UsersRepository usersRepository) {
        this.chatroomsRepository = chatroomsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<Chatroom> getLastRoom() {
        return chatroomsRepository.findLastRoom();
    }

    @Override
    public Optional<Chatroom> getRoomById(Long id) {
        return chatroomsRepository.findById(id);
    }

    @Override
    public Chatroom createRoom(User owner, String name) {
        Long id = (long) (chatroomsRepository.findAll().size() + 1);
        Chatroom chatroom = new Chatroom(id, name, owner);
        chatroomsRepository.save(chatroom);
        return chatroom;
    }

    @Override
    public List<Chatroom> getRooms() {
        return chatroomsRepository.findAll();
    }
}
