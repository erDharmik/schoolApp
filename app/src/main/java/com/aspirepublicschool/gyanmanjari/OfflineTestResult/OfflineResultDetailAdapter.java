package com.aspirepublicschool.gyanmanjari.OfflineTestResult;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class OfflineResultDetailAdapter extends FragmentPagerAdapter {
    private int totleTabs;

    public OfflineResultDetailAdapter(FragmentManager fm, int totleTabs) {
        super(fm);
        this.totleTabs = totleTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                ClassTest classTestFragment1 = new ClassTest();
                return classTestFragment1;
                /*OnlineTestResult onlineTestResult = new OnlineTestResult();
                return onlineTestResult;*/
            /*case 1:
             *//* OnlineTestResult onlineTestResult = new OnlineTestResult();
                return onlineTestResult;*//*
                WRTResultFragment wrtResultFragment = new WRTResultFragment();
                return wrtResultFragment;*/
            case 1:
                HalfYearly halfYearlyFragment = new HalfYearly();
                return halfYearlyFragment;
            case 2:
                FinalExam finalExamFragment = new FinalExam();
                return finalExamFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totleTabs;
    }
}
