package com.aspirepublicschool.gyanmanjari;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TimetableAdapter extends FragmentPagerAdapter {
    private int totleTabs;

    public TimetableAdapter(FragmentManager fm, int totleTabs) {
        super(fm);
        this.totleTabs = totleTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                MondayFragment mondayFragment = new MondayFragment();
                return mondayFragment;
            case 1:
                TuesdayFragment tuesdayFragment = new TuesdayFragment();
                return tuesdayFragment;
            case 2:
                WednesdayFragment wednesdayFragment = new WednesdayFragment();
                return wednesdayFragment;
            case 3:
                ThursdayFragment thursdayFragment = new ThursdayFragment();
                return thursdayFragment;
            case 4:
                FridayFragment fridayFragment = new FridayFragment();
                return fridayFragment;
            case 5:
                SaturdayFragment saturdayFragment = new SaturdayFragment();
                return saturdayFragment;
          /*  case 6:
                SundayFragment sundayFragment = new SundayFragment();
                return sundayFragment;*/

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totleTabs;
    }
}
