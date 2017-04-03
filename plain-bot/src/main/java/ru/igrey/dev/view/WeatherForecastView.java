package ru.igrey.dev.view;

/**
 * Created by sanasov on 03.04.2017.
 */
public class WeatherForecastView {
    String date;
    String low;
    String high;
    String description;

    public WeatherForecastView(String date, String low, String high, String description) {
        this.date = date;
        this.low = low;
        this.high = high;
        this.description = description;
    }

    public WeatherForecastView() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String toStringView() {
        return date + "  low " + low + " high " + high + "" + ", " + description;
    }

}

