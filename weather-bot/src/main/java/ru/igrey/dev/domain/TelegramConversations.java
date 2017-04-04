package ru.igrey.dev.domain;

import ru.igrey.dev.ConversationStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanasov on 03.04.2017.
 */
public class TelegramConversations {

    public List<TelegramConversation> conversations = new ArrayList<>();

    public List<TelegramConversation> conversations() {
        return conversations;
    }

    public TelegramConversations() {
    }

    public TelegramConversation getConversationByCharId(Long chatId) {
        TelegramConversation result = conversations.stream()
                .filter(conversation -> conversation.getChatId().equals(chatId))
                .findAny()
                .orElse(new TelegramConversation(chatId,
                        ConversationStatus.NEW,
                        LocalDateTime.now()));

        if (ChronoUnit.MINUTES.between(result.getLastConversationTime(), LocalDateTime.now()) >= 1) {
            result.setStatus(ConversationStatus.NEW);
        }
        result.setLastConversationTime(LocalDateTime.now());
        return result;
    }

    public void addConversationIfNotExist(TelegramConversation newConversation) {
        if (conversations.stream()
                .filter(conversation -> conversation.getChatId().equals(newConversation.getChatId()))
                .count() == 0
                ) {
            conversations.add(newConversation);
        }
    }

}
