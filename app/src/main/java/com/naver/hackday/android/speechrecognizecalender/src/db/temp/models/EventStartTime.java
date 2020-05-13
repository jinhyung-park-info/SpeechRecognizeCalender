package com.naver.hackday.android.speechrecognizecalender.src.db.temp.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.naver.hackday.android.speechrecognizecalender.src.db.temp.Converters;

import java.util.Date;

@Entity
@TypeConverters({Converters.class})
public class EventStartTime {
    private Date startTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
