package com.nachiketnaik.stormy.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nachiketnaik.stormy.R;
import com.nachiketnaik.stormy.Weather.CurrentWeather;
import com.nachiketnaik.stormy.Weather.DailyData;
import com.nachiketnaik.stormy.Weather.Forcast;
import com.nachiketnaik.stormy.Weather.HourlyData;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILYFORCAST ="DailyData" ;
    //private CurrentWeather current;
    private Forcast forcast;

    @Bind(R.id.timeLabel)
    TextView timeLabel;
    @Bind(R.id.tempratureLabel)
    TextView tempratureLabel;
    @Bind(R.id.humidityValue)
    TextView humidityValue;
    @Bind(R.id.precipValue)
    TextView precipValue;
    @Bind(R.id.summaryText)
    TextView summaryLabel;
    @Bind(R.id.iconImageView)
    ImageView iconImageView;
    @Bind(R.id.refreshImageView)
    ImageView refreshImageView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.INVISIBLE);
        final double lattitude = 37.8267;
        final double longitude = -122.423;


        refreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(lattitude, longitude);
            }
        });

        getForecast(lattitude, longitude);
    }

    private void getForecast(double lattitude, double longitude) {
        String apiKey = "3f6dd928b0ef426d34301aa7522d8dbd";

        String url = "https://api.forecast.io/forecast/" + apiKey + "/" + lattitude + "," + longitude;
        if (isNetworkAvaiable()) {
            toggleView();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.e(TAG, "faliure " + request.toString(), e);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleView();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleView();
                        }
                    });

                    try {
                        //Response response = call.execute();
                        if (response.isSuccessful()) {
                            forcast= parseForcastDetails(response.body().string());
                           // current = getCurrentDetails(response.body().string());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });

                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "exception caught", e);
                    }
                }
            });

        } else {
            Toast.makeText(this, "network is Unavaliable", Toast.LENGTH_LONG).show();
        }
    }

    private void toggleView() {
        if (progressBar.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
            refreshImageView.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            refreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        CurrentWeather current = forcast.getCurentWeather();
        tempratureLabel.setText(current.getTemp() + "");
        timeLabel.setText("At " + current.getFormattedTime() + " it will be");
        humidityValue.setText(current.getmHumidity() + "");
        precipValue.setText(current.getmPrecipChance() + "%");
        summaryLabel.setText(current.getSummary());
        Drawable drawable = getResources().getDrawable(current.getIconId());
        iconImageView.setImageDrawable(drawable);

    }


    private boolean isNetworkAvaiable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    private Forcast parseForcastDetails(String jsonData)throws JSONException{
        Forcast forcast = new Forcast();
        forcast.setCurentWeather(getCurrentDetails(jsonData));
        forcast.setHourlyData(getHourlyForcastData(jsonData));
        forcast.setDailyData(getDailyForcastData(jsonData));
        return forcast;
    }

    private HourlyData[] getHourlyForcastData(String jsonData)throws  JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timeZone = forecast.getString("timezone");
        JSONObject hourly= forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");
        HourlyData[] hourlyData = new HourlyData[data.length()];
        for(int i=0;i<data.length();i++){
            JSONObject jsonhour  = data.getJSONObject(i);
            HourlyData hour = new HourlyData();
            hour.setIcon(jsonhour.getString("icon"));
            hour.setSummary(jsonhour.getString("summary"));
            hour.setTemp(jsonhour.getDouble("temperature"));
            hour.setTime(jsonhour.getLong("time"));
            hour.setTimeZone(timeZone);
             hourlyData[i]= hour;
        }
        return hourlyData;
    }
    private DailyData[] getDailyForcastData(String jsonData)throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timeZone = forecast.getString("timezone");

        JSONObject daily= forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        DailyData[] dailyData = new DailyData[data.length()];
        for(int i=0;i<data.length();i++){
            JSONObject jsonhour  = data.getJSONObject(i);
            DailyData day = new DailyData();
            day.setIcon(jsonhour.getString("icon"));
            day.setSummary(jsonhour.getString("summary"));
            day.setTemp(jsonhour.getDouble("temperatureMax"));
            day.setTime(jsonhour.getLong("time"));
            day.setTimeZone(timeZone);
            dailyData[i]= day;
        }
        return dailyData;

    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timeZone = forecast.getString("timezone");
        JSONObject currently = forecast.getJSONObject("currently");
        CurrentWeather curerntWeather = new CurrentWeather();
        curerntWeather.setmHumidity(currently.getDouble("humidity"));
        curerntWeather.setTemp(currently.getDouble("temperature"));
        curerntWeather.setTime(currently.getLong("time"));
        curerntWeather.setmPrecipChance(currently.getDouble("precipProbability"));
        curerntWeather.setIcon(currently.getString("icon"));
        curerntWeather.setSummary(currently.getString("summary"));
        curerntWeather.setTimeZone(timeZone);
        Log.i(TAG, curerntWeather.getFormattedTime());
        return curerntWeather;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }
    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view){
        Intent intent = new Intent(this,Main2Activity.class);
        intent.putExtra(MainActivity.DAILYFORCAST,forcast.getDailyData());
        startActivity(intent);
    }

}
