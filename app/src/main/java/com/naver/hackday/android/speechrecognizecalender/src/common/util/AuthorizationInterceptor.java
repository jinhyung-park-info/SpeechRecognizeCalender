package com.naver.hackday.android.speechrecognizecalender.src.common.util;

import com.naver.hackday.android.speechrecognizecalender.src.ApplicationClass;
import com.naver.hackday.android.speechrecognizecalender.src.ui.login.MainActivity;
import com.naver.hackday.android.speechrecognizecalender.src.ui.login.viewModels.LogInViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.Request;

import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.ACCESS_TOKEN;


public class AuthorizationInterceptor implements Interceptor {

    private static final String AUTH_PREFIX = "Bearer ";

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        final String accessToken = AUTH_PREFIX + SharedPreferenceManager.getString(ApplicationClass.getApplicationClassContext(), ACCESS_TOKEN);
        if (accessToken != null) {
            builder.addHeader("Authorization", accessToken);
        }
        return chain.proceed(builder.build());
    }
}
