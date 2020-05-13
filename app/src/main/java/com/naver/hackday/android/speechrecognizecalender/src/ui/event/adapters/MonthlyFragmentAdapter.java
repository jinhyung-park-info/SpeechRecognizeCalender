package com.naver.hackday.android.speechrecognizecalender.src.ui.event.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.naver.hackday.android.speechrecognizecalender.src.ui.event.fragments.MonthlyCalenderFragment;

import java.util.ArrayList;

public class MonthlyFragmentAdapter extends FragmentStateAdapter {
    private ArrayList<MonthlyCalenderFragment> calenderFragments = new ArrayList<>();

    public MonthlyFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void addFragment(MonthlyCalenderFragment fragment) {
        calenderFragments.add(fragment);
    }

    public void clearFragment() {
        calenderFragments.clear();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return calenderFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return calenderFragments.size();
    }
}