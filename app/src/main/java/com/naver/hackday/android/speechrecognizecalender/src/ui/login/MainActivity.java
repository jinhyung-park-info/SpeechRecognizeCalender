package com.naver.hackday.android.speechrecognizecalender.src.ui.login;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.naver.hackday.android.speechrecognizecalender.R;
import com.naver.hackday.android.speechrecognizecalender.databinding.ActivityMainBinding;
import com.naver.hackday.android.speechrecognizecalender.src.BaseActivity;
import com.naver.hackday.android.speechrecognizecalender.src.ui.login.viewModels.LogInViewModel;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mBinding;
    private LogInViewModel mLogInViewModel;

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

    }
}
