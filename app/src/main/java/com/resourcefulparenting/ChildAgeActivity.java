package com.resourcefulparenting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ChildAgeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Context context;
    private Button next;
    private Spinner date, month, year;
    private int date_, month_, year_;
    private long age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_age);
        context = this;
        String access = getIntent().getStringExtra("access_token");
        next = findViewById(R.id.btn_next);
        date = findViewById(R.id.date);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        next.setOnClickListener(view -> {
            age = getAge(year_, month_, date_);
            if(age <= 2) {
                Intent next = new Intent(context, MainActivity.class);
                next.putExtra("access_token", access);
                startActivity(next);
            }else if(age > 2) {
                Intent next = new Intent(context, QuestionaireActivity.class);
                next.putExtra("access_token", access);
                startActivity(next);
            }
        });

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2010; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, years);
        year.setAdapter(adapter);

        List<Integer> dates = new ArrayList<>();
        dates.add(1);
        dates.add(2);
        dates.add(3);
        dates.add(4);
        dates.add(5);
        dates.add(6);
        dates.add(7);
        dates.add(8);
        dates.add(9);
        dates.add(10);
        dates.add(11);
        dates.add(12);
        dates.add(13);
        dates.add(14);
        dates.add(15);
        dates.add(16);
        dates.add(17);
        dates.add(18);
        dates.add(19);
        dates.add(20);
        dates.add(21);
        dates.add(22);
        dates.add(23);
        dates.add(24);
        dates.add(25);
        dates.add(26);
        dates.add(27);
        dates.add(28);
        dates.add(29);
        dates.add(30);
        dates.add(31);

        // Creating adapter for spinner
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(context, android.R.layout.simple_spinner_item, dates);
        // attaching data adapter to spinner
        date.setAdapter(dataAdapter);

        List<Integer> months = new ArrayList<>();
        months.add(1);
        months.add(2);
        months.add(3);
        months.add(4);
        months.add(5);
        months.add(6);
        months.add(7);
        months.add(8);
        months.add(9);
        months.add(10);
        months.add(11);
        months.add(12);

        // Creating adapter for spinner
        ArrayAdapter<Integer> dataAdapter1 = new ArrayAdapter<Integer>(context, android.R.layout.simple_spinner_item, months);
        // attaching data adapter to spinner
        month.setAdapter(dataAdapter1);

        date_ = Integer.parseInt(date.getSelectedItem().toString());
        month_ = Integer.parseInt(month.getSelectedItem().toString());
        year_ = Integer.parseInt(year.getSelectedItem().toString());


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
    }

    public int getAge (int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if(a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;
    }

}