package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.rowmappers.MessageRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component("messagesRepository")
public class MessagesRepositoryImpl implements MessagesRepository {
    private final JdbcTemplate template;
    private final UsersRepository usersRepository;
    private final ChatroomsRepository chatroomsRepository;

    @Autowired
    public MessagesRepositoryImpl(
            @Qualifier("hikariDataSource") DataSource dataSource,
            @Qualifier("usersRepository") UsersRepository usersRepository,
            @Qualifier("chatroomsRepository") ChatroomsRepository chatroomsRepository) {
        this.template = new JdbcTemplate(dataSource);
        this.usersRepository = usersRepository;
        this.chatroomsRepository = chatroomsRepository;
    }

    @Override
    public Optional<Message> findById(Long id) {
        try {
            String sql = "SELECT * FROM messages WHERE identifier = ?;";
            Message message = template.queryForObject(sql, new MessageRowMapper(
                    usersRepository, chatroomsRepository), id);
            return Optional.ofNullable(message);
        } catch (EmptyResultDataAccessException ignored) {
        }
        return Optional.empty();
    }

    @Override
    public List<Message> findAll() {
        String sql = "SELECT * FROM messages;";
        return template.query(sql,
                new MessageRowMapper(usersRepository, chatroomsRepository));
    }

    @Override
    public void save(Message entity) {
        String sql = "INSERT INTO messages " +
                "(identifier, sender, room, text, date_time) " +
                "VALUES (?, ?, ?, ?, ?)";
        template.update(sql,
                entity.getIdentifier(),
                entity.getSender().getIdentifier(),
                entity.getRoom().getIdentifier(),
                entity.getText(),
                Timestamp.valueOf(entity.getDateTime()));
    }

    @Override
    public void update(Message entity) {
        String sql = "UPDATE messages " +
                "SET identifier = ?, sender = ?, room = ?, " +
                "text = ?, date_time = ? " +
                "WHERE identifier = ?;";
        Long id = entity.getIdentifier();
        template.update(sql, id,
                entity.getSender().getIdentifier(),
                entity.getRoom().getIdentifier(),
                entity.getText(), Timestamp.valueOf(entity.getDateTime()), id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM messages WHERE identifier = ?;";
        template.update(sql, id);
    }

    @Override
    public List<Message> findAllOfSenderId(Long id) {
        String sql = "SELECT * FROM messages WHERE sender = ?;";
        return template.query(sql, new MessageRowMapper(
                usersRepository, chatroomsRepository), id);
    }

    @Override
    public List<Message> findLast30FromRoomId(Long id) {
        String sql = "SELECT * FROM messages WHERE room = ? " +
                "ORDER BY identifier DESC LIMIT 30;";
        return template.query(sql,
                new MessageRowMapper(usersRepository, chatroomsRepository), id);
    }
}
