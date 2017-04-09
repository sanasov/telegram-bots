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
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.igrey.dev.domain.TelegramUser;
import ru.igrey.dev.domain.UserProcessStatus;
import ru.igrey.dev.domain.poll.Poll;
import ru.igrey.dev.statemachine.create.PollExchange;
import ru.igrey.dev.statemachine.create.PollStateMachine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static ru.igrey.dev.CommandBtn.DELETE_POLL;
import static ru.igrey.dev.CommandBtn.POST_POLL;
import static ru.igrey.dev.CommandBtn.SHOW_RESULT;
import static ru.igrey.dev.Emoji.SMILING_FACE_WITH_SMILING_EYES;

/**
 * Created by sanasov on 01.04.2017.
 */
public class PollBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(PollBot.class);
    private PollService answerEngine;
    public static Set<TelegramUser> telegramUsers = new HashSet<>();

    public PollBot(PollService answerEngine) {
        this.answerEngine = answerEngine;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            handleIncomingMessage(update);
        } else if (update.hasCallbackQuery()) {
            handleButtonClick(update);
        }
    }

    private void handleButtonClick(Update update) {
        CallbackQuery query = update.getCallbackQuery();
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(query.getId());
        answer.setText(query.getData());
        // сообщение в чат
        switch (CommandBtn.getCommand(query.getData())) {
            case DELETE_POLL:
                sendButtonMessage(query.getMessage().getChatId(), DELETE_POLL.title(), null);
                break;
            case SHOW_RESULT:
                sendButtonMessage(query.getMessage().getChatId(), SHOW_RESULT.title(), null);
                break;
            case POST_POLL:
                sendButtonMessage(query.getMessage().getChatId(), POST_POLL.title(), null);
                break;

        }
        // callback ответ
        try {
            answerCallbackQuery(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleIncomingMessage(Update update) {
        TelegramUser telegramUser = getOrCreateTelegramUserByUserId(update.getMessage().getChat());
        Message incomingMessage = update.getMessage();
        if (incomingMessage.getChat().isGroupChat()) {
            return;
        }
        logger.info("User: " + incomingMessage.getChat());
        logger.info("Text: " + incomingMessage.getText());

        if (incomingMessage.getText().equals(KeyboardText.CREATE_POLL)
                && telegramUser.status() != UserProcessStatus.CREATE_POLL) {
            telegramUser.setStatus(UserProcessStatus.CREATE_POLL);
        }

        if (telegramUser.status() == UserProcessStatus.CREATE_POLL) {
            telegramUser.pollMachine().create(incomingMessage.getText());
            PollExchange pollExchange = telegramUser.pollMachine().getPollExchange();
            sendTextMessage(incomingMessage.getChatId(), pollExchange.getResponseText(), incomingMessage.getMessageId(), pollExchange.getReplyKeyboardMarkup());
        } else if (KeyboardText.SHOW_CREATED_POLLS.equals(incomingMessage.getText())) {
            if (telegramUser.myPolls().size() == 0) {
                sendTextMessage(incomingMessage.getChatId(), "Сейчас нет ни одного опросника. Создайте " + SMILING_FACE_WITH_SMILING_EYES.toString(), null, ReplyKeyboard.getKeyboardOnUserStart());
            }
            for (Poll poll : telegramUser.myPolls()) {
                sendButtonMessage(incomingMessage.getChatId(), poll.toShortView(), PollService.createButtonsForPollView(poll.getPollId()));
            }
        } else {
            sendTextMessage(
                    incomingMessage.getChatId(),
                    "Выберите действие",
                    incomingMessage.getMessageId(),
                    ReplyKeyboard.getKeyboardOnUserStart()
            );
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
        PollStateMachine machine = new PollStateMachine(PollExchange.createNewPollExchange());
        TelegramUser user = new TelegramUser(
                chat.getId(),
                chat.getFirstName(),
                chat.getLastName(),
                chat.getUserName(),
                UserProcessStatus.START,
                new ArrayList<>(),
                machine
        );
        machine.setAuthor(user);
        return user;
    }

    private void sendTextMessage(Long chatId, String responseMessage, Integer messageId, ReplyKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
//        sendMessage.setReplyToMessageId(messageId);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMarkup);
        sendMessage.setChatId(chatId)
                .setText(responseMessage);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendButtonMessage(Long chatId, String text, InlineKeyboardMarkup markup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
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

