package com.resourcefulparenting.models.Login;

import com.resourcefulparenting.models.Userdetails;

public class LoginResponse {
    public int status;
    public boolean error;
    public String message;
    public String login_token;
    public Userdetails userdetails;
}
