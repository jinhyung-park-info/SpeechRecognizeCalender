package com.naver.hackday.android.speechrecognizecalender.src.network.event.interfaces;

import com.naver.hackday.android.speechrecognizecalender.src.common.models.DefaultResponse;
import com.naver.hackday.android.speechrecognizecalender.src.network.event.models.EventRequestParams;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.Event;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.EventListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface EventRetrofitInterface {
    @GET("events")
    Call<EventListResponse> getAllEvents();

    @GET("events/{eventId}")
    Call<Event> getEvent(@Path("eventId") String eventId);

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @POST("events")
    Call<Event> postEvent(@Body EventRequestParams eventRequestParams);

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @PUT("events/{eventId}")
    Call<Event> updateEvent(@Path("eventId") String eventId, @Body EventRequestParams eventRequestParams);

    @DELETE("events/{eventId}")
    Call<DefaultResponse> deleteEvent(@Path("eventId") String eventId);
}