package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.domain.poll.Poll;

/**
 * Created by sanasov on 06.04.2017.
 */
public class PollStateMachine {

    private CreatePollAction newPollAction;
    private CreatePollAction namePollAction;
    private CreatePollAction answerOptionAction1;
    private CreatePollAction answerAnotherOptionAction;
    private CreatePollAction answerOptionAction2;
    private CreatePollAction questionPollAction;
    private CreatePollAction completePollAction;

    private CreatePollAction currentAction;
    private Poll poll;
    private Boolean isComplete = false;


    public PollStateMachine(Poll poll) {
        this.poll = poll;
        newPollAction = new CreateNewPollAction(this);
        namePollAction = new CreatePollNameAction(this);
        answerOptionAction1 = new CreatePollAnswerOptionAction1(this);
        answerOptionAction2 = new CreatePollAnswerOptionAction2(this);
        answerAnotherOptionAction = new CreatePollAnotherAnswerOptionAction(this);
        questionPollAction = new CreatePollQuestionAction(this);
        completePollAction = new CreatePollCompleteAction(this);
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
            case NEW:
                return newPollAction;
            case CREATE_NAME:
                return namePollAction;
            case CREATE_QUESTION:
                return questionPollAction;
            case CREATE_ANSWER1:
                return answerOptionAction1;
            case CREATE_ANSWER2:
                return answerOptionAction2;
            case CREATE_ANOTHER_ANSWER:
                return answerAnotherOptionAction;
            case COMPLETED:
                return completePollAction;
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

    public CreatePollAction getAnswerOptionAction1() {
        return answerOptionAction1;
    }

    public CreatePollAction getAnswerAnotherOptionAction() {
        return answerAnotherOptionAction;
    }

    public CreatePollAction getAnswerOptionAction2() {
        return answerOptionAction2;
    }

    public CreatePollAction getCompletePollAction() {
        return completePollAction;
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
