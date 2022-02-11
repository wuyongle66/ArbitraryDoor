package com.sunnyweather.arbitrarydoor.service;

import com.sunnyweather.arbitrarydoor.User.User_update;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UpdateService {
    @POST("user/updateMessage")
    Call<User_update> update(@Header("Authorization") String authorization,@Body User_update user_update);

}
