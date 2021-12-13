package ru.android.lab6.notification_pages;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ru.android.lab6.R;
import ru.android.lab6.alarm_receiver.AlarmReceiver;
import ru.android.lab6.db_helper.DBHelper;

public class DeleteNotificationActivity extends AppCompatActivity {

    TextView notificationList;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notification);
        notificationList = findViewById(R.id.listNotifications);
        notificationList.setMovementMethod(new ScrollingMovementMethod());
        dbHelper = new DBHelper(this);
    }

    @Override
    protected void onResume() {

        super.onResume();
        Cursor cursor = dbHelper.getAllData();
        if (cursor.moveToFirst()) {
            do {
                this.notificationList.append("\nid: "
                        + cursor.getInt(0) + "\n"
                        + "Заголовок: " + cursor.getString(1) + "\n"
                        + "Текст заметки:" + cursor.getString(2) + "\n"
                        + "Дата и время срабатывания:" + cursor.getString(3)
                        + "\n" + "-------------------------------------------");
            } while (cursor.moveToNext());
        }
    }

    public void deleteNotification(View view) {
        EditText id = findViewById(R.id.IDforDelete);
        String idForDelete = id.getText().toString();
        if (!idForDelete.equals("".trim())) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(Integer.parseInt(idForDelete));
            }

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                    Integer.parseInt(idForDelete),
                    myIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.cancel(pendingIntent);
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            if (database.delete(DBHelper.TABLE_NAME, "id = ?", new String[]{idForDelete}) >= 1) {
                Toast.makeText(getApplicationContext(), "Заметка с id = " + idForDelete + " успешно удалена!", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "Заметки с данным id не существует!", Toast.LENGTH_SHORT).show();
            dbHelper.close();

        } else {
            Toast.makeText(getApplicationContext(), "Error id!", Toast.LENGTH_SHORT).show();
        }
    }
}