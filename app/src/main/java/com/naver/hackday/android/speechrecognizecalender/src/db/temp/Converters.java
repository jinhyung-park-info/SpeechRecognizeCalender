package com.naver.hackday.android.speechrecognizecalender.src.db.temp;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.util.Date;

import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.DATE_FORMAT_DAY;

public class Converters {

//    @TypeConverter
//    public static Date fromStringTimestamp(String value) {
//        //2019-09-02T02:45:15.000Z
//        Date time = new Date();
//        try {
//            time = DATE_FORMAT_DAY.parse(value);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return time;
//    }
//
//    @TypeConverter
//    public static String dateToString(Date date) {
//        String to = DATE_FORMAT_DAY.format(date);
//        return to;
//    }

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

