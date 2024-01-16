package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;
    private final Map<Long, Chatroom> roomCache;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.roomCache = new HashMap<>();
    }

    @Override
    public List<User> findAll(int page, int size) {
        List<User> users = new LinkedList<>();
        try {
            ResultSet set = getResultSet(page, size);
            while (set.next()) {
                Long id = set.getLong("id");
                String login = set.getString("login");
                String password = set.getString("password");
                User user = new User(id, login, password, null, null);
                user.setCreatedRooms(getCreatedRooms(user, set));
                user.setRooms(getRooms(set, users));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new NotSavedSubEntityException(e.getMessage());
        }
        return users;
    }

    private ResultSet getResultSet(int page, int size) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "WITH paginated_users AS (SELECT * FROM users " +
                        "ORDER BY id LIMIT ? OFFSET ?), " +
                        "user_chatrooms AS (" +
                        "SELECT u.id, " +
                        "array_agg(DISTINCT cr.id) AS created_rooms_ids, " +
                        "array_agg(DISTINCT cr.name) AS created_rooms_names, " +
                        "array_agg(DISTINCT mr.id) AS rooms_ids, " +
                        "array_agg(DISTINCT mr.name) AS rooms_names, " +
                        "array_agg(DISTINCT o.id) AS owners_ids, " +
                        "array_agg(DISTINCT o.login) AS owners_logins," +
                        "array_agg(DISTINCT o.password) AS owners_passwords " +
                        "FROM paginated_users u " +
                        "LEFT JOIN chatrooms cr ON u.id = cr.owner " +
                        "LEFT JOIN messages m ON u.id = m.author " +
                        "LEFT JOIN chatrooms  mr ON m.room = mr.id " +
                        "LEFT JOIN users o ON mr.owner = o.id GROUP BY u.id) " +
                        "SELECT u.*," +
                        "       uc.created_rooms_ids," +
                        "       uc.created_rooms_names," +
                        "       uc.rooms_ids," +
                        "       uc.rooms_names," +
                        "       uc.owners_ids," +
                        "       uc.owners_logins," +
                        "       uc.owners_passwords " +
                        "FROM paginated_users u " +
                        "LEFT JOIN user_chatrooms uc ON u.id = uc.id;");
        statement.setInt(1, size);
        statement.setInt(2, page * size);
        return statement.executeQuery();
    }

    private List<Chatroom> getCreatedRooms(User owner, ResultSet set)
            throws SQLException {
        List<Chatroom> rooms = new LinkedList<>();
        Long[] ids = (Long[]) set.getArray("created_rooms_ids").getArray();
        String[] names =
                (String[]) set.getArray("created_rooms_names").getArray();
        if (ids[0] != null) {
            for (int i = 0; i < ids.length; ++i) {
                Chatroom chatroom = roomCache.get(ids[i]);
                if (chatroom == null) {
                    chatroom = new Chatroom(ids[i], names[i], owner,
                            null);
                    roomCache.put(ids[i], chatroom);
                }
                rooms.add(chatroom);
            }
        }
        return rooms;
    }

    private List<Chatroom> getRooms(ResultSet set, List<User> users)
            throws SQLException {
        List<Chatroom> rooms = new LinkedList<>();
        Long[] roomsIds = (Long[]) set.getArray("rooms_ids").getArray();
        String[] names = (String[]) set.getArray("rooms_names").getArray();
        Long[] ownersIds = (Long[]) set.getArray("owners_ids").getArray();
        String[] logins = (String[]) set.getArray("owners_logins").getArray();
        String[] passwords =
                (String[]) set.getArray("owners_passwords").getArray();
        if (roomsIds[0] != null && ownersIds[0] != null) {
            for (int i = 0; i < roomsIds.length; ++i) {
                Chatroom chatroom = roomCache.get(roomsIds[i]);
                if (chatroom == null) {
                    User owner = getOwner(users, ownersIds[i], logins[i],
                            passwords[i]);
                    chatroom = new Chatroom(roomsIds[i], names[i], owner, null);
                    owner.getRooms().add(chatroom);
                    roomCache.put(roomsIds[i], chatroom);
                }
                rooms.add(chatroom);
            }
        }
        return rooms;
    }

    private User getOwner(List<User> users, Long id, String login,
                          String password) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return new User(id, login, password, new LinkedList<>(),
                new LinkedList<>());
    }
}
