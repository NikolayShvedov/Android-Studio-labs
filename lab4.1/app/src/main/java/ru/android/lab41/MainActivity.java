package ru.android.lab41;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ru.android.lab41.async_task.MyAsyncTaskActivity;
import ru.android.lab41.db_helper.DBHelper;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    Button btnViewListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        btnViewListData = findViewById(R.id.buttonViewListData);
    }

    public void onClickViewListData(View v) {
        Intent intent = new Intent(this, MyAsyncTaskActivity.class);
        startActivity(intent);
    }
}