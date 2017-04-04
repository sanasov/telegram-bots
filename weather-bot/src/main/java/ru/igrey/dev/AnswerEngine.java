package ru.igrey.dev;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import ru.igrey.dev.domain.TelegramConversation;
import ru.igrey.dev.telegram.ReplyKeyboard;
import ru.igrey.dev.yahooweather.service.WeatherServiceFromYahoo;

/**
 * Created by sanasov on 03.04.2017.
 */
public class AnswerEngine {
    private Update update;
    private TelegramConversation conversation;
    private WeatherServiceFromYahoo weatherService;

    public AnswerEngine(Update update, TelegramConversation conversation, WeatherServiceFromYahoo weatherService) {
        this.update = update;
        this.conversation = conversation;
        this.weatherService = weatherService;
    }

    public SendMessage answerMessage() {
        SendMessage message = new SendMessage();
        String incomingMessage = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        String responseMessage;
        if (conversation.getStatus() == ConversationStatus.NEW
                || conversation.getStatus() == ConversationStatus.COMPLETED) {
            conversation.setStatus(ConversationStatus.WEATHER_REQUEST_YES_NO);
            responseMessage = "Хотите узнать прогноз погоды?";
            message.setReplyMarkup(ReplyKeyboard.getYesNoKeyboard());
        } else if (conversation.getStatus() == ConversationStatus.WEATHER_REQUEST_YES_NO) {
            if (incomingMessage.toLowerCase().contains("да")
                    || incomingMessage.toLowerCase().contains("yes")
                    || incomingMessage.toLowerCase().contains("yes")
                    || incomingMessage.toLowerCase().contains("yep")
                    ) {
                responseMessage = "В каком городе?";
                conversation.setStatus(ConversationStatus.WAITING_FOR_CITY);
            } else if (incomingMessage.toLowerCase().contains("нет")
                    || incomingMessage.toLowerCase().contains("no")
                    || incomingMessage.toLowerCase().contains("nope")) {
                responseMessage = "Пидора ответ!)";
                conversation.setStatus(ConversationStatus.COMPLETED);
            } else {
                responseMessage = "Еще разок)";
                conversation.setStatus(ConversationStatus.COMPLETED);
            }
        } else {
            responseMessage = weatherService.getWeather(incomingMessage);
            conversation.setStatus(ConversationStatus.COMPLETED);
        }

        message.setChatId(chatId)
                .setText(responseMessage);
        return message;

    }




}
