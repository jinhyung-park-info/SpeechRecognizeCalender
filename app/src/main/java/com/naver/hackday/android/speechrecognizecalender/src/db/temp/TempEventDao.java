//package com.naver.hackday.android.speechrecognizecalender.src.db.temp;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import com.naver.hackday.android.speechrecognizecalender.src.db.temp.models.MonthCount;
//import com.naver.hackday.android.speechrecognizecalender.src.db.temp.models.Event;
//import com.naver.hackday.android.speechrecognizecalender.src.db.temp.models.EventStartTime;
//
//import java.util.Date;
//import java.util.List;
//
////Dao = Data Access Object @Dao = db접근 객체
//@Dao
//public abstract class TempEventDao {
//    @Query("SELECT * FROM Event")
//    abstract LiveData<List<Event>> getAll();
//
//    @Query("SELECT * FROM Event")
//    public abstract LiveData<List<Event>> getEventList();
//
//    @Query("SELECT * FROM Event WHERE startTime between :from and :to")
//    public abstract LiveData<List<Event>> getMonthlyEventList(Date from, Date to);
//
//    @Query("SELECT startTime FROM Event WHERE startTime")
//    public abstract LiveData<List<EventStartTime>> getStartTime();
//
//    @Query("SELECT strftime('%Y년 %m월', startTime / 1000, 'unixepoch')  as month , COUNT(*) as count FROM Event GROUP BY month;")
//    public abstract LiveData<List<MonthCount>> getMonth();
//
//    @Insert
//    public abstract void insert(List<Event> Event);
//
//    @Update
//    abstract void update(Event Event);
//
//    @Delete
//    abstract void delete(Event Event);
//
//}
