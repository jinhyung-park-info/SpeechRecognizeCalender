package com.naver.hackday.android.speechrecognizecalender.src.repository.event;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.AppDatabase;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.EventDao;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.Event;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EventRepository {
    private EventDao mEventDao;
    private ExecutorService mExecutorService;
    private Future<LiveData<List<Event>>> mAllEvents;


    public EventRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mExecutorService = Executors.newSingleThreadExecutor();

        mEventDao = db.eventDao();
        mAllEvents = mExecutorService.submit(new EventRunnable.GetAllCallable(mEventDao));
    }

    public Future<LiveData<List<Event>>> getAllEvents() {
        return mAllEvents;
    }

    public void insert(Event event) {
        mExecutorService.execute(new EventRunnable.InsertRunnable(mEventDao, event));
    }

    public void insertAll(List<Event> events) {
        mExecutorService.execute(new EventRunnable.InsertAllRunnable(mEventDao, events));
    }

    public void update(Event event) {
        mExecutorService.execute(new EventRunnable.UpdateRunnable(mEventDao, event));
    }

    public void delete(Event event) {
        mExecutorService.execute(new EventRunnable.DeleteRunnable(mEventDao, event));
    }

    public void deleteAll() {
        mExecutorService.execute(new EventRunnable.DeleteAllRunnable(mEventDao));
    }


}
