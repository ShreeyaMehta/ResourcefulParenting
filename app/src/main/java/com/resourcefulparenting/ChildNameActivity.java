package com.resourcefulparenting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class ChildNameActivity extends AppCompatActivity {
    private Context context;
    private Button get_started;
    private CardView selected_boy, selected_girl;
    private ImageView img_boy, img_girl;
    private String gender = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_name);
        context = this;
        ids();
        String access = getIntent().getStringExtra("access_token");

        img_boy.setOnClickListener(view -> {
            selected_boy.setVisibility(View.VISIBLE);
            selected_girl.setVisibility(View.GONE);
            gender = "Boy";
        });

        img_girl.setOnClickListener(view -> {
            selected_girl.setVisibility(View.VISIBLE);
            selected_boy.setVisibility(View.GONE);
            gender = "Girl";
        });

        get_started.setOnClickListener(view -> {
            Intent next = new Intent(context, ChildAgeActivity.class);
            next.putExtra("access_token", access);
            next.putExtra("gender",gender);
            startActivity(next);
        });
    }

    private void ids() {
        get_started = findViewById(R.id.btn_getStarted);
        selected_boy = findViewById(R.id.selected_card_boy);
        selected_girl = findViewById(R.id.selected_card_girl);
        img_boy = findViewById(R.id.img_boy);
        img_girl = findViewById(R.id.img_girl);
    }
}
