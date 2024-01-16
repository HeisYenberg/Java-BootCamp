package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.NotSavedSubEntityException;

import java.sql.SQLException;
import java.util.Optional;

public class Program {
    public static void main(String[] args) {
        try {
            DBManager manager = new DBManager();
            Optional<Message> optional = manager.find(6L);
            if (optional.isPresent()) {
                Message message = optional.get();
                message.setMessage("Hello world");
                message.setDateTime(null);
                manager.update(message);
                System.out.println(message.getMessage());
                System.out.println(message.getDateTime());
            } else {
                System.out.println("Message not found");
            }
        } catch (SQLException | NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }
    }
}
