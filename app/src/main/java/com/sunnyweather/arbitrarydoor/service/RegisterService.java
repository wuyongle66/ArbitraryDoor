package com.sunnyweather.arbitrarydoor.service;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import com.sunnyweather.arbitrarydoor.User.User_register;

public interface RegisterService {
    @POST("user/register")
    Call<User_register> postObjectParamUser(@Body User_register user_register);
}
