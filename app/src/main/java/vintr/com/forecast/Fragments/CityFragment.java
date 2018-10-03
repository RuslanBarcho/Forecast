package vintr.com.forecast.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    TextView temperature;

    public CityFragment() { }

    public static CityFragment newInstance() {
        return new CityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_city, container, false);

        temperature = mRootView.findViewById(R.id.temperature_city);

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

        APIWrapper apiWrapper = new APIWrapper();
        apiWrapper.getCurrentWeather(new DisposableObserver<InformationHolder>() {
            @Override
            public void onNext(InformationHolder informationHolder) {
                Double a = informationHolder.getMain().getTemp() - 273.15;
                temperature.setText(String.valueOf(a.intValue()) + "°");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("Retrofit", "error" + e.toString());
            }

            @Override
            public void onComplete() {

            }
        });

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
                Log.i("Retrofit", "Done!");
            }
        });
        //Toast.makeText(getContext(), String.valueOf(getArguments().getInt("position")), Toast.LENGTH_SHORT).show();
        return mRootView;
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
