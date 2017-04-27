package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.ReplyKeyboard;
import ru.igrey.dev.domain.AnswerOption;
import ru.igrey.dev.domain.poll.PollStatus;

import static ru.igrey.dev.KeyboardText.COMPLETE_CREATE_POLL;
import static ru.igrey.dev.domain.UserProcessStatus.START;
import static ru.igrey.dev.statemachine.create.ResponseMessagesInCreatingPollProcess.ANOTHER_ANSWER_ADDED;
import static ru.igrey.dev.statemachine.create.ResponseMessagesInCreatingPollProcess.POLL_CREATED;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollAnotherAnswerOptionAction implements CreatePollAction {

    private PollStateMachine machine;

    public CreatePollAnotherAnswerOptionAction(PollStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public void applyToPoll(String possibleAnswer) {
        PollExchange pollExchange = machine.getPollExchange();
        if (possibleAnswer.equals(COMPLETE_CREATE_POLL)) {
            machine.setCurrentAction(machine.getCompletePollAction());
            complete();
        } else {
            pollExchange.setResponseText(ANOTHER_ANSWER_ADDED);
            pollExchange.getPoll().addAnswer(new AnswerOption(possibleAnswer));
            pollExchange.setReplyKeyboardMarkup(ReplyKeyboard.getKeyboardOnCompleteCreatingPoll());
        }
    }

    public void complete() {
        PollExchange pollExchange = machine.getPollExchange();
        pollExchange.setStatus(PollStatus.NEW);
        pollExchange.getAuthor().myPolls().add(pollExchange.getPoll());
        pollExchange.setResponseText(POLL_CREATED);
        pollExchange.setReplyKeyboardMarkup(ReplyKeyboard.getKeyboardOnUserStart());
        pollExchange.getAuthor().changeStatus(START);
        machine.getCurrentAction().applyToPoll("");
    }
}
