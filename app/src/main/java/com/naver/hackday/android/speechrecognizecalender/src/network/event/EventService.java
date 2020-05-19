package com.naver.hackday.android.speechrecognizecalender.src.network.event;

import android.util.Log;

import com.naver.hackday.android.speechrecognizecalender.src.ApplicationClass;
import com.naver.hackday.android.speechrecognizecalender.src.common.util.AuthorizationInterceptor;
import com.naver.hackday.android.speechrecognizecalender.src.common.util.GoogleApi;
import com.naver.hackday.android.speechrecognizecalender.src.common.util.SharedPreferenceManager;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.Event;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.EventListResponse;

import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.ACCESS_TOKEN;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.EMAIL;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventService {
    public interface EventCallback {
        void fetchSuccess(EventListResponse eventListResponse);

        void networkFail();
    }


    private static final String GOOGLE_CALENDAR_BASE_URL = "https://www.googleapis.com/calendar/v3/calendars/";
    public static Retrofit retrofit;

    public static Retrofit getCalendarRetrofit() {
        final AuthorizationInterceptor authorizationInterceptor = new AuthorizationInterceptor();
        final String calendarId = SharedPreferenceManager.getString(ApplicationClass.getApplicationClassContext(), EMAIL);
        final String retrofitBaseUrl = GOOGLE_CALENDAR_BASE_URL + calendarId + "/";
        authorizationInterceptor.setAccessToken(SharedPreferenceManager.getString(ApplicationClass.getApplicationClassContext(), ACCESS_TOKEN));

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(authorizationInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(retrofitBaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public void getAllEventsFromCalendar(EventCallback eventCallback) {
        final GoogleApi googleApi = getCalendarRetrofit().create(GoogleApi.class);
        googleApi.getAllEvents().enqueue(new Callback<EventListResponse>() {
            @Override
            public void onResponse(Call<EventListResponse> call, Response<EventListResponse> response) {
                EventListResponse eventListResponse = response.body();
                if (eventListResponse == null) {
                    eventCallback.networkFail();
                    return;
                }
                if (!eventListResponse.getItems().isEmpty()) {
                    eventCallback.fetchSuccess(eventListResponse);
                    return;
                }
                eventCallback.fetchSuccess(eventListResponse);
            }

            @Override
            public void onFailure(Call<EventListResponse> call, Throwable t) {
                t.printStackTrace();
                eventCallback.networkFail();
            }
        });
    }
}
