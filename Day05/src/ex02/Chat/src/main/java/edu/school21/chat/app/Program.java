package edu.school21.chat.app;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.NotSavedSubEntityException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class Program {
    public static void main(String[] args) {
        try {
            DBManager manager = new DBManager();
            User creator = new User(5L, "user", "user", new LinkedList<>(),
                    new LinkedList<>());
            Chatroom room = new Chatroom(5L, "room", creator,
                    new LinkedList<>());
            Message message = new Message(null, creator, room, "Hello!",
                    LocalDateTime.now());
            manager.save(message);
            System.out.println(message.getId());
        } catch (SQLException | NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }
    }
}
