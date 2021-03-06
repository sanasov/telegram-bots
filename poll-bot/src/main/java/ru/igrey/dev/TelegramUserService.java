package ru.igrey.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.User;
import ru.igrey.dev.domain.TelegramUser;
import ru.igrey.dev.domain.UserProcessStatus;
import ru.igrey.dev.statemachine.create.PollExchange;
import ru.igrey.dev.statemachine.create.PollStateMachine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanasov on 10.04.2017.
 */
public class TelegramUserService {
    private static final Logger logger = LoggerFactory.getLogger(VoteService.class);
    public static List<TelegramUser> telegramUsers = new ArrayList<>();

    public TelegramUser getOrCreateTelegramUserByUserId(User user) {
        TelegramUser result = telegramUsers.stream()
                .filter(u -> u.userId().equals(user.getId().longValue()))
                .findAny()
                .orElse(null);
        if (result == null) {
            result = createTelegramUser(user);
            telegramUsers.add(result);
        }
        return result;
    }

    private TelegramUser createTelegramUser(User user) {
        PollStateMachine machine = new PollStateMachine(PollExchange.createNewPollExchange());
        TelegramUser telegramUser = new TelegramUser(
                user.getId().longValue(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                UserProcessStatus.START,
                new ArrayList<>(),
                machine
        );
        machine.setAuthor(telegramUser);
        logger.info("created user " + telegramUser);
        return telegramUser;
    }
}
