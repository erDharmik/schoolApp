package com.aspirepublicschool.gyanmanjari.Result;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aspirepublicschool.gyanmanjari.Result.OfflineFragmnet;
import com.aspirepublicschool.gyanmanjari.Result.TestResultFragment;

class OfflineTestAdapter extends FragmentPagerAdapter {
    private int totleTabs;

    public OfflineTestAdapter(@NonNull FragmentManager fm, int totleTabs) {
        super(fm);
        this.totleTabs = totleTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                TestResultFragment homeworkFragment1 = new TestResultFragment();
                return  homeworkFragment1 ;
            case 1:
                OfflineFragmnet submittedHomework = new OfflineFragmnet();
                return submittedHomework;
            /*case 2:
                AssignmentFragment assignmentFragment = new AssignmentFragment();
                return assignmentFragment;
*/
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totleTabs;
    }
}
