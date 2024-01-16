package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component("usersRepositoryJdbcTemplate")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private final JdbcTemplate template;

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(
            @Qualifier("driverManagerDataSource") DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
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
        String sql = "INSERT INTO users (identifier, email, password) " +
                "VALUES (?, ?, ?);";
        template.update(sql,
                entity.getIdentifier(),
                entity.getEmail(),
                entity.getPassword());
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE users " +
                "SET identifier = ?, email = ?, password = ? " +
                "WHERE identifier = ?;";
        Long id = entity.getIdentifier();
        template.update(sql, id, entity.getEmail(), id);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE identifier = ?;";
        template.update(sql, id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            String sql = "SELECT * FROM users WHERE email = ?;";
            User user = template.queryForObject(
                    sql, new UserRowMapper(), email);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException ignored) {
        }
        return Optional.empty();
    }
}
