package com.naver.hackday.android.speechrecognizecalender.src.persistence.event;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.Event;

import java.util.List;

@Dao
public interface EventDao {
    @Query("SELECT * FROM Events")
    LiveData<List<Event>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Event event);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Event> events);

    @Delete
    void delete(Event event);

    @Query("DELETE FROM events")
    void deleteAll();

    @Update
    void update(Event event);


}
