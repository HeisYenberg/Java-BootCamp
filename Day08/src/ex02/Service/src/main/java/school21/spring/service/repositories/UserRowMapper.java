package school21.spring.service.repositories;

import org.springframework.jdbc.core.RowMapper;
import school21.spring.service.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getLong("identifier"),
                rs.getString("email"),
                rs.getString("password"));
    }
}
