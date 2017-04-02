package ru.igrey.dev.imdb;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.igrey.dev.BotMovieBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 * Created by sanasov on 02.04.2017.
 */
public class OmdbHttpClient {
    private static final Logger logger = LoggerFactory.getLogger(BotMovieBot.class);

    public String get(String requestParam) {
        String url = "http://www.omdbapi.com/?t=" + requestParam.replace(" ", "+");
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        request.addHeader("User-Agent", USER_AGENT);
        StringBuffer result = new StringBuffer();
        try {
            HttpResponse response = client.execute(request);

            logger.debug("Response Code : " + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return result.toString();
    }



}
