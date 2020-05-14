package com.naver.hackday.android.speechrecognizecalender.src.ui.event.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.naver.hackday.android.speechrecognizecalender.src.ApplicationClass;
import com.naver.hackday.android.speechrecognizecalender.src.common.models.DefaultFailResponse;
import com.naver.hackday.android.speechrecognizecalender.src.network.event.EventService;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.Event;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.EventListResponse;
import com.naver.hackday.android.speechrecognizecalender.src.repository.event.EventRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class EventViewModel extends ViewModel {

    private EventRepository mEventRepository;

    //calendar api로 받아올 때 필요한 response
    public MutableLiveData<EventListResponse> calendarResponse = new MutableLiveData<EventListResponse>();
    public MutableLiveData<DefaultFailResponse> calendarFailResponse = new MutableLiveData<DefaultFailResponse>();

    public EventViewModel() {
        mEventRepository = new EventRepository(ApplicationClass.getApplicationClassContext());
        fetchAllCalendarEvents();
    }

    public void fetchAllCalendarEvents() {
        // calendar api 를 이용해 모든 event 받아오기
        final EventService eventService = new EventService();
        eventService.getAllEventsFromCalendar(new EventService.EventCallback() {
            @Override
            public void testSuccess(EventListResponse eventListResponse) {
                calendarResponse.setValue(eventListResponse);
                deleteAllEvents();
                insertAllEvents(eventListResponse.getItems());
            }

            @Override
            public void testSuccess(Event event) { }


            @Override
            public void networkFail() {
                calendarFailResponse.setValue(new DefaultFailResponse());
            }
        });
    }


    /* db 접근 */
    public LiveData<List<Event>> getAllEventsFromDB() {
        try {
            return mEventRepository.getAllEvents().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertAllEvents(List<Event> events) {
        mEventRepository.insertAll(events);
    }

    public void insertEvent(Event event) {
        mEventRepository.insert(event);
    }

    public void updateEvent(Event event) {
        mEventRepository.update(event);
    }

    public void deleteEvent(Event event) {
        mEventRepository.delete(event);
    }

    public void deleteAllEvents() {
        mEventRepository.deleteAll();
    }
}
