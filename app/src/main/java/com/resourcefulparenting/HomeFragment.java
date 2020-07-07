package com.resourcefulparenting;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    Context context;
    ArrayList<PieEntry> pieEntries;
    private Spinner child;
    private ConstraintLayout target_Activity, show_activity;
    private boolean isvisible = false;

  /*  private String[] images = {String.valueOf(R.drawable.language), String.valueOf(R.drawable.environment),
            String.valueOf(R.drawable.logic), String.valueOf(R.drawable.spatial), String.valueOf(R.drawable.physical),
            String.valueOf(R.drawable.interpersonal), String.valueOf(R.drawable.intrapersonal), String.valueOf(R.drawable.music)};
    private String[] percent = {"","","","","","","",""};
    private int[] values = {30,2,3,40,5,6,70,8};*/

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        pieChart = view.findViewById(R.id.pie_Chart);
        child = view.findViewById(R.id.spin_child_name);
        target_Activity = view.findViewById(R.id.show_hide_activity_details);
        show_activity = view.findViewById(R.id.home_activity);
        context = container.getContext();
        String access = getArguments().getString("access_token");
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView milestone =getActivity().findViewById(R.id.milestone_img);
        milestone.setVisibility(View.VISIBLE);

        getEntries();

        List<String> childs = new ArrayList<>();
        childs.add("Child1");
        childs.add("Child2");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text, childs);
        // attaching data adapter to spinner
        child.setAdapter(dataAdapter);

        final int[] MY_COLORS = {getResources().getColor(R.color.language), getResources().getColor(R.color.logic),
                getResources().getColor(R.color.physical), getResources().getColor(R.color.intrapersonal),
                getResources().getColor(R.color.interpersonal),getResources().getColor(R.color.spatial),
                getResources().getColor(R.color.music),getResources().getColor(R.color.environment)};
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int c: MY_COLORS) colors.add(c);

        pieDataSet = new PieDataSet(pieEntries, "");
        pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setData(pieData);

        pieChart.setHoleRadius(70.0f);
        pieChart.setTransparentCircleAlpha(0);

        pieDataSet.setColors(colors);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(10f);
        pieDataSet.setSelectionShift(0f);
        pieChart.getData().setDrawValues(false);

        pieChart.setRotationEnabled(false);
        pieDataSet.setIconsOffset(new MPPointF(0, 50));
        pieChart.getDescription().setEnabled(false);

        pieChart.setExtraOffsets(40, 0, 40, 0);

        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);

        target_Activity.setOnClickListener(view1 -> {
            if(!isvisible){
                show_activity.setVisibility(View.VISIBLE);
                isvisible = true;
            }else {
                show_activity.setVisibility(View.GONE);
                isvisible = false;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getEntries() {
    pieEntries = new ArrayList<>();

       /* for (int i = 0; i < count ; i++) {
            pieEntries.add(new PieEntry((float)((Math.random() * range) + range / 5),
                    percent[i % percent.length], getResources().getDrawable(R.drawable.lang_icon)));
        }*/

        pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(10,"", getResources().getDrawable(R.drawable.language_icon)));
        pieEntries.add(new PieEntry(1,"", getResources().getDrawable(R.drawable.logic_icon)));
        pieEntries.add(new PieEntry(20,"", getResources().getDrawable(R.drawable.physical_icon)));
        pieEntries.add(new PieEntry(1,"", getResources().getDrawable(R.drawable.intrapersonal_icon)));
        pieEntries.add(new PieEntry(10,"", getResources().getDrawable(R.drawable.interpersonal_icon)));
        pieEntries.add(new PieEntry(1,"", getResources().getDrawable(R.drawable.spatial_icon)));
        pieEntries.add(new PieEntry(20,"", getResources().getDrawable(R.drawable.music_icon)));
        pieEntries.add(new PieEntry(5,"", getResources().getDrawable(R.drawable.environment_icon)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pieChart = null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }
}