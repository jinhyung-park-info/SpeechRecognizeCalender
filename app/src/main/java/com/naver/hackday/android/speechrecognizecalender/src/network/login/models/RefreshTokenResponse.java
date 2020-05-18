package com.naver.hackday.android.speechrecognizecalender.src.network.login.models;

import com.google.gson.annotations.SerializedName;

public class RefreshTokenResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private Long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

}
