package com.naver.hackday.android.speechrecognizecalender.src.network.event.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reminders {

    @SerializedName("useDefault")
    @Expose
    private Boolean useDefault;

    public Boolean getUseDefault() {
        return useDefault;
    }

    public void setUseDefault(Boolean useDefault) {
        this.useDefault = useDefault;
    }

}