package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM messages WHERE id = " + id);
            ResultSet messageSet = statement.executeQuery();
            if (messageSet.next()) {
                Message message = new Message(messageSet.getLong("id"));
                User author = getUser(messageSet.getLong("author"));
                message.setAuthor(author);
                Chatroom chatroom = getChatroom(messageSet.getLong("room"));
                chatroom.getMessages().add(message);
                message.setRoom(chatroom);
                author.getChatrooms().add(chatroom);
                message.setMessage(messageSet.getString("message"));
                message.setDateTime(messageSet.getTimestamp("date_time")
                        .toLocalDateTime());
                return Optional.of(message);
            }
        } catch (SQLException ignored) {
        }
        return Optional.empty();
    }

    private User getUser(Long id) throws SQLException {
        User user = new User(id);
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE id = " + id);
        ResultSet userSet = statement.executeQuery();
        if (userSet.next()) {
            user.setLogin(userSet.getString("login"));
            user.setPassword(userSet.getString("password"));
            return user;
        }
        throw new SQLException("Unable to find room");
    }

    private Chatroom getChatroom(Long id) throws SQLException {
        Chatroom chatroom = new Chatroom(id);
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM chatrooms WHERE id = " + id);
        ResultSet chatroomSet = statement.executeQuery();
        if (chatroomSet.next()) {
            chatroom.setName(chatroomSet.getString("name"));
            User owner = getUser(chatroomSet.getLong("owner"));
            chatroom.setOwner(owner);
            owner.getCreatedRooms().add(chatroom);
            return chatroom;
        }
        throw new SQLException("Unable to find room");
    }
}
