package ru.igrey.dev.statemachine.create;

import static ru.igrey.dev.statemachine.create.PollExchange.createNewPollExchange;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreateNewPollAction implements CreatePollAction {

    private PollStateMachine machine;

    public CreateNewPollAction(PollStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public void applyToPoll(String possibleAnswer) {
        machine.setPollExchange(createNewPollExchange());
        machine.setCurrentAction(machine.getNamePollAction());
    }
}
