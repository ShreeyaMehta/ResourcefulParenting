package com.resourcefulparenting.application;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.resourcefulparenting.BuildConfig;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
