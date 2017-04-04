package ru.igrey.dev.yahooweather.service;

import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;
import ru.igrey.dev.view.WeatherMapper;
import ru.igrey.dev.view.WeatherView;

/**
 * Created by sanasov on 01.04.2017.
 */
public class WeatherServiceFromYahoo {
    public String getWeather(String location) {
        String result;
        try {
            YahooWeatherService service = new YahooWeatherService();
            YahooWeatherService.LimitDeclaration channel = service.getForecastForLocation(location, DegreeUnit.CELSIUS);
            Channel firstSuitableChannel = channel.all().get(0);
            WeatherView weatherView = WeatherMapper.toView(firstSuitableChannel);
            result = firstSuitableChannel.getDescription().substring(7) + "\n"
                    + weatherView.toStringView();

        } catch (Exception e) {
            result = "Не нашел такой город: " + location + "\n Попробуйте еще, но в этот раз пишите нормально, а не как обычно)";
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new WeatherServiceFromYahoo().getWeather("Surgut"));
    }

}
