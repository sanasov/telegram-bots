package ru.igrey.dev;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.igrey.dev.domain.poll.Poll;

import java.util.ArrayList;
import java.util.List;

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
        postBtn.setText("Опубликовать");
        postBtn.setCallbackData("postPoll_" + pollId);
        InlineKeyboardButton deleteBtn = new InlineKeyboardButton();
        deleteBtn.setText("Удалить");
        deleteBtn.setCallbackData("deletePoll_" + pollId);
        InlineKeyboardButton showBtn = new InlineKeyboardButton();
        showBtn.setText("Просмотреть");
        showBtn.setCallbackData("showResult_" + pollId);
        buttonRow.add(showBtn);
        buttonRow.add(postBtn);
        buttonRow.add(deleteBtn);
        keyboard.add(buttonRow);
        markup.setKeyboard(keyboard);
        return markup;
    }

}
