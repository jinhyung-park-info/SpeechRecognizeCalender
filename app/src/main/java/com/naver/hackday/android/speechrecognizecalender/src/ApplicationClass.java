package com.naver.hackday.android.speechrecognizecalender.src;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.TAG;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.sSharedPreferences;

@SuppressLint("Registered")
public class ApplicationClass extends Application {

    private static volatile ApplicationClass instance = null;

    public static ApplicationClass getApplicationClassContext() {
        if (instance == null)
            throw new IllegalStateException("Error of ApplicationClass");
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (sSharedPreferences == null) {
            sSharedPreferences = getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }

}