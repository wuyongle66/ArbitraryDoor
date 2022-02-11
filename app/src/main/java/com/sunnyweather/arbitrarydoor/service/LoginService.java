package com.sunnyweather.arbitrarydoor.service;

import com.sunnyweather.arbitrarydoor.User.User_login;
import com.sunnyweather.arbitrarydoor.User.User_register;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("user/login")
    Call<User_login> postObjectParamUser(@Body User_login user_login);
}
