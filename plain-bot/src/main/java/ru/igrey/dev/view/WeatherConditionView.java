package ru.igrey.dev.view;

/**
 * Created by sanasov on 03.04.2017.
 */
public class WeatherConditionView {
    private String temperature;
    private String description;

    public WeatherConditionView(String temperature, String description) {
        this.temperature = temperature;
        this.description = description;
    }

    public WeatherConditionView() {
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toStringView() {
        return temperature + ", " + description;
    }
}
