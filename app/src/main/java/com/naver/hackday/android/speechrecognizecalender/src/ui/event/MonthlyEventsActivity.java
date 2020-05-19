package com.naver.hackday.android.speechrecognizecalender.src.ui.event;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.tabs.TabLayoutMediator;
import com.naver.hackday.android.speechrecognizecalender.R;
import com.naver.hackday.android.speechrecognizecalender.databinding.ActivityMontlyCalanderBinding;
import com.naver.hackday.android.speechrecognizecalender.src.BaseActivity;
import com.naver.hackday.android.speechrecognizecalender.src.network.clova.ClovaViewModel;
import com.naver.hackday.android.speechrecognizecalender.src.network.clova.TextExtractionViewModel;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.adapters.MonthlyFragmentAdapter;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.fragments.MonthlyCalenderFragment;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.viewModels.EventViewModel;
import com.naver.hackday.android.speechrecognizecalender.src.ui.login.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import static com.naver.hackday.android.speechrecognizecalender.src.common.util.Util.permissionCheck;

public class MonthlyEventsActivity extends BaseActivity {

    private EventViewModel mEventViewModel;
    private ClovaViewModel mClovaViewModel;
    private TextExtractionViewModel mTextExtractionViewModel;

    private ActivityMontlyCalanderBinding mBinding;
    private MonthlyFragmentAdapter mMonthlyFragmentAdapter;
    private List<String> mTabTitleArray = new ArrayList<>();
    private Button buttonSignOut;
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
            mTextExtractionViewModel.sentenceToDate();
        });

        mTextExtractionViewModel.mDate.observe(this, response -> {
//            mTextExtractionViewModel.mMode
            Log.d("결과", response);
        });

        mEventViewModel.eventDetail.observe(this, response -> {
//            mTextExtractionViewModel.mMode
            Log.d("calenderId", response);
        });
    }

    @Override
    protected void initAfterBinding() {
        permissionCheck(this);
        setSignOutButton();
    }

    private void setSignOutButton() {
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        buttonSignOut = findViewById(R.id.activity_home_bt_sign_out);
//        buttonSignOut.setOnClickListener(v -> {
//            if (v.getId() == R.id.activity_home_bt_sign_out) {
//                signOut();
//            }
//        });
    }

    private void signOut() {
//        mGoogleSignInClient.signOut()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(MonthlyEventsActivity.this, "정상적으로 로그아웃되었습니다", Toast.LENGTH_LONG).show();
//                        finish();
//                    }
//                });
    }

}
