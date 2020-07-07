package com.resourcefulparenting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.resourcefulparenting.models.Register.RegisterCheck;
import com.resourcefulparenting.models.Register.RegisterResponse;
import com.resourcefulparenting.network.Api;
import com.resourcefulparenting.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private Context context;
    private Button register;
    private TextInputLayout edt_parent_name, edt_mobile_no, edt_email_id, edt_password;
    private String parent_name,mobile_no,email_id,password, login_token, user_id;
    private SharedPreferences settings;
    private boolean isloggedin;

    final RegisterCheck registerCheck = new RegisterCheck();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        ids();

        settings=getSharedPreferences("prefs",0);
        isloggedin=settings.getBoolean("firstRun",false);
        login_token = settings.getString("access", login_token);
        //if running for first time
        if(isloggedin){
            Intent a = new Intent(context, MainActivity.class);
            a.putExtra("access_token", login_token);
            startActivity(a);
        }

        register.setOnClickListener(view -> {
            parent_name = edt_parent_name.getEditText().getText().toString();
            mobile_no = edt_mobile_no.getEditText().getText().toString();
            email_id = edt_email_id.getEditText().getText().toString();
            password = edt_password.getEditText().getText().toString();

            registerCheck.parentName = parent_name;
            registerCheck.mobileNo = mobile_no;
            registerCheck.email = email_id;
            registerCheck.password = password;
            registerCheck.deviceType = "Android";
            registerCheck.deviceUniqueId = "bcod";
            registerCheck.loginType = "1";

            if(parent_name.isEmpty()){
                edt_parent_name.setError("Parent Name Required");
            }else if(email_id.isEmpty()){
                edt_email_id.setError("E-mail Required");
            }else if (password.isEmpty()) {
                edt_password.setError("Password Required");
            } else {
                getRegister();
            }

        });
    }

    private void getRegister() {
        Call<RegisterResponse> call = ApiClient.getRetrofit().create(Api.class).RegisterParent(registerCheck);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.d("response", String.valueOf(response.body()));

                SharedPreferences.Editor editor=settings.edit();
                editor.putBoolean("firstRun",true);
                editor.putString("access",response.body().login_token);
                editor.apply();
                Intent home_page = new Intent(context, ChildNameActivity.class);
                home_page.putExtra("access_token", response.body().login_token);
                startActivity(home_page);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(context, SignInActivity.class);
        startActivity(back);
    }

    private void ids() {
        register = findViewById(R.id.btn_register);
        edt_parent_name = findViewById(R.id.edt_parent_name);
        edt_mobile_no = findViewById(R.id.edt_parent_no);
        edt_email_id = findViewById(R.id.edt_register_email);
        edt_password = findViewById(R.id.edt_register_password);
    }

}
