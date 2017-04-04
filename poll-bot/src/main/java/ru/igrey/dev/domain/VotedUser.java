package ru.igrey.dev.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class VotedUser {
    private String fullName;
    private String userId;
    private AnswerOption answer;

    public VotedUser(String fullName, String userId, AnswerOption answer) {
        this.fullName = fullName;
        this.userId = userId;
        this.answer = answer;
    }

    public VotedUser() {
    }
}