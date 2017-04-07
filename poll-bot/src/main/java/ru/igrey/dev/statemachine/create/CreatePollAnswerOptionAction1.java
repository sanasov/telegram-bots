package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.domain.AnswerOption;
import ru.igrey.dev.domain.poll.PollStatus;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollAnswerOptionAction1 implements CreatePollAction {

    private PollStateMachine machine;

    public CreatePollAnswerOptionAction1(PollStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public void applyToPoll(String possibleAnswer) {
        machine.setPoll(machine.getPoll().toNewStatus(PollStatus.CREATE_ANSWER2));
        machine.setCurrentAction(machine.getAnswerOptionAction2());
        machine.getPoll().addAnswer(new AnswerOption(possibleAnswer));
    }

    @Override
    public String responseOnCreateAction() {
        return "Добавьте второй вариант ответа";
    }
}
