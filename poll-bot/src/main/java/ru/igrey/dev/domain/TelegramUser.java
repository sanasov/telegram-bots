package ru.igrey.dev.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ru.igrey.dev.domain.poll.Poll;
import ru.igrey.dev.entity.TelegramUserEntity;
import ru.igrey.dev.statemachine.create.PollExchange;
import ru.igrey.dev.statemachine.create.PollStateMachine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@ToString
@EqualsAndHashCode(exclude = {"myPolls", "pollMachine"})
public class TelegramUser {
    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private UserProcessStatus status;
    private List<Poll> myPolls;
    private PollStateMachine pollMachine;

    public TelegramUser(Long userId, String firstName, String lastName, String userName, UserProcessStatus status, List<Poll> myPolls, PollStateMachine pollMachine) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.status = status;
        this.myPolls = myPolls;
        this.pollMachine = pollMachine;
    }


    public TelegramUser toNewStatus(UserProcessStatus status) {
        return new TelegramUser(userId,
                firstName,
                lastName,
                userName,
                status,
                myPolls,
                pollMachine
        );
    }

    public void setStatus(UserProcessStatus status) {
        this.status = status;
    }

    public TelegramUser() {
    }

    public Long userId() {
        return userId;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }


    public String userName() {
        return userName;
    }

    public UserProcessStatus status() {
        return status;
    }


    public List<Poll> myPolls() {
        if (myPolls == null) {
            myPolls = new ArrayList<>();
        }
        return myPolls;
    }

    public String myPollsView() {
        return Optional.ofNullable(myPolls).orElse(new ArrayList<>())
                .stream()
                .map(Poll::toString)
                .reduce((a, b) -> a + "\n " + b)
                .orElse("Нет ни одного опросника");

    }

    public PollStateMachine pollMachine() {
        return pollMachine;
    }

    public void changeStatus(UserProcessStatus status) {
        this.status = status;
    }

    public TelegramUserEntity toEntity() {
        JSONArray pollList = new JSONArray();
        for (Poll poll : this.myPolls) {
            pollList.add(poll.toJsonObject());
        }
        return new TelegramUserEntity(userId,
                firstName,
                lastName,
                userName,
                status.toString(),
                pollList.toJSONString(),
                null,
                true);
    }

    public TelegramUser fromEntity(TelegramUserEntity entity) {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonPoll;
        try {
            jsonPoll = (JSONArray) jsonParser.parse(entity.getMyPolls());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        PollStateMachine machine = new PollStateMachine(PollExchange.createNewPollExchange());
        TelegramUser user = new TelegramUser(
                entity.getUserId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getUserName(),
                UserProcessStatus.valueOf(entity.getStatus()),
                pollListFromJson(jsonPoll),
                machine);
        machine.setAuthor(user);
        return user;
    }

    private static List<Poll> pollListFromJson(JSONArray jsonArrayPolls) {
        List<Poll> result = new ArrayList<>();
        Iterator<JSONObject> pollIt = jsonArrayPolls.iterator();
        while (pollIt.hasNext()) {
            result.add(Poll.fromJsonObject(pollIt.next()));
        }
        return result;
    }

    @Override
    public String toString() {
        return "userId=" + userId +
                ", firstName= " + firstName +
                ", lastName= " + lastName +
                ", userName= " + userName;
    }
}
