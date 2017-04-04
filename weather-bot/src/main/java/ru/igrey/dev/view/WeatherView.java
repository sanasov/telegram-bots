package ru.igrey.dev.view;

import java.util.List;

/**
 * Created by sanasov on 03.04.2017.
 */
public class WeatherView {
    List<WeatherForecastView> forecasts;
    WeatherConditionView conditionView;

    public WeatherView() {
    }

    public WeatherView(List<WeatherForecastView> forecasts, WeatherConditionView conditionView) {
        this.forecasts = forecasts;
        this.conditionView = conditionView;
    }

    public List<WeatherForecastView> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<WeatherForecastView> forecasts) {
        this.forecasts = forecasts;
    }

    public WeatherConditionView getConditionView() {
        return conditionView;
    }

    public void setConditionView(WeatherConditionView conditionView) {
        this.conditionView = conditionView;
    }

    public String toStringView() {
        return conditionView.toStringView() + "\n"
                + "\n"
                + "Forecast: \n"
                + forecastsToStringView();
    }


    public String forecastsToStringView() {
        return forecasts.stream()
                .map(WeatherForecastView::toStringView)
                .reduce((cur, sum) -> cur + "\n" + sum)
                .get();
    }
}
