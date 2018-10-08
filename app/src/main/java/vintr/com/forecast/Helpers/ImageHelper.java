package vintr.com.forecast.Helpers;

import android.graphics.drawable.Drawable;

import vintr.com.forecast.R;

public class ImageHelper {

    public ImageHelper() {

    }

    public int getBackgroundImage(String type){
        switch (type){
            case "Clear":
                return R.drawable.background_clear_day;
            case "Clouds":
                return  R.drawable.background_cloudy_day;
                default: return R.drawable.background_clear_day;
        }
    }
}
