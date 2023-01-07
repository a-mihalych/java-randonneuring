package ru.mihalych.randonneuring.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.mihalych.randonneuring.exception.NotFoundException;
import ru.mihalych.randonneuring.model.Brevet;
import ru.mihalych.randonneuring.storage.interfaces.BrevetStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class BrevetDBStorage implements BrevetStorage {

    private final JdbcTemplate jdbsTemplate;

    @Override
    public List<Brevet> getBrevets() {
        String sql = "SELECT * FROM BREVETS";
        return jdbsTemplate.query(sql, this::makeBrevet);
    }

    @Override
    public Brevet getBrevet(Integer id) {
        String sql = "SELECT * FROM BREVETS WHERE BREVET_ID = ?";
        List<Brevet> brevets = jdbsTemplate.query(sql, this::makeBrevet, id);
        if (brevets.size() != 1) {
            throw new NotFoundException("Бревет не найдён");
        }
        return brevets.get(0);
    }

    @Override
    public Brevet createBrevet(Brevet brevet) {
        String sql = "INSERT INTO BREVETS(BREVET_NAME, DISTANCE) " +
                     "VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbsTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"BREVET_ID"});
            stmt.setString(1, brevet.getName());
            stmt.setInt(2, brevet.getDistance());
            return stmt;
        }, keyHolder);
        brevet.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return brevet;
    }

    @Override
    public Brevet saveBrevet(Brevet brevet) {
        String sql = "UPDATE BREVETS SET " +
                     "BREVET_NAME = ?, DISTANCE = ?";
        jdbsTemplate.update(sql, brevet.getName(), brevet.getDistance());
        return brevet;
    }

    private Brevet makeBrevet(ResultSet rs, int rowNum) throws SQLException {
        return Brevet.builder()
                .id(rs.getInt("BREVET_ID"))
                .name(rs.getString("BREVET_NAME"))
                .distance(rs.getInt("DISTANCE"))
                .build();
    }
}
