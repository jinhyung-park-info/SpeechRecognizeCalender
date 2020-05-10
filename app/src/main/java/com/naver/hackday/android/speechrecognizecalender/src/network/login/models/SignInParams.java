package com.naver.hackday.android.speechrecognizecalender.src.network.login.models;

import com.google.gson.annotations.SerializedName;

public class SignInParams {
    @SerializedName("id")
    private String id;
    @SerializedName("password")
    private String pw;

    public SignInParams(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

}