package ru.igrey.dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.CallbackQuery;
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

import static ru.igrey.dev.CommandBtn.PICKED_ANSWER;
import static ru.igrey.dev.CommandBtn.POST_POLL;
import static ru.igrey.dev.Emoji.SMILING_FACE_WITH_SMILING_EYES;

/**
 * Created by sanasov on 01.04.2017.
 */
public class PollBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(PollBot.class);
    private PollService pollService;


    public PollBot(PollService pollService) {
        this.pollService = pollService;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            handleIncomingMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            handleButtonClick(update);
        }
    }

    private void handleIncomingMessage(Message message) {
        logger.info("Incoming message: " + message.getText());
        logger.info("From user: " + message.getFrom() + "; chatId: " + message.getChat().getId());

        if (message.getChat().isGroupChat()) {
            handleIncomingMessageFromGroupChat((message));
        } else if (message.getChat().isUserChat()) {
            handlePrivateIncomingMessage(message);
        }
    }

    private void handleIncomingMessageFromGroupChat(Message message) {
        Poll poll = pollService.findPollById(
                message.getText()
                        .replace("/start@SeanPollBot ", "")
        );
        sendButtonMessage(message.getChatId(), poll.toView(), ReplyKeyboard.buttonsForPollViewInGroupChat(poll.getPollId()));
    }

    private void handlePrivateIncomingMessage(Message incomingMessage) {
        TelegramUser telegramUser = new TelegramUserService().getOrCreateTelegramUserByUserId(incomingMessage.getFrom());
        Long chatId = incomingMessage.getChatId();
        String incomingMessageText = incomingMessage.getText();
        if (incomingMessageText.equals(KeyboardText.CREATE_POLL)
                && telegramUser.status() != UserProcessStatus.CREATE_POLL) {
            telegramUser.setStatus(UserProcessStatus.CREATE_POLL);
        }

        if (telegramUser.status() == UserProcessStatus.CREATE_POLL) {
            telegramUser.pollMachine().create(incomingMessageText);
            PollExchange pollExchange = telegramUser.pollMachine().getPollExchange();
            sendTextMessage(chatId, pollExchange.getResponseText(), pollExchange.getReplyKeyboardMarkup());
        } else if (KeyboardText.SHOW_CREATED_POLLS.equals(incomingMessageText)) {
            if (telegramUser.myPolls().size() == 0) {
                sendTextMessage(chatId, "Сейчас нет ни одного опросника. Создайте " + SMILING_FACE_WITH_SMILING_EYES.toString(), ReplyKeyboard.getKeyboardOnUserStart());
            }
            for (Poll poll : telegramUser.myPolls()) {
                sendButtonMessage(chatId, poll.toView(), ReplyKeyboard.buttonsForPollViewInOwnUserChat(poll.getPollId()));
            }
        } else {
            sendTextMessage(
                    chatId,
                    "Выберите действие",
                    ReplyKeyboard.getKeyboardOnUserStart()
            );
        }
    }

    private void handleButtonClick(Update update) {
        CallbackQuery query = update.getCallbackQuery();
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(query.getId());
        Poll poll = pollService.findPollById(extractPollId(query.getData()));
        Message message = query.getMessage();
        switch (CommandBtn.getCommandBtn(query.getData())) {
            case DELETE_POLL:
                deletePoll(query.getMessage().getChatId(), query.getMessage().getMessageId(), poll);
                answer.setText(new MarkDownWrapper().toInlineFixedWidthCode("удален"));
                break;
            case POST_POLL:
                sendButtonMessage(message.getChatId(), POST_POLL.title(), null);
                break;
            case VOTE:
                showVoteMode(message.getChatId(), message.getMessageId(), poll);
                answer.setText(new MarkDownWrapper().toInlineFixedWidthCode("вы проголосовали"));
                break;
            case PICKED_ANSWER:
                vote(message.getFrom().getId().longValue(),
                        message.getChatId(),
                        message.getMessageId(),
                        poll,
                        extractAnswer(query.getData()),
                        message.getChat().isGroupChat());
                break;
        }
        try {
            answerCallbackQuery(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void deletePoll(Long chatId, Integer messageId, Poll poll) {
        pollService.deletePollById(poll.getPollId(), chatId);
        editMessage(chatId, messageId, Emoji.HEAVY_MULTIPLICATION_X.toString(), null);
    }

    private void showVoteMode(Long chatId, Integer messageId, Poll poll) {
        editMessage(chatId, messageId, poll.votingView(), ReplyKeyboard.pollAnswersButtons(poll));
    }

    private void vote(Long userId, Long chatId, Integer messageId, Poll poll, String answer, boolean isGroupChat) {
        new VoteService(userId, poll, answer).vote();
        if (isGroupChat) {
            showResultInGroupChat(chatId, messageId, poll);
        } else {
            showResultInOwnChat(chatId, messageId, poll);
        }
    }

    private void showResultInOwnChat(Long chatId, Integer messageId, Poll poll) {
        editMessage(chatId, messageId, poll.toView(), ReplyKeyboard.buttonsForPollViewInOwnUserChat(poll.getPollId()));
    }


    private void showResultInGroupChat(Long chatId, Integer messageId, Poll poll) {
        editMessage(chatId, messageId, poll.toView(), ReplyKeyboard.buttonsForPollViewInGroupChat(poll.getPollId()));
    }


    private String extractPollId(String data) {
        if (CommandBtn.getCommandBtn(data) == CommandBtn.PICKED_ANSWER) {
            return data.substring(data.indexOf("#", PICKED_ANSWER.name().length() + 1) + 1, data.length());
        } else {
            return data.substring(CommandBtn.getCommandBtn(data).name().length() + 1, data.length());
        }
    }

    private String extractAnswer(String data) {
        String pollId = extractPollId(data);
        return data.replace("PICKED_ANSWER", "")
                .replace(pollId, "")
                .replace("#", "");
    }

    private void sendTextMessage(Long chatId, String responseMessage, ReplyKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true)
                .setReplyMarkup(keyboardMarkup)
                .setChatId(chatId)
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


    private void editMessage(Long chatId, Integer messageId, String responseText, InlineKeyboardMarkup markup) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setText(responseText);
        editMessageText.setReplyMarkup(markup);
        editMessageText.setMessageId(messageId);
        editMessageText.enableMarkdown(true);
        try {
            editMessageText(editMessageText);
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

