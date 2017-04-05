package ru.igrey.dev.statemachine.createengine;

import ru.igrey.dev.domain.Poll;
import ru.igrey.dev.domain.PollStatus;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollQuestionAction implements CreatePollAction {

    private String question;

    public CreatePollQuestionAction(String question) {
        this.question = question;
    }

    @Override
    public Poll applyToPoll(Poll poll) {
        Poll pollWithQuestion = poll.toNewQuestion(question);
        return pollWithQuestion.toNewStatus(PollStatus.CREATE_ANSWERS);
    }
}
