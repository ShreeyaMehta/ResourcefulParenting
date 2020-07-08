package com.resourcefulparenting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.resourcefulparenting.models.ForgotPasswordCheck;
import com.resourcefulparenting.models.CommonResponse;
import com.resourcefulparenting.network.Api;
import com.resourcefulparenting.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrivePassword extends AppCompatActivity {
    private TextInputLayout edt_email_id;
    private Button retrive;
    private Context context;
    private String emailid;
    final ForgotPasswordCheck forgotPasswordCheck = new ForgotPasswordCheck();
    RelativeLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_password);
        context = this;
        ids();

        retrive.setOnClickListener(view -> {
            emailid = edt_email_id.getEditText().getText().toString();
            forgotPasswordCheck.email = emailid;
            if(emailid.isEmpty()){
                edt_email_id.setError("Email Recovered");
            }else{
                loading.setVisibility(View.VISIBLE);
                getForgotPassword();
            }
        });


    }

    private void getForgotPassword() {
        Call<CommonResponse> call = ApiClient.getRetrofit().create(Api.class).ForgotPassword(forgotPasswordCheck);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(emailid.equals(forgotPasswordCheck.email)){
                    Toast.makeText(context, response.body().message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, SignInActivity.class));
                    loading.setVisibility(View.GONE);
                }else {
                    Toast.makeText(context, response.body().message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context, SignInActivity.class));
        super.onBackPressed();
    }

    private void ids() {
        edt_email_id = findViewById(R.id.edt_recover_email_id);
        retrive = findViewById(R.id.btn_retrive_password);
        loading = findViewById(R.id.loading);
    }
}