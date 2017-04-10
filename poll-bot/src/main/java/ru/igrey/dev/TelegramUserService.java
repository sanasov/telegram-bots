package ru.igrey.dev;

import org.telegram.telegrambots.api.objects.Chat;
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

    public static List<TelegramUser> telegramUsers = new ArrayList<>();

    public TelegramUser getOrCreateTelegramUserByUserId(Chat chat) {
        TelegramUser result = telegramUsers.stream()
                .filter(u -> u.userId().equals(chat.getId()))
                .findAny()
                .orElse(null);
        if (result == null) {
            result = createTelegramUser(chat);
            telegramUsers.add(result);
        }
        return result;
    }

    private TelegramUser createTelegramUser(Chat chat) {
        PollStateMachine machine = new PollStateMachine(PollExchange.createNewPollExchange());
        TelegramUser user = new TelegramUser(
                chat.getId(),
                chat.getFirstName(),
                chat.getLastName(),
                chat.getUserName(),
                UserProcessStatus.START,
                new ArrayList<>(),
                machine
        );
        machine.setAuthor(user);
        return user;
    }
}
