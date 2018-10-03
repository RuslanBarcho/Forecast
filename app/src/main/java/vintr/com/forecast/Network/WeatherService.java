package vintr.com.forecast.Network;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vintr.com.forecast.Network.CurrentWeatherModels.InformationHolder;
import vintr.com.forecast.Network.FiveDaysWeatherModels.FiveDaysHolder;

public interface WeatherService {
    @GET("weather?")
    Observable<InformationHolder> getCurWeather(
            @Query("q") String location,
            @Query("APPID") String APPID
    );

    @GET("forecast?")
    Single<FiveDaysHolder> getFiveDaysWeather(
            @Query("q") String location,
            @Query("APPID") String APPID
    );
}
