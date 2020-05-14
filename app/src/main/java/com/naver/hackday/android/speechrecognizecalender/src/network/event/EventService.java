package com.naver.hackday.android.speechrecognizecalender.src.network.event;

import com.naver.hackday.android.speechrecognizecalender.src.common.util.XAccessTokenInterceptor;
import com.naver.hackday.android.speechrecognizecalender.src.network.event.interfaces.EventRetrofitInterface;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.Event;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.EventListResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventService {
    public interface EventCallback {
        void testSuccess(EventListResponse eventListResponse);
        void testSuccess(Event event);
        void networkFail();
    }


    private static final String CALENDAR_ID = "whdwhd0115@gmail.com";
    private static final String ACCESS_TOKEN = "ya29.a0AfH6SMB0m2R-CKSZc4EgtaoAJG2iS9W_kJ3P8q7tc7JRt8TMjd4ExpvVIGiLSpu2yDQXPaNRvCk3pwOhjB6CNH6Apmxt7ikcgBVZ4KAiOh_ZI38xEHocE6eWuZ140yn0TQ5IQ_88qKLMEzL8axCWqbpT0hBulU2EanU";
    private static final String AUTH_PREFIX = "Bearer ";
    private static final String GOOGLE_CALENDAR_BASE_URL = "https://www.googleapis.com/calendar/v3/calendars/" + CALENDAR_ID + "/";


    public static Retrofit retrofit;

    public static Retrofit getCalendarRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .addNetworkInterceptor(new XAccessTokenInterceptor()) // JWT 자동 헤더 전송
                    .addNetworkInterceptor(httpLoggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(GOOGLE_CALENDAR_BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public void getAllEventsFromCalendar(EventCallback eventCallback) {
        final EventRetrofitInterface eventRetrofitInterface = getCalendarRetrofit().create(EventRetrofitInterface.class);
        final String authToken = AUTH_PREFIX + ACCESS_TOKEN;
        eventRetrofitInterface.getAllEvents(authToken).enqueue(new Callback<EventListResponse>() {
            @Override
            public void onResponse(Call<EventListResponse> call, Response<EventListResponse> response) {
                EventListResponse eventListResponse = response.body();
                if (eventListResponse == null) {
                    eventCallback.networkFail();
                    return;
                }
                if (!eventListResponse.getItems().isEmpty()) {
                    eventCallback.testSuccess(eventListResponse);
                    return;
                }
                eventCallback.testSuccess(eventListResponse);
            }

            @Override
            public void onFailure(Call<EventListResponse> call, Throwable t) {
                t.printStackTrace();
                eventCallback.networkFail();
            }
        });
    }

    public void getEventFromCalendar(String eventId, EventCallback eventCallback) {
        final EventRetrofitInterface eventRetrofitInterface = getCalendarRetrofit().create(EventRetrofitInterface.class);
        final String authToken = AUTH_PREFIX + ACCESS_TOKEN;
        eventRetrofitInterface.getEvent(authToken, eventId).enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                Event eventResponse = response.body();
                if (eventResponse == null) {
                    eventCallback.networkFail();
                    return;
                }

                eventCallback.testSuccess(eventResponse);
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                t.printStackTrace();
                eventCallback.networkFail();
            }
        });
    }

}
