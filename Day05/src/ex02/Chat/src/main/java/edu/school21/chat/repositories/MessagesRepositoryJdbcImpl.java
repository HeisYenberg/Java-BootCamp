package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
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
                User author = getUser(messageSet.getLong("author"));
                Chatroom chatroom = getChatroom(messageSet.getLong("room"));
                author.getRooms().add(chatroom);
                String text = messageSet.getString("message");
                LocalDateTime dateTime = messageSet.getTimestamp("date_time").
                        toLocalDateTime();
                Message message =
                        new Message(id, author, chatroom, text, dateTime);
                chatroom.getMessages().add(message);
                return Optional.of(message);
            }
        } catch (SQLException ignored) {
        }
        return Optional.empty();
    }

    @Override
    public void save(Message message) {
        try {
            PreparedStatement statement = getPreparedStatement(message);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                message.setId(generatedKeys.getLong("id"));
            } else {
                throw new NotSavedSubEntityException(
                        "Failed to save a message");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private PreparedStatement getPreparedStatement(Message message)
            throws SQLException {
        if (message == null) {
            throw new NotSavedSubEntityException("No message entered");
        }
        if (message.getAuthor() == null || message.getRoom() == null) {
            throw new NotSavedSubEntityException("User or Room not entered");
        }
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO messages (author, room, message, date_time) " +
                        "VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, message.getAuthor().getId());
        statement.setLong(2, message.getRoom().getId());
        statement.setString(3, message.getMessage());
        statement.setTimestamp(4, Timestamp.valueOf(message.getDateTime()));
        return statement;
    }

    private User getUser(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE id = " + id);
        ResultSet userSet = statement.executeQuery();
        if (userSet.next()) {
            String login = userSet.getString("login");
            String password = userSet.getString("password");
            return new User(id, login, password, new LinkedList<>(),
                    new LinkedList<>());
        }
        throw new SQLException("Unable to find user");
    }

    private Chatroom getChatroom(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM chatrooms WHERE id = " + id);
        ResultSet chatroomSet = statement.executeQuery();
        if (chatroomSet.next()) {
            String name = chatroomSet.getString("name");
            User owner = getUser(chatroomSet.getLong("owner"));
            Chatroom chatroom =
                    new Chatroom(id, name, owner, new LinkedList<>());
            owner.getCreatedRooms().add(chatroom);
            return chatroom;
        }
        throw new SQLException("Unable to find room");
    }
}
