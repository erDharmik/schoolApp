package com.aspirepublicschool.gyanmanjari;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ResultDetailAdapter extends FragmentPagerAdapter {
    private int totleTabs;

    public ResultDetailAdapter(FragmentManager fm, int totleTabs) {
        super(fm);
        this.totleTabs = totleTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                ClassTest classTestFragment1 = new ClassTest();
                return classTestFragment1;
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
