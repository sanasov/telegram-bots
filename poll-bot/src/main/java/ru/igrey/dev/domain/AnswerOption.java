package ru.igrey.dev.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.igrey.dev.Emoji;
import ru.igrey.dev.MarkDownWrapper;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@ToString
public class AnswerOption {

    private final static Integer MAX_BLACK_SMALL_SQUARE = 7;

    public AnswerOption(String answer) {
        this.answer = answer;
    }

    private String answer;

    public String answer() {
        return answer;
    }

    Set<Long> userIdSet;

    public Set<Long> userIdSet() {
        if (userIdSet == null) {
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

    public String view(Integer total) {
        MarkDownWrapper wrapper = new MarkDownWrapper();
        return answer + "\n"
                + wrapper.toInlineFixedWidthCode(personVotedView(total));
    }

    private String personVotedView(Integer total) {
        return persentIndicator(total) + "  " + votedAmount();
    }

    private String persentIndicator(Integer total) {
        Double indicatorLength = MAX_BLACK_SMALL_SQUARE * ((double) votedAmount() / total);
        String persentIndicator = "";
        if (indicatorLength == 0) {
            persentIndicator = Emoji.WHITE_SMALL_SQUARE.toString();
            return persentIndicator;
        }
        for (int i = 0; i < MAX_BLACK_SMALL_SQUARE; i++) {
            if (i < indicatorLength) {
                persentIndicator += Emoji.BLACK_SMALL_SQUARE;
            } else {
                persentIndicator = Emoji.WHITE_SMALL_SQUARE.toString();
            }
        }
        return persentIndicator;
    }

}
