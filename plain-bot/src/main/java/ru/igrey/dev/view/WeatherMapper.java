package ru.igrey.dev.view;

import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.Condition;
import com.github.fedy2.weather.data.Forecast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sanasov on 03.04.2017.
 */
public class WeatherMapper {


    public static WeatherView toView(Channel channel) {
        return new WeatherView(
                toWeatherForecastViews(channel.getItem().getForecasts()),
                toWeatherConditionView(channel.getItem().getCondition()))
                ;
    }

    private static List<WeatherForecastView> toWeatherForecastViews(List<Forecast> forecasts) {
        return forecasts.stream()
                .filter(item -> item.getDate().after(new Date()))
                .map(forecast -> toWeatherForecastView(forecast))
                .collect(Collectors.toList());
    }

    private static WeatherForecastView toWeatherForecastView(Forecast forecast) {
        return new WeatherForecastView(getDateView(forecast.getDate()), getTemperatureView(forecast.getLow()), getTemperatureView(forecast.getHigh()), forecast.getText());
    }

    private static WeatherConditionView toWeatherConditionView(Condition condition) {
        return new WeatherConditionView(getDateView(condition.getDate()), getTemperatureView(condition.getTemp()), condition.getText());
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

    private static String getDateView(Date date) {
        DateFormat df = new SimpleDateFormat("dd.MM");
        return df.format(date);
    }
}
