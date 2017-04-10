package ru.igrey.dev;

/**
 * Created by sanasov on 09.04.2017.
 */
public enum CommandBtn {
    POST_POLL("Опубликовать"),
    DELETE_POLL("Удалить"),
    SHOW_RESULT("Просмотреть"),
    HIDE_POLL("Скрыть"),
    VOTE("Голосовать"),
    PICKED_ANSWER("");

    private final String title;
    private final static String delimiter = "#";

    CommandBtn(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }


    public String nameWithDelimeter() {
        return name() + delimiter;
    }

    public static CommandBtn getCommandBtn(String buttonId) {
        String commandBtnName = buttonId.substring(0, buttonId.indexOf("#"));
        return CommandBtn.valueOf(commandBtnName);
    }
    public static void main(String[] args) {
        System.out.println(CommandBtn.getCommandBtn("POST_POLL#vmwok_ds").title());
    }
}
