package edu.school21.sockets.services;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.User;

import java.util.List;
import java.util.Optional;

public interface ChatroomService {
    Chatroom createRoom(User owner, String name);

    List<Chatroom> getRooms();

    Optional<Chatroom> getLastRoom();

    Optional<Chatroom> getRoomById(Long id);
}
