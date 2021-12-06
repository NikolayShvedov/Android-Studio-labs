package ru.android.lab3.db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.android.lab3.model.Classmate;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Classmates.db";
    public static final String TABLE_NAME = "classmate_table";

    public static final String KEY_ID = "ID";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_PATRONYMIC = "patronymic";
    public static final String KEY_DATE_CREATE = "date_create";

    String pattern = "MM/dd/yyyy HH:mm:ss";
    String todayAsString;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        KEY_NAME + " TEXT," +
                                                        KEY_SURNAME + " TEXT," +
                                                        KEY_PATRONYMIC + " TEXT," +
                                                        KEY_DATE_CREATE + " TEXT" + ")");

        db.delete(TABLE_NAME, null, null);

        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        todayAsString = df.format(today);

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, "Николай");
        contentValues.put(KEY_SURNAME, "Шведов");
        contentValues.put(KEY_PATRONYMIC, "Николаевич");
        contentValues.put(KEY_DATE_CREATE, todayAsString);
        db.insert(TABLE_NAME,null , contentValues);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(KEY_NAME, "Смирнов");
        contentValues2.put(KEY_SURNAME, "Иван");
        contentValues2.put(KEY_PATRONYMIC, "Иванович");
        contentValues2.put(KEY_DATE_CREATE, todayAsString);
        db.insert(TABLE_NAME,null , contentValues2);

        ContentValues contentValues3 = new ContentValues();
        contentValues3.put(KEY_NAME, "Петров");
        contentValues3.put(KEY_SURNAME, "Пётр");
        contentValues3.put(KEY_PATRONYMIC, "Петрович");
        contentValues3.put(KEY_DATE_CREATE, todayAsString);
        db.insert(TABLE_NAME,null , contentValues3);

        ContentValues contentValues4 = new ContentValues();
        contentValues4.put(KEY_NAME, "Андрей");
        contentValues4.put(KEY_SURNAME, "Малахов");
        contentValues4.put(KEY_PATRONYMIC, "Валерьевич");
        contentValues4.put(KEY_DATE_CREATE, todayAsString);
        db.insert(TABLE_NAME,null , contentValues4);

        ContentValues contentValues5 = new ContentValues();
        contentValues5.put(KEY_NAME, "Иван");
        contentValues5.put(KEY_SURNAME, "Иванов");
        contentValues5.put(KEY_PATRONYMIC, "Николаевич");
        contentValues5.put(KEY_DATE_CREATE, todayAsString);
        db.insert(TABLE_NAME,null , contentValues5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
    }

    public boolean insertData(Classmate classmate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, classmate.getName());
        contentValues.put(KEY_SURNAME, classmate.getSurname());
        contentValues.put(KEY_PATRONYMIC, classmate.getPatronymic());
        contentValues.put(KEY_DATE_CREATE, classmate.getDateCreate());
        long result = db.insert(TABLE_NAME,null ,contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.rawQuery("select * from " + TABLE_NAME,null);
    }

    public boolean updateData(String id, String name, String surname, String patronymic, String date_create) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_SURNAME, surname);
        contentValues.put(KEY_PATRONYMIC, patronymic);
        contentValues.put(KEY_DATE_CREATE, date_create);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] { id });
        return true;
    }

    public boolean updateLastData(Classmate classmate) {

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();

        classmate.setId(cursor.getInt(0));
        classmate.setName(cursor.getString(1));
        classmate.setSurname(cursor.getString(2));
        classmate.setPatronymic(cursor.getString(3));
        classmate.setDateCreate(cursor.getString(4));

        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        todayAsString = df.format(today);

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, "Иван");
        contentValues.put(KEY_SURNAME, "Иванов");
        contentValues.put(KEY_PATRONYMIC, "Иванович");
        contentValues.put(KEY_DATE_CREATE, todayAsString);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {String.valueOf(classmate.getId())});
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

}
