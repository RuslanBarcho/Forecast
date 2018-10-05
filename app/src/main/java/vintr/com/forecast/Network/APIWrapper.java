package vintr.com.forecast.Network;

import android.annotation.SuppressLint;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import vintr.com.forecast.Network.CurrentWeatherModels.InformationHolder;
import vintr.com.forecast.Network.FiveDaysWeatherModels.FiveDaysHolder;
import vintr.com.forecast.Network.FiveDaysWeatherModels.FiveDaysList;

public class APIWrapper {

    @SuppressLint("CheckResult")
    public void getWeatherObservable(String city, String lat, String lon, DisposableObserver<InformationHolder> observer) {
        Observable weatherObservable = Observable.empty();
        if (city != null) weatherObservable = NetworkSingleton.getRetrofit().create(WeatherService.class).getCurWeather(city, "cbe768adbe16ad6ce8c15294944172ac");
        else if (lat != null & lon != null) weatherObservable =  NetworkSingleton.getRetrofit().create(WeatherService.class).getCurWeather(lat, lon , "cbe768adbe16ad6ce8c15294944172ac");
        weatherObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(s -> {
                    Log.i("Retrofit", "Subscribed");
                })
                .subscribeWith(observer);
    }

    public void getCurrentWeather(String city, DisposableObserver<InformationHolder> observer){
        getWeatherObservable(city, null, null, observer);
    }

    @SuppressLint("CheckResult")
    public void getCurrentWeather(String lat, String lon, DisposableObserver<InformationHolder> observer){
        getWeatherObservable(null, lat, lon, observer);
    }

    @SuppressLint("CheckResult")
    public void getFiveDaysWeather(Observer<FiveDaysList> observer){
        NetworkSingleton.getRetrofit().create(WeatherService.class)
                .getFiveDaysWeather("Moscow,ru", "cbe768adbe16ad6ce8c15294944172ac")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(FiveDaysHolder::getList)
                .flatMapObservable(Observable::fromIterable)
                .doOnComplete(() -> {
                    Log.i("Retrofit", "Done!");
                })
                .subscribeWith(observer);
    }
}
