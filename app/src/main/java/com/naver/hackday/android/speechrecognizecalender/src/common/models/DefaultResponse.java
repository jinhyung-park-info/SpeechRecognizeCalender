package com.naver.hackday.android.speechrecognizecalender.src.common.models;
import com.google.gson.annotations.SerializedName;

public class DefaultResponse  {

    @SerializedName("code")
    protected int code;

    @SerializedName("message")
    protected String message;

    @SerializedName("isSuccess")
    protected boolean isSuccess;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public DefaultResponse() {
    }
}