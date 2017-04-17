package ru.igrey.dev.statemachine.create;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.igrey.dev.domain.poll.Poll;
import ru.igrey.dev.domain.poll.PollStatus;

import static ru.igrey.dev.statemachine.create.ResponseMessagesInCreatingPollProcess.WRITE_QUESTION;

/**
 * Created by sanasov on 07.04.2017.
 */
public class PollExchange {
    private Poll poll;
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private String responseText;
    private PollStatus status;


    public PollExchange() {
    }

    public PollExchange(Poll poll, ReplyKeyboardMarkup replyKeyboardMarkup, String responseText, PollStatus status) {
        this.poll = poll;
        this.replyKeyboardMarkup = replyKeyboardMarkup;
        this.responseText = responseText;
        this.status = status;
    }

    public static PollExchange createNewPollExchange() {
        Poll newPoll = new Poll(String.valueOf(System.currentTimeMillis()));
        return new PollExchange(newPoll, null, WRITE_QUESTION, PollStatus.NEW);
    }


    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }


    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        return replyKeyboardMarkup;
    }

    public void setReplyKeyboardMarkup(ReplyKeyboardMarkup replyKeyboardMarkup) {
        this.replyKeyboardMarkup = replyKeyboardMarkup;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public PollStatus getStatus() {
        return status;
    }

    public void setStatus(PollStatus status) {
        this.status = status;
    }
}
