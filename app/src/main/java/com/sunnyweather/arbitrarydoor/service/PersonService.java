package com.sunnyweather.arbitrarydoor.service;

import com.sunnyweather.arbitrarydoor.User.User_person;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface PersonService {
    @GET("user/info")
    Call<User_person> getUser(@Header("Authorization") String authorization);
}
