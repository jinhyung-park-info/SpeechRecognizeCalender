package com.naver.hackday.android.speechrecognizecalender.src.network.login.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.naver.hackday.android.speechrecognizecalender.src.ApplicationClass;
import com.naver.hackday.android.speechrecognizecalender.src.common.util.SharedPreferenceManager;
import com.naver.hackday.android.speechrecognizecalender.src.common.util.GoogleApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.ACCESS_TOKEN;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.REFRESH_TOKEN;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.EXPIRE_TIME;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.LOGIN_BASE_URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.O)
public class GoogleAuthSystem {

    DateTimeFormatter mDateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");

    public void requestAccessToken(String authCode, String clientID, String clientSecret, TokenListener tokenListener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LOGIN_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final GoogleApi googleApi = retrofit.create(GoogleApi.class);

        // Make the actual post request to google server
        if (!accessTokenExpired()) {
            getAccessToken(googleApi, authCode, clientID, clientSecret, tokenListener);
        } else {
            String refreshToken = SharedPreferenceManager.getString(ApplicationClass.getApplicationClassContext(), REFRESH_TOKEN);
            getRefreshToken(googleApi, clientID, clientSecret, tokenListener, refreshToken);
        }
    }

    private boolean accessTokenExpired() {
        String accessTokenCandidate = SharedPreferenceManager.getString(ApplicationClass.getApplicationClassContext(), ACCESS_TOKEN);
        // if the token is set to default string value "", the access token has
        // never issued, so just request for an initial access token
        if (accessTokenCandidate.equals("")) {
            return false;
        } else {
            String expireTimeInString = SharedPreferenceManager.getString(ApplicationClass.getApplicationClassContext(), EXPIRE_TIME);
            LocalDateTime expireTime = LocalDateTime.parse(expireTimeInString, mDateFormatter);
            if (LocalDateTime.now().isBefore(expireTime)) {
                return false;
            } else {
                return true;
            }
        }
    }

    private void getAccessToken(GoogleApi googleApi, String authCode, String clientID, String clientSecret, TokenListener tokenListener) {
        AccessTokenRequestBody accessTokenRequestBody = new AccessTokenRequestBody(authCode, clientID, clientSecret);
        Call<AccessTokenResponse> call = googleApi.getAccessToken(accessTokenRequestBody);
        call.enqueue(new Callback<AccessTokenResponse>() {

            @Override
            public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                if (!response.isSuccessful()) {
                    tokenListener.onFailureGetAccessToken();
                    return;
                }

                AccessTokenResponse accessTokenResponse = response.body();
                if (accessTokenResponse == null) {
                    tokenListener.onFailureGetAccessToken();
                    return;
                }

                // On Success, store the access token, refresh token(that is needed to
                // request for refresh token in the future), and expire time
                String accessToken = accessTokenResponse.getAccessToken();
                storeString(ACCESS_TOKEN, accessToken);

                String refreshToken = accessTokenResponse.getRefreshToken();
                storeString(REFRESH_TOKEN, refreshToken);

                // Calculate when the access token will expire and store it
                Long expiresInSeconds = accessTokenResponse.getExpiresIn();
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime expireTime = now.plusSeconds(expiresInSeconds);
                storeString(EXPIRE_TIME, expireTime.format(mDateFormatter));
                tokenListener.onSuccessGetAccessToken();
            }

            @Override
            public void onFailure(Call<AccessTokenResponse> call, Throwable t) {
                t.printStackTrace();
                tokenListener.onFailureGetAccessToken();
            }
        });
    }

    private void getRefreshToken(GoogleApi googleApi, String clientID, String clientSecret, TokenListener tokenListener, String refreshToken) {
        RefreshTokenRequestBody refreshTokenRequestBody = new RefreshTokenRequestBody(clientID, clientSecret, refreshToken);
        Call<RefreshTokenResponse> call = googleApi.getRefreshToken(refreshTokenRequestBody);
        call.enqueue(new Callback<RefreshTokenResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (!response.isSuccessful()) {
                    tokenListener.onFailureGetAccessToken();
                    return;
                }

                RefreshTokenResponse refreshTokenResponse = response.body();
                if (refreshTokenResponse == null) {
                    tokenListener.onFailureGetAccessToken();
                    return;
                }

                // On Success, store the (refreshed) access token
                // Since this is the new access token, store it as ACCESS_TOKEN,
                // not REFRESH_TOKEN
                String accessToken = refreshTokenResponse.getAccessToken();
                storeString(ACCESS_TOKEN, accessToken);

                // Calculate when the access token will expire and store it
                Long expiresInSeconds = refreshTokenResponse.getExpiresIn();
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime expireTime = now.plusSeconds(expiresInSeconds);
                storeString(EXPIRE_TIME, expireTime.format(mDateFormatter));
                tokenListener.onSuccessGetAccessToken();
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                t.printStackTrace();
                tokenListener.onFailureGetAccessToken();
            }
        });
    }

    private void storeString(String key, String value) {
        SharedPreferenceManager.setString(ApplicationClass.getApplicationClassContext(), key, value);
    }
}
