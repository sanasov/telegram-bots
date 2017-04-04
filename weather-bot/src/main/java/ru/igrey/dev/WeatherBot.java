package ru.igrey.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.igrey.dev.domain.TelegramConversation;
import ru.igrey.dev.domain.TelegramConversations;
import ru.igrey.dev.yahooweather.service.WeatherServiceFromYahoo;

/**
 * Created by sanasov on 01.04.2017.
 */
public class WeatherBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(WeatherBot.class);


    private WeatherServiceFromYahoo weatherService;
    private AnswerEngine answerEngine;
    private TelegramConversations telegramConversations;

    public WeatherBot(TelegramConversations telegramConversations, WeatherServiceFromYahoo weatherServiceFromYahoo) {
        weatherService = weatherServiceFromYahoo;
        this.telegramConversations = telegramConversations;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        logger.info("User: " + update.getMessage().getChat());
        logger.info("Text: " + update.getMessage().getText());
        TelegramConversation conversation = telegramConversations.getConversationByCharId(update.getMessage().getChatId());
        answerEngine = new AnswerEngine(
                update,
                conversation,
                weatherService);
        telegramConversations.addConversationIfNotExist(conversation);
        try {
            sendMessage(answerEngine.answerMessage());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }


    @Override
    public String getBotUsername() {
        return "sergiseyBot";
    }

    @Override
    public String getBotToken() {
        return "298962706:AAFJMqitHXvDNWT1Bw4N-ebU2u0Ny9GBSZU";
    }


}

