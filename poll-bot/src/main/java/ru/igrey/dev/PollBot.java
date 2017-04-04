package ru.igrey.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.igrey.dev.domain.TelegramUser;
import ru.igrey.dev.domain.UserProcessStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanasov on 01.04.2017.
 */
public class PollBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(PollBot.class);

    private AnswerEngine answerEngine;

    public PollBot(AnswerEngine answerEngine) {
        this.answerEngine = answerEngine;
    }

    @Override
    public void onUpdateReceived(Update update) {
        TelegramUser telegramUser = getTelegramUserByUserId(getChatId(update));

        if (update.hasMessage()) {
            Message incomingMessage = update.getMessage();
            if (incomingMessage.getChat().isGroupChat()) {
                return;
            }
            logger.info("User: " + incomingMessage.getChat());
            logger.info("Text: " + incomingMessage.getText());
            if (telegramUser.status() == UserProcessStatus.START_CREATE_POLL) {

            }
            if (KeyboardText.CREATE_POLL.equals(incomingMessage.getText())) {
                sendTextMessage(
                        "Name your poll",
                        incomingMessage.getChatId(),
                        null
                );
                telegramUser = telegramUser.toNewStatus(UserProcessStatus.START_CREATE_POLL);
            } else if (telegramUser.status() == UserProcessStatus.CREATE_NAME_POLL) {

            } else if (KeyboardText.SHOW_CREATED_POLLS.equals(incomingMessage.getText())) {

            } else {
                sendTextMessage(
                        "Выберите действие",
                        incomingMessage.getChatId(),
                        ReplyKeyboard.getKeyboardOnUserStart()
                );
            }

        } else if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            AnswerCallbackQuery answer = new AnswerCallbackQuery();
            answer.setCallbackQueryId(query.getId());
            answer.setText("You have voted. Thank you");
            // сообщение в чат
            if (query.getData().equals("yesClickId")) {
                sendButtonMessage(query.getMessage(), "You click yes");
            } else if (query.getData().equals("noClickId")) {
                sendButtonMessage(query.getMessage(), "You click no");
            }
            // callback ответ
            try {
                answerCallbackQuery(answer);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private Long getChatId(Update update) {
        return null;
    }

    private TelegramUser getTelegramUserByUserId(Long id) {
        return null;
    }

    private void sendTextMessage() {

    }

    private void sendTextMessage(String responseMessage, Long chatId, ReplyKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(keyboardMarkup);
        sendMessage.setChatId(chatId)
                .setText(responseMessage);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendButtonMessage(Message message, String text) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton yesBtn = new InlineKeyboardButton();
        yesBtn.setText("Yes");
        yesBtn.setCallbackData("yesClickId");
        InlineKeyboardButton noBtn = new InlineKeyboardButton();
        noBtn.setText("No");
        noBtn.setCallbackData("noClickId");
        row.add(yesBtn);
        row.add(noBtn);
        keyboard.add(row);
        markup.setKeyboard(keyboard);

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(markup);

        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return "SeanPollBot";
    }

    @Override
    public String getBotToken() {
        return "348703524:AAFvR1UHOv8nWS5IMYhswjnwP4KJQCXCkqc";
    }


}

