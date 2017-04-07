package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.domain.AnswerOption;
import ru.igrey.dev.domain.poll.PollStatus;

import static ru.igrey.dev.KeyboardText.COMPLETE_CREATE_POLL;

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
        completeIfNeeded(possibleAnswer);
        if (machine.getComplete()) {
            machine.setPoll(machine.getPoll().toNewStatus(PollStatus.COMPLETED));
            machine.setCurrentAction(machine.getCompletePollAction());
        } else {
            machine.getPoll().addAnswer(new AnswerOption(possibleAnswer));
        }
    }

    private void completeIfNeeded(String incomingMessage) {
        if (incomingMessage.equals(COMPLETE_CREATE_POLL)) {
            machine.complete();
        }
    }

    @Override
    public String responseOnCreateAction() {
        if (machine.getComplete()) {
            return "Опросник готов";
        } else {
            return "Ответ добавлен";
        }
    }
}
