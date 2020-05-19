package com.naver.hackday.android.speechrecognizecalender.src.common.util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AuthorizationInterceptor implements Interceptor {

    private static final String AUTH_PREFIX = "Bearer ";
    private String accessToken;

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        final String modifiedAccessToken = AUTH_PREFIX + accessToken;
        if (!accessToken.isEmpty()) {
            builder.addHeader("Authorization", modifiedAccessToken);
        }
        return chain.proceed(builder.build());
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
