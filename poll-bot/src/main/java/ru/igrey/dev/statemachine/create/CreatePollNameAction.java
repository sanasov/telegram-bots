package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.domain.poll.Poll;
import ru.igrey.dev.domain.poll.PollStatus;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollNameAction implements CreatePollAction {

    private PollStateMachine machine;

    public CreatePollNameAction(PollStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public void applyToPoll(String pollName) {
        Poll namedPoll = machine.getPoll().toNewName(pollName);
        machine.setPoll(namedPoll.toNewStatus(PollStatus.CREATE_QUESTION));
        machine.setCurrentAction(machine.getQuestionPollAction());
    }

    @Override
    public String responseOnCreateAction() {
        return "Теперь придумайте и запишите вопрос";
    }
}
