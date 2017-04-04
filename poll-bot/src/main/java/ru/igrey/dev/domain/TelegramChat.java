package ru.igrey.dev.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;


@EqualsAndHashCode
@ToString
public class TelegramChat {
    private String title;
    private Long chatId;

    public TelegramChat(String title, Long chatId) {
        this.title = title;
        this.chatId = chatId;
    }
}
