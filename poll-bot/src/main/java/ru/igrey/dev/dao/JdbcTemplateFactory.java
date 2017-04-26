package ru.igrey.dev.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.sqlite.SQLiteDataSource;

import java.sql.SQLException;

/**
 * Created by sanasov on 26.04.2017.
 */
public class JdbcTemplateFactory {
    public JdbcTemplate create() {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite:C:/trainings/sqlite/botdb.s3db");
        sqLiteDataSource.setDatabaseName("botdb");
        return new JdbcTemplate(sqLiteDataSource);
    }


    public static void main(String[] args) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplateFactory().create();
        TelegramUserDao dao = new TelegramUserDao(jdbcTemplate);
        dao.delete(12L);
        dao.delete(123L);
    }
}
