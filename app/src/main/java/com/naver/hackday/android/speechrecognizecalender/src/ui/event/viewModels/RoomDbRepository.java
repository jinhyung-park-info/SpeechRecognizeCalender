package com.naver.hackday.android.speechrecognizecalender.src.ui.event.viewModels;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.naver.hackday.android.speechrecognizecalender.src.db.temp.TempAppDatabase;
import com.naver.hackday.android.speechrecognizecalender.src.db.temp.TempEventDao;
import com.naver.hackday.android.speechrecognizecalender.src.db.temp.models.MonthCount;
import com.naver.hackday.android.speechrecognizecalender.src.db.temp.models.Event;
import com.naver.hackday.android.speechrecognizecalender.src.db.temp.models.EventStartTime;

import java.util.Date;
import java.util.List;

import static com.naver.hackday.android.speechrecognizecalender.src.ApplicationClass.getApplicationClassContext;

class RoomDbRepository {
    private TempEventDao mTempEventDao;

    RoomDbRepository() {
        this.mTempEventDao = TempAppDatabase.getAppDatabase(getApplicationClassContext()).tempEventDao();
    }

    LiveData<List<Event>> getAllEvents() {
        return mTempEventDao.getEventList();
    }

    LiveData<List<Event>> getMonthlyEvents(Date from, Date to) {
        return mTempEventDao.getMonthlyEventList(from, to);
    }

    LiveData<List<EventStartTime>> getStartTime() {
        return mTempEventDao.getStartTime();
    }

    LiveData<List<MonthCount>> getMonthData() {
        return mTempEventDao.getMonth();
    }

    void insertEventList(List<Event> eventList) {
        new InsertAsyncTask(mTempEventDao).execute(eventList);
    }

    //비동기처리                                 //넘겨줄객체, 중간에 처리할 데이터, 결과물(return)
    private static class InsertAsyncTask extends AsyncTask<List<Event>, Void, Void> {
        private TempEventDao tempEventDao;

        InsertAsyncTask(TempEventDao mTodoDao) {
            this.tempEventDao = mTodoDao;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Event>... events) {
            tempEventDao.insert(events[0]);
            return null;
        }
    }
}