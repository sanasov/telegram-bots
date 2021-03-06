package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.domain.poll.Poll;
import ru.igrey.dev.domain.poll.PollStatus;

import static ru.igrey.dev.statemachine.create.ResponseMessagesInCreatingPollProcess.ADD_FIRST_ANSWER;

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
        PollExchange pollExchange = machine.getPollExchange();
        Poll pollWithQuestion = pollExchange.getPoll().toNewQuestion(question);
        pollExchange.setPoll(pollWithQuestion);
        pollExchange.setStatus(PollStatus.CREATE_ANSWER1);
        pollExchange.setResponseText(ADD_FIRST_ANSWER);
        machine.setCurrentAction(machine.getAnswerOptionAction1());
        pollExchange.setReplyKeyboardMarkup(null);
    }
}
