package ru.igrey.dev;

/**
 * Created by sanasov on 09.04.2017.
 */
public class MarkDownWrapper {

    public String toBold(String text) {
        return "*" + text + "*";
    }

    public String toItalic(String text) {
        return "_" + text + "_";
    }

    public String toAHref(String text, String url) {
        return "[" + text + "]" + "(" + url + ")";
    }


    public String toInlineFixedWidthCode(String text) {
        return "`" + text + "`";
    }

    public String toPreFormatted(String text) {
        return "```" + text + "```";
    }
}
