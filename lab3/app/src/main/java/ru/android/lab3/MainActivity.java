package ru.android.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ru.android.lab3.classmates.CreateClassmatesActivity;
import ru.android.lab3.classmates.UpdateClassmatesActivity;
import ru.android.lab3.db_helper.DBHelper;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    Button btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        btnViewAll = findViewById(R.id.buttonSelect);
        viewAll();
    }

    public void goToCreateClassmates(View view) {
        Intent intent = new Intent(this, CreateClassmatesActivity.class);
        startActivity(intent);
    }

    public void goToUpdateClassmates(View view) {
        Intent intent = new Intent(this, UpdateClassmatesActivity.class);
        startActivity(intent);
    }

    public void viewAll() {
        btnViewAll.setOnClickListener(
                v -> {
                    Cursor res = dbHelper.getAllData();
                    if(res.getCount() == 0) {
                        // show message
                        showMessage("Error","Nothing found");
                        return;
                    }

                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append("Id :"+ res.getString(0)+"\n");
                        buffer.append("Имя :"+ res.getString(1)+"\n");
                        buffer.append("Фамилия :"+ res.getString(2)+"\n");
                        buffer.append("Отчество :"+ res.getString(3)+"\n");
                        buffer.append("Дата добавления :"+ res.getString(4)+"\n\n");
                    }

                    // Show all data
                    showMessage("Data",buffer.toString());
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }



}