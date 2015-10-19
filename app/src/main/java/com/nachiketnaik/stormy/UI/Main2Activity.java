package com.nachiketnaik.stormy.UI;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.nachiketnaik.stormy.R;
import com.nachiketnaik.stormy.Weather.DailyData;
import com.nachiketnaik.stormy.adapters.DayAdapter;

import java.util.Arrays;

public class Main2Activity extends ListActivity {
    private DailyData[] days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String arrayOfWeek[] ={ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        //ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,arrayOfWeek);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILYFORCAST);
        days = Arrays.copyOf(parcelables,parcelables.length,DailyData[].class);

        DayAdapter dayAdapter = new DayAdapter(this,days);
        setListAdapter(dayAdapter);

    }

}
