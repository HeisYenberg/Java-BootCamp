package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.rowmappers.ChatroomRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component("chatroomsRepository")
public class ChatroomsRepositoryImpl implements ChatroomsRepository {
    private final JdbcTemplate template;
    private final UsersRepository usersRepository;

    @Autowired
    public ChatroomsRepositoryImpl(
            @Qualifier("hikariDataSource") DataSource dataSource,
            @Qualifier("usersRepository") UsersRepository usersRepository) {
        this.template = new JdbcTemplate(dataSource);
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<Chatroom> findLastRoom() {
        try {
            String sql = "SELECT * FROM chatrooms " +
                    "ORDER BY identifier DESC LIMIT 1;";
            Chatroom chatroom = template.queryForObject(
                    sql, new ChatroomRowMapper(usersRepository));
            return Optional.ofNullable(chatroom);
        } catch (EmptyResultDataAccessException ignored) {
        }
        return Optional.empty();
    }

    @Override
    public Optional<Chatroom> findById(Long id) {
        try {
            String sql = "SELECT * FROM chatrooms WHERE identifier = ?;";
            Chatroom chatroom = template.queryForObject(
                    sql, new ChatroomRowMapper(usersRepository), id);
            return Optional.ofNullable(chatroom);
        } catch (EmptyResultDataAccessException ignored) {
        }
        return Optional.empty();
    }

    @Override
    public List<Chatroom> findAll() {
        String sql = "SELECT * FROM chatrooms;";
        return template.query(sql, new ChatroomRowMapper(usersRepository));
    }

    @Override
    public void save(Chatroom entity) {
        String sql = "INSERT INTO chatrooms (identifier, name, owner) " +
                "VALUES (?, ?, ?);";
        template.update(sql, entity.getIdentifier(),
                entity.getName(), entity.getOwner().getIdentifier());
    }

    @Override
    public void update(Chatroom entity) {
        String sql = "UPDATE chatrooms " +
                "SET identifier = ?, name = ?, owner = ?" +
                "WHERE identifier = ?;";
        Long id = entity.getIdentifier();
        template.update(sql, id, entity.getName(),
                entity.getOwner().getIdentifier(), id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM chatrooms WHERE identifier = ?;";
        template.update(sql, id);
    }
}
