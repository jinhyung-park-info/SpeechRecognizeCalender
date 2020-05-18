package com.naver.hackday.android.speechrecognizecalender.src.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayoutMediator;
import com.naver.hackday.android.speechrecognizecalender.R;
import com.naver.hackday.android.speechrecognizecalender.databinding.ActivityMontlyCalanderBinding;
import com.naver.hackday.android.speechrecognizecalender.src.BaseActivity;
import com.naver.hackday.android.speechrecognizecalender.src.common.util.SharedPreferenceManager;
import com.naver.hackday.android.speechrecognizecalender.src.network.clova.ClovaViewModel;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.adapters.MonthlyFragmentAdapter;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.fragments.MonthlyCalenderFragment;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.viewModels.EventViewModel;
import com.naver.hackday.android.speechrecognizecalender.src.ui.login.HomeActivity;
import com.naver.hackday.android.speechrecognizecalender.src.ui.login.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static com.naver.hackday.android.speechrecognizecalender.src.common.util.Util.permissionCheck;

public class MonthlyEventsActivity extends BaseActivity {

    private EventViewModel mEventViewModel;
    private ClovaViewModel mClovaViewModel;
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
        mBinding.setEventViewModel(mEventViewModel);
        mClovaViewModel = new ViewModelProvider(getViewModelStore(), viewModelFactory).get(ClovaViewModel.class);
        mBinding.setClovaViewModel(mClovaViewModel);

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
        showToast("정상적으로 로그아웃되었습니다!");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
