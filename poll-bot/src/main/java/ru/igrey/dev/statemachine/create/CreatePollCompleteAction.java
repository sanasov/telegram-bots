package ru.igrey.dev.statemachine.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.igrey.dev.ReplyKeyboard;
import ru.igrey.dev.domain.poll.PollStatus;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollCompleteAction implements CreatePollAction {

    private PollStateMachine machine;
    private static final Logger logger = LoggerFactory.getLogger(PollExchange.class);

    public CreatePollCompleteAction(PollStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public void applyToPoll(String possibleAnswer) {
        PollExchange pollExchange = machine.getPollExchange();
        machine.setCurrentAction(machine.getNewPollAction());
        pollExchange.setStatus(PollStatus.NEW);
        pollExchange.setReplyKeyboardMarkup(ReplyKeyboard.getKeyboardOnUserStart());
        logger.info(machine.getAuthor() + " created new poll " + pollExchange.getPoll().getPollId());
    }

}
