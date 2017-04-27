package ru.igrey.dev.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.igrey.dev.entity.TelegramUserEntity;

import java.util.List;

/**
 * Created by sanasov on 26.04.2017.
 */
public class TelegramUserDao {
    private JdbcTemplate jdbcTemplate;

    public TelegramUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(TelegramUserEntity user) {
        if (findById(user.getUserId()) == null) {
            insert(user);
        } else {
            update(user);
        }
    }

    public void insert(TelegramUserEntity user) {
        String sqlInsert = "INSERT INTO user (id, user_name, first_name, last_name, is_active, status, poll_exchange,polls)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlInsert, new Object[]{
                user.getUserId(),
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getActive(),
                user.getStatus(),
                user.getPollExchange(),
                user.getMyPolls()
        });
    }

    public void update(TelegramUserEntity user) {
        String sqlUpdate = "update user set" +
                " user_name = ?," +
                " first_name = ?," +
                " last_name = ?," +
                " is_active = ?," +
                " status = ?," +
                " poll_exchange = ?," +
                " polls = ?" +
                " where id = ?";
        jdbcTemplate.update(sqlUpdate, new Object[]{
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getActive(),
                user.getStatus(),
                user.getPollExchange(),
                user.getMyPolls(),
                user.getUserId(),
        });
    }

    public void delete(Long userId) {
        String sqlDelete = "delete from user where id = ?";
        jdbcTemplate.update(sqlDelete, new Object[]{userId});
    }

    public TelegramUserEntity findById(Long userId) {
        String sql = "SELECT * FROM USER WHERE ID = ?";
        List<TelegramUserEntity> userEntities = jdbcTemplate.query(
                sql, new Object[]{userId}, new TelegramUserMapper());
        return userEntities.isEmpty() ? null : userEntities.get(0);
    }

    public List<TelegramUserEntity> findAll() {
        String sql = "SELECT * FROM USER";
        List<TelegramUserEntity> users = jdbcTemplate.query(sql,
                new TelegramUserMapper());
        return users;
    }
}
