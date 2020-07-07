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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.resourcefulparenting.models.Login.LoginCheck;
import com.resourcefulparenting.models.Login.LoginResponse;
import com.resourcefulparenting.network.Api;
import com.resourcefulparenting.network.ApiClient;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    private Context context;
    private Button register, login;
    private TextView forgot_password;
    private TextInputLayout edt_email, edt_password;
    private String email, password, login_token, user_id;
    private SharedPreferences settings;
    private boolean isloggedin;
    final LoginCheck loginCheck = new LoginCheck();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        context = this;
        ids();

        settings=getSharedPreferences("prefs",0);
        isloggedin=settings.getBoolean("firstRun",false);
        login_token = settings.getString("access", login_token);
        user_id = settings.getString("id", user_id);
        //if running for first time
        if(isloggedin){
            Intent a = new Intent(context, MainActivity.class);
            a.putExtra("access_token", login_token);
            a.putExtra("id", user_id);
            startActivity(a);
        }
        register.setOnClickListener(view -> {
            Intent next = new Intent(context, RegisterActivity.class);
            startActivity(next);
        });

        forgot_password.setOnClickListener(view -> {
            Intent next = new Intent(context, RetrivePassword.class);
            startActivity(next);
        });

        login.setOnClickListener(view -> {
            email = edt_email.getEditText().getText().toString();
            password = edt_password.getEditText().getText().toString();

            loginCheck.email = email;
            loginCheck.password = password;
            loginCheck.device_type = "Android";
            loginCheck.device_unique_id = "bcod";
            loginCheck.login_type = "1";

            if(email.isEmpty()){
                edt_email.setError("E-mail Required");
            } else if (!email.matches(loginCheck.email)) {
                edt_email.setError("Invalid E-mail");
            } else if (password.isEmpty()) {
                edt_password.setError("Password Required");
            } else {
                getLogin();
            }
        });
    }

    private void getLogin() {
        Call<LoginResponse> call = ApiClient.getRetrofit().create(Api.class).Login(loginCheck);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("Response", String.valueOf(response.body()));
                if(!isloggedin){
                    SharedPreferences.Editor editor=settings.edit();
                    editor.putBoolean("firstRun",true);
                    editor.putString("access",response.body().login_token);
                    editor.putString("id", String.valueOf(response.body().userdetails.user_id));
                    editor.apply();
                    if(email.equals(loginCheck.email) && password.equals(loginCheck.password)){

                        Log.d("Success", String.valueOf(response.body().userdetails.user_id));

                        Intent home_page = new Intent(context, MainActivity.class);
                        home_page.putExtra("access_token", response.body().login_token);
                        startActivity(home_page);
                    }else{
                        assert response.body() != null;
                        Log.d("Fail", response.body().message);
                        Toast.makeText(context, "Invaid Username or Password", Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Intent a = new Intent(context, MainActivity.class);
                    a.putExtra("access_token", response.body().login_token);
                    a.putExtra("id", response.body().userdetails.user_id);
                    startActivity(a);
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ids() {
        register = findViewById(R.id.btn_register1);
        forgot_password = findViewById(R.id.tv_forgot_password);
        edt_email = findViewById(R.id.signin_edt_email);
        edt_password = findViewById(R.id.signin_edt_password);
        login = findViewById(R.id.btn_login);
    }
}
