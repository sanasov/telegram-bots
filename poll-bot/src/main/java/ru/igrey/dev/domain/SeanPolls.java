package ru.igrey.dev.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode
@ToString
public class SeanPolls {
    private List<Poll> polls;
    private List<TelegramUser> users;
    private List<TelegramChat> chats;
}
