package com.aspirepublicschool.gyanmanjari;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class HomeWorkDetailsAdapter extends FragmentPagerAdapter {
    private int totleTabs;

    public HomeWorkDetailsAdapter(@NonNull FragmentManager fm, int totleTabs) {
        super(fm);
        this.totleTabs = totleTabs;
    }

/*  public HomeWorkDetailsAdapter(FragmentManager fm, int totleTabs) {
        super(fm);
        this.totleTabs = totleTabs;
    }
*/

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                HomeworkFragment homeworkFragment1 = new HomeworkFragment();
                return  homeworkFragment1 ;
            case 1:
                AssignmentFragment assignmentFragment = new AssignmentFragment();
                return assignmentFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totleTabs;
    }
}
