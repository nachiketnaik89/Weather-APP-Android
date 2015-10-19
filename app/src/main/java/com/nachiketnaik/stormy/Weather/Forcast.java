package com.nachiketnaik.stormy.Weather;

import com.nachiketnaik.stormy.R;

/**
 * Created by nachiketnaik on 9/27/15.
 */
public class Forcast {
    private CurrentWeather curentWeather;

    public CurrentWeather getCurentWeather() {
        return curentWeather;
    }

    public void setCurentWeather(CurrentWeather curentWeather) {
        this.curentWeather = curentWeather;
    }

    public HourlyData[] getHourlyData() {
        return hourlyData;
    }

    public void setHourlyData(HourlyData[] hourlyData) {
        this.hourlyData = hourlyData;
    }

    public DailyData[] getDailyData() {
        return dailyData;
    }

    public void setDailyData(DailyData[] dailyData) {
        this.dailyData = dailyData;
    }

    private HourlyData[] hourlyData;
    private DailyData[] dailyData;

    public static int getIconId(String Icon){

        int iconId = R.mipmap.clear_day;
        if (Icon.equals("clear-day")) {
            iconId = R.mipmap.clear_day;
        }
        else if (Icon.equals("clear-night")) {
            iconId = R.mipmap.clear_night;
        }
        else if (Icon.equals("rain")) {
            iconId = R.mipmap.rain;
        }
        else if (Icon.equals("snow")) {
            iconId = R.mipmap.snow;
        }
        else if (Icon.equals("sleet")) {
            iconId = R.mipmap.sleet;
        }
        else if (Icon.equals("wind")) {
            iconId = R.mipmap.wind;
        }
        else if (Icon.equals("fog")) {
            iconId = R.mipmap.fog;
        }
        else if (Icon.equals("cloudy")) {
            iconId = R.mipmap.cloudy;
        }
        else if (Icon.equals("partly-cloudy-day")) {
            iconId = R.mipmap.partly_cloudy;
        }
        else if (Icon.equals("partly-cloudy-night")) {
            iconId = R.mipmap.cloudy_night;
        }
        return iconId;
    }
}
