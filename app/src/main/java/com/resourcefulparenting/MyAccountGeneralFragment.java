package com.resourcefulparenting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.resourcefulparenting.models.CommonResponse;
import com.resourcefulparenting.models.LogoutCheck;
import com.resourcefulparenting.network.Api;
import com.resourcefulparenting.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyAccountGeneralFragment extends Fragment {
    final LogoutCheck logoutCheck = new LogoutCheck();
    private Context context;
    private String login_token, user_id;

    public MyAccountGeneralFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account_general, container, false);
        context = container.getContext();

        assert getArguments() != null;
        login_token = getArguments().getString("access_token");

        logoutCheck.login_token = login_token;

        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView milestone =getActivity().findViewById(R.id.milestone_img);
        milestone.setVisibility(View.GONE);

        TextView setParentsName =view.findViewById(R.id.account_set_parent_name_text);
      //  TextView change_password =view.findViewById(R.id.account_change_password_text);
      //  TextView child1 = view.findViewById(R.id.account_1_text);
        Button logout = view.findViewById(R.id.btn_logout);
        setParentsName.setOnClickListener(view1 -> ((InsideAccount)getActivity()).setName());
      //  change_password.setOnClickListener(view12 -> ((InsideAccount)getActivity()).changePassword());
     //   child1.setOnClickListener(view1 -> ((InsideAccount)getActivity()).changeProfile());
        logout.setOnClickListener(view12 -> {
            getLogout();
        });
    }

    private void getLogout() {
        Call<CommonResponse> call = ApiClient.getRetrofit().create(Api.class).Logout(logoutCheck);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                if(login_token.equals(logoutCheck.login_token)) {
                    SharedPreferences settings = getActivity().getSharedPreferences("prefs", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(context, SignInActivity.class));
                }
            }
            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface InsideAccount{
        void setName();
        void changePassword();
        void changeProfile();
    }
}