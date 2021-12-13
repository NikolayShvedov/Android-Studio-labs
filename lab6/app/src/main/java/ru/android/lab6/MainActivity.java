package ru.android.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.android.lab6.alarm_receiver.AlarmReceiver;
import ru.android.lab6.db_helper.DBHelper;
import ru.android.lab6.models.Notification;
import ru.android.lab6.notification_pages.DeleteNotificationActivity;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private EditText title, notification_text;
    private String date_notify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        title = findViewById(R.id.editTitle);
        notification_text = findViewById(R.id.editNotification);
        date_notify = convertNowDateToString();
    }

    public void onClickView(View v) {
        Intent intent = new Intent(this, DeleteNotificationActivity.class);
        startActivity(intent);
    }

    public void addNotification(View v) throws ParseException {

        Notification notification = new Notification();

        notification.setTitle(title.getText().toString());
        notification.setNotification(notification_text.getText().toString());
        notification.setDateNotify(date_notify);

        dbHelper.insertNotification(notification);

        String id = dbHelper.getNotificationId();

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        notificationIntent.putExtra("id", id);
        notificationIntent.putExtra("title", notification.getTitle());
        notificationIntent.putExtra("notification", notification.getNotification());
        notificationIntent.putExtra("date", date_notify);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Integer.parseInt(id), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Date dateToNotify = convertNowDateToString(date_notify);
        long millis = dateToNotify.getTime();
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
        Toast.makeText(getApplicationContext(), "Заметка успешно создана!", Toast.LENGTH_SHORT).show();
        title.setText("");
        notification_text.setText("");
    }

    public String convertNowDateToString() {

        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        return todayAsString;
    }

    public Date convertNowDateToString(String dateString) throws ParseException {

        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = dateFormat.parse(dateString);
        return date;
    }
}