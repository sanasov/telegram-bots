package ru.igrey.dev.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.igrey.dev.entity.TelegramUserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sanasov on 26.04.2017.
 */
public class TelegramUserMapper implements RowMapper
{
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setUserId(rs.getLong("ID"));
        user.setFirstName(rs.getString("FIRST_NAME"));
        user.setLastName(rs.getString("LAST_NAME"));
        user.setUserName(rs.getString("USER_NAME"));
        user.setStatus(rs.getString("STATUS"));
        user.setMyPolls(rs.getString("POLLS"));
        user.setActive(rs.getBoolean("IS_ACTIVE"));
        user.setPollExchange(rs.getString("POLL_EXCHANGE"));
        return user;
    }

}