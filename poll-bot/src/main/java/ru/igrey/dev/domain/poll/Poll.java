package ru.igrey.dev.domain.poll;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.igrey.dev.Emoji;
import ru.igrey.dev.MarkDownWrapper;
import ru.igrey.dev.domain.AnswerOption;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@EqualsAndHashCode(exclude = "author")
@ToString(exclude = {"author", "status"})
public class Poll {
    private String pollId;
    private String question;
    private List<AnswerOption> possibleAnswers;

    public Poll(String pollId, String question, List<AnswerOption> possibleAnswers) {
        this.pollId = pollId;
        this.question = question;
        this.possibleAnswers = possibleAnswers;
    }

    public Poll(String pollId) {
        this.pollId = pollId;
    }

    public Poll() {
    }

    public Poll toNewQuestion(String newQuestion) {
        return new Poll(pollId, newQuestion, possibleAnswers);
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

    public List<AnswerOption> getAnswerOptions() {
        return possibleAnswers;
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
                .map(answerOption -> answerOption.view(totalVotedAmount()))
                .reduce((a, b) -> a + "\n" + b)
                .get();
    }

    public String votingView() {
        return new MarkDownWrapper().toBold(question);
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

    public JSONObject toJsonObject() {
        JSONArray answers = new JSONArray();
        for (AnswerOption answerOption : possibleAnswers) {
            answers.add(answerOption.toJsonObject());
        }
        JSONObject poll = new JSONObject();
        poll.put("pollId", pollId);
        poll.put("question", question);
        poll.put("possibleAnswers", answers);
        return poll;
    }

    public static Poll fromJsonObject(JSONObject jsonObject) {
        return new Poll(
                (String) jsonObject.get("pollId"),
                (String) jsonObject.get("question"),
                answerListFromJson((JSONArray) jsonObject.get("possibleAnswers"))
        );
    }

    private static List<AnswerOption> answerListFromJson(JSONArray jsonArrayAnswers) {
        List<AnswerOption> result = new ArrayList<>();
        Iterator<JSONObject> answerIt = jsonArrayAnswers.iterator();
        while (answerIt.hasNext()) {
            result.add(AnswerOption.fromJsonObject(answerIt.next()));
        }
        return result;
    }

}
