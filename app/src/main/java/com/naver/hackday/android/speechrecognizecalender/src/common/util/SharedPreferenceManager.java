package com.naver.hackday.android.speechrecognizecalender.src.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.time.LocalDateTime;

import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.PREFERENCES_NAME;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.DEFAULT_VALUE_STRING;

public class SharedPreferenceManager {

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getString(key, DEFAULT_VALUE_STRING);
    }
}
