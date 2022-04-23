package com.aspirepublicschool.gyanmanjari;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class AnnouncemntDetailsAdapter extends FragmentPagerAdapter {
    private int totleTabs;

    public AnnouncemntDetailsAdapter(FragmentManager fm, int totleTabs) {
        super(fm);
        this.totleTabs = totleTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {

            case 0:
                NoticeboardFragment noticeboardFragment = new NoticeboardFragment();
                return noticeboardFragment;
            case 1:
                PublicEvent publicEvent = new PublicEvent();
                return  publicEvent ;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totleTabs;
    }

}
