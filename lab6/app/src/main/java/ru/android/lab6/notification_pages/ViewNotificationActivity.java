package ru.android.lab6.notification_pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ru.android.lab6.R;

public class ViewNotificationActivity extends AppCompatActivity {

    private TextView title, notification, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_notification);
        title = findViewById(R.id.textViewNotifyTitle);
        notification = findViewById(R.id.textViewNotifyText);
        date = findViewById(R.id.textViewNotifyDate);

        Intent intent = getIntent();
        String titleForView = intent.getStringExtra("titleShow");
        String notificationForView = intent.getStringExtra("notificationShow");
        String dateForView = intent.getStringExtra("dateShow");

        title.setText(titleForView);
        notification.setText(notificationForView);
        date.setText(dateForView);
    }
}