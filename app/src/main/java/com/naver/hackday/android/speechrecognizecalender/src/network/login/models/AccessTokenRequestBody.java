package com.naver.hackday.android.speechrecognizecalender.src.network.login.models;

import com.google.gson.annotations.SerializedName;
import com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants;

public class AccessTokenRequestBody {

    @SerializedName("code")
    private String serverAuthCode;

    @SerializedName("client_id")
    private String clientID;

    @SerializedName("client_secret")
    private String clientSecret;

    @SerializedName("grant_type")
    private String grantType;

    @SerializedName("redirect_uri")
    private String redirectUri;

    public AccessTokenRequestBody(String serverAuthCode, String clientID, String clientSecret) {
        this.serverAuthCode = serverAuthCode;
        this.clientID = clientID;
        this.clientSecret = clientSecret;
        this.grantType = AppConstants.GRANT_TYPE;
        this.redirectUri = "";
    }
}
