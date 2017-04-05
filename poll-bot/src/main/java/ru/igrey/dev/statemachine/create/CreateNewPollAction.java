package ru.igrey.dev.statemachine.create;

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
        machine.setCurrentAction(machine.getNamePollAction());
    }

    @Override
    public String responseOnCreateAction() {
        return "Назовите опросник";
    }
}
