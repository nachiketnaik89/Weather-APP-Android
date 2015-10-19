package com.nachiketnaik.stormy.Weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by nachiketnaik on 9/27/15.
 */
public class DailyData implements Parcelable {
    private long time;
    private String summary;
    private double temp;
    private String icon;
    private String timeZone;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public int getIconId() {
        return Forcast.getIconId(icon);
    }

    public String getDayOfTheWeek() {
        SimpleDateFormat sf = new SimpleDateFormat("EEEE");
        sf.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date dateTime = new Date(time * 1000);
        return sf.format(dateTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeString(summary);
        dest.writeString(timeZone);
        dest.writeDouble(temp);
        dest.writeString(icon);
    }

    private DailyData(Parcel in){
        time = in.readLong();
        summary= in.readString();
        timeZone=in.readString();
        temp= in.readDouble();
        icon = in.readString();
    }

    public static final Creator<DailyData> CREATOR = new Creator<DailyData>() {
        @Override
        public DailyData createFromParcel(Parcel source) {
            return new DailyData(source);
        }

        @Override
        public DailyData[] newArray(int size) {
            return new DailyData[size];
        }
    };

    public DailyData(){

    }

}
