package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.domain.poll.PollStatus;


/**
 * Created by sanasov on 04.04.2017.
 */
public class CreateNewPollAction implements CreatePollAction {

    private PollStateMachine machine;

    public CreateNewPollAction(PollStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public PollExchange applyToPoll(String possibleAnswer) {
        machine.getPollExchange().setStatus(PollStatus.CREATE_QUESTION);
        machine.getPollExchange().setResponseText(ResponseMessagesInCreatingPollProcess.WRITE_QUESTION);
        machine.getPollExchange().setReplyKeyboardMarkup(null);
        return machine.getPollExchange();
    }
}
