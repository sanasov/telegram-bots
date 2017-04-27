package ru.igrey.dev;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.igrey.dev.dao.JdbcTemplateFactory;
import ru.igrey.dev.dao.TelegramUserDao;
import ru.igrey.dev.domain.TelegramUser;

import java.util.stream.Collectors;

/**
 * Created by sanasov on 01.04.2017.
 */
public class PollBotStart {

    public static void main(String[] args) {

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(createPollBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static PollBot createPollBot() {
        TelegramUserDao userDao = new TelegramUserDao(new JdbcTemplateFactory().create());
        loadAllTelegramUsersFromDb(userDao);
        return new PollBot(
                new PollService(userDao),
                new TelegramUserService(userDao)
        );
    }

    private static void loadAllTelegramUsersFromDb(TelegramUserDao userDao) {
        TelegramUserService.telegramUsers.addAll(
                userDao.findAll()
                        .stream()
                        .map(entity -> TelegramUser.fromEntity(entity))
                        .collect(Collectors.toList())
        );
    }
}
