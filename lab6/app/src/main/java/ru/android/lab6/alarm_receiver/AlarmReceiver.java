package ru.android.lab6.alarm_receiver;

import static androidx.core.app.NotificationCompat.PRIORITY_DEFAULT;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ru.android.lab6.R;
import ru.android.lab6.notification_pages.ViewNotificationActivity;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "CHANNEL_ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        String id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        String notification = intent.getStringExtra("notification");
        String date = intent.getStringExtra("date");

        Intent intentToViewNotification = new Intent(context, ViewNotificationActivity.class);
        intentToViewNotification.putExtra("titleShow", title);
        intentToViewNotification.putExtra("notificationShow", notification);
        intentToViewNotification.putExtra("dateShow", date);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, Integer.parseInt(id), intentToViewNotification, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.baseline_feedback_24)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.baseline_feedback_24))
                        .setContentIntent(pendingIntent)
                        .setContentTitle(title)
                        .setContentText(notification)
                        .addAction(R.drawable.ic_launcher_foreground, "Показать полностью", pendingIntent)
                        .setPriority(PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        createChannelIfNeeded(notificationManager);
        notificationManager.notify(Integer.parseInt(id), notificationBuilder.build());
    }

    public static void createChannelIfNeeded(NotificationManagerCompat manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}
