package com.naver.hackday.android.speechrecognizecalender.src.network.clova.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.UNKNOWN;

public class ExtractionResult {
    public String date;
    public String startDate;
    public String endDate;
    public int mode;

    public ExtractionResult(int mode) {
        this.mode = mode;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public ExtractionResult(String date, String startDate, String endDate) {
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
        this.mode = UNKNOWN;
    }

    public ExtractionResult(String date, String startDate, String endDate, int mode) {
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
        this.mode = mode;
    }

    @NonNull
    @Override
    public String toString() {
        return "mode= " + mode + " date= " + date + " startDate= " + startDate + " endDate= " + endDate;
    }
}
