package com.resourcefulparenting.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static String Base_url = "https://resourcefulparenting.parkmapped.com/api/";

    private static Retrofit retrofit;

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Base_url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
