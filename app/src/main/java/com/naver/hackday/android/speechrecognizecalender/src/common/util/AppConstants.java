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
    // 1. 서버 주소 관련
    public static final String TEST_BASE_URL = "http://52.78.11.153/";     // 테스트 서버 주소
    public static final String LOGIN_BASE_URL = "https://accounts.google.com/o/oauth2/";     // Google Login 서버 주소

    // 2. 구글 로그인 관련
    public static final String SCOPE_URL = "https://www.googleapis.com/auth/calendar";     // Google Login Scope
    public static final int RC_SIGN_IN = 0;                           // Google Login 키 값
    public static final String GRANT_TYPE = "authorization_code";     // Google Login API Grant Type

    // 3. SharedPreferences 관련
    public static final String PREFERENCES_NAME = "rebuild_preference";     // 이름
    public static final String DEFAULT_VALUE_STRING = "";
    public static final String ACCESS_TOKEN = "ACCESS-TOKEN";               // Access Token 키 값
    public static final String REFRESH_TOKEN = "REFRESH-TOKEN";             // Refresh Token 키 값
    public static final String EXPIRE_TIME = "EXPIRE-TIME";                 // Expire Time 키 값
    public static final String EMAIL = "EMAIL";                             // email 키 값

    // TODO SharedPreferenceManager 만들었는데 이제 이거 필요한가요?
    public static final String TAG = "APP";                                 // SharedPreferences 키 값
    public static SharedPreferences sSharedPreferences = null;

    // 4. API Client 관련
    public static final String GOOGLE_CLIENT_ID = "673874763624-6nmt7t4g0t8r9bcgbv609qdms4tuoik8.apps.googleusercontent.com";
    public static final String GOOGLE_CLIENT_SECRET = "-Q64vIK_e8Z0WIbuuROliBAw";
    public static final String CLOVA_CLIENT_ID = "tcc3ojy059";

    // 5. JWT Token 값
    public static final String X_ACCESS_TOKEN = "X-ACCESS-TOKEN";

    // 6. 날짜 형식
    public static final SimpleDateFormat DATE_FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.KOREA);
    public static final SimpleDateFormat DATE_FORMAT_SECOND = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    public static final SimpleDateFormat DATE_FORMAT_KR = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA);
    public static final SimpleDateFormat DATE_FORMAT_EN = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

    // 7. Retrofit 인스턴스
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
                    .baseUrl(TEST_BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    // 8. 일정 형식
    public static final int ONE_DAY = 1; //날짜만(yyyy-MM-dd 00:00:00)
    public static final int DAY_TO_DAY = 2;
    public static final int UNKNOWN = 3;

}
