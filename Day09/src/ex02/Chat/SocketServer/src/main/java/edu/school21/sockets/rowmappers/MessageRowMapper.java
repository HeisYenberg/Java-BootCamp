package edu.school21.sockets.rowmappers;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.ChatroomsRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MessageRowMapper implements RowMapper<Message> {
    private final UsersRepository usersRepository;
    private final ChatroomsRepository chatroomsRepository;

    public MessageRowMapper(UsersRepository usersRepository,
                            ChatroomsRepository chatroomsRepository) {
        this.usersRepository = usersRepository;
        this.chatroomsRepository = chatroomsRepository;
    }

    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        Optional<User> sender = usersRepository.findById(rs.getLong("sender"));
        Optional<Chatroom> chatroom = chatroomsRepository.
                findById(rs.getLong("room"));
        if (sender.isPresent() && chatroom.isPresent()) {
            return new Message(rs.getLong("identifier"),
                    sender.get(), chatroom.get(), rs.getString("text"),
                    rs.getTimestamp("date_time").toLocalDateTime());
        }
        return null;
    }
}
