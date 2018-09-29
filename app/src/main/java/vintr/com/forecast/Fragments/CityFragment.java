package vintr.com.forecast.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import vintr.com.forecast.Adapters.WeatherByDayAdapter;
import vintr.com.forecast.Adapters.WeatherByTimeAdapter;
import vintr.com.forecast.Models.WeatherByDay;
import vintr.com.forecast.Models.WeatherByTime;
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

    public CityFragment() { }

    public static CityFragment newInstance() {
        return new CityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_city, container, false);

        for(int i = 0; i<24; i++){
            weatherByTimes.add(new WeatherByTime("9:00", String.valueOf(i)));
        }

        weatherByTimeView = mRootView.findViewById(R.id.weatherByTimeView);
        weatherByTimeView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        weatherByTimeAdapter = new WeatherByTimeAdapter(weatherByTimes);
        weatherByTimeView.setAdapter(weatherByTimeAdapter);

        for(int i = 0; i<7; i++){
            weatherByDays.add(new WeatherByDay("Суббота", String.valueOf(i), String.valueOf(i+1)));
        }

        weatherByDayView = mRootView.findViewById(R.id.weatherByDayView);
        weatherByDayView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        weatherByDayAdapter = new WeatherByDayAdapter(weatherByDays);
        weatherByDayView.setAdapter(weatherByDayAdapter);

        //Toast.makeText(getContext(), String.valueOf(getArguments().getInt("position")), Toast.LENGTH_SHORT).show();
        return mRootView;
    }
}
