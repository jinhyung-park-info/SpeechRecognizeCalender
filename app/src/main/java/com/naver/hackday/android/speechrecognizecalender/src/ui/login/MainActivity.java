package com.naver.hackday.android.speechrecognizecalender.src.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.naver.hackday.android.speechrecognizecalender.R;
import com.naver.hackday.android.speechrecognizecalender.databinding.ActivityMainBinding;
import com.naver.hackday.android.speechrecognizecalender.src.BaseActivity;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.MonthlyEventsActivity;
import com.naver.hackday.android.speechrecognizecalender.src.ui.login.viewModels.LogInViewModel;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mBinding;
    private LogInViewModel mLogInViewModel;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mBinding.setLifecycleOwner();
        //
    }

    @Override
    protected void initStartView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mLogInViewModel = new ViewModelProvider(getViewModelStore(), viewModelFactory).get(LogInViewModel.class);
        mBinding.setViewModel(mLogInViewModel);
        btnNext = findViewById(R.id.activity_main_btn_next);
    }

    @Override
    protected void initDataBinding() {
        /* Observing */
        mLogInViewModel.defaultResponse.observe(this, response -> {
            showSimpleMessageDialog(response.getMessage());
        });

        mLogInViewModel.defaultFailResponse.observe(this, response -> {
            showSimpleMessageDialog(getString(R.string.network_error));
        });
    }

    @Override
    protected void initAfterBinding() {
        // 임시
        btnNext.setOnClickListener(view -> {
            Intent intent = new Intent(this, MonthlyEventsActivity.class);
            startActivity(intent);
        });

    }
}
