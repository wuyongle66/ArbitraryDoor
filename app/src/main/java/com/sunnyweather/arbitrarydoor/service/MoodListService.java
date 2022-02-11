package com.sunnyweather.arbitrarydoor.service;

import com.sunnyweather.arbitrarydoor.User.User_mood;
import com.sunnyweather.arbitrarydoor.User.User_mood_list;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MoodListService {
    @GET("mood-diary/get")
    Call<User_mood_list> postObjectParamUser(@Header("Authorization") String authorization);
}
