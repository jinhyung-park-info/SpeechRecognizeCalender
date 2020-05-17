package com.naver.hackday.android.speechrecognizecalender.src.ui.event.viewModels;

import java.util.Date;

import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.DATE_FORMAT_KR;

public class EventItemConverter {

    public static String dateToString(Date date) {
        return DATE_FORMAT_KR.format(date);
    }
}
