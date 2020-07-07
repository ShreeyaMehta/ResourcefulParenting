package com.resourcefulparenting;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ExplorerFragment extends Fragment {
    private Context context;
    private Spinner child;

    public ExplorerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explorer, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView milestone =getActivity().findViewById(R.id.milestone_img);
        milestone.setVisibility(View.VISIBLE);

        child = view.findViewById(R.id.spin_child_name);


        List<String> childs = new ArrayList<>();
        childs.add("Child1");
        childs.add("Child2");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text, childs);
        // attaching data adapter to spinner
        child.setAdapter(dataAdapter);
    }
}