package ru.igrey.dev;

import ru.igrey.dev.domain.poll.Poll;
import ru.igrey.dev.domain.poll.PollStatus;
import ru.igrey.dev.domain.TelegramUser;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollProcess {

    private Poll poll;

    public CreatePollProcess(String pollId, TelegramUser author) {
        this.poll = new Poll(pollId, author, PollStatus.NEW);
    }

    public void createPoll() {

    }
}
