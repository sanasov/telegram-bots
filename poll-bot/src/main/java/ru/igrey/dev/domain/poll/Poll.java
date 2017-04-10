package ru.igrey.dev.domain.poll;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.igrey.dev.Emoji;
import ru.igrey.dev.MarkDownWrapper;
import ru.igrey.dev.domain.AnswerOption;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(exclude = "author")
@ToString(exclude = {"author", "status"})
public class Poll {
    private String title;
    private String pollId;
    private String question;
    private List<AnswerOption> possibleAnswers;

    public Poll(String title, String pollId, String question, List<AnswerOption> possibleAnswers) {
        this.title = title;
        this.pollId = pollId;
        this.question = question;
        this.possibleAnswers = possibleAnswers;
    }

    public Poll(String pollId) {
        this.pollId = pollId;
    }

    public Poll() {
    }

    public Poll toNewName(String newName) {
        return new Poll(newName, pollId, question, possibleAnswers);
    }

    public Poll toNewQuestion(String newQuestion) {
        return new Poll(title, pollId, newQuestion, possibleAnswers);
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

    public String toShortView() {
        MarkDownWrapper wrapper = new MarkDownWrapper();
        return wrapper.toBold(title);
    }

    public String toView() {
        MarkDownWrapper wrapper = new MarkDownWrapper();
        return wrapper.toBold(question) + "\n"
                + answersView() + "\n"
                + Emoji.BUSTS_IN_SILHOUETTE.toString() + "  "
                + wrapper.toInlineFixedWidthCode(personVotedView(totalVotedAmount()));
    }

    private String answersView() {
        return possibleAnswers.stream()
                .map(AnswerOption::view)
                .reduce((a, b) -> a + "\n" + b)
                .get();
    }

    public Integer totalVotedAmount() {
        return possibleAnswers.stream()
                .map(AnswerOption::votedAmount)
                .reduce((a, b) -> a + b)
                .get();
    }

    private String personVotedView(Integer votedAmount) {
        return !votedAmount.equals(1) ? votedAmount + " persons voted" : " 1 person voted";
    }


}
