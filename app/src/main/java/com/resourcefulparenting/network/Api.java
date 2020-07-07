package com.resourcefulparenting.network;

import com.resourcefulparenting.models.ForgotPasswordCheck;
import com.resourcefulparenting.models.CommonResponse;
import com.resourcefulparenting.models.Login.LoginCheck;
import com.resourcefulparenting.models.Login.LoginResponse;
import com.resourcefulparenting.models.LogoutCheck;
import com.resourcefulparenting.models.Register.RegisterCheck;
import com.resourcefulparenting.models.Register.RegisterResponse;
import com.resourcefulparenting.models.SetParentNameCheck;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {

    @POST("register")
    Call<RegisterResponse> RegisterParent(@Body RegisterCheck registerCheck);

    @POST("login")
    Call<LoginResponse> Login(@Body LoginCheck loginCheck);

    @POST("forgetpassword")
    Call<CommonResponse> ForgotPassword(@Body ForgotPasswordCheck forgotPasswordCheck);

    @POST("logout")
    Call<CommonResponse> Logout(@Body LogoutCheck logoutCheck);

    @POST("changeparentname")
    Call<CommonResponse> ChangeParentName(@Body SetParentNameCheck setParentNameCheck);

}