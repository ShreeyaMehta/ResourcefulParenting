package com.resourcefulparenting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ActivityPageFragment extends Fragment {


    public ActivityPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ConstraintLayout constraintLayout = view.findViewById(R.id.test1);
        constraintLayout.setOnClickListener(view1 -> {
            ((activityPage)getActivity()).ActivityDetails();
        });
    }

    public interface activityPage{
        void ActivityDetails();
    }
}