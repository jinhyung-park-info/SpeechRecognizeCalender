package com.naver.hackday.android.speechrecognizecalender.src.ui.event.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.naver.hackday.android.speechrecognizecalender.src.db.temp.models.MonthCount;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.adapters.EventListAdapter;
import com.naver.hackday.android.speechrecognizecalender.src.db.temp.models.Event;
import com.naver.hackday.android.speechrecognizecalender.src.db.temp.models.EventStartTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class EventViewModel extends ViewModel {

    private RoomDbRepository mRoomDbRepository;
    public EventListAdapter mEventListAdapter;

    /* Constructor  */
    public EventViewModel() {
        mRoomDbRepository = new RoomDbRepository();
        mEventListAdapter = new EventListAdapter(this);
    }

    /* RoomDB Query*/
    public LiveData<List<Event>> getAllEvents() {
        return mRoomDbRepository.getAllEvents();
    }

    public LiveData<List<Event>> getMonthlyEvents(Date from, Date to) {
        return mRoomDbRepository.getMonthlyEvents(from, to);
    }

    public LiveData<List<EventStartTime>> getStartTime() {
        return mRoomDbRepository.getStartTime();
    }

    public LiveData<List<MonthCount>> getMonthData() {
        return mRoomDbRepository.getMonthData();
    }
    /* ~~~~ */


    /* Click Listener */
    public void clickedInsertEventList() { //더미용 데이터 추가하는 함수
        List<Event> eventList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        for (int i = 0; i < 200; i++) {
            cal.add(Calendar.DATE, +1);
            Date d = new Date(cal.getTimeInMillis());
            eventList.add(new Event("calenderId" + i, "#calender#event", "done", "일정 내용" + i, "일정 상세" + i, "장소이름", new Date(cal.getTimeInMillis()),
                    new Date(), new Date(), new Date()));
        }
        mRoomDbRepository.insertEventList(eventList);
    }


}
