package com.resourcefulparenting.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.resourcefulparenting.R;

public class ChildInstructions extends AppCompatActivity {

    AppCompatButton btn_ok;
    ImageView img_back;
    Context context;
    private String  name, gender, date_, month_, year_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_instructions);
        context=this;

        name = getIntent().getStringExtra("name");
        gender = getIntent().getStringExtra("gender");
        date_ = getIntent().getStringExtra("date");
        month_ = getIntent().getStringExtra("month");
        year_ = getIntent().getStringExtra("year");

        btn_ok =findViewById(R.id.btn_ok);
        img_back =findViewById(R.id.img_back);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent next = new Intent(context, AddChildQuestions.class);
                next.putExtra("name", name);
                next.putExtra("gender", gender);
                next.putExtra("date", date_);
                next.putExtra("month", month_);
                next.putExtra("year", year_);
                //  next.putExtra("login_token", login_token);
                startActivity(next);
              //  finish();
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}