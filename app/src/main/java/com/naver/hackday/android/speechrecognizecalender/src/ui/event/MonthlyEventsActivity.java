package com.naver.hackday.android.speechrecognizecalender.src.ui.event;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.naver.hackday.android.speechrecognizecalender.R;
import com.naver.hackday.android.speechrecognizecalender.src.BaseActivity;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.viewModels.EventViewModel;

public class MonthlyEventsActivity extends BaseActivity {
    private EventViewModel mEventViewModel;

    // 임시 test text - api 통해서 calendar 받아와지는지 확인하기 위함
    private TextView textTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_montly_calander);

        // 임시 test text
        textTest = findViewById(R.id.activity_monthly_calendar_tv_test);
    }

    @Override
    protected void initStartView() {
        mEventViewModel = new ViewModelProvider(getViewModelStore(), viewModelFactory).get(EventViewModel.class);
    }

    @Override
    protected void initDataBinding() {
        /* Observing */

        // calendar api 로 받아온 결과 성공 시 item 개수 반환
        mEventViewModel.calendarResponse.observe(this, response -> {
            showSimpleMessageDialog(Integer.toString(response.getItems().size()));
        });

        // calendar api 로 받아온 결과 network fail 시
        mEventViewModel.calendarFailResponse.observe(this, response -> {
            showSimpleMessageDialog(getString(R.string.network_error));
        });

        // room db에 저장한 결과 임시로 text에 보여줌
        mEventViewModel.getAllEventsFromDB().observe(this, response -> {
            textTest.setText(response.toString());
        });


    }

    @Override
    protected void initAfterBinding() {

    }
}