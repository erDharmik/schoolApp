package com.aspirepublicschool.gyanmanjari;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class TabAdapter extends FragmentPagerAdapter {
    private int totleTabs;


    public TabAdapter(@NonNull FragmentManager fm,int totleTabs) {
        super(fm);
        this.totleTabs = totleTabs;
    }



    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                ClassTimeTable classTimeTable = new ClassTimeTable();
                return  classTimeTable ;
            case 1:
                TestTimeTable testTimeTable = new TestTimeTable();
                return testTimeTable;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totleTabs;
    }
}
