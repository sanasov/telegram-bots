package ru.igrey.dev.domain;

import org.apache.commons.lang3.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.json.simple.JSONObject;
import ru.igrey.dev.Emoji;
import ru.igrey.dev.MarkDownWrapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
public class AnswerOption {

    private final static Integer MAX_BLACK_SMALL_SQUARE = 7;

    public AnswerOption(String answer) {
        this.answer = answer;
    }

    public AnswerOption(String answer, Set<Long> userIdSet) {
        this.answer = answer;
        this.userIdSet = userIdSet;
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

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("answer", answer);
        jsonObject.put("userIdSet", joinUserIdSetToString());
        return jsonObject;
    }

    public static AnswerOption fromJsonObject(JSONObject jsonObject) {
        return new AnswerOption(
                (String) jsonObject.get("answer"),
                splitStringUserIdsToSet((String) jsonObject.get("userIdSet"))
        );
    }

    private static Set<Long> splitStringUserIdsToSet(String userIds) {
        if (userIds.isEmpty()) {
            return new HashSet<>();
        }
        return Arrays.asList(userIds.split(","))
                .stream()
                .map(id -> Long.valueOf(id))
                .collect(Collectors.toSet());
    }

    private String joinUserIdSetToString() {
        return userIdSet().isEmpty() ? "" : StringUtils.join(
                userIdSet.stream()
                        .map(id -> id.toString())
                        .collect(Collectors.toSet()),
                ",");
    }

}

