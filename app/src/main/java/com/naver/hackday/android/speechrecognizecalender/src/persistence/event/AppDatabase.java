package com.naver.hackday.android.speechrecognizecalender.src.persistence.event;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.Event;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract EventDao eventDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "event_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
