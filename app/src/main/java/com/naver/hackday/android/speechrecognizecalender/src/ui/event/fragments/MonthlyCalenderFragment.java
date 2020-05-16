package com.naver.hackday.android.speechrecognizecalender.src.ui.event.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayoutMediator;
import com.naver.hackday.android.speechrecognizecalender.R;
import com.naver.hackday.android.speechrecognizecalender.databinding.FragmentMonthlyCalenderBinding;
import com.naver.hackday.android.speechrecognizecalender.src.ui.event.viewModels.EventViewModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.naver.hackday.android.speechrecognizecalender.src.ApplicationClass.getApplicationClassContext;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.DATE_FORMAT_DAY;
import static com.naver.hackday.android.speechrecognizecalender.src.common.util.AppConstants.DATE_FORMAT_KR;

public class MonthlyCalenderFragment extends Fragment {

    private FragmentMonthlyCalenderBinding binding;
    private String mMonth;
    private EventViewModel mEventViewModel;
    private ViewModelProvider.AndroidViewModelFactory viewModelFactory;


    public MonthlyCalenderFragment(String mMonth) {
        this.mMonth = mMonth;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_monthly_calender, container, false);

        if (viewModelFactory == null) {
            viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplicationClassContext());
        }
        mEventViewModel = new ViewModelProvider(getViewModelStore(), viewModelFactory).get(EventViewModel.class);

        initView();
        initDataBinding();
        return binding.getRoot();
    }

    private void initView() {
        /* RecyclerView */
        binding.fragmentMonthlyCalenderRv.setAdapter(mEventViewModel.mEventListAdapter);
    }

    private void initDataBinding() {
        Date from, to;
        from = new Date();
        try {
            from = DATE_FORMAT_KR.parse(mMonth + " 01일");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        to = new Date();
        try {
            to = DATE_FORMAT_KR.parse(mMonth + " 31일");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(mMonth == null){

        }

        /* RecyclerView */
        mEventViewModel.getMonthlyEvents(from, to).observe(getViewLifecycleOwner(), eventList -> {
            mEventViewModel.mEventListAdapter.submitList(eventList);
        });

    }

}
