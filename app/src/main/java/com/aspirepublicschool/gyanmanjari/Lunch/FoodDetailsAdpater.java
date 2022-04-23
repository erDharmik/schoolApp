package com.aspirepublicschool.gyanmanjari.Lunch;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FoodDetailsAdpater extends FragmentPagerAdapter {
    private int totleTabs;

    public FoodDetailsAdpater(FragmentManager fm, int totleTabs) {
        super(fm);
        this.totleTabs = totleTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                BreakfastDetails breakfastDetailsFragment1 = new BreakfastDetails();
                return breakfastDetailsFragment1;
            case 1:
                LunchDetails lunchDetailsFragment = new LunchDetails();
                return lunchDetailsFragment;
            case 2:
               DinnerDetails dinnerDetails = new DinnerDetails();
                return dinnerDetails;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totleTabs;
    }
}
