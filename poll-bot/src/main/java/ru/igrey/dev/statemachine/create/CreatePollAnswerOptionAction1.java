package ru.igrey.dev.statemachine.create;

import ru.igrey.dev.domain.AnswerOption;
import ru.igrey.dev.domain.poll.PollStatus;

import static ru.igrey.dev.statemachine.create.ReponseMessagesInCreatingPollProcess.ADD_SECOND_ANSWER;

/**
 * Created by sanasov on 04.04.2017.
 */
public class CreatePollAnswerOptionAction1 implements CreatePollAction {

    private PollStateMachine machine;

    public CreatePollAnswerOptionAction1(PollStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public void applyToPoll(String possibleAnswer) {
        PollExchange pollExchange = machine.getPollExchange();
        pollExchange.setPoll(pollExchange.getPoll().toNewStatus(PollStatus.CREATE_ANSWER2));
        pollExchange.setResponseText(ADD_SECOND_ANSWER);
        pollExchange.setReplyKeyboardMarkup(null);
        machine.setCurrentAction(machine.getAnswerOptionAction2());
        pollExchange.getPoll().addAnswer(new AnswerOption(possibleAnswer));
    }
}
