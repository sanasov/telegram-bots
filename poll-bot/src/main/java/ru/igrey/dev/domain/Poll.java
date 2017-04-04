package ru.igrey.dev.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode
@ToString
public class Poll {
    private String title;
    private String question;
    private List<AnswerOption> possibleAnswers;
    private List<VotedUser> votedUsers;
    private TelegramUser author;

    public Poll(String title, String question, List<AnswerOption> possibleAnswers, List<VotedUser> votedUsers, TelegramUser author) {
        this.title = title;
        this.question = question;
        this.possibleAnswers = possibleAnswers;
        this.votedUsers = votedUsers;
        this.author = author;
    }

    public Poll() {
    }
}
