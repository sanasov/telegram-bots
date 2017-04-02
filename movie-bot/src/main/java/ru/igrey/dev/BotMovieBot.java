package ru.igrey.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.igrey.dev.imdb.OmdbService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanasov on 01.04.2017.
 */
public class BotMovieBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(BotMovieBot.class);
    private static List<TelegramConversation> conversations = new ArrayList<>();

    private OmdbService omdbService;

    public BotMovieBot(OmdbService omdbService) {
        this.omdbService = omdbService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        logger.info("User: " + update.getMessage().getChat());
        logger.info("Text: " + update.getMessage().getText());
        TelegramConversation conversation = getConversationByCharId(update.getMessage().getChatId());
        addConversationIfNotExist(conversation);
        answerOnTextMessage(update, conversation);
    }

    private TelegramConversation getConversationByCharId(Long chatId) {
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

    private void addConversationIfNotExist(TelegramConversation newConversation) {
        if (conversations.stream()
                .filter(conversation -> conversation.getChatId().equals(newConversation.getChatId()))
                .count() == 0
                ) {
            conversations.add(newConversation);
        }

    }

    @Override
    public String getBotUsername() {
        return "BotMovieBot";
    }

    @Override
    public String getBotToken() {
        return "314299945:AAEOHia7WtBbQhCO43gsPcNk1m1xRPjvVvE";
    }

    private void answerOnTextMessage(Update update, TelegramConversation conversation) {
        String incomingMessage = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        String responseMessage;
        if (conversation.getStatus() == ConversationStatus.NEW
                || conversation.getStatus() == ConversationStatus.COMPLETED) {
            conversation.setStatus(ConversationStatus.WAITING_FOR_MOVIE);
            responseMessage = "Введите название фильма на английском";
        } else {
            responseMessage = omdbService.getMovie(incomingMessage)!=null? omdbService.getMovie(incomingMessage).toString() : "Фильм не найден. Название фильма должно содержать латинские буквы";
            conversation.setStatus(ConversationStatus.COMPLETED);
        }

        SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(chatId)
                .setText(responseMessage);

        try {
            sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}

