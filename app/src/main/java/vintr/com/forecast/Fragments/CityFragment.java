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

import vintr.com.forecast.Adapters.WeatherByTimeAdapter;
import vintr.com.forecast.Models.WeatherByTime;
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

    public CityFragment() {
        // Required empty public constructor
    }

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        weatherByTimeView = mRootView.findViewById(R.id.weatherByTimeView);
        weatherByTimeView.setLayoutManager(layoutManager);
        weatherByTimeAdapter = new WeatherByTimeAdapter(weatherByTimes);
        weatherByTimeView.setAdapter(weatherByTimeAdapter);

        Toast.makeText(getContext(), String.valueOf(getArguments().getInt("position")), Toast.LENGTH_SHORT).show();
        return mRootView;
    }
}
