package ru.igrey.dev;

import java.time.LocalDateTime;

/**
 * Created by sanasov on 01.04.2017.
 */
public class TelegramConversation {
    Long chatId;
    ConversationStatus status;
    LocalDateTime lastConversationTime;

    public TelegramConversation(Long chatId, ConversationStatus status, LocalDateTime lastConversationTime) {
        this.chatId = chatId;
        this.status = status;
        this.lastConversationTime = lastConversationTime;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public ConversationStatus getStatus() {
        return status;
    }

    public void setStatus(ConversationStatus status) {
        this.status = status;
    }

    public LocalDateTime getLastConversationTime() {
        return lastConversationTime;
    }

    public void setLastConversationTime(LocalDateTime lastConversationTime) {
        this.lastConversationTime = lastConversationTime;
    }
}
