package com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;


public class EventListResponse {
    @SerializedName("items")
    private List<Event> items;

    public EventListResponse(String kind, List<Event> items) {
        this.items = items;
    }

    public List<Event> getItems() {
        return items;
    }

    public void setItems(List<Event> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EventListResponse{");
        sb.append("items=").append(items);
        sb.append('}');
        return sb.toString();
    }
}
