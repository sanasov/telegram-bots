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
            handleIncomingMessage(update);
        } else if (update.hasCallbackQuery()) {
            handleButtonClick(update);
        }
    }

    private void handleButtonClick(Update update) {
        CallbackQuery query = update.getCallbackQuery();
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(query.getId());
        Poll poll = pollService.findPollById(extractPollId(query.getData()));

        switch (CommandBtn.getCommandBtn(query.getData())) {
            case DELETE_POLL:
                deletePoll(query.getMessage().getChatId(), query.getMessage().getMessageId(), poll);
                answer.setText(new MarkDownWrapper().toInlineFixedWidthCode( " удален"));
                break;
            case POST_POLL:
                sendButtonMessage(query.getMessage().getChatId(), POST_POLL.title(), null);
                break;
            case VOTE:
                showVoteMode(query.getMessage().getChatId(), query.getMessage().getMessageId(), poll);
                break;
            case PICKED_ANSWER:
                vote(query.getMessage().getChatId(), query.getMessage().getMessageId(), poll, extractAnswer(query.getData()));
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

    private void showResult(Long chatId, Integer messageId, Poll poll) {
        editMessage(chatId, messageId, poll.toView(), ReplyKeyboard.buttonsForPollShortView(poll.getPollId()));
    }

    private void showVoteMode(Long chatId, Integer messageId, Poll poll) {
        editMessage(chatId, messageId, poll.votingView(), ReplyKeyboard.pollAnswersButtons(poll));
    }

    private void vote(Long chatId, Integer messageId, Poll poll, String answer) {
        new VoteService(chatId, poll, answer).vote();
        showResult(chatId, messageId, poll);
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

    private void handleIncomingMessage(Update update) {
        TelegramUser telegramUser = new TelegramUserService().getOrCreateTelegramUserByUserId(update.getMessage().getChat());
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
            sendTextMessage(incomingMessage.getChatId(), pollExchange.getResponseText(), pollExchange.getReplyKeyboardMarkup());
        } else if (KeyboardText.SHOW_CREATED_POLLS.equals(incomingMessage.getText())) {
            if (telegramUser.myPolls().size() == 0) {
                sendTextMessage(incomingMessage.getChatId(), "Сейчас нет ни одного опросника. Создайте " + SMILING_FACE_WITH_SMILING_EYES.toString(), ReplyKeyboard.getKeyboardOnUserStart());
            }
            for (Poll poll : telegramUser.myPolls()) {
                sendButtonMessage(incomingMessage.getChatId(), poll.toView(), ReplyKeyboard.buttonsForPollShortView(poll.getPollId()));
            }
        } else {
            sendTextMessage(
                    incomingMessage.getChatId(),
                    "Выберите действие",
                    ReplyKeyboard.getKeyboardOnUserStart()
            );
        }
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

