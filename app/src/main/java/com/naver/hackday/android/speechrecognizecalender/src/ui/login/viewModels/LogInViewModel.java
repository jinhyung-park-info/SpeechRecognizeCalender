package com.naver.hackday.android.speechrecognizecalender.src.ui.login.viewModels;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.naver.hackday.android.speechrecognizecalender.src.ApplicationClass;
import com.naver.hackday.android.speechrecognizecalender.src.common.util.SharedPreferenceManager;
import com.naver.hackday.android.speechrecognizecalender.src.network.login.models.GoogleAuthSystem;
import com.naver.hackday.android.speechrecognizecalender.src.network.login.models.TokenListener;
import com.naver.hackday.android.speechrecognizecalender.src.ui.login.AuthListener;

import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.ACCESS_TOKEN;

public class LogInViewModel extends ViewModel {

    public AuthListener authListener = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void requestAccessToken(String authCode, String clientID, String clientSecret) {
        try {
            if (authCode == null) {
                authListener.onFailureGetAccessToken();
                return;
            }

            GoogleAuthSystem googleAuthSystem = new GoogleAuthSystem();
            googleAuthSystem.requestAccessToken(authCode, clientID, clientSecret, new TokenListener() {
                @Override
                public void onSuccessGetAccessToken() {
                    authListener.onSuccessGetAccessToken();
                }

                @Override
                public void onFailureGetAccessToken() {
                    authListener.onFailureGetAccessToken();
                }
            });
        } catch (Exception e) {
            Log.d("에러", e.toString());
            e.printStackTrace();
            authListener.onFailureGetAccessToken();
        }
    }
}

