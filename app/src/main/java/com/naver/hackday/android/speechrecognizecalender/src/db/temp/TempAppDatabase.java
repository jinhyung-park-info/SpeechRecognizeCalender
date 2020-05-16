package com.naver.hackday.android.speechrecognizecalender.src.db.temp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.naver.hackday.android.speechrecognizecalender.src.db.temp.models.Event;

@Database(entities = {Event.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class TempAppDatabase extends RoomDatabase {
    //데이터베이스를 매번 생성하는건 리소스를 많이사용하므로 싱글톤이 권장된다고한다.
    private static TempAppDatabase INSTANCE;

    public abstract TempEventDao tempEventDao();

    //싱글톤 객체 가져오기
    public static TempAppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, TempAppDatabase.class, "weto-db")
//                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build();

        }
        return INSTANCE;
    }

    //디비객체제거
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
