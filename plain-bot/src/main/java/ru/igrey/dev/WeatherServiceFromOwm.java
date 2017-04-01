//package ru.igrey.dev;
//
//import org.bitpipeline.lib.owm.OwmClient;
//import org.bitpipeline.lib.owm.WeatherData;
//import org.bitpipeline.lib.owm.WeatherStatusResponse;
//
///**
// * Created by sanasov on 01.04.2017.
// */
//public class WeatherServiceFromOwm {
//
//    public WeatherServiceFromOwm() {
//    }
//
//    public String getWeather(String cityName, CountryIsoCode countryIsoCode) {
//        String result = "";
//        OwmClient owm = new OwmClient();
//        WeatherStatusResponse currentWeather = null;
//        try {
//            if (countryIsoCode == null) {
//                currentWeather = owm.currentWeatherAtCity(cityName);
//            } else {
//                currentWeather = owm.currentWeatherAtCity(cityName, countryIsoCode.getCode());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (currentWeather == null || !currentWeather.hasWeatherStatus()) {
//            result = "Не нашел такой город: " + cityName + "\n Попробуйте еще, но в этот раз пишите нормально, а не как обычно)";
//        }
//
//        WeatherData weather = currentWeather.getWeatherStatus().get(0);
//        if (weather.getPrecipitation() == Integer.MIN_VALUE) {
//            WeatherData.WeatherCondition weatherCondition = weather.getWeatherConditions().get(0);
//            String description = weatherCondition.getDescription();
//            if (description.contains("rain") || description.contains("shower")) {
//                result += "No rain measures in " + cityName + " but reports of " + description + "\n";
//            } else {
//                result += "No rain measures in " + cityName + " " + description + "\n";
//            }
//        } else {
//            result += "It's raining in " + cityName + weather.getPrecipitation() + " mm/h \n";
//        }
//        return result;
//    }
//
//    public static void main(String[] args) {
//        System.out.println(new WeatherServiceFromOwm().getWeather("Moscow", null));
//    }
//
////    public void getWeather() {
////
////        // declaring object of "OpenWeatherMap" class
////        OpenWeatherMap owm = new OpenWeatherMap("");
////
////        // getting current weather data for the "London" city
////        CurrentWeather cwd = owm.currentWeatherByCityName("London");
////
////        // checking data retrieval was successful or not
////        if (cwd.isValid()) {
////
////            // checking if city name is available
////            if (cwd.hasCityName()) {
////                //printing city name from the retrieved data
////                System.out.println("City: " + cwd.getCityName());
////            }
////
////            // checking if max. temp. and min. temp. is available
////            if (cwd.getMainInstance().hasMaxTemperature() && cwd.getMainInstance().hasMinTemperature()) {
////                // printing the max./min. temperature
////                System.out.println("Temperature: " + cwd.getMainInstance().getMaxTemperature()
////                        + "/" + cwd.getMainInstance().getMinTemperature() + "\'F");
////            }
////        }
////    }
//}
