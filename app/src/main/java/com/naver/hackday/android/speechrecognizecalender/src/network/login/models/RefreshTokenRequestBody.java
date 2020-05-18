package com.naver.hackday.android.speechrecognizecalender.src.network.login.models;

import com.google.gson.annotations.SerializedName;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.GRANT_TYPE;

public class RefreshTokenRequestBody {

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("client_id")
    private String clientID;

    @SerializedName("client_secret")
    private String clientSecret;

    @SerializedName("grant_type")
    private String grantType;

    public RefreshTokenRequestBody(String clientID, String clientSecret, String refreshToken) {
        this.grantType = GRANT_TYPE;
        this.clientID = clientID;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
    }
}
