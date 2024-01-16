package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component("usersRepository")
public class UsersRepositoryImpl implements UsersRepository {
    private final JdbcTemplate template;

    @Autowired
    public UsersRepositoryImpl(
            @Qualifier("hikariDataSource") DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            String sql = "SELECT * FROM users WHERE identifier = ?;";
            User user = template.queryForObject(sql, new UserRowMapper(), id);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException ignored) {
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users;";
        return template.query(sql, new UserRowMapper());
    }

    @Override
    public void save(User entity) {
        String sql = "INSERT INTO users (identifier, username, password) " +
                "VALUES (?, ?, ?);";
        template.update(sql,
                entity.getIdentifier(),
                entity.getUsername(),
                entity.getPassword());
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE users " +
                "SET identifier = ?, username = ?, password = ? " +
                "WHERE identifier = ?;";
        Long id = entity.getIdentifier();
        template.update(sql, id, entity.getUsername(), id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE identifier = ?;";
        template.update(sql, id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username = ?;";
            User user = template.queryForObject(
                    sql, new UserRowMapper(), username);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException ignored) {
        }
        return Optional.empty();
    }
}
