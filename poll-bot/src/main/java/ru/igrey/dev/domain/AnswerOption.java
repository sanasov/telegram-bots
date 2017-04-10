package ru.igrey.dev.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.igrey.dev.MarkDownWrapper;

import java.util.HashSet;
import java.util.Set;

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

    Set<Long> userIdSet;

    public Set<Long> userIdSet() {
        if(userIdSet == null){
            userIdSet = new HashSet<>();
        }
        return userIdSet;
    }

    public Integer votedAmount() {
        return userIdSet().size();
    }

    public void addVotedUserId(Long userId) {
        userIdSet().add(userId);
    }

    public String view() {
        MarkDownWrapper wrapper = new MarkDownWrapper();
        return answer + "        "
                + wrapper.toInlineFixedWidthCode(personVotedView());
    }

    private String personVotedView() {
        return !votedAmount().equals(1) ? votedAmount() + " persons voted " : "1 person voted ";
    }

}
