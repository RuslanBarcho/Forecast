package vintr.com.forecast.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import vintr.com.forecast.Adapters.WeatherByDayAdapter;
import vintr.com.forecast.Adapters.WeatherByTimeAdapter;
import vintr.com.forecast.Models.WeatherByDay;
import vintr.com.forecast.Models.WeatherByTime;
import vintr.com.forecast.Network.APIWrapper;
import vintr.com.forecast.Network.CurrentWeatherModels.InformationHolder;
import vintr.com.forecast.Network.FiveDaysWeatherModels.FiveDaysList;
import vintr.com.forecast.R;

/**
 * A simple {@link Fragment} subclass.
 */

// comment
public class CityFragment extends Fragment {
    ArrayList<WeatherByTime> weatherByTimes = new ArrayList<>();
    ArrayList<WeatherByDay> weatherByDays = new ArrayList<>();
    View mRootView;
    RecyclerView weatherByTimeView;
    WeatherByTimeAdapter weatherByTimeAdapter;
    RecyclerView weatherByDayView;
    WeatherByDayAdapter weatherByDayAdapter;
    LocationManager locationManager;
    APIWrapper apiWrapper;

    TextView temperature;
    Button cityName;

    public CityFragment() { }

    public static CityFragment newInstance() {
        return new CityFragment();
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_city, container, false);
        apiWrapper = new APIWrapper();

        temperature = mRootView.findViewById(R.id.temperature_city);
        cityName = mRootView.findViewById(R.id.city_name);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        //Log.i("CITY:", getArguments().getString("city"));

        if ((ContextCompat.checkSelfPermission( getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) & !(getArguments().getString("city").equals("Location"))) {
            Log.i("CITY:", "Downloading Cur w data");
            getCurWeather(getArguments().getString("city"), null, null);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            getCurWeather(null, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
            Log.i("CITY COORDS", String.valueOf(location.getLatitude()) + " " + String.valueOf(location.getLongitude()));
        }

        for(int i = 0; i<24; i++){
            weatherByTimes.add(new WeatherByTime("9:00", String.valueOf(i)));
        }

        weatherByTimeView = mRootView.findViewById(R.id.weatherByTimeView);
        weatherByTimeAdapter = new WeatherByTimeAdapter(weatherByTimes);
        configureRecyclerview(weatherByTimeView,weatherByTimeAdapter, new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        for(int i = 0; i<7; i++){
            weatherByDays.add(new WeatherByDay("Суббота", String.valueOf(i), String.valueOf(i+1)));
        }

        weatherByDayView = mRootView.findViewById(R.id.weatherByDayView);
        weatherByDayAdapter = new WeatherByDayAdapter(weatherByDays);
        configureRecyclerview(weatherByDayView, weatherByDayAdapter, new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        apiWrapper.getFiveDaysWeather(new Observer<FiveDaysList>() {
            ArrayList<WeatherByTime> newWeatherData = new ArrayList<>();
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(FiveDaysList fiveDaysList) {
                Double a = fiveDaysList.getMain().getTemp() - 273.15;
                //Date date = new Date(fiveDaysList.getDt());
                newWeatherData.add(new WeatherByTime(fiveDaysList.getDtTxt().substring(11,16),String.valueOf(a.intValue())));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                weatherByTimes.clear();
                weatherByTimes.addAll(newWeatherData);
                updateHoursRecycler();
            }
        });
        //Toast.makeText(getContext(), String.valueOf(getArguments().getInt("position")), Toast.LENGTH_SHORT).show();
        return mRootView;
    }

    private void getCurWeather(String city, String lat, String lon){
        apiWrapper.getWeatherObservable(city, lat, lon, new DisposableObserver<InformationHolder>() {
            @Override
            public void onNext(InformationHolder informationHolder) {
                Double a = informationHolder.getMain().getTemp() - 273.15;
                temperature.setText(String.valueOf(a.intValue()) + "°");
                cityName.setText(informationHolder.getName());
                Log.i("CITY DOWNLOADED", informationHolder.getName());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("Retrofit", "error" + e.toString());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void updateHoursRecycler(){
        weatherByTimeAdapter.updateInfo(weatherByTimes);
        weatherByTimeAdapter.notifyDataSetChanged();
    }

    private void configureRecyclerview(RecyclerView recyclerView, RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager){
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

}
