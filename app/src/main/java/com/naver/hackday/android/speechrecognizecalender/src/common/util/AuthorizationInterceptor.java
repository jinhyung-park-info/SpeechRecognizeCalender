package com.naver.hackday.android.speechrecognizecalender.src.common.util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.Request;

public class AuthorizationInterceptor implements Interceptor {

    private static final String AUTH_PREFIX = "Bearer ";
    private static final String ACCESS_TOKEN = "ya29.a0AfH6SMCfNP6w6P3s7IFjETy-fxrhefGfuZqAJOGqKF-gij4GqYl33iCU2GGJTD-ouoBCQwMDllJsaHRmGPirY4mqSTxkYvjIp9eNO_1kZo30cXKn6zr1AJCdv45XDJHWVYpwIjkHpq5h8QpLCV61jeXKcHllcOqlkIo";

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        final String accessToken = AUTH_PREFIX + ACCESS_TOKEN;
        if (accessToken != null) {
            builder.addHeader("Authorization", accessToken);
        }
        return chain.proceed(builder.build());
    }
}
