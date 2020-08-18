package com.resourcefulparenting.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.resourcefulparenting.R;
import com.resourcefulparenting.activity.AddChildName;
import com.resourcefulparenting.databinding.FragmentExplorerBinding;
import com.resourcefulparenting.models.AddChild.ChildDetails;
import com.resourcefulparenting.util.H;
import com.resourcefulparenting.util.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExplorerFragment extends Fragment {
    private FragmentExplorerBinding binding;
    private Context context;
    private Spinner child;
    int n=1;
    private String name,activity_id, login_token;
    private List<ChildDetails> childDetails1 = new ArrayList<>();
    ArrayList<String> childs = new ArrayList<>();
    String child_id="";
    public ExplorerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExplorerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        context = container.getContext();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView milestone =getActivity().findViewById(R.id.milestone_img);
        milestone.setVisibility(View.GONE);

        binding.addChild.setOnClickListener(view12 -> {
            Intent add_child = new Intent(context, AddChildName.class);
            startActivity(add_child);
        });

        child = binding.spinChildName;

        binding.environment.setOnClickListener(view1 -> {
            ((InsideExplorer)getActivity()).environment();
            Prefs.setCategory_id(context, "8");
            Prefs.setChildID(context, child_id);
            name = "environment";
        });

        binding.physical.setOnClickListener(view1 -> {
            ((InsideExplorer)getActivity()).physical();
            name = "physical";
            Prefs.setCategory_id(context, "3");
            Prefs.setChildID(context, child_id);
        });

        binding.logic.setOnClickListener(view1 -> {
            ((InsideExplorer)getActivity()).logic();
            name = "logic";
            Prefs.setCategory_id(context, "2");
            Prefs.setChildID(context, child_id);
        });

        binding.language.setOnClickListener(view1 -> {
            ((InsideExplorer)getActivity()).language();
            name = "language";
             Prefs.setCategory_id(context, "1");
             Prefs.setChildID(context, child_id);

        });

        binding.music.setOnClickListener(view1 -> {
            ((InsideExplorer)getActivity()).music();
            name = "music";
            Prefs.setCategory_id(context, "7");
            Prefs.setChildID(context, child_id);
        });

        binding.spatial.setOnClickListener(view1 -> {
            ((InsideExplorer)getActivity()).spatial();
            name = "spatial";
            Prefs.setCategory_id(context, "6");
            Prefs.setChildID(context, child_id);
        });

        binding.interpersonal.setOnClickListener(view1 -> {
            ((InsideExplorer)getActivity()).interpersonal();
            name = "interpersonal";
            Prefs.setCategory_id(context, "5");
            Prefs.setChildID(context, child_id);
        });

        binding.intrapersonal.setOnClickListener(view1 -> {
            ((InsideExplorer)getActivity()).intrapersonal();
            Prefs.setCategory_id(context, "4");
            Prefs.setChildID(context, child_id);
            name = "intrapersonal";
        });

        try {
            childDetails1.clear();
            JSONArray jsonArray=new JSONArray(Prefs.getChildDetails(context));
            Log.d("Arraym", String.valueOf(jsonArray.length()));
            for (int i=0;i<jsonArray.length();i++)
            {
                ChildDetails childDetails=new ChildDetails();
                JSONObject object = jsonArray.getJSONObject(i);
                String id=object.getString("child_id");
                String name=object.getString("child_name");
                childDetails.setId(id);
                childDetails.setChild_name(name);
                childs.add(name);
                childDetails1.add(childDetails);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text, childs);
        // attaching data adapter to spinner
        child.setAdapter(dataAdapter);
        if (childs.size() == 1)
        {
            child.setEnabled(false);
            for (int j = 0; j < childDetails1.size(); j++)
            {
                child_id = childDetails1.get(j).getId();
                H.L("call"+childDetails1.get(j).getId());
                Prefs.setChildID(context, child_id);
                break;
            }

        }
        else
        {
            n=1;
            child.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    String text = parent.getItemAtPosition(pos).toString();
                    JSONArray    jsonArray1 = new JSONArray();
                    for (int j = 0; j < childDetails1.size(); j++) {
                        JSONObject object = new JSONObject();
                        if (text.equalsIgnoreCase(childDetails1.get(j).getChild_name()))
                        {
                            child_id = childDetails1.get(j).getId();
                            H.L("idd"+childDetails1.get(j).getId());
                            Prefs.setChildID(context, child_id);

                            try {
                                object.put("child_id", childDetails1.get(j).getId());
                                object.put("child_name", childDetails1.get(j).getChild_name());
                                jsonArray1.put(0,object);
                                // Prefs.setChildDetails(context, jsonArray1.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // break;
                        }
                        else {
                            try {
                                object.put("child_id", childDetails1.get(j).getId());
                                object.put("child_name", childDetails1.get(j).getChild_name());
                                jsonArray1.put(n,object);
                                n++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    }
                    Prefs.setChildDetails(context, jsonArray1.toString());
                    n=1;

                }
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }


    }

    public interface InsideExplorer{
        void environment();
        void interpersonal();
        void intrapersonal();
        void language();
        void music();
        void physical();
        void logic();
        void spatial();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}