package ru.igrey.dev;

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.igrey.dev.domain.poll.Poll;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.igrey.dev.CommandBtn.*;
import static ru.igrey.dev.KeyboardText.*;

/**
 * Created by sanasov on 04.04.2017.
 */
public class ReplyKeyboard {
    public static ReplyKeyboardMarkup getKeyboardOnUserStart() {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(SHOW_CREATED_POLLS);
        keyboardFirstRow.add(CREATE_POLL);

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup getKeyboardOnCompleteCreatingPoll() {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(COMPLETE_CREATE_POLL);

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }


    public static InlineKeyboardMarkup buttonsForPollShortView(String pollId) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();

        buttonRow.add(createInlineKeyboardButton(SHOW_RESULT.nameWithDelimeter() + pollId, SHOW_RESULT.title()));
        buttonRow.add(createInlineKeyboardButton(POST_POLL.nameWithDelimeter() + pollId, POST_POLL.title()));
        buttonRow.add(createInlineKeyboardButton(DELETE_POLL.nameWithDelimeter() + pollId, DELETE_POLL.title()));

        keyboard.add(buttonRow);
        markup.setKeyboard(keyboard);
        return markup;
    }

    public static InlineKeyboardMarkup buttonsForPollFullView(String pollId) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();

        buttonRow.add(createInlineKeyboardButton(VOTE.nameWithDelimeter() + pollId, VOTE.title()));
        buttonRow.add(createInlineKeyboardButton(HIDE_POLL.nameWithDelimeter() + pollId, HIDE_POLL.title()));

        keyboard.add(buttonRow);
        markup.setKeyboard(keyboard);
        return markup;
    }

    public static InlineKeyboardMarkup pollAnswersButtons(Poll poll) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();

        buttonRow.addAll(poll.getAnswerOptions().stream()
                .map(answerOption -> createInlineKeyboardButton(PICKED_ANSWER.nameWithDelimeter() + answerOption.answer() + "#" + poll.getPollId(), answerOption.answer()))
                .collect(Collectors.toList())
        );

        keyboard.add(buttonRow);
        markup.setKeyboard(keyboard);
        return markup;
    }

    private static InlineKeyboardButton createInlineKeyboardButton(String buttonId, String label) {
        InlineKeyboardButton btn = new InlineKeyboardButton();
        btn.setText(label);
        btn.setCallbackData(buttonId);
        return btn;
    }
}
