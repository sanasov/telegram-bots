package ru.igrey.dev;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.igrey.dev.domain.TelegramConversations;
import ru.igrey.dev.yahooweather.service.WeatherServiceFromYahoo;

/**
 * Created by sanasov on 01.04.2017.
 */
public class WeatherBotStart {
    public static void main(String[] args) {

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new WeatherBot(
                    new TelegramConversations(),
                    new WeatherServiceFromYahoo()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
