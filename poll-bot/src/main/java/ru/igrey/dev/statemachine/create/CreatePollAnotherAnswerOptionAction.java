package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.ReplyKeyboard;
import ru.igrey.dev.domain.poll.AnswerOption;

import static ru.igrey.dev.statemachine.create.ResponseMessagesInCreatingPollProcess.ANOTHER_ANSWER_ADDED;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollAnotherAnswerOptionAction implements CreatePollAction {

    private PollStateMachine machine;

    public CreatePollAnotherAnswerOptionAction(PollStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public PollExchange applyToPoll(String possibleAnswer) {
        PollExchange pollExchange = machine.getPollExchange();
        pollExchange.setResponseText(ANOTHER_ANSWER_ADDED);
        pollExchange.getPoll().addAnswer(new AnswerOption(possibleAnswer));
        pollExchange.setReplyKeyboardMarkup(ReplyKeyboard.getKeyboardOnCompleteCreatingPoll());
        return pollExchange;
    }

}
