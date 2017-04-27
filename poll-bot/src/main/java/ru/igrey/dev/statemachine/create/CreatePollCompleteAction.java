package ru.igrey.dev.statemachine.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.igrey.dev.ReplyKeyboard;
import ru.igrey.dev.domain.poll.Poll;
import ru.igrey.dev.domain.poll.PollStatus;

import static ru.igrey.dev.statemachine.create.ResponseMessagesInCreatingPollProcess.POLL_CREATED;

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
    public PollExchange applyToPoll(String possibleAnswer) {
        logger.info(machine.getPollExchange().getAuthor() + " poll created " + machine.getPollExchange().getPoll().getPollId());
        PollExchange newPollExchange = new PollExchange(new Poll(String.valueOf(System.currentTimeMillis())), ReplyKeyboard.getKeyboardOnUserStart(), POLL_CREATED, PollStatus.NEW);
        newPollExchange.setAuthor(machine.getPollExchange().getAuthor());
        return newPollExchange;
    }

}
