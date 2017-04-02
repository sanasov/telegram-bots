package ru.igrey.dev.imdb.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by sanasov on 02.04.2017.
 */
@EqualsAndHashCode
@ToString
public class Rating {
    private String source;
    private String value;

    public Rating() {
    }

    public Rating(String source, String value) {
        this.source = source;
        this.value = value;
    }
    @JsonProperty("Source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    @JsonProperty("Value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

