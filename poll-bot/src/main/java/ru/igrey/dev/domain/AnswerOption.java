package ru.igrey.dev.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.igrey.dev.MarkDownWrapper;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
public class AnswerOption {

    public AnswerOption(String answer) {
        this.answer = answer;
    }

    private String answer;

    public String answer() {
        return answer;
    }

    List<String> userIdList;

    public List<String> userIdList() {
        if(userIdList == null){
            userIdList = new ArrayList<>();
        }
        return userIdList;
    }

    public Integer votedAmount() {
        return userIdList().size();
    }

    public void addVotedUserId(String userId) {
        userIdList().add(userId);
    }

    public String view() {
        MarkDownWrapper wrapper = new MarkDownWrapper();
        return answer + "\n"
                + wrapper.toInlineFixedWidthCode(personVotedView());
    }

    private String personVotedView() {
        return !votedAmount().equals(1) ? votedAmount() + " persons voted " : "1 person voted ";
    }

}
