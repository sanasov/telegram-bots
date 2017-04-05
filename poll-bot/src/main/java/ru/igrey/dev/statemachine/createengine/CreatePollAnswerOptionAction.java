package ru.igrey.dev.statemachine.createengine;

import ru.igrey.dev.domain.AnswerOption;
import ru.igrey.dev.domain.Poll;
import ru.igrey.dev.domain.PollStatus;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollAnswerOptionAction implements CreatePollAction {

    private String possibleAnswer;
    private Boolean isComplete;

    public CreatePollAnswerOptionAction(String possibleAnswer, Boolean isComplete) {
        this.possibleAnswer = possibleAnswer;
        this.isComplete = isComplete;
    }

    @Override
    public Poll applyToPoll(Poll poll) {
        if (isComplete) {
            return poll.toNewStatus(PollStatus.COMPLETED);
        }
        poll.addAnswer(new AnswerOption(possibleAnswer));
        return poll;
    }
}
