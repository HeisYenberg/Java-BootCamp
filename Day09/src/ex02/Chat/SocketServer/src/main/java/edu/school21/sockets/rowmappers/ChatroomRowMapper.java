package edu.school21.sockets.rowmappers;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ChatroomRowMapper implements RowMapper<Chatroom> {
    private final UsersRepository usersRepository;

    public ChatroomRowMapper(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Chatroom mapRow(ResultSet rs, int rowNum) throws SQLException {
        Optional<User> owner = usersRepository.findById(rs.getLong("owner"));
        if (owner.isPresent()) {
            return new Chatroom(rs.getLong("identifier"),
                    rs.getString("name"), owner.get());
        }
        return null;
    }
}
