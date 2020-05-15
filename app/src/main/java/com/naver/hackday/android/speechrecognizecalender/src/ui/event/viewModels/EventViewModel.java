package com.naver.hackday.android.speechrecognizecalender.src.ui.event.viewModels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.naver.hackday.android.speechrecognizecalender.src.common.models.DefaultFailResponse;
import com.naver.hackday.android.speechrecognizecalender.src.network.event.EventDetail;
import com.naver.hackday.android.speechrecognizecalender.src.network.event.models.EventResource;

import retrofit2.Retrofit;

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

    public EventViewModel() {
        createStartDate = new ObservableField<>("");
        createEndDate = new ObservableField<>("");
        updateStartDate = new ObservableField<>("");
        updateEndDate = new ObservableField<>("");
        eventCreateResponse = new MutableLiveData<EventResource>();
        eventDeleteResponse = new MutableLiveData<String>();
        eventFailResponse = new MutableLiveData<DefaultFailResponse>();
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

    public void eventUpdateBtnClicked(){
        final EventDetail eventDetail = new EventDetail();
        eventDetail.eventUpdate(calendarID,eventID,updateEndDate.get(),updateStartDate.get(), new EventDetail.EventCallback(){
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

}
