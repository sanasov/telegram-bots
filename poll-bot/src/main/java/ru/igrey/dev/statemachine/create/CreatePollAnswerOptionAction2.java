package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.domain.AnswerOption;
import ru.igrey.dev.domain.poll.PollStatus;

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
        machine.getPoll().addAnswer(new AnswerOption(possibleAnswer));
        machine.setPoll(machine.getPoll().toNewStatus(PollStatus.CREATE_ANOTHER_ANSWER));
        machine.setCurrentAction(machine.getAnswerAnotherOptionAction());

    }

    @Override
    public String responseOnCreateAction() {
        return "Второй ответ добавлен. Добавляйте пока не надоест";
    }
}
