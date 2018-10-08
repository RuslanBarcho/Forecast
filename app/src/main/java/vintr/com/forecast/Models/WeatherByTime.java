package vintr.com.forecast.Models;

public class WeatherByTime {
    private String time;
    private String temperature;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public WeatherByTime(String time, String temperature, String type) {
        this.time = time;
        this.temperature = temperature;
        this.type = type;
    }
}
