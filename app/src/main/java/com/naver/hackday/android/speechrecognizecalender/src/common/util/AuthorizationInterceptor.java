package com.naver.hackday.android.speechrecognizecalender.src.common.util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.Request;

public class AuthorizationInterceptor implements Interceptor {

    private static final String AUTH_PREFIX = "Bearer ";
    private static final String ACCESS_TOKEN = "ya29.a0AfH6SMC0tWsBX37mXi_OmAhjqKHUH5trnhYD1p5oENGoHvsCioHb9pHe82Hz4YASjJftco6eogJWbnMFUaEe3Wh2jbja3CR1sHflOAdwDGgzEpK-KScetlyz_MnlF17FkkB9aRKd5W_EntufN5iYjaknHm0Ywjxxg_w";

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
