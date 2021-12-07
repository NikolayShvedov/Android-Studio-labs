package ru.android.lab41.db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.android.lab41.models.MusicsRadioRequest;

public class DBHelper extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "musics_data.db";
    public static final String TABLE_NAME = "musics";

    public static final String KEY_ID = "ID";
    public static final String KEY_AUTHOR_NAME = "author_name";
    public static final String KEY_MUSIC_NAME = "music_name";
    public static final String KEY_DATE_CREATE = "date_create";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        KEY_AUTHOR_NAME + " TEXT," +
                                                        KEY_MUSIC_NAME + " TEXT," +
                                                        KEY_DATE_CREATE + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
    }

    public boolean insertData(MusicsRadioRequest request) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_AUTHOR_NAME, request.getAuthorName());
        contentValues.put(KEY_MUSIC_NAME, request.getMusicName());
        contentValues.put(KEY_DATE_CREATE, request.getDateCreate());
        long result = db.insert(TABLE_NAME,null ,contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    public Cursor getLastData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    public void deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME, null, null);
    }

}
