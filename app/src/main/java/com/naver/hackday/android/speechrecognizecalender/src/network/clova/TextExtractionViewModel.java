package com.naver.hackday.android.speechrecognizecalender.src.network.clova;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.naver.hackday.android.speechrecognizecalender.src.network.clova.models.ExtractionResult;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.DATE_FORMAT_DAY;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.DAY_TO_DAY;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.ONE_DAY;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.UNKNOWN;


public class TextExtractionViewModel extends ViewModel {

    public MutableLiveData<String> mSentence;
    public MutableLiveData<ExtractionResult> mResult;


    public TextExtractionViewModel() {
        mSentence = new MutableLiveData<>("");
        mResult = new MutableLiveData<>();
    }

    public void doExtraction() {
        if (mSentence.getValue().contains("부터")) {
            String startTime, endTime, startDay, endDay;
            String startDate, endDate;

            String startSentence, endSentence;

            startSentence = mSentence.getValue().substring(0, mSentence.getValue().indexOf("부터"));
            endSentence = mSentence.getValue().substring(mSentence.getValue().indexOf("부터"));

            startDay = sentenceToDay(startSentence);
//            mStartDate.setValue(startDay);
            if (startDay.length() > 1) {//startDate날짜가 추출되었음
                startTime = sentenceToTime(startSentence);//시간추출시도
                if (startTime.length() > 1) {
                    //startTime 추출 완료
                    startDate = startDay + " " + startTime;
                } else {
                    //start의 시간이 없음
                    startDate = startDay + " " + "00:00";
                }
            } else {
                //날짜정보가 없음
                mResult.setValue(new ExtractionResult(UNKNOWN));
                return;
            }

            endDay = sentenceToDay(endSentence);
            if (endDay.length() > 1) {
                //endDate날짜가 추출되었음
                endTime = sentenceToTime(endSentence);//시간추출시도
                if (endTime.length() > 1) {
                    //endTime 추출 완료
                    endDate = endDay + " " + endTime;
                } else {
                    //end의 시간이 없음
                    endDate = endDay + " " + "00:00";
                }
                mResult.setValue(new ExtractionResult("", startDate, endDate, DAY_TO_DAY));
            } else {
                //endDate날짜가 없음
                //시간추출해서 있으면 날짜는 start와 맞춤
                endTime = sentenceToTime(endSentence);//시간추출시도
                if (endTime.length() > 1) {
                    //endTime 추출 완료
                    endDate = startDay + " " + endTime;
                    mResult.setValue(new ExtractionResult("", startDate, endDate, DAY_TO_DAY));
                } else {
                    //endDate자체가 없음
                    mResult.setValue(new ExtractionResult(UNKNOWN));
                }
            }
            return;
        } else {
            String onlyTime, onlyDay;
            String onlyDate;

            onlyDay = sentenceToDay(mSentence.getValue());
            if (onlyDay.length() > 1) {//Date날짜가 추출되었음 (단일 날짜)
                onlyTime = sentenceToTime(mSentence.getValue());//시간추출시도
                if (onlyTime.length() > 1) {
                    onlyDate = onlyDay + " " + onlyTime;
                } else {
                    onlyDate = onlyDay + " " + "00:00";
                }
                mResult.setValue(new ExtractionResult(onlyDate, "", "", ONE_DAY));
            } else {
                //날짜정보가 없음
                mResult.setValue(new ExtractionResult(UNKNOWN));
            }
            return;
        }
    }

    public String sentenceToDay(String sentence) {
        //오늘, 내일, 모래
        Date todayDate = new Date();
        String dateString = DATE_FORMAT_DAY.format(todayDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(todayDate);

        if (Objects.requireNonNull(sentence).contains("오늘")) {
            return dateString;
        } else if (sentence.contains("모래") || sentence.contains("모레")) {
            cal.add(Calendar.DATE, 2);
            return DATE_FORMAT_DAY.format(cal.getTime());
        } else if (sentence.contains("내일") || sentence.contains("네일")) {
            cal.add(Calendar.DATE, 1);
            return DATE_FORMAT_DAY.format(cal.getTime());
        }

        //        ?요일
        Pattern dayOfWeekPattern = Pattern.compile(".?요일");
        Matcher dayOfWeekPatternMatcher = dayOfWeekPattern.matcher(sentence);
        if (dayOfWeekPatternMatcher.find()) {
            String dayOfWeek = String.valueOf(dayOfWeekPatternMatcher.group().charAt(0));
            Log.d("요일추출", dayOfWeek);
            Log.d("확인 ?요일", dayOfWeekPatternMatcher.group());
            if (sentence.contains("이번")) {
                //
            } else if (sentence.contains("다음")) {
                cal.add(Calendar.DATE, 7);
            }

            if (dayOfWeek.equals("월")) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            } else if (dayOfWeek.equals("화")) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            } else if (dayOfWeek.equals("수")) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            } else if (dayOfWeek.equals("목")) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            } else if (dayOfWeek.equals("금")) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            } else if (dayOfWeek.equals("토")) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            } else if (dayOfWeek.equals("일")) {
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            }
            return DATE_FORMAT_DAY.format(cal.getTime());
        }

//        ?월?일
        Pattern monthDayPattern = Pattern.compile(".?.?.?월.?.?.?일");
        Matcher monthDayPatternMatcher = monthDayPattern.matcher(sentence);
        if (monthDayPatternMatcher.find()) {
            String output = monthDayPatternMatcher.group();
            Log.d("확인 ?월?일", output);
            int indexMonth = output.indexOf("월");
            int indexDay = output.indexOf("일");
            int Month, Day;
            try {
                Month = Integer.parseInt(output.substring(indexMonth - 2, indexMonth));
            } catch (Exception e) {
                Month = Integer.parseInt(output.substring(indexMonth - 1, indexMonth));
            }
            try {
                Day = Integer.parseInt(output.substring(indexDay - 2, indexDay));
            } catch (NumberFormatException e) {
                Day = Integer.parseInt(output.substring(indexDay - 1, indexDay));
            }
            cal.set(Calendar.MONTH, Month - 1);
            cal.set(Calendar.DATE, Day);
            return DATE_FORMAT_DAY.format(cal.getTime());
        }

//      ?일
        Pattern dayPattern = Pattern.compile(".?.?일");
        Matcher dayPatternMatcher = dayPattern.matcher(sentence);
        if (dayPatternMatcher.find()) {
            String output = dayPatternMatcher.group();
            Log.d("확인 ?일", output);
            int indexDay = output.indexOf("일");
            int Day;
            try {
                Day = Integer.parseInt(output.substring(indexDay - 2, indexDay));
            } catch (Exception e) {
                Day = Integer.parseInt(output.substring(indexDay - 1, indexDay));
            }
            cal.set(Calendar.DATE, Day);
            return DATE_FORMAT_DAY.format(cal.getTime());
        }
        return "";
    }

    private String sentenceToTime(String sentence) {
        //.?.?시?.?분
        Pattern hourMinutePattern = Pattern.compile(".?.?시.?.?.?분");
        Matcher hourMinutePatternMatcher = hourMinutePattern.matcher(sentence);
        if (hourMinutePatternMatcher.find()) {
            String output = hourMinutePatternMatcher.group();
            Log.d("확인 ?시 ?분", output);
            int indexHour = output.indexOf("시");
            int indexMinute = output.indexOf("분");
            int hour, minute;
            String hourStr;
            hourStr = output.substring(indexHour - 2, indexHour);

            try {
                hour = Integer.parseInt(output.substring(indexHour - 2, indexHour));
            } catch (Exception e) {
                try {
                    hour = Integer.parseInt(output.substring(indexHour - 1, indexHour));
                } catch (Exception e2) {
                    hour = hourKorToNum(hourStr);
                    if (hour == 0) {
                        return "";
                    }
                }
            }
            if (sentence.contains("오후")) {
                hour += 12;
            }

            try {
                minute = Integer.parseInt(output.substring(indexMinute - 2, indexMinute));
            } catch (Exception e) {
                minute = Integer.parseInt(output.substring(indexMinute - 1, indexMinute));
            }

            if (hour < 10) {
                return "0" + hour + ":" + minute;
            }
            if (minute < 10) {
                return hour + ":" + "0" + minute;
            }
            return hour + ":" + minute;
        }

        //.?.?시
        Pattern hourPattern = Pattern.compile(".?.?시");
        Matcher hourPatternMatcher = hourPattern.matcher(sentence);
        if (hourPatternMatcher.find()) {
            String output = hourPatternMatcher.group();
            Log.d("확인 ?시", output);
            int indexHour = output.indexOf("시");
            int hour;
            String hourStr;
            hourStr = output.substring(indexHour - 2, indexHour);

            try {
                hour = Integer.parseInt(output.substring(indexHour - 2, indexHour));
            } catch (NumberFormatException e) {
                try {
                    hour = Integer.parseInt(output.substring(indexHour - 1, indexHour));
                } catch (Exception e2) {
                    hour = hourKorToNum(hourStr);
                    if (hour == 0) {
                        return "";
                    }
                }
            }

            if (sentence.contains("오후")) {
                hour += 12;
            }
            if (hour < 10) {
                return "0" + hour + ":00";
            }
            return hour + ":00";
        }
        return "";
    }

    private int hourKorToNum(String hourStr) {
        if (hourStr.equals("열한")) {
            return 11;
        } else if (hourStr.equals("열두")) {
            return 12;
        } else if (hourStr.substring(1).equals("한")) {
            return 1;
        } else if (hourStr.substring(1).equals("두")) {
            return 2;
        } else if (hourStr.substring(1).equals("세")) {
            return 3;
        } else if (hourStr.substring(1).equals("네")) {
            return 4;
        } else if (hourStr.equals("다섯")) {
            return 5;
        } else if (hourStr.equals("여섯")) {
            return 6;
        } else if (hourStr.equals("일곱")) {
            return 7;
        } else if (hourStr.equals("여덟")) {
            return 8;
        } else if (hourStr.equals("아홉")) {
            return 9;
        } else if (hourStr.substring(1).equals("열")) {
            return 10;
        }
        return 0;
    }
}

