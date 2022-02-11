package com.sunnyweather.arbitrarydoor.service;

import com.sunnyweather.arbitrarydoor.User.User_login;
import com.sunnyweather.arbitrarydoor.User.User_mood;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MoodDiaryService {
    @POST("mood-diary/edit")
    Call<User_mood> postObjectParamUser(@Body User_mood user_mood,@Header("Authorization") String authorization);
}
