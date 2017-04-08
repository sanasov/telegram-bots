package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.domain.poll.Poll;
import ru.igrey.dev.domain.poll.PollStatus;

import static ru.igrey.dev.statemachine.create.ReponseMessagesInCreatingPollProcess.NOW_WRITE_QUESTION;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollNameAction implements CreatePollAction {

    private PollStateMachine machine;

    public CreatePollNameAction(PollStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public void applyToPoll(String pollName) {
        PollExchange pollExchange = machine.getPollExchange();
        Poll namedPoll = pollExchange.getPoll().toNewName(pollName);
        pollExchange.setStatus(PollStatus.CREATE_QUESTION);
        pollExchange.setPoll(namedPoll);
        pollExchange.setResponseText(NOW_WRITE_QUESTION);
        pollExchange.setReplyKeyboardMarkup(null);
        machine.setCurrentAction(machine.getQuestionPollAction());
    }

}
