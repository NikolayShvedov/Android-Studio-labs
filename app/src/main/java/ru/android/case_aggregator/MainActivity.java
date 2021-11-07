package ru.android.case_aggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra("newNote", editText.getText().toString());
        startActivity(intent);
    }
}