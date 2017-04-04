package ru.igrey.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

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
        // We check if the update has a message and the message has text
        if (update.hasMessage()) {
            logger.info("User: " + update.getMessage().getChat());
            logger.info("Text: " + update.getMessage().getText());
            sendMsg(update.getMessage(), "Привет, я робот", true);
        } else if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            AnswerCallbackQuery answer = new AnswerCallbackQuery();
            answer.setCallbackQueryId(query.getId());
            answer.setText("You have voted. Thank you");
            // сообщение в чат
            if (query.getData().equals("yesClickId")) {
                sendMsg(query.getMessage(), "You click yes", false);
            } else if (query.getData().equals("noClickId")) {
                sendMsg(query.getMessage(), "You click no", false);
            }
            // callback ответ
            try {
                answerCallbackQuery(answer);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMsg(Message message, String text, boolean isReplay) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        sendMessage.setChatId(message.getChatId().toString());
        if (isReplay) {
            sendMessage.setReplyToMessageId(message.getMessageId());
        }

        sendMessage.setText(text);

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

