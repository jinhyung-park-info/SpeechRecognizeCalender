package com.naver.hackday.android.speechrecognizecalender.src.common.util;

import com.naver.hackday.android.speechrecognizecalender.src.network.login.models.AccessTokenRequestBody;
import com.naver.hackday.android.speechrecognizecalender.src.network.login.models.AccessTokenResponse;
import com.naver.hackday.android.speechrecognizecalender.src.network.event.models.*;
import com.naver.hackday.android.speechrecognizecalender.src.common.models.DefaultResponse;
import com.naver.hackday.android.speechrecognizecalender.src.network.event.models.EventRequestParams;
import com.naver.hackday.android.speechrecognizecalender.src.network.login.models.RefreshTokenRequestBody;
import com.naver.hackday.android.speechrecognizecalender.src.network.login.models.RefreshTokenResponse;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.Event;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.EventListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Headers;

public interface GoogleApi {

    //access token
    @POST("token")
    Call<AccessTokenResponse> getAccessToken(@Body AccessTokenRequestBody body);

    //refresh token
    @POST("token")
    Call<RefreshTokenResponse> getRefreshToken(@Body RefreshTokenRequestBody body);

    //event List
    @GET("{CalendarID}/events")
    Call<GoogleCalendarResult> getData(@Path("CalendarID") String CalendarID, @Query("key") String api_key);

    //event insert
    //@FormUrlEncoded
    @POST("{CalendarID}/events")
    Call<EventResource> postData(@Header("Authorization") String token, @Path("CalendarID") String CalendarID, @Body RequestBody requestBody);

    //event delete
    @DELETE("{CalendarID}/events/{eventID}")
    Call<Void> deleteData(@Header("Authorization") String token, @Path("CalendarID") String CalendarID, @Path("eventID") String eventID);

    //event update
    @PUT("{CalendarID}/events/{eventID}")
    Call<EventResource> updateData(@Header("Authorization") String token, @Path("CalendarID") String CalendarID, @Path("eventID") String eventID, @Body RequestBody requestBody);

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
