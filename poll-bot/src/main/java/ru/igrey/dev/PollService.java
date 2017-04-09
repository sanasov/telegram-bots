package ru.igrey.dev;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.igrey.dev.domain.poll.Poll;

import java.util.ArrayList;
import java.util.List;

import static ru.igrey.dev.CommandBtn.DELETE_POLL;
import static ru.igrey.dev.CommandBtn.POST_POLL;
import static ru.igrey.dev.CommandBtn.SHOW_RESULT;

/**
 * Created by sanasov on 03.04.2017.
 */
public class PollService {
    private Update update;

    public void createPoll() {

    }

    public Poll showPollById() {
        return new Poll();
    }

    public void sendPollResult(Long userId) {

    }

    public void postPollinChat(Long chatId) {

    }

    public static InlineKeyboardMarkup createButtonsForPollView(String pollId) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();

        InlineKeyboardButton postBtn = new InlineKeyboardButton();
        postBtn.setText(POST_POLL.title());
        postBtn.setCallbackData(POST_POLL.nameWithDelimeter() + pollId);
        InlineKeyboardButton deleteBtn = new InlineKeyboardButton();
        deleteBtn.setText(DELETE_POLL.title());
        deleteBtn.setCallbackData(DELETE_POLL.nameWithDelimeter() + pollId);
        InlineKeyboardButton showBtn = new InlineKeyboardButton();
        showBtn.setText(SHOW_RESULT.title());
        showBtn.setCallbackData(SHOW_RESULT.nameWithDelimeter() + pollId);
        buttonRow.add(showBtn);
        buttonRow.add(postBtn);
        buttonRow.add(deleteBtn);
        keyboard.add(buttonRow);
        markup.setKeyboard(keyboard);
        return markup;
    }

}
