package com.naver.hackday.android.speechrecognizecalender.src.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayoutMediator;
import com.naver.hackday.android.speechrecognizecalender.R;
import com.naver.hackday.android.speechrecognizecalender.databinding.ActivityMontlyCalanderBinding;
import com.naver.hackday.android.speechrecognizecalender.src.BaseActivity;
import com.naver.hackday.android.speechrecognizecalender.src.network.clova.ClovaViewModel;
import com.naver.hackday.android.speechrecognizecalender.src.network.clova.TextExtractionViewModel;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.adapters.MonthlyFragmentAdapter;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.fragments.MonthlyCalenderFragment;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.viewModels.EventViewModel;
import com.naver.hackday.android.speechrecognizecalender.src.ui.login.MainActivity;


import java.util.ArrayList;
import java.util.List;

import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.DAY_TO_DAY;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.ONE_DAY;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.UNKNOWN;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.Util.permissionCheck;

public class MonthlyEventsActivity extends BaseActivity {

    private EventViewModel mEventViewModel;
    private ClovaViewModel mClovaViewModel;
    private TextExtractionViewModel mTextExtractionViewModel;

    private ActivityMontlyCalanderBinding mBinding;
    private MonthlyFragmentAdapter mMonthlyFragmentAdapter;
    private List<String> mTabTitleArray = new ArrayList<>();
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initStartView() {
        /* ViewModel & Binding */
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_montly_calander);
        mEventViewModel = new ViewModelProvider(getViewModelStore(), viewModelFactory).get(EventViewModel.class);
        mEventViewModel.initViewModel();
        mBinding.setEventViewModel(mEventViewModel);
        mClovaViewModel = new ViewModelProvider(getViewModelStore(), viewModelFactory).get(ClovaViewModel.class);
        mBinding.setClovaViewModel(mClovaViewModel);
        mTextExtractionViewModel = new ViewModelProvider(getViewModelStore(), viewModelFactory).get(TextExtractionViewModel.class);
        mBinding.setTextExtractionViewModel(mTextExtractionViewModel);

        /* ViewPager */
        mMonthlyFragmentAdapter = new MonthlyFragmentAdapter(getSupportFragmentManager(), getLifecycle());
        mBinding.activityMonthlyCalenderVp.setAdapter(mMonthlyFragmentAdapter);
    }

    @Override
    protected void initDataBinding() {

        // calendar api 로 받아온 결과 network fail 시
        mEventViewModel.calendarFailResponse.observe(this, response -> {
            showSimpleMessageDialog(getString(R.string.network_error));
        });

        /* Month 조회 & tabLayout set */
        mEventViewModel.getMonthData().observe(this, months -> {
            mMonthlyFragmentAdapter.clearFragment();
            mTabTitleArray.clear();
            for (int i = 0; i < months.size(); i++) {
                mMonthlyFragmentAdapter.addFragment(new MonthlyCalenderFragment((months.get(i).getMonth())));
                mTabTitleArray.add(months.get(i).getMonth());
            }
            mMonthlyFragmentAdapter.notifyDataSetChanged();

            /* TabLayout */
            new TabLayoutMediator(mBinding.activityMonthlyCalenderTabLayout, mBinding.activityMonthlyCalenderVp, true,
                    (tab, position) -> tab.setText(mTabTitleArray.get(position))).attach();

        });

        mClovaViewModel.mRecognizedString.observe(this, response -> {
            mTextExtractionViewModel.mSentence.setValue(response);
            mTextExtractionViewModel.doExtraction();
        });

        mTextExtractionViewModel.mResult.observe(this, response -> {
            Log.d("결과 mEndDate", response.toString());
            if(response.getMode() == ONE_DAY){
                showSimpleMessageDialog(response.getDate());
            }
            else if(response.getMode() == DAY_TO_DAY){
                showSimpleMessageDialog("start = "+response.getStartDate() + "\nend = " + response.getEndDate());
            }
            else if(response.getMode() == UNKNOWN ){
                showSimpleMessageDialog("인식된 날짜정보가 없습니다");
            }
            else{
                showSimpleMessageDialog("else" + response.getMode());
            }
        });
    }

    @Override
    protected void initAfterBinding() {
        permissionCheck(this);
        buildClient();
    }

    private void buildClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void signOutBtnClicked(View v) {
        mGoogleSignInClient.signOut();
        updateUIwhenSignOut();
    }

    private void updateUIwhenSignOut() {
        mEventViewModel.deleteAllEvents();
        showToast("정상적으로 로그아웃되었습니다!");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
