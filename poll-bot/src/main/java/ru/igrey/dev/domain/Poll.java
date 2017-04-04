package ru.igrey.dev.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode
@ToString(exclude = "author")
public class Poll {
    private String title;
    private String pollId;
    private String question;
    private List<AnswerOption> possibleAnswers;
    private List<VotedUser> votedUsers;
    private TelegramUser author;
    private PollStatus status;

    public Poll(String title, String pollId, String question, List<AnswerOption> possibleAnswers, List<VotedUser> votedUsers, TelegramUser author, PollStatus status) {
        this.title = title;
        this.pollId = pollId;
        this.question = question;
        this.possibleAnswers = possibleAnswers;
        this.votedUsers = votedUsers;
        this.author = author;
        this.status = status;
    }

    public Poll(String pollId, TelegramUser author, PollStatus status) {
        this.pollId = pollId;
        this.author = author;
        this.status = status;
    }

    public Poll() {
    }

    public Poll toNewStatus(PollStatus status) {
        return new Poll(title, pollId, question, possibleAnswers, votedUsers, author, status);
    }
}