package com.naver.hackday.android.speechrecognizecalender.src.persistence.event;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.MonthCount;
import com.naver.hackday.android.speechrecognizecalender.src.persistence.event.models.Event;

import java.util.Date;
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

    @Query("SELECT * FROM Events WHERE start_date between :from and :to OR start_dateTime between :from and :to")
    public abstract LiveData<List<Event>> getMonthlyEventList(Date from, Date to);

    @Query("SELECT " +
            "CASE WHEN(start_date IS NULL) then strftime('%Y년 %m월', start_dateTime / 1000, 'unixepoch') " +
            "else strftime('%Y년 %m월', start_date / 1000, 'unixepoch') END as month," +
            " COUNT(*) as count FROM Events GROUP BY month;")
    public abstract LiveData<List<MonthCount>> getMonth();

}
