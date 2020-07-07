package com.resourcefulparenting;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ActivityListingFragment extends Fragment {
    private Spinner child;
    private Context context;

    public ActivityListingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity_listing, container, false);
        child = view.findViewById(R.id.spin_child_name);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ConstraintLayout constraintLayout = view.findViewById(R.id.test);

        constraintLayout.setOnClickListener(view1 -> ((activities)getActivity()).ActivityPage());
        ImageView milestone =getActivity().findViewById(R.id.milestone_img);
        milestone.setVisibility(View.VISIBLE);

        List<String> childs = new ArrayList<>();
        childs.add("Child1");
        childs.add("Child2");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text, childs);
        // attaching data adapter to spinner
        child.setAdapter(dataAdapter);
    }

    public interface activities{
        void ActivityPage();
    }
}