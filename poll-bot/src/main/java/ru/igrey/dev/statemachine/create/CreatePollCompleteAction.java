package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.domain.poll.PollStatus;

import static ru.igrey.dev.domain.UserProcessStatus.START;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollCompleteAction implements CreatePollAction {

    private PollStateMachine machine;

    public CreatePollCompleteAction(PollStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public void applyToPoll(String possibleAnswer) {
        machine.getPoll().author().changeStatus(START);
        machine.getPoll().author().myPolls().add(machine.getPoll());
        machine.setPoll(machine.getPoll().toNewStatus(PollStatus.NEW));
    }

    @Override
    public String responseOnCreateAction() {
        return "Опросник создан";
    }
}
