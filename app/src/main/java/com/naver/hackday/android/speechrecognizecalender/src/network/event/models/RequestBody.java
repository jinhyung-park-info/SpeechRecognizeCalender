package com.naver.hackday.android.speechrecognizecalender.src.network.event.models;

import com.google.gson.annotations.SerializedName;

public class RequestBody
{
    @SerializedName("start")
    private Start start;
    @SerializedName("end")
    private End end;

    public RequestBody(Start start, End end){
        start = this.start;
        end = this.end;
    }

    public Start getStart ()
    {
        return start;
    }

    public void setStart (Start start)
    {
        this.start = start;
    }

    public End getEnd ()
    {
        return end;
    }

    public void setEnd (End end)
    {
        this.end = end;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [start = "+start+", end = "+end+"]";
    }
}