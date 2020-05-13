package com.naver.hackday.android.speechrecognizecalender.src.db.temp.models;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.naver.hackday.android.speechrecognizecalender.src.db.temp.Converters;

import java.util.Date;

@Entity
public class MonthCount {
    private String month;

    private int count;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
