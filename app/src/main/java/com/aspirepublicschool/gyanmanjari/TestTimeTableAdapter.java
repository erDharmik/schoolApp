package com.aspirepublicschool.gyanmanjari;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TestTimeTableAdapter extends FragmentPagerAdapter {
    private int totleTabs;

    public TestTimeTableAdapter(FragmentManager fm, int totleTabs) {
        super(fm);
        this.totleTabs = totleTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                ClassTestTimeTableFragment classTestTimeTableFragment = new ClassTestTimeTableFragment();
                return classTestTimeTableFragment;
            case 1:
                HalfYearlyTimeTableFragment halfYearlyTimeTableFragment = new HalfYearlyTimeTableFragment();
                return halfYearlyTimeTableFragment;
            case 2:
                FinalTestTimeTableFragment finalTestTimeTableFragment = new FinalTestTimeTableFragment();
                return finalTestTimeTableFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totleTabs;
    }
}
