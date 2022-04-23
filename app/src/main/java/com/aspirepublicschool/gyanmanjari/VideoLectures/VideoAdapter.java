package com.aspirepublicschool.gyanmanjari.VideoLectures;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class VideoAdapter extends FragmentPagerAdapter {
    private int totleTabs;

    public VideoAdapter(FragmentManager fm, int totleTabs) {
        super(fm);
        this.totleTabs = totleTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                VideoToday mondayFragment = new VideoToday();
                return mondayFragment;
            case 1:
                VideoHistory tuesdayFragment = new VideoHistory();
                return tuesdayFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totleTabs;
    }
}
