package ru.igrey.dev.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.User;
import ru.igrey.dev.dao.TelegramUserDao;
import ru.igrey.dev.domain.TelegramUser;
import ru.igrey.dev.domain.UserProcessStatus;
import ru.igrey.dev.statemachine.create.PollExchange;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanasov on 10.04.2017.
 */
public class TelegramUserService {

    private TelegramUserDao telegramUserDao;
    private static final Logger logger = LoggerFactory.getLogger(TelegramUserService.class);
    public static List<TelegramUser> telegramUsers = new ArrayList<>();

    public TelegramUserService(TelegramUserDao telegramUserDao) {
        this.telegramUserDao = telegramUserDao;
    }

    public TelegramUser getOrCreateTelegramUserByUserId(User user) {
        TelegramUser result = telegramUsers.stream()
                .filter(u -> u.userId().equals(user.getId().longValue()))
                .findAny()
                .orElse(null);
        if (result == null) {
            result = createTelegramUser(user);
            saveTelegramUser(result);
            telegramUsers.add(result);
        }
        return result;
    }

    private TelegramUser createTelegramUser(User user) {
        PollExchange pollExchange = PollExchange.createNewPollExchange();
        TelegramUser telegramUser = new TelegramUser(
                user.getId().longValue(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                UserProcessStatus.START,
                new ArrayList<>(),
                pollExchange
        );
        pollExchange.setAuthor(telegramUser);
        logger.info("created user " + telegramUser);
        return telegramUser;
    }

    public void saveTelegramUser(TelegramUser author) {
        telegramUserDao.save(author.toEntity());
    }
}
