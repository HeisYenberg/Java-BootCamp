package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component("usersRepositoryJdbc")
public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;

    @Autowired
    public UsersRepositoryJdbcImpl(
            @Qualifier("hikariDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE identifier = ?;");
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                User user = new User(
                        set.getLong("identifier"),
                        set.getString("email"),
                        set.getString("password"));
                return Optional.of(user);
            }
            connection.close();
        } catch (SQLException ignored) {
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new LinkedList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users;");
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                users.add(new User(
                        set.getLong("identifier"),
                        set.getString("email"),
                        set.getString("password")));
            }
            connection.close();
        } catch (SQLException ignored) {
        }
        return users;
    }

    @Override
    public void save(User entity) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (identifier, email, password) " +
                            "VALUES (?, ?, ?);");
            statement.setLong(1, entity.getIdentifier());
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getPassword());
            if (statement.executeUpdate() == 0) {
                throw new SQLException("Unable to save entity");
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(User entity) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE users SET identifier = ?, email = ?, password = ?" +
                            " WHERE identifier = ?;");
            Long id = entity.getIdentifier();
            statement.setLong(1, id);
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getPassword());
            statement.setLong(4, id);
            if (statement.executeUpdate() == 0) {
                throw new SQLException("Unable to update entity");
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users WHERE identifier = ?;");
            statement.setLong(1, id);
            if (statement.executeUpdate() == 0) {
                throw new SQLException("Unable to delete entity");
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE email = ?;");
            statement.setString(1, email);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                User user = new User(
                        set.getLong("identifier"),
                        set.getString("email"),
                        set.getString("password"));
                return Optional.of(user);
            }
            connection.close();
        } catch (SQLException ignored) {
        }
        return Optional.empty();
    }
}
