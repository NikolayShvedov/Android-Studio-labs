package ru.android.lab6.db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.android.lab6.models.Notification;

public class DBHelper extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "notifications_data.db";
    public static final String TABLE_NAME = "notifications";

    public static final String KEY_ID = "ID";
    public static final String KEY_TITLE = "title";
    public static final String KEY_NOTIFICATION = "notification";
    public static final String KEY_DATE_NOTIFY = "date_notify";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_TITLE + " TEXT," +
                KEY_NOTIFICATION + " TEXT," +
                KEY_DATE_NOTIFY + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
    }

    public boolean insertNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, notification.getTitle());
        contentValues.put(KEY_NOTIFICATION, notification.getNotification());
        contentValues.put(KEY_DATE_NOTIFY, notification.getDateNotify());
        long result = db.insert(TABLE_NAME,null ,contentValues);
        return result != -1;
    }

    public String getNotificationId() {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT id FROM " + TABLE_NAME + " WHERE id = (SELECT MAX(id) FROM " + TABLE_NAME + ");";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToLast();
        return cursor.getString(0);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

}
