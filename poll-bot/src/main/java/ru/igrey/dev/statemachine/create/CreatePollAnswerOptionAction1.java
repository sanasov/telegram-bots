package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.domain.poll.AnswerOption;
import ru.igrey.dev.domain.poll.PollStatus;

import static ru.igrey.dev.statemachine.create.ResponseMessagesInCreatingPollProcess.ADD_SECOND_ANSWER;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollAnswerOptionAction1 implements CreatePollAction {

    private PollStateMachine machine;

    public CreatePollAnswerOptionAction1(PollStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public PollExchange applyToPoll(String possibleAnswer) {
        PollExchange pollExchange = machine.getPollExchange();
        pollExchange.setStatus(PollStatus.CREATE_ANSWER2);
        pollExchange.setResponseText(ADD_SECOND_ANSWER);
        pollExchange.setReplyKeyboardMarkup(null);
        pollExchange.getPoll().addAnswer(new AnswerOption(possibleAnswer));
        return pollExchange;
    }
}
