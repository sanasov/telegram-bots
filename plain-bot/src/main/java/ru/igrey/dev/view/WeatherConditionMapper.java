package ru.igrey.dev.view;

import com.github.fedy2.weather.data.Condition;

/**
 * Created by sanasov on 03.04.2017.
 */
public class WeatherConditionMapper {

    public static WeatherConditionView toView(Condition condition) {
        return new WeatherConditionView(getTemperatureView(condition.getTemp()), condition.getText());
    }

    private static String getTemperatureView(int degree) {
        if (degree == 0) {
            return "0 C";
        } else if (degree > 0) {
            return "+" + degree + " C";
        } else {
            return degree + " C";
        }
    }
}
