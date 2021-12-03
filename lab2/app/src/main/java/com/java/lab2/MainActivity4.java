package com.java.lab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity4 extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private RelativeLayout relativeLayout;
    int col = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        constraintLayout = findViewById(R.id.constraintLayout);
        relativeLayout = findViewById(R.id.relativeLayout);
        constraintLayout.setBackgroundColor(Color.WHITE);
    }

    public void changeColor(View view) {
        if(col == Color.WHITE)
            col = Color.GREEN;
        else
            col = Color.WHITE;
        constraintLayout.setBackgroundColor(col);
        relativeLayout.setBackgroundColor(col);
    }
}