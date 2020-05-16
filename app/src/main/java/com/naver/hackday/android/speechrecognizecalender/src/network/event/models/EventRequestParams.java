package com.naver.hackday.android.speechrecognizecalender.src.network.event.models;

import com.google.gson.annotations.SerializedName;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.Event;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.EventDate;

public class EventRequestParams {
    @SerializedName("status")
    private String status;

    @SerializedName("summary")
    private String summary;

    @SerializedName("description")
    private String description;

    @SerializedName("location")
    private String location;

    @SerializedName("start")
    private EventDate start;

    @SerializedName("end")
    private EventDate end;

    public EventRequestParams(Event event) {
        this.status = event.getStatus();
        this.summary = event.getSummary();
        this.description = event.getDescription();
        this.location = event.getLocation();
        this.start = event.getStart();
        this.end = event.getEnd();
    }

}
