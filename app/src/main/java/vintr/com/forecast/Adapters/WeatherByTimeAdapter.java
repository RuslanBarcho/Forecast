package vintr.com.forecast.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vintr.com.forecast.Models.WeatherByTime;
import vintr.com.forecast.R;

public class WeatherByTimeAdapter extends RecyclerView.Adapter<WeatherByTimeAdapter.WeatherByTimeViewHolder>{

    private ArrayList<WeatherByTime> items;

    public WeatherByTimeAdapter(ArrayList<WeatherByTime> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public WeatherByTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_weather_by_time, parent, false);
        return new WeatherByTimeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final WeatherByTimeViewHolder holder, final int position){

        holder.time.setText(items.get(position).getTime());
        holder.temperature.setText(items.get(position).getTemperature());
        switch (items.get(position).getType()){
            case "Rain":
                holder.icon.setImageResource(R.drawable.ic_rain);
                break;
            case "Clear":
                holder.icon.setImageResource(R.drawable.ic_clear);
                break;
                default:
                    holder.icon.setImageResource(R.drawable.ic_partly_cloudy);
                    break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class WeatherByTimeViewHolder extends RecyclerView.ViewHolder {

        public TextView time;
        public TextView temperature;
        public ImageView icon;

        public WeatherByTimeViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            temperature = itemView.findViewById(R.id.temperature);
            icon = itemView.findViewById(R.id.weather_by_time_icon);
        }
    }

    public void updateInfo(ArrayList<WeatherByTime> items){
        this.items = items;
    }
}
