package ru.igrey.dev.statemachine.create;

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

    }

}
