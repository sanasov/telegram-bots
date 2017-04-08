package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.ReplyKeyboard;
import ru.igrey.dev.domain.AnswerOption;

import static ru.igrey.dev.KeyboardText.COMPLETE_CREATE_POLL;
import static ru.igrey.dev.statemachine.create.ReponseMessagesInCreatingPollProcess.ANOTHER_ANSWER_ADDED;

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
        completeIfNeeded(possibleAnswer);
        if (pollExchange.getComplete()) {
            machine.setCurrentAction(machine.getCompletePollAction());
        } else {
            pollExchange.setResponseText(ANOTHER_ANSWER_ADDED);
            pollExchange.getPoll().addAnswer(new AnswerOption(possibleAnswer));
            pollExchange.setReplyKeyboardMarkup(ReplyKeyboard.getKeyboardOnCompleteCreatingPoll());
        }
    }

    private void completeIfNeeded(String incomingMessage) {
        if (incomingMessage.equals(COMPLETE_CREATE_POLL)) {
            machine.complete();
        }
    }

}
