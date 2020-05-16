package com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models;

import androidx.annotation.Nullable;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class EventDate{
    // '하루 종일' 로 설정한 경우 date field 존재. dateTime field 는 null
    @Nullable
    @SerializedName("date")
    private Date date;

    // '시간 설정' 시 dateTime field 존재. date field 는 null
    @Nullable
    @SerializedName("dateTime")
    private Date dateTime;

    public EventDate(@Nullable Date date, @Nullable Date dateTime) {
        this.date = date;
        this.dateTime = dateTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EventDate{");
        sb.append("date=").append(date);
        sb.append(", dateTime=").append(dateTime);
        sb.append('}');
        return sb.toString();
    }
}
