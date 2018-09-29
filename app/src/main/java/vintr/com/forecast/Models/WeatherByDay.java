package vintr.com.forecast.Models;

public class WeatherByDay {
    private String day;
    private String temperature_days_day;
    private String temperature_days_night;

    public WeatherByDay(String day, String temperature_days_day, String temperature_days_night) {
        this.day = day;
        this.temperature_days_day = temperature_days_day;
        this.temperature_days_night = temperature_days_night;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTemperature_days_day() {
        return temperature_days_day;
    }

    public void setTemperature_days_day(String temperature_days_day) {
        this.temperature_days_day = temperature_days_day;
    }

    public String getTemperature_days_night() {
        return temperature_days_night;
    }

    public void setTemperature_days_night(String temperature_days_night) {
        this.temperature_days_night = temperature_days_night;
    }
}
