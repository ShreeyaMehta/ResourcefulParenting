package com.resourcefulparenting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class QuestionaireActivity extends AppCompatActivity {
    private Context context;
    private Button btn1, btn2, btn3, btn4, btn5, back, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);
        context = this;
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        btn1 = findViewById(R.id.rate_1);
        btn2 = findViewById(R.id.rate_2);
        btn3 = findViewById(R.id.rate_3);
        btn4 = findViewById(R.id.rate_4);
        btn5 = findViewById(R.id.rate_5);

        next.setOnClickListener(view -> startActivity(new Intent(context, MainActivity.class)));

        back.setOnClickListener(view -> startActivity(new Intent(context, ChildAgeActivity.class)));

        btn1.setOnClickListener(view -> {
            btn1.setBackgroundResource(R.drawable.orange_bg);
            btn2.setBackgroundResource(R.drawable.blue_bg);
            btn3.setBackgroundResource(R.drawable.blue_bg);
            btn4.setBackgroundResource(R.drawable.blue_bg);
            btn5.setBackgroundResource(R.drawable.blue_bg);
        });

        btn2.setOnClickListener(view -> {
            btn2.setBackgroundResource(R.drawable.orange_bg);
            btn1.setBackgroundResource(R.drawable.blue_bg);
            btn3.setBackgroundResource(R.drawable.blue_bg);
            btn4.setBackgroundResource(R.drawable.blue_bg);
            btn5.setBackgroundResource(R.drawable.blue_bg);
        });

        btn3.setOnClickListener(view -> {
            btn3.setBackgroundResource(R.drawable.orange_bg);
            btn2.setBackgroundResource(R.drawable.blue_bg);
            btn1.setBackgroundResource(R.drawable.blue_bg);
            btn4.setBackgroundResource(R.drawable.blue_bg);
            btn5.setBackgroundResource(R.drawable.blue_bg);
        });

        btn4.setOnClickListener(view -> {
            btn4.setBackgroundResource(R.drawable.orange_bg);
            btn2.setBackgroundResource(R.drawable.blue_bg);
            btn3.setBackgroundResource(R.drawable.blue_bg);
            btn1.setBackgroundResource(R.drawable.blue_bg);
            btn5.setBackgroundResource(R.drawable.blue_bg);
        });

        btn5.setOnClickListener(view -> {
            btn5.setBackgroundResource(R.drawable.orange_bg);
            btn2.setBackgroundResource(R.drawable.blue_bg);
            btn3.setBackgroundResource(R.drawable.blue_bg);
            btn4.setBackgroundResource(R.drawable.blue_bg);
            btn1.setBackgroundResource(R.drawable.blue_bg);
        });
    }
}