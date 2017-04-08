package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.ReplyKeyboard;
import ru.igrey.dev.domain.AnswerOption;
import ru.igrey.dev.domain.poll.PollStatus;

import static ru.igrey.dev.KeyboardText.COMPLETE_CREATE_POLL;
import static ru.igrey.dev.statemachine.create.ReponseMessagesInCreatingPollProcess.SECOND_ANSWER_ADDED;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollAnswerOptionAction2 implements CreatePollAction {

    private PollStateMachine machine;

    public CreatePollAnswerOptionAction2(PollStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public void applyToPoll(String possibleAnswer) {
        PollExchange pollExchange = machine.getPollExchange();
        completeIfNeeded(possibleAnswer);
        if (pollExchange.getComplete()) {
            machine.setCurrentAction(machine.getCompletePollAction());
        } else {
            pollExchange.getPoll().addAnswer(new AnswerOption(possibleAnswer));
            pollExchange.setResponseText(SECOND_ANSWER_ADDED);
            pollExchange.setReplyKeyboardMarkup(ReplyKeyboard.getKeyboardOnCompleteCreatingPoll());
            pollExchange.setPoll(pollExchange.getPoll().toNewStatus(PollStatus.CREATE_ANOTHER_ANSWER));
            machine.setCurrentAction(machine.getAnswerAnotherOptionAction());
        }
    }

    private void completeIfNeeded(String incomingMessage) {
        if (incomingMessage.equals(COMPLETE_CREATE_POLL)) {
            machine.complete();
        }
    }
}
