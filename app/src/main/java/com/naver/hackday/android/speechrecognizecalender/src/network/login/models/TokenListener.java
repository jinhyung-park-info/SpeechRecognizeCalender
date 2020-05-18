package com.naver.hackday.android.speechrecognizecalender.src.network.login.models;

public interface TokenListener {
    void onSuccessGetAccessToken();
    void onFailureGetAccessToken();
}
