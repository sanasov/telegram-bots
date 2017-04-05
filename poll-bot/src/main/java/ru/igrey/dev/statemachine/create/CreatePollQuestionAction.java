package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.domain.poll.Poll;
import ru.igrey.dev.domain.poll.PollStatus;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollQuestionAction implements CreatePollAction {

    private PollStateMachine machine;

    public CreatePollQuestionAction(PollStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public void applyToPoll(String question) {
        Poll pollWithQuestion = machine.getPoll().toNewQuestion(question);
        machine.setPoll(pollWithQuestion.toNewStatus(PollStatus.CREATE_ANSWERS));
        machine.setCurrentAction(machine.getQuestionPollAction());
    }

    @Override
    public String responseOnCreateAction() {
        return "Добавьте вариант ответа";
    }
}
