package ru.mihalych.randonneuring.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.mihalych.randonneuring.exception.NotFoundException;
import ru.mihalych.randonneuring.model.User;
import ru.mihalych.randonneuring.storage.interfaces.UserStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbsTemplate;

    @Override
    public List<User> getUsers() {
        String sql = "SELECT * FROM USERS";
        return jdbsTemplate.query(sql, this::makeUser);
    }

    @Override
    public User getUser(Integer id) {
        String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
        List<User> users = jdbsTemplate.query(sql, this::makeUser, id);
        if (users.size() != 1) {
            throw new NotFoundException("Не найден пользователь");
        }
        return users.get(0);
    }

    @Override
    public User createUser(User user) {
        String sql = "INSERT INTO USERS(USER_NAME1_RU, USER_NAME2_RU, USER_NAME1_EN, USER_NAME2_EN, NICK) " +
                     "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbsTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"USER_ID"});
            stmt.setString(1, user.getRuNameI());
            stmt.setString(2, user.getRuNameF());
            stmt.setString(3, user.getEnNameI());
            stmt.setString(4, user.getEnNameF());
            stmt.setString(5, user.getNick());
            return stmt;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return user;
    }

    @Override
    public User saveUser(User user) {
        String sql = "UPDATE USERS SET " +
                     "USER_NAME1_RU = ?, USER_NAME2_RU = ?, USER_NAME1_EN = ?, USER_NAME2_EN = ?, NICK = ?";
        jdbsTemplate.update(sql,
                            user.getRuNameI(), user.getRuNameF(),
                            user.getEnNameI(), user.getEnNameF(),
                            user.getNick());
        return user;
    }

    private User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getInt("USER_ID"))
                .ruNameI(rs.getString("USER_NAME1_RU"))
                .ruNameF(rs.getString("USER_NAME2_RU"))
                .enNameI(rs.getString("USER_NAME1_EN"))
                .enNameF(rs.getString("USER_NAME2_EN"))
                .nick(rs.getString("NICK"))
                .build();
    }
}
