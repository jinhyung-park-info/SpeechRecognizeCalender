package com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "events")
public class Event {
    // Event entity column : index, eventId, kind, status, summary, description, location, created, updated, start_date, start_dateTime, end_date, end_dateTime

    @PrimaryKey(autoGenerate = true)
    private int index;

    @SerializedName("id")
    private String eventId;

    @SerializedName("kind")
    private String kind;

    @SerializedName("status")
    private String status;

    @SerializedName("summary")
    private String summary;

    @SerializedName("description")
    private String description;

    @SerializedName("location")
    private String location;

    @SerializedName("created")
    private Date created;

    @SerializedName("updated")
    private Date updated;

    @SerializedName("start")
    @Embedded(prefix = "start_")
    private EventDate start;

    @SerializedName("end")
    @Embedded(prefix = "end_")
    private EventDate end;

    public Event(String eventId, String kind, String status, String summary, String description, String location, Date created, Date updated, EventDate start, EventDate end) {
        this.eventId = eventId;
        this.kind = kind;
        this.status = status;
        this.summary = summary;
        this.description = description;
        this.location = location;
        this.created = created;
        this.updated = updated;
        this.start = start;
        this.end = end;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @NonNull
    public String getEventId() {
        return eventId;
    }

    public void setEventId(@NonNull String eventId) {
        this.eventId = eventId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EventDate getStart() {
        return start;
    }

    public void setStart(EventDate start) {
        this.start = start;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public EventDate getEnd() {
        return end;
    }

    public void setEnd(EventDate end) {
        this.end = end;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Event{");
        sb.append("index=").append(index);
        sb.append(", eventId='").append(eventId).append('\'');
        sb.append(", kind='").append(kind).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", summary='").append(summary).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", created=").append(created);
        sb.append(", updated=").append(updated);
        sb.append(", start=").append(start);
        sb.append(", end=").append(end);
        sb.append('}');
        return sb.toString();
    }
}
