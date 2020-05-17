package com.naver.hackday.android.speechrecognizecalender.src.repository.event;

import androidx.lifecycle.LiveData;

import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.EventDao;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.Event;

import java.util.List;
import java.util.concurrent.Callable;

public class EventRunnable {

    protected static class GetAllCallable implements Callable<LiveData<List<Event>>> {
        private EventDao eventDao;

        public GetAllCallable(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        public LiveData<List<Event>> call() {
            return eventDao.getAll();
        }
    }

    protected static class InsertRunnable implements Runnable {
        private EventDao eventDao;
        private Event event;

        public InsertRunnable(EventDao eventDao, Event event) {
            this.eventDao = eventDao;
            this.event = event;
        }

        @Override
        public void run() {
            eventDao.insert(event);
        }
    }

    protected static class InsertAllRunnable implements Runnable {
        private EventDao eventDao;
        private List<Event> events;

        public InsertAllRunnable(EventDao eventDao, List<Event> events) {
            this.eventDao = eventDao;
            this.events = events;
        }

        @Override
        public void run() {
            eventDao.insertAll(events);
        }
    }

    protected static class UpdateRunnable implements Runnable {
        private EventDao eventDao;
        private Event event;

        public UpdateRunnable(EventDao eventDao, Event event) {
            this.eventDao = eventDao;
            this.event = event;
        }

        @Override
        public void run() {
            eventDao.update(event);
        }
    }

    protected static class DeleteRunnable implements Runnable {
        private EventDao eventDao;
        private Event event;

        public DeleteRunnable(EventDao eventDao, Event event) {
            this.eventDao = eventDao;
            this.event = event;
        }

        @Override
        public void run() {
            eventDao.delete(event);
        }
    }

    protected static class DeleteAllRunnable implements Runnable {
        private EventDao eventDao;

        public DeleteAllRunnable(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        public void run() {
            eventDao.deleteAll();
        }
    }
}
