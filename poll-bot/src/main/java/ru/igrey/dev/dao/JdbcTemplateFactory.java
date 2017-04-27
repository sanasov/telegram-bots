package ru.igrey.dev.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.sqlite.SQLiteDataSource;
import ru.igrey.dev.domain.AnswerOption;
import ru.igrey.dev.domain.TelegramUser;
import ru.igrey.dev.domain.UserProcessStatus;
import ru.igrey.dev.domain.poll.Poll;
import ru.igrey.dev.statemachine.create.PollExchange;
import ru.igrey.dev.statemachine.create.PollStateMachine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
        TelegramUser telegramUser = createTelegramUser();
        dao.save(telegramUser.toEntity());
        TelegramUser.fromEntity(dao.findById(11L));
    }

    private static TelegramUser createTelegramUser() {
        PollStateMachine machine = new PollStateMachine(PollExchange.createNewPollExchange());
        TelegramUser telegramUser = new TelegramUser(
                11L,
                "user.getFirstName()",
                "user.getLastName()",
                "user.getUserName()",
                UserProcessStatus.START,
                new ArrayList<>(),
                machine
        );
        machine.setAuthor(telegramUser);
        telegramUser.myPolls().addAll(createPolls());
        return telegramUser;
    }

    private static List<Poll> createPolls() {
        return Arrays.asList(
                new Poll("poll_id1", "Question1",
                        Arrays.asList(
                                new AnswerOption("Answer11", new HashSet<>(Arrays.asList(111L, 112L))),
                                new AnswerOption("Answer12", new HashSet<>(Arrays.asList(111L, 112L)))
                        )),
                new Poll("poll_id1", "Question2",
                        Arrays.asList(
                                new AnswerOption("Answer21", new HashSet<>(Arrays.asList(211L, 212L))),
                                new AnswerOption("Answer22", new HashSet<>(Arrays.asList(211L, 212L)))
                        ))
        );
    }
}
