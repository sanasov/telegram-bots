package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.domain.AnswerOption;
import ru.igrey.dev.domain.poll.PollStatus;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollAnswerOptionAction implements CreatePollAction {

    private PollStateMachine machine;

    public CreatePollAnswerOptionAction(PollStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public void applyToPoll(String possibleAnswer) {
        if (machine.getComplete()) {
             machine.setPoll(machine.getPoll().toNewStatus(PollStatus.COMPLETED));
             machine.setCurrentAction(null);
        }
        machine.getPoll().addAnswer(new AnswerOption(possibleAnswer));
    }

    @Override
    public String responseOnCreateAction() {
        return "Ответ добавлен";
    }
}
