package vintr.com.forecast.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import vintr.com.forecast.Adapters.WeatherByDayAdapter;
import vintr.com.forecast.Adapters.WeatherByTimeAdapter;
import vintr.com.forecast.Helpers.GridItemDecoration;
import vintr.com.forecast.Helpers.ImageHelper;
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
    View mRootView;
    RecyclerView weatherByTimeView;
    WeatherByTimeAdapter weatherByTimeAdapter;
    LocationManager locationManager;
    APIWrapper apiWrapper;
    ImageView currentWeatherCardBackground;

    TextView temperature;
    TextView cityName;
    TextView weatherType;
    TextView dayName;

    public CityFragment() { }

    public static CityFragment newInstance() {
        return new CityFragment();
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_city, container, false);
        apiWrapper = new APIWrapper();

        temperature = mRootView.findViewById(R.id.temperature_city);
        cityName = mRootView.findViewById(R.id.city_name);
        dayName = mRootView.findViewById(R.id.day_name);
        weatherType = mRootView.findViewById(R.id.weather_type);
        currentWeatherCardBackground = mRootView.findViewById(R.id.current_weather_card_background);

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        dayName.setText(new SimpleDateFormat("EEEE, d MMMM", Locale.ENGLISH).format(date.getTime()));

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if ((ContextCompat.checkSelfPermission( getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) & !(getArguments().getString("city").equals("Location"))) {
            Log.i("CITY:", "Downloading Cur w data");
            getCurWeather(getArguments().getString("city"), null, null);
        } else {
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener(){
                @Override
                public void onLocationChanged(Location location) {
                    getCurWeather(null, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                }

                @Override
                public void onProviderDisabled(String s) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }
            }, null);
            //Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }

        for(int i = 0; i<10; i++){
            weatherByTimes.add(new WeatherByTime("9:00", String.valueOf(i), "Clear"));
        }

        weatherByTimeView = mRootView.findViewById(R.id.weatherByTimeView);
        weatherByTimeAdapter = new WeatherByTimeAdapter(weatherByTimes);
        weatherByTimeView.addItemDecoration(new GridItemDecoration(getContext(), R.dimen.item_offset));
        configureRecyclerView(weatherByTimeView,weatherByTimeAdapter, new GridLayoutManager(getContext(), 2));

        apiWrapper.getFiveDaysWeather(new Observer<FiveDaysList>() {
            ArrayList<WeatherByTime> newWeatherData = new ArrayList<>();
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(FiveDaysList fiveDaysList) {
                Double a = fiveDaysList.getMain().getTemp() - 273.15;
                //Date date = new Date(fiveDaysList.getDt());
                newWeatherData.add(new WeatherByTime(fiveDaysList.getDtTxt().substring(11,16),String.valueOf(a.intValue()) + "°", fiveDaysList.getWeather().get(0).getMain()));
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
        return mRootView;
    }

    private void getCurWeather(String city, String lat, String lon){
        apiWrapper.getWeatherObservable(city, lat, lon, new DisposableObserver<InformationHolder>() {
            @Override
            public void onNext(InformationHolder informationHolder) {
                Double a = informationHolder.getMain().getTemp() - 273.15;
                String type = informationHolder.getWeather().get(0).getMain();
                temperature.setText(String.valueOf(a.intValue()) + "°");
                weatherType.setText(type);
                cityName.setText(informationHolder.getName());
                currentWeatherCardBackground.setImageResource(new ImageHelper().getBackgroundImage(type));
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

    private void configureRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter, RecyclerView.LayoutManager manager){
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

}
