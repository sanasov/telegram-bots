package ru.igrey.dev;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by sanasov on 01.04.2017.
 */
public class WeatherBotStart {
    public static void main(String[] args) {

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new IgreyBot(new WeatherServiceFromYahoo()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
