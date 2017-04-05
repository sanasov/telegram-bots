package ru.igrey.dev.statemachine.createengine;

import ru.igrey.dev.domain.Poll;
import ru.igrey.dev.domain.PollStatus;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollNameAction implements CreatePollAction {

    private String pollName;

    public CreatePollNameAction(String pollName) {
        this.pollName = pollName;
    }

    @Override
    public Poll applyToPoll(Poll poll) {
        Poll namedPoll = poll.toNewName(pollName);
        return namedPoll.toNewStatus(PollStatus.CREATE_QUESTION);
    }
}
