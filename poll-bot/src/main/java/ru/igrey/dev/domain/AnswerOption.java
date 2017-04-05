package ru.igrey.dev.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

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
}
