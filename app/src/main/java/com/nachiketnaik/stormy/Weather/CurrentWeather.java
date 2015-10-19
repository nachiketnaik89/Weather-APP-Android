package com.nachiketnaik.stormy.Weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by nachiketnaik on 9/26/15.
 */
public class CurrentWeather {

    private String Icon;
    private long Time;
    private double Temp;
    private double mHumidity;
    private double mPrecipChance;
    private String summary;
    private String timeZone;



    public String getFormattedTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        String timeString = sdf.format(new Date(getTime()*1000));
        return timeString;
    }

    @Override
    public String toString() {
        return "Time: "+Time+" temp: "+Temp+" humidity: "+mHumidity+" precipchace: "+mPrecipChance+" summary: "+summary+" timeformatted: "+timeZone;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        Time = time;
    }

    public double getTemp() {
        return (int)Temp;
    }

    public int getIconId(){
        return Forcast.getIconId(getIcon());
    }


    public void setTemp(double temp) {
        Temp = temp;
    }

    public double getmHumidity() {
        return mHumidity;
    }

    public void setmHumidity(double mHumidity) {
        this.mHumidity = mHumidity;
    }

    public double getmPrecipChance() {
        return (int)mPrecipChance*100;
    }

    public void setmPrecipChance(double mPrecipChance) {
        this.mPrecipChance = mPrecipChance;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
