package ru.igrey.dev;

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static ru.igrey.dev.CommandBtn.*;
import static ru.igrey.dev.CommandBtn.HIDE_POLL;
import static ru.igrey.dev.KeyboardText.COMPLETE_CREATE_POLL;
import static ru.igrey.dev.KeyboardText.CREATE_POLL;
import static ru.igrey.dev.KeyboardText.SHOW_CREATED_POLLS;

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

    public static InlineKeyboardMarkup buttonsForPollFullView(String pollId) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow = new ArrayList<>();

        InlineKeyboardButton hideBtn = new InlineKeyboardButton();
        hideBtn.setText(HIDE_POLL.title());
        hideBtn.setCallbackData(HIDE_POLL.nameWithDelimeter() + pollId);
        buttonRow.add(hideBtn);
        keyboard.add(buttonRow);
        markup.setKeyboard(keyboard);
        return markup;
    }
}
