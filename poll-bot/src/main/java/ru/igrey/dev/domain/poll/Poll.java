package ru.igrey.dev.domain.poll;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.igrey.dev.domain.AnswerOption;
import ru.igrey.dev.domain.TelegramUser;
import ru.igrey.dev.domain.VotedUser;

import java.util.ArrayList;
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

    public Poll toNewStatus(PollStatus newStatus) {
        return new Poll(title, pollId, question, possibleAnswers, votedUsers, author, newStatus);
    }

    public void setStatus(PollStatus pollStatus) {
        this.status = pollStatus;
    }

    public Poll toNewName(String newName) {
        return new Poll(newName, pollId, question, possibleAnswers, votedUsers, author, status);
    }

    public Poll toNewQuestion(String newQuestion) {
        return new Poll(title, pollId, newQuestion, possibleAnswers, votedUsers, author, status);
    }

    public PollStatus status() {
        return status;
    }


    public void addAnswer(AnswerOption answerOption) {
        if (possibleAnswers == null) {
            possibleAnswers = new ArrayList<>();
        }
        possibleAnswers.add(answerOption);
    }

    public TelegramUser author() {
        return author;
    }

    public void setAuthor(TelegramUser author) {
        this.author = author;
    }
}
