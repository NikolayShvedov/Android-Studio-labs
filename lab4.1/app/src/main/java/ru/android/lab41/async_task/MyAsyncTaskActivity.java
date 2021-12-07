package ru.android.lab41.async_task;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import ru.android.lab41.R;
import ru.android.lab41.db_helper.DBHelper;
import ru.android.lab41.models.MusicsRadioRequest;
import ru.android.lab41.models.MusicsRadioResponse;
import ru.android.lab41.models.MusicsRadioURL;
import ru.android.lab41.models.MusicsRadioUtils;
import ru.android.lab41.models.UserRadioData;

public class MyAsyncTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private static MusicsRadioUtils utils;
    public DBHelper dbHelper;
    public Timer timer = new Timer();

    private TextView lastMusic, status, musics;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_async_task);
        dbHelper = new DBHelper(this);
        utils = new MusicsRadioUtils();
        button = findViewById(R.id.clearDB);
        button.setOnClickListener(this);
        lastMusic = findViewById(R.id.lastMusic);
        status = findViewById(R.id.status);
        musics = findViewById(R.id.musicsList);
        musics.setMovementMethod(new ScrollingMovementMethod());

        showAll();
        if (hasConnection(this) == false) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Проверьте подключение к интернету!", Toast.LENGTH_SHORT);
            toast.show();
            status.setText("Офлайн");
            status.setTextColor(Color.RED);
        }
        else {
            status.setText("Онлайн");
            status.setTextColor(Color.BLUE);

        }

        TimerTask timerTask = new TimerTask() {
            @Override

            public void run(){
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(20, TimeUnit.SECONDS)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(MusicsRadioUtils.URL)
                        .client(okHttpClient)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                MusicsRadioURL service = retrofit.create(MusicsRadioURL.class);

                Call<MusicsRadioResponse> call=service.getNameMusic(new UserRadioData(MusicsRadioUtils.LOGIN, MusicsRadioUtils.PASSWORD));
                call.enqueue(new Callback<MusicsRadioResponse>() {
                    @Override
                    public void onResponse(Call<MusicsRadioResponse> call, Response<MusicsRadioResponse> response) {
                        status.setText("Онлайн");
                        status.setTextColor(Color.BLUE);
                        lastMusic.setText(response.body().info);
                        System.out.println("+");
                        String[] strings = response.body().info.split(" - ");
                        if(!lastMusic().equals(strings[1])){
                            insertDataInDataBase(strings[0], strings[1]);
                            showAll();
                        }
                    }

                    @Override
                    public void onFailure(Call<MusicsRadioResponse> call, Throwable t) {
                        status.setText("Офлайн");
                        status.setTextColor(Color.RED);
                        lastMusic.setText("Офлайн режим работы, функция недоступна!");
                    }
                });
            }

        };
        timer.schedule(timerTask, 0, 20000);

    }

    public void showAll(){

        Cursor cursor = dbHelper.getAllData();
        if (cursor.moveToFirst()) {
            musics.setText("");

            int authorIndex = cursor.getColumnIndex(DBHelper.KEY_AUTHOR_NAME);
            int musicIndex = cursor.getColumnIndex(DBHelper.KEY_MUSIC_NAME);
            int dateCreateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE_CREATE);
            do {
                this.musics.append(cursor.getString(authorIndex) + " " + cursor.getString(musicIndex) + " "  + cursor.getString(dateCreateIndex) + "\n" + "\n");
            } while (cursor.moveToNext());
        }
        else {
            musics.setText("The database is empty!");
        }

    }

    public String lastMusic() {
        Cursor cursor = dbHelper.getLastData();
        if (cursor.moveToFirst()) {
            cursor.moveToLast();
            int musicIndex = cursor.getColumnIndex(DBHelper.KEY_MUSIC_NAME);

            dbHelper.close();
            return cursor.getString(musicIndex);
        }
        else {
            return "null";
        }
    }

    public void insertDataInDataBase(String author, String music){

        MusicsRadioRequest newRequest = new MusicsRadioRequest();
        newRequest.setAuthorName(author);
        newRequest.setMusicName(music);
        newRequest.setDateCreate(convertNowDateToString());

        boolean isInserted = dbHelper.insertData(newRequest);
        if(isInserted)
            Toast.makeText(MyAsyncTaskActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MyAsyncTaskActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    public void onClick(View v) {
        dbHelper.deleteData();
        showAll();
    }

    public static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }

    public String convertNowDateToString() {
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        return todayAsString;
    }
}