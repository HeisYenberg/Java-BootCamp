package edu.school21.chat.app;

import edu.school21.chat.models.User;
import edu.school21.chat.repositories.NotSavedSubEntityException;

import java.sql.SQLException;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        try {
            DBManager manager = new DBManager();
            List<User> users = manager.findAll(0, 2);
            for (User user : users) {
                System.out.println("user=" + user);
                System.out.println("createdRooms=" + user.getCreatedRooms());
                System.out.println("rooms=" + user.getRooms());
            }
        } catch (SQLException | NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }
    }
}
