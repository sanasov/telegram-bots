package ru.igrey.dev.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.igrey.dev.domain.poll.Poll;
import ru.igrey.dev.statemachine.create.PollStateMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ToString
@EqualsAndHashCode
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
        return myPolls;
    }

    public String myPollsView() {
        return Optional.ofNullable(myPolls).orElse(new ArrayList<>())
                .stream()
                .map(Poll::toString)
                .reduce((a, b) -> a + "\n " + b)
                .orElse("There is no polls you've created");

    }

    public PollStateMachine pollMachine() {
        return pollMachine;
    }

    public void changeStatus(UserProcessStatus status) {
        this.status = status;
    }
}
