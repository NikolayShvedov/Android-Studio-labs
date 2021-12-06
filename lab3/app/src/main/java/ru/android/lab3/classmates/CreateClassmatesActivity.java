package ru.android.lab3.classmates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.android.lab3.R;
import ru.android.lab3.db_helper.DBHelper;
import ru.android.lab3.model.Classmate;

public class CreateClassmatesActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editName;
    private EditText editSurname;
    private EditText editPatronymic;
    private String editDateCreate;
    private Button button;

    private DBHelper dbHelper;
    public Classmate classmate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_classmates);

        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_surname);
        editPatronymic = (EditText) findViewById(R.id.editText_Patronymic);
        editDateCreate = convertNowDateToString();
        button = (Button) findViewById(R.id.button_add);
        button.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View view) {

        classmate = new Classmate();
        classmate.setName(editName.getText().toString());
        classmate.setSurname(editSurname.getText().toString());
        classmate.setPatronymic(editPatronymic.getText().toString());
        classmate.setDateCreate(editDateCreate);

        boolean isInserted = dbHelper.insertData(classmate);
        if(isInserted)
            Toast.makeText(CreateClassmatesActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(CreateClassmatesActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
    }

    public String convertNowDateToString() {

        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        return todayAsString;
    }
}