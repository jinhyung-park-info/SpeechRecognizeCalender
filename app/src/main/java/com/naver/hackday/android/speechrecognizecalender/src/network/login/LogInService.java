package com.naver.hackday.android.speechrecognizecalender.src.network.login;

import com.naver.hackday.android.speechrecognizecalender.src.common.models.DefaultResponse;
import com.naver.hackday.android.speechrecognizecalender.src.network.login.interfaces.LogInRetrofitInterface;
import com.naver.hackday.android.speechrecognizecalender.src.network.login.models.SignInParams;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.getRetrofit;

public class LogInService {

    public interface LogInCallback {
        void testSuccess(DefaultResponse defaultResponse);

        void networkFail();
    }

    public void trySignIn(String id, String password, LogInCallback logInCallback) {
        final LogInRetrofitInterface logInRetrofitInterface = getRetrofit().create(LogInRetrofitInterface.class);
        logInRetrofitInterface.postSignIn(new SignInParams(id, password)).enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    logInCallback.networkFail();
                    return;
                }
                if (!defaultResponse.getIsSuccess()) {
                    logInCallback.networkFail();
                    return;
                }
                logInCallback.testSuccess(defaultResponse);
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                t.printStackTrace();
                logInCallback.networkFail();
            }
        });
    }

    public void tryTest(LogInCallback logInCallback) {
        final LogInRetrofitInterface logInRetrofitInterface = getRetrofit().create(LogInRetrofitInterface.class);
        logInRetrofitInterface.getTest().enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    logInCallback.networkFail();
                    return;
                }
                if (!defaultResponse.getIsSuccess()) {
                    logInCallback.testSuccess(defaultResponse);
                    return;
                }
                logInCallback.testSuccess(defaultResponse);
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                t.printStackTrace();
                logInCallback.networkFail();
            }
        });
    }
}
