package com.naver.hackday.android.speechrecognizecalender.src.ui.event;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayoutMediator;
import com.naver.hackday.android.speechrecognizecalender.R;
import com.naver.hackday.android.speechrecognizecalender.databinding.ActivityMontlyCalanderBinding;
import com.naver.hackday.android.speechrecognizecalender.src.BaseActivity;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.adapters.MonthlyFragmentAdapter;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.fragments.MonthlyCalenderFragment;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.viewModels.EventViewModel;

import java.util.ArrayList;
import java.util.List;

public class MonthlyEventsActivity extends BaseActivity {

    private EventViewModel mEventViewModel;
    private ActivityMontlyCalanderBinding mBinding;
    private MonthlyFragmentAdapter mMonthlyFragmentAdapter;
    private List<String> mTabTitleArray = new ArrayList<>();

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

        /* ViewPager */
        mMonthlyFragmentAdapter = new MonthlyFragmentAdapter(getSupportFragmentManager(), getLifecycle());
        mBinding.activityMonthlyCalenderVp.setAdapter(mMonthlyFragmentAdapter);

    }

    @Override
    protected void initDataBinding() {
        /* Month 조회 & tabLayout set */
        mEventViewModel.getMonthData().observe(this, months -> {
            mMonthlyFragmentAdapter.clearFragment();
            mTabTitleArray.clear();
            for (int i = 0; i < months.size(); i++) {
                Log.d("월별 통", months.get(i).getMonth() + " " + months.get(i).getCount());
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

    }

}
