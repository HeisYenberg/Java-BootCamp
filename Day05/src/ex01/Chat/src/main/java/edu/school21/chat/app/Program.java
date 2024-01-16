package edu.school21.chat.app;

import java.sql.SQLException;

public class Program {
    public static void main(String[] args) {
        try {
            DBManager manager = new DBManager();
            manager.find();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
