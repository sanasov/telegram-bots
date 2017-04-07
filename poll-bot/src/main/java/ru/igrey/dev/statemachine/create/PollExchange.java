package ru.igrey.dev.statemachine.create;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.igrey.dev.domain.poll.Poll;

/**
 * Created by sanasov on 07.04.2017.
 */
public class PollExchange {
    private Poll poll;
    private Boolean isComplete;
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private String responseText;

    public PollExchange() {
    }

    public PollExchange(Poll poll, Boolean isComplete, ReplyKeyboardMarkup replyKeyboardMarkup, String responseText) {
        this.poll = poll;
        this.isComplete = isComplete;
        this.replyKeyboardMarkup = replyKeyboardMarkup;
        this.responseText = responseText;
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

    public void setComplete(Boolean complete) {
        isComplete = complete;
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
}
