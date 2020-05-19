package com.naver.hackday.android.speechrecognizecalender.src.network.clova;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.DATE_FORMAT_DAY;


public class TextExtractionViewModel extends ViewModel {

    public MutableLiveData<String> mSentence;
    public MutableLiveData<String> mDate;
    public MutableLiveData<String> mTime;

    public MutableLiveData<String> mStartDate;
    public MutableLiveData<String> mEndDate;
    public MutableLiveData<Integer> mMode; // 날짜만(yyyy-MM-dd 00:00:00), 날짜+시간(yyyy-MM-dd HH:mm:ss), 시작일+끝나는날

    public TextExtractionViewModel() {
        mDate = new MutableLiveData<>();
        mTime = new MutableLiveData<>();
        mSentence = new MutableLiveData<>();
    }

    public void task() {
        if(mSentence.getValue().contains("부터")){

        }
    }
    public void sentenceToDate() {
        //오늘, 내일, 모래, ?월?일, ?일, ?요일
        Date todayDate = new Date();
        String dateString = DATE_FORMAT_DAY.format(todayDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(todayDate);

        if (Objects.requireNonNull(mSentence.getValue()).contains("오늘")) {
            mDate.setValue(dateString);
            return;
        } else if (mSentence.getValue().contains("모래") || mSentence.getValue().contains("모레")) {
            cal.add(Calendar.DATE, 2);
            mDate.setValue(DATE_FORMAT_DAY.format(cal.getTime()));
            return;
        } else if (mSentence.getValue().contains("내일") || mSentence.getValue().contains("네일")) {
            cal.add(Calendar.DATE, 1);
            mDate.setValue(DATE_FORMAT_DAY.format(cal.getTime()));
            return;
        }

        //        ?요일
        Pattern dayOfWeekPattern = Pattern.compile(".?요일");
        Matcher dayOfWeekPatternMatcher = dayOfWeekPattern.matcher(mSentence.getValue());
        if (dayOfWeekPatternMatcher.find()) {
            String dayOfWeek = String.valueOf(dayOfWeekPatternMatcher.group().charAt(0));
            Log.d("요일추출", dayOfWeek);
            Log.d("확인 ?요일", dayOfWeekPatternMatcher.group());
            if (mSentence.getValue().contains("이번")) {
                //
            } else if (mSentence.getValue().contains("다음")) {
                cal.add(Calendar.DATE, 7);
            }

            if (dayOfWeek.equals("월")) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                mDate.setValue(DATE_FORMAT_DAY.format(cal.getTime()));
            } else if (dayOfWeek.equals("화")) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                mDate.setValue(DATE_FORMAT_DAY.format(cal.getTime()));
            } else if (dayOfWeek.equals("수")) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                mDate.setValue(DATE_FORMAT_DAY.format(cal.getTime()));
            } else if (dayOfWeek.equals("목")) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                mDate.setValue(DATE_FORMAT_DAY.format(cal.getTime()));
            } else if (dayOfWeek.equals("금")) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                mDate.setValue(DATE_FORMAT_DAY.format(cal.getTime()));
            } else if (dayOfWeek.equals("토")) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                mDate.setValue(DATE_FORMAT_DAY.format(cal.getTime()));
            } else if (dayOfWeek.equals("일")) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                mDate.setValue(DATE_FORMAT_DAY.format(cal.getTime()));
            }
            return;
        }

//        ?월?일
        Pattern monthDayPattern = Pattern.compile(".?.?.?월.?.?.?일");
        Matcher monthDayPatternMatcher = monthDayPattern.matcher(mSentence.getValue());
        if (monthDayPatternMatcher.find()) {
            String output = monthDayPatternMatcher.group();
            Log.d("확인 ?월?일", output);
            int indexMonth = output.indexOf("월");
            int indexDay = output.indexOf("일");
            int Month, Day;
            try{
                Month = Integer.parseInt(output.substring(indexMonth - 2, indexMonth));
            }catch (StringIndexOutOfBoundsException e){
                Month = Integer.parseInt(output.substring(indexMonth - 1, indexMonth));
            }
            try{
                Day = Integer.parseInt(output.substring(indexDay - 2, indexDay));
            }catch (NumberFormatException e){
                Day = Integer.parseInt(output.substring(indexDay - 1, indexDay));
            }
            cal.set(Calendar.MONTH, Month-1);
            cal.set(Calendar.DATE, Day);
            mDate.setValue(DATE_FORMAT_DAY.format(cal.getTime()));
            return;
        }
//      ?일
        Pattern dayPattern = Pattern.compile(".?.?일");
        Matcher dayPatternMatcher = monthDayPattern.matcher(mSentence.getValue());
        if (dayPatternMatcher.find()) {
            String output = dayPatternMatcher.group();
            Log.d("확인 ?일", output);
            int indexDay = output.indexOf("일");
            int Day = Integer.parseInt(output.substring(indexDay - 2, indexDay));
            cal.set(Calendar.DATE, Day);
            mDate.setValue(DATE_FORMAT_DAY.format(cal.getTime()));
            return;
        }
    }
}

