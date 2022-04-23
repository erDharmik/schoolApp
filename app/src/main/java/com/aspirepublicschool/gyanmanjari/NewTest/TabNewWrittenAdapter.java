package com.aspirepublicschool.gyanmanjari.NewTest;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabNewWrittenAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public TabNewWrittenAdapter(@NonNull FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return DynamicNewWrittenFragment.addfrag(position);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
