package com.naver.hackday.android.speechrecognizecalender.src.ui.event.viewModels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.naver.hackday.android.speechrecognizecalender.src.common.models.DefaultFailResponse;
import com.naver.hackday.android.speechrecognizecalender.src.network.event.EventDetail;
import com.naver.hackday.android.speechrecognizecalender.src.network.event.models.EventResource;

import retrofit2.Retrofit;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.MonthCount;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.Event;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.adapters.EventListAdapter;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

import com.naver.hackday.android.speechrecognizecalender.src.ApplicationClass;
import com.naver.hackday.android.speechrecognizecalender.src.common.models.DefaultFailResponse;
import com.naver.hackday.android.speechrecognizecalender.src.network.event.EventService;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.EventListResponse;
import com.naver.hackday.android.speechrecognizecalender.src.repository.event.EventRepository;

import java.util.concurrent.ExecutionException;

public class EventViewModel extends ViewModel {
    public ObservableField<String> createStartDate;
    public ObservableField<String> createEndDate;
    public ObservableField<String> updateStartDate;
    public ObservableField<String> updateEndDate;

    public MutableLiveData<EventResource> eventCreateResponse;
    public MutableLiveData<DefaultFailResponse> eventFailResponse;
    public MutableLiveData<String> eventDeleteResponse;

    public Retrofit retrofit;
    public String calendarID = ""; //생성 버튼 클릭 시 넘어옴
    public String eventID = ""; //delete, update용

//    private RoomDbRepository mRoomDbRepository;
    public EventListAdapter mEventListAdapter;
    private EventRepository mEventRepository;

    /* RoomDB Query*/
    public LiveData<List<Event>> getAllEvents() {
        return mEventRepository.getAllEvents();
    }

    public LiveData<List<Event>> getMonthlyEvents(Date from, Date to) {
        return mEventRepository.getMonthlyEvents(from, to);
    }

    public LiveData<List<MonthCount>> getMonthData() {
        return mEventRepository.getMonthData();
    }

    //calendar api로 받아올 때 필요한 response
    public MutableLiveData<EventListResponse> calendarResponse = new MutableLiveData<EventListResponse>();
    public MutableLiveData<DefaultFailResponse> calendarFailResponse = new MutableLiveData<DefaultFailResponse>();

    public EventViewModel() {
        createStartDate = new ObservableField<>("");
        createEndDate = new ObservableField<>("");
        updateStartDate = new ObservableField<>("");
        updateEndDate = new ObservableField<>("");
        eventCreateResponse = new MutableLiveData<EventResource>();
        eventDeleteResponse = new MutableLiveData<String>();
        eventFailResponse = new MutableLiveData<DefaultFailResponse>();
        mEventRepository = new EventRepository(ApplicationClass.getApplicationClassContext());
        mEventListAdapter = new EventListAdapter(this);
        fetchAllCalendarEvents();
    }

    public void eventCreateBtnClicked(){
        final EventDetail eventDetail = new EventDetail();
        eventDetail.eventCreate(calendarID, createEndDate.get(), createStartDate.get(), new EventDetail.EventCallback(){
            @Override
            public void testSuccess(EventResource response) {
                eventCreateResponse.setValue(response);
            }
            @Override
            public void testSuccess(String success) {
                return;
            }
            @Override
            public void testFail() {
                eventFailResponse.setValue(new DefaultFailResponse());
            }
        });

    }

    public void eventDeleteBtnClicked(){
        final EventDetail eventDetail = new EventDetail();
        eventDetail.eventDelete(calendarID, eventID, new EventDetail.EventCallback() {
            @Override
            public void testSuccess(EventResource response) { //사용x
                return;
            }

            @Override
            public void testSuccess(String eventID) {
                eventDeleteResponse.setValue(eventID);
            }

            @Override
            public void testFail() {
                eventFailResponse.setValue(new DefaultFailResponse());
            }
        });

    }

    public void eventUpdateBtnClicked() {
        final EventDetail eventDetail = new EventDetail();
        eventDetail.eventUpdate(calendarID, eventID, updateEndDate.get(), updateStartDate.get(), new EventDetail.EventCallback() {
            @Override
            public void testSuccess(EventResource response) {
                eventCreateResponse.setValue(response);
            }

            @Override
            public void testSuccess(String eventID) {
                return;
            }

            @Override
            public void testFail() {
                eventFailResponse.setValue(new DefaultFailResponse());
            }
        });
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
            public void testSuccess(Event event) {
            }


            @Override
            public void networkFail() {
                calendarFailResponse.setValue(new DefaultFailResponse());
            }
        });
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
