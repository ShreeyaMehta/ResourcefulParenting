package com.resourcefulparenting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.resourcefulparenting.models.CommonResponse;
import com.resourcefulparenting.models.SetParentNameCheck;
import com.resourcefulparenting.network.Api;
import com.resourcefulparenting.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SetParentsNameFragment extends Fragment {
    private TextInputLayout edt_parent_name;
    private String login_token, parents_Name;
    private Button submit;
    final SetParentNameCheck setParentNameCheck = new SetParentNameCheck();

    public SetParentsNameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_parents_name, container, false);

        login_token = getArguments().getString("access_token");

        edt_parent_name = view.findViewById(R.id.edt_set_parent_name);
        submit = view.findViewById(R.id.btn_set_parent_name_submit);

        parents_Name = edt_parent_name.getEditText().getText().toString();

        setParentNameCheck.login_token = login_token;
        setParentNameCheck.parent_name = parents_Name;

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        submit.setOnClickListener(view1 -> {

            if(parents_Name.isEmpty()){
                edt_parent_name.setError("Required");
            }else{
                getParentName();
            }

        });
    }

    private void getParentName() {
        Call<CommonResponse> call = ApiClient.getRetrofit().create(Api.class).ChangeParentName(setParentNameCheck);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(login_token.equals(setParentNameCheck.login_token) && parents_Name.equals(setParentNameCheck.parent_name)){
                    Toast.makeText(getActivity(), response.body().message, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), response.body().message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}