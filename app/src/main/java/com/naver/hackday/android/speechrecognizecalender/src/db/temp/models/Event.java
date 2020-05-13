package com.naver.hackday.android.speechrecognizecalender.src.db.temp.models;

import androidx.annotation.NonNull;
import com.naver.hackday.android.speechrecognizecalender.src.db.temp.*;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity
public class Event {
    @PrimaryKey(autoGenerate = true)
    private int no;             //room db내의 식별자
    private String calenderId;     //api에서 전달해주는 식별자
    private String kind;           //calender resource type ex)#calender#event)
    private String status;      //확정 or 취소 여
    private String summary;        //내용
    private String description;    //세부내용 (optional)
    private String location;       //위치 (optional)
    private Date startTime;      //시작 시간
    private Date endTime;        //종료 시간
    private Date createdTime;    //생성 시간
    private Date updatedTime;    //수정 시간

    public Event(String calenderId, String kind, String status, String summary, String description, String location, Date startTime, Date endTime, Date createdTime, Date updatedTime) {
        this.calenderId = calenderId;
        this.kind = kind;
        this.status = status;
        this.summary = summary;
        this.description = description;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getCalenderId() {
        return calenderId;
    }

    public void setCalenderId(String calenderId) {
        this.calenderId = calenderId;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @NonNull
    @Override
    public String toString() {
        return this.no + " / " + summary + " / " + location + " / " + startTime + "~" + endTime + "\n";
    }
}
