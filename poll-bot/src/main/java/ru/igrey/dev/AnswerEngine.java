package ru.igrey.dev;

import org.telegram.telegrambots.api.objects.Update;
import ru.igrey.dev.domain.Poll;

import java.util.List;

/**
 * Created by sanasov on 03.04.2017.
 */
public class AnswerEngine {
    private Update update;

    public void createPoll() {

    }

    public List<Poll> showPollsByUserId() {
        return null;
    }


    public Poll showPollById() {
        return new Poll();
    }

    public void sendPollResult(Long userId) {

    }

    public void postPollinChat(Long chatId) {

    }

}
