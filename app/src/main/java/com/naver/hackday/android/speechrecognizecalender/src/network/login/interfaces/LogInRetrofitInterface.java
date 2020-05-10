package com.naver.hackday.android.speechrecognizecalender.src.network.login.interfaces;
import com.naver.hackday.android.speechrecognizecalender.src.common.models.DefaultResponse;
import com.naver.hackday.android.speechrecognizecalender.src.network.login.models.SignInParams;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LogInRetrofitInterface {
    @POST("/user")
    Call<DefaultResponse> postSignIn(@Body SignInParams signInParams);

    @GET("/test")
    Call<DefaultResponse> getTest();
}
