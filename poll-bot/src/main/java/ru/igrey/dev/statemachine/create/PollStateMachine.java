package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.domain.poll.Poll;
import ru.igrey.dev.domain.poll.PollStatus;

/**
 * Created by sanasov on 06.04.2017.
 */
public class PollStateMachine {

    private CreatePollAction namePollAction;
    private CreatePollAction answerOptionAction;
    private CreatePollAction questionPollAction;

    private CreatePollAction currentAction;
    private Poll poll;
    private Boolean isComplete = false;


    public PollStateMachine(Poll poll) {
        if (poll.status().equals(PollStatus.COMPLETED)) {
            complete();
        }

        namePollAction = new CreatePollNameAction(this);
        answerOptionAction = new CreatePollAnswerOptionAction(this);
        questionPollAction = new CreatePollQuestionAction(this);
        currentAction = createCurrentState(poll);
    }

    public void create(String incomingText) {
        currentAction.applyToPoll(incomingText);
    }

    public String getResponseOnCreateAction() {
        return currentAction.responseOnCreateAction();
    }

    private CreatePollAction createCurrentState(Poll poll) {
        switch (poll.status()) {
            case CREATE_NAME:
                return namePollAction;
            case CREATE_QUESTION:
                return questionPollAction;
            case CREATE_ANSWERS:
                return answerOptionAction;
        }
        throw new IllegalStateException("No state for status: " + poll.status());
    }

    public void complete() {
        isComplete = true;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Boolean getComplete() {
        return isComplete;
    }


    //getter setter
    public CreatePollAction getNamePollAction() {
        return namePollAction;
    }

    public CreatePollAction getAnswerOptionAction() {
        return answerOptionAction;
    }

    public CreatePollAction getQuestionPollAction() {
        return questionPollAction;
    }

    public CreatePollAction getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(CreatePollAction currentAction) {
        this.currentAction = currentAction;
    }
}
