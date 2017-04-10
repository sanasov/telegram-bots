package ru.igrey.dev;

import ru.igrey.dev.domain.TelegramUser;
import ru.igrey.dev.domain.poll.Poll;

import java.util.Collection;

/**
 * Created by sanasov on 03.04.2017.
 */
public class PollService {


    public Poll findPollById(String id) {
        return TelegramUserService.telegramUsers.stream()
                .map(TelegramUser::myPolls)
                .flatMap(Collection::stream)
                .filter(poll -> poll.getPollId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Couldn't find Poll with id " + id));
    }

    public void deletePollById(String pollId, Long chatId) {
        TelegramUser user = TelegramUserService.telegramUsers.stream()
                .filter(telegramUser -> telegramUser.userId().equals(chatId))
                .findFirst()
                .get();
        Poll poll = user.myPolls().stream()
                .filter(poll1 -> poll1.getPollId().equals(pollId))
                .findAny()
                .get();
        user.myPolls().remove(poll);
    }

}
