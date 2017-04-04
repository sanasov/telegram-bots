package ru.igrey.dev.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode
@ToString
public class TelegramUser {
    private String fullname;
    private Long userId;
    private List<Poll> myPolls;

    public TelegramUser(String fullname, Long userId, List<Poll> myPolls) {
        this.fullname = fullname;
        this.userId = userId;
        this.myPolls = myPolls;
    }

    public TelegramUser() {
    }
}
