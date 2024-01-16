package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class DBManager {
    private final HikariDataSource dataSource;

    public DBManager() throws SQLException {
        try {
            HikariConfig config = new HikariConfig(
                    "src/main/resources/database.properties");
            dataSource = new HikariDataSource(config);
        } catch (HikariPool.PoolInitializationException |
                 IllegalArgumentException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void find() {
        MessagesRepository repository =
                new MessagesRepositoryJdbcImpl(dataSource);
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a message ID");
        System.out.print("-> ");
        if (in.hasNextLong()) {
            Optional<Message> message = repository.findById(in.nextLong());
            if (message.isPresent()) {
                System.out.println(message.get());
            } else {
                System.out.println("Message not found");
            }
        } else {
            System.out.println("Invalid input for message id");
            find();
        }
        in.close();
    }

    public void save(Message message) {
        MessagesRepository repository =
                new MessagesRepositoryJdbcImpl(dataSource);
        repository.save(message);
    }
}
