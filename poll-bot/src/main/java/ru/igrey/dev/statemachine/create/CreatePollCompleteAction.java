package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.ReplyKeyboard;
import ru.igrey.dev.domain.poll.PollStatus;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollCompleteAction implements CreatePollAction {

    private PollStateMachine machine;

    public CreatePollCompleteAction(PollStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public void applyToPoll(String possibleAnswer) {
        PollExchange pollExchange = machine.getPollExchange();
        pollExchange.setComplete(false);
        machine.setCurrentAction(machine.getNewPollAction());
        pollExchange.getPoll().setStatus(PollStatus.NEW);
        pollExchange.setReplyKeyboardMarkup(ReplyKeyboard.getKeyboardOnUserStart());
    }

}
