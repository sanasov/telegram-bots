package ru.igrey.dev;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.igrey.dev.imdb.OmdbService;

/**
 * Created by sanasov on 01.04.2017.
 */
public class StartMovieBot {
    public static void main(String[] args) {

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new BotMovieBot(new OmdbService()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
