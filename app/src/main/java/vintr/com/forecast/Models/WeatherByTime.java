package vintr.com.forecast.Models;

public class WeatherByTime {
    private String time;
    private String temperature;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public WeatherByTime(String time, String temperature) {
        this.time = time;
        this.temperature = temperature;
    }
}
