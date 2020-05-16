package com.naver.hackday.android.speechrecognizecalender.src.ui.login.viewModels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.naver.hackday.android.speechrecognizecalender.src.common.models.DefaultFailResponse;
import com.naver.hackday.android.speechrecognizecalender.src.common.models.DefaultResponse;
import com.naver.hackday.android.speechrecognizecalender.src.network.login.LogInService;


public class LogInViewModel extends ViewModel {

    //xml과 연결되는 변수 (Data Binding)
    public ObservableField<String> id = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");

    //view 에서 observe하고있을 변수
    public MutableLiveData<DefaultResponse> defaultResponse = new MutableLiveData<DefaultResponse>();
    public MutableLiveData<DefaultFailResponse> defaultFailResponse = new MutableLiveData<DefaultFailResponse>();

    //setLifeCycle

    public LogInViewModel() {

    }

    public void signInBtnClicked() {
        final LogInService logInService = new LogInService();
        logInService.tryTest(new LogInService.LogInCallback() {
            @Override
            public void testSuccess(DefaultResponse response) {
                defaultResponse.setValue(response);
            }

            @Override
            public void networkFail() {
                defaultFailResponse.setValue(new DefaultFailResponse());
            }
        });
    }
}

