package com.nachiketnaik.stormy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nachiketnaik.stormy.R;
import com.nachiketnaik.stormy.Weather.DailyData;

/**
 * Created by nachiketnaik on 9/30/15.
 */
public class DayAdapter extends BaseAdapter {
    private Context context;
    private DailyData[] dailyDatas;

    public DayAdapter(Context c, DailyData[] d){
        this.context=c;
        this.dailyDatas=d;
    }

    @Override
    public int getCount() {
        return dailyDatas.length;
    }

    @Override
    public Object getItem(int position) {
        return dailyDatas[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.daily_list_item,null);
            holder = new ViewHolder();
            holder.iconImageview= (ImageView)convertView.findViewById(R.id.iconImageView);
            holder.temperatureLabel =(TextView)convertView.findViewById(R.id.tempLabel);
            holder.dayLabel=(TextView)convertView.findViewById(R.id.dayNameLabel);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        DailyData day=dailyDatas[position];
        holder.iconImageview.setImageResource(day.getIconId());
        holder.temperatureLabel.setText(day.getTemp() + "");
        holder.dayLabel.setText(day.getDayOfTheWeek());
        return convertView;
    }

    private static class ViewHolder{
        ImageView iconImageview;
        TextView temperatureLabel;
        TextView dayLabel;
    }
}
