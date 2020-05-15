package com.naver.hackday.android.speechrecognizecalender.src.ui.event;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.naver.hackday.android.speechrecognizecalender.src.*;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.viewModels.EventViewModel;

public class EventDetailActivity extends BaseActivity {

    private EventViewModel mEventViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initStartView() {
        mEventViewModel = new ViewModelProvider(getViewModelStore(), viewModelFactory).get(EventViewModel.class); //viewmodel을 가져오기
   }

    @Override
    protected void initDataBinding() {
        mEventViewModel.eventCreateResponse.observe(this, eventResource -> {
            //DB 업데이트
        });

        mEventViewModel.eventDeleteResponse.observe(this, s -> {
            //DB 업데이트
        });


    }

    @Override
    protected void initAfterBinding() {

    }


}
