package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.TelegramUserService;
import ru.igrey.dev.domain.UserProcessStatus;

import static ru.igrey.dev.KeyboardText.COMPLETE_CREATE_POLL;

/**
 * Created by sanasov on 06.04.2017.
 */
public class PollStateMachine {

    private CreatePollAction newPollAction;
    private CreatePollAction answerOptionAction1;
    private CreatePollAction answerAnotherOptionAction;
    private CreatePollAction answerOptionAction2;
    private CreatePollAction questionPollAction;
    private CreatePollAction completePollAction;

    private CreatePollAction currentAction;

    private PollExchange pollExchange;
    private TelegramUserService telegramUserService;

    public PollStateMachine(PollExchange pollExchange, TelegramUserService telegramUserService) {
        this.pollExchange = pollExchange;
        newPollAction = new CreateNewPollAction(this);
        answerOptionAction1 = new CreatePollAnswerOptionAction1(this);
        answerOptionAction2 = new CreatePollAnswerOptionAction2(this);
        answerAnotherOptionAction = new CreatePollAnotherAnswerOptionAction(this);
        questionPollAction = new CreatePollQuestionAction(this);
        completePollAction = new CreatePollCompleteAction(this);
        currentAction = createCurrentState(pollExchange);
        this.telegramUserService = telegramUserService;
    }

    public PollExchange apply(String incomingText) {
        if (incomingText.equals(COMPLETE_CREATE_POLL)) {
            return complete();
        } else {
            return currentAction.applyToPoll(incomingText);
        }
    }


    public PollExchange complete() {
        pollExchange.getAuthor().myPolls().add(pollExchange.getPoll());
        pollExchange.getAuthor().setStatus(UserProcessStatus.START);
        telegramUserService.saveTelegramUser(pollExchange.getAuthor());
        return completePollAction.applyToPoll(null);
    }

    private CreatePollAction createCurrentState(PollExchange pollExchange) {
        switch (pollExchange.getStatus()) {
            case NEW:
                return newPollAction;
            case CREATE_QUESTION:
                return questionPollAction;
            case CREATE_ANSWER1:
                return answerOptionAction1;
            case CREATE_ANSWER2:
                return answerOptionAction2;
            case CREATE_ANOTHER_ANSWER:
                return answerAnotherOptionAction;
        }
        throw new IllegalStateException("No state for status: " + pollExchange.getStatus());
    }


    public PollExchange getPollExchange() {
        return pollExchange;
    }

    public void setPollExchange(PollExchange pollExchange) {
        this.pollExchange = pollExchange;
    }

    //getter setter

    public CreatePollAction getAnswerOptionAction1() {
        return answerOptionAction1;
    }

    public CreatePollAction getAnswerAnotherOptionAction() {
        return answerAnotherOptionAction;
    }

    public CreatePollAction getAnswerOptionAction2() {
        return answerOptionAction2;
    }

    public CreatePollAction getQuestionPollAction() {
        return questionPollAction;
    }


    public CreatePollAction getNewPollAction() {
        return newPollAction;
    }

    public CreatePollAction getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(CreatePollAction currentAction) {
        this.currentAction = currentAction;
    }
}
