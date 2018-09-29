package vintr.com.forecast.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import vintr.com.forecast.Models.WeatherByDay;
import vintr.com.forecast.Models.WeatherByTime;
import vintr.com.forecast.R;

public class WeatherByDayAdapter extends RecyclerView.Adapter<WeatherByDayAdapter.WeatherByDayViewHolder>{

    private ArrayList<WeatherByDay> items;

    public WeatherByDayAdapter(ArrayList<WeatherByDay> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public WeatherByDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_weather_by_day, parent, false);
        return new WeatherByDayViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final WeatherByDayViewHolder holder, final int position){

        holder.day.setText(items.get(position).getDay());
        holder.temperature_days_day.setText(items.get(position).getTemperature_days_day());
        holder.temperature_days_night.setText(items.get(position).getTemperature_days_night());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class WeatherByDayViewHolder extends RecyclerView.ViewHolder {

        public TextView day;
        public TextView temperature_days_day;
        public TextView temperature_days_night;

        public WeatherByDayViewHolder(View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            temperature_days_day = itemView.findViewById(R.id.temperature_days_day);
            temperature_days_night = itemView.findViewById(R.id.temperature_days_night);
        }
    }
}