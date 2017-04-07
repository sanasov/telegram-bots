package ru.igrey.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.igrey.dev.domain.TelegramUser;
import ru.igrey.dev.domain.UserProcessStatus;
import ru.igrey.dev.domain.poll.Poll;
import ru.igrey.dev.domain.poll.PollStatus;
import ru.igrey.dev.statemachine.create.PollStateMachine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sanasov on 01.04.2017.
 */
public class PollBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(PollBot.class);
    private AnswerEngine answerEngine;
    public static Set<TelegramUser> telegramUsers = new HashSet<>();

    public PollBot(AnswerEngine answerEngine) {
        this.answerEngine = answerEngine;
    }

    @Override
    public void onUpdateReceived(Update update) {
        TelegramUser telegramUser = getOrCreateTelegramUserByUserId(update.getMessage().getChat());

        if (update.hasMessage()) {
            Message incomingMessage = update.getMessage();
            String responseText;
            if (incomingMessage.getChat().isGroupChat()) {
                return;
            }
            logger.info("User: " + incomingMessage.getChat());
            logger.info("Text: " + incomingMessage.getText());

            if (incomingMessage.getText().equals(KeyboardText.CREATE_POLL)
                    && telegramUser.status() != UserProcessStatus.CREATE_POLL) {
                telegramUser = telegramUser.toNewStatus(UserProcessStatus.CREATE_POLL);
            }

            if (telegramUser.status() == UserProcessStatus.CREATE_POLL) {
                responseText = telegramUser.pollMachine().getResponseOnCreateAction();
                telegramUser.pollMachine().create(incomingMessage.getText());
                sendTextMessage(responseText, incomingMessage.getChatId(), null);
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


    private TelegramUser getOrCreateTelegramUserByUserId(Chat chat) {
        TelegramUser result = telegramUsers.stream()
                .filter(u -> u.userId().equals(chat.getId()))
                .findAny()
                .orElse(createTelegramUser(chat));
        telegramUsers.add(result);
        return result;
    }

    private TelegramUser createTelegramUser(Chat chat) {
        Poll newPoll = new Poll("1L", null, PollStatus.NEW);
        TelegramUser user = new TelegramUser(
                chat.getId(),
                chat.getFirstName(),
                chat.getLastName(),
                chat.getUserName(),
                UserProcessStatus.START,
                new ArrayList<>(),
                new PollStateMachine(newPoll));
        newPoll.setAuthor(user);
        return user;
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

