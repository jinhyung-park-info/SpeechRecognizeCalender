package com.naver.hackday.android.speechrecognizecalender.src.common.util;

import android.content.SharedPreferences;
import android.util.DisplayMetrics;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class AppConstants {
    // 테스트 서버 주소
    public static final String BASE_URL = "http://52.78.11.153/";

    public static SharedPreferences sSharedPreferences = null;

    // SharedPreferences 키 값
    public static final String TAG = "APP";

    // JWT Token 값
    public static final String X_ACCESS_TOKEN = "X-ACCESS-TOKEN";

    //날짜 형식
    public static final SimpleDateFormat DATE_FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    public static final SimpleDateFormat DATE_FORMAT_SECOND = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    public static final SimpleDateFormat DATE_FORMAT_KR = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA);
    public static final SimpleDateFormat DATE_FORMAT_EN = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

    // Retrofit 인스턴스
    public static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .addNetworkInterceptor(new XAccessTokenInterceptor()) // JWT 자동 헤더 전송
                    .addNetworkInterceptor(httpLoggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}