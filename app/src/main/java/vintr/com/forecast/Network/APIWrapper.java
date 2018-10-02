package vintr.com.forecast.Network;

import android.annotation.SuppressLint;
import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import vintr.com.forecast.Network.CurrentWeatherModels.InformationHolder;
import vintr.com.forecast.Network.NetworkSingleton;
import vintr.com.forecast.Network.WeatherService;

public class APIWrapper {

    @SuppressLint("CheckResult")
    public void getCurrentWeather(DisposableObserver<InformationHolder> observer){
        NetworkSingleton.getRetrofit().create(WeatherService.class)
                .getCurWeather("Moscow,ru", "cbe768adbe16ad6ce8c15294944172ac")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> {
                    Log.i("Retrofit", "Subscribed");
                })
                .subscribeWith(observer);
    }
}
