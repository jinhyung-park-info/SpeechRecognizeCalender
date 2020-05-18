package com.naver.hackday.android.speechrecognizecalender.src.network.event;

import com.naver.hackday.android.speechrecognizecalender.src.network.event.models.End;
import com.naver.hackday.android.speechrecognizecalender.src.network.event.models.EventResource;
import com.naver.hackday.android.speechrecognizecalender.src.network.event.models.RequestBody;
import com.naver.hackday.android.speechrecognizecalender.src.network.event.models.Start;
import com.naver.hackday.android.speechrecognizecalender.src.common.util.GoogleApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventDetail {
    String BASE_URL = "https://www.googleapis.com/calendar/v3/calendars/";
    String accessToken; // 받아와야됨
    String APIKey; //받아와야됨
    static Retrofit eventRetrofit;

    public interface EventCallback {
        void testSuccess(EventResource eventResource); //response 들어감
        void testSuccess(String eventID); //delete
        void testFail();
    }

    public Retrofit getEventRetrofit() {
        if (eventRetrofit == null){
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return eventRetrofit;
    }

    public void eventCreate(String calenderID, String endDate, String startDate, EventCallback eventCallback){
        GoogleApi googleApi = getEventRetrofit().create(GoogleApi.class);
        End endTime = new End(endDate);
        Start startTime = new Start(startDate);
        RequestBody requestBody = new RequestBody(startTime, endTime);

        googleApi.postData(accessToken, calenderID, requestBody).enqueue(new Callback<EventResource>() {
            @Override
            public void onResponse(Call<EventResource> call, Response<EventResource> response) {
                EventResource postResponse = response.body();
                if (response.isSuccessful()){
                    eventCallback.testSuccess(postResponse);
                }
                else {
                    eventCallback.testFail();
                }
            }
            @Override
            public void onFailure(Call<EventResource> call, Throwable t) {
                t.printStackTrace();
                eventCallback.testFail();
            }
        });
    }

    //event 삭제
    public void eventDelete(String calenderID, String eventID, EventCallback eventCallback){
        GoogleApi googleApi = getEventRetrofit().create(GoogleApi.class);
        googleApi.deleteData(APIKey, calenderID, eventID).enqueue(new Callback<Void>(){
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    eventCallback.testSuccess(eventID);
                }
                else{
                    eventCallback.testFail();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                eventCallback.testFail();
            }
        });
    }

    public void eventUpdate(String calendarID, String eventID, String endDate, String startDate, EventCallback eventCallback){
        GoogleApi googleApi = getEventRetrofit().create(GoogleApi.class);
        End endTime = new End(endDate);
        Start startTime = new Start(startDate);
        RequestBody requestBody = new RequestBody(startTime, endTime);
        googleApi.updateData(APIKey, calendarID, eventID, requestBody).enqueue(new Callback<EventResource>() {
            @Override
            public void onResponse(Call<EventResource> call, Response<EventResource> response) {
                EventResource putResponse = response.body();
                if (response.isSuccessful()){
                    eventCallback.testSuccess(putResponse);
                }
                else{
                    eventCallback.testFail();
                }
            }
            @Override
            public void onFailure(Call<EventResource> call, Throwable t) {
                eventCallback.testFail();
            }
        });
    }
}
