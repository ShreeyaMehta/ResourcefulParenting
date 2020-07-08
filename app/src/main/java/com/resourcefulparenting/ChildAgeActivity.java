package com.resourcefulparenting;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class ChildAgeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Context context;
    private Button next;
    private Spinner date, month, year;
    private int date_;
    private int month_;
    private int year_;
    private int age;
    ArrayList<Integer> years;

    @RequiresApi(api = Build.VERSION_CODES.N)
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
            year_ = years.get(year.getSelectedItemPosition());
            month_ = Integer.parseInt(month.getItemAtPosition(month.getSelectedItemPosition()).toString());
            date_ = Integer.parseInt(date.getItemAtPosition(date.getSelectedItemPosition()).toString());
            int ages = Integer.parseInt(getAge(year_, month_, date_));
            if(ages > 3) {
                Intent next = new Intent(context, QuestionaireActivity.class);
                next.putExtra("access_token", access);
                startActivity(next);
            }else {
                Intent next = new Intent(context, MainActivity.class);
                next.putExtra("access_token", access);
                startActivity(next);
            }
        });

        years = new ArrayList<Integer>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2010; i <= thisYear; i++) {
            years.add(i);
        }
        Comparator comparator = Collections.reverseOrder();
        Collections.sort(years, comparator);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(context, android.R.layout.simple_spinner_item, years);
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

     /* year_ = Integer.parseInt(year.getSelectedItem().toString());
        month_ = Integer.parseInt(month.getSelectedItem().toString());
        date_ = Integer.parseInt(date.getSelectedItem().toString());
*/
       // year_ = (int) year.getSelectedItem();

     //   month_ = (int) month.getSelectedItem();
    //    date_ = (int) date.getSelectedItem();
        Log.d("Year", String.valueOf(year_));
        Log.d("Month", String.valueOf(month_));
        Log.d("Year", String.valueOf(date_));



    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
    }

    public String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
        return ageS;
    }
}