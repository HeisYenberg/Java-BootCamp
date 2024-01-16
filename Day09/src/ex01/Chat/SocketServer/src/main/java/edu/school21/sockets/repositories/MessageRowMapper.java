package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MessageRowMapper implements RowMapper<Message> {
    private final UsersRepository usersRepository;

    public MessageRowMapper(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        Optional<User> sender = usersRepository.findById(rs.getLong("sender"));
        if (sender.isPresent()) {
            return new Message(rs.getLong("identifier"),
                    sender.get(), rs.getString("text"),
                    rs.getTimestamp("date_time").toLocalDateTime());
        }
        return null;
    }
}
