package ru.igrey.dev.imdb;

import ru.igrey.dev.imdb.view.JsonMapper;
import ru.igrey.dev.imdb.view.Movie;

/**
 * Created by sanasov on 02.04.2017.
 */
public class OmdbService {
    public Movie getMovie(String requestParam) {
        String result = new OmdbHttpClient().get(requestParam);
        if (result.equals("{\"Response\":\"False\",\"Error\":\"Movie not found!\"}")) {
            return null;
        }
        Movie movie = JsonMapper.toView(result);
        return movie;
    }
}
