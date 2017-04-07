package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.domain.poll.Poll;
import ru.igrey.dev.domain.poll.PollStatus;

import static ru.igrey.dev.statemachine.create.ReponseMessagesInCreatingPollProcess.ADD_FIRST_ANSWER;

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
        Poll pollWithQuestion = machine.getPollExchange().getPoll().toNewQuestion(question);
        machine.getPollExchange().setPoll(pollWithQuestion.toNewStatus(PollStatus.CREATE_ANSWER1));
        machine.getPollExchange().setResponseText(ADD_FIRST_ANSWER);
        machine.setCurrentAction(machine.getAnswerOptionAction1());
    }
}
