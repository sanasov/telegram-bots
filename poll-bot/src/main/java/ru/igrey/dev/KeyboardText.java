package ru.igrey.dev;

/**
 * Created by sanasov on 04.04.2017.
 */
public class KeyboardText {
    public static final String SHOW_CREATED_POLLS = "Show created polls";
    public static final String CREATE_POLL = "Create poll";

    public static Boolean isTextEqualsKeyboardText(String text) {
        if (SHOW_CREATED_POLLS.equals(text)
                || CREATE_POLL.equals(text)) {
            return true;
        } else {
            return false;
        }
    }
}
