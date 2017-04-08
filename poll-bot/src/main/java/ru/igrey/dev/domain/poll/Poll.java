package ru.igrey.dev.domain.poll;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.igrey.dev.domain.AnswerOption;
import ru.igrey.dev.domain.VotedUser;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = "author")
@ToString(exclude = {"author", "status"})
public class Poll {
    private String title;
    private String pollId;
    private String question;
    private List<AnswerOption> possibleAnswers;
    private List<VotedUser> votedUsers;

    public Poll(String title, String pollId, String question, List<AnswerOption> possibleAnswers, List<VotedUser> votedUsers) {
        this.title = title;
        this.pollId = pollId;
        this.question = question;
        this.possibleAnswers = possibleAnswers;
        this.votedUsers = votedUsers;
    }

    public Poll(String pollId) {
        this.pollId = pollId;
    }

    public Poll() {
    }

    public Poll toNewName(String newName) {
        return new Poll(newName, pollId, question, possibleAnswers, votedUsers);
    }

    public Poll toNewQuestion(String newQuestion) {
        return new Poll(title, pollId, newQuestion, possibleAnswers, votedUsers);
    }

    public void addAnswer(AnswerOption answerOption) {
        if (possibleAnswers == null) {
            possibleAnswers = new ArrayList<>();
        }
        possibleAnswers.add(answerOption);
    }

    public String getPollId() {
        return pollId;
    }

    public String getTitle() {
        return title;
    }
}
