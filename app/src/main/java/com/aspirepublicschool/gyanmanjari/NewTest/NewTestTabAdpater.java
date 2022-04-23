package com.aspirepublicschool.gyanmanjari.NewTest;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class NewTestTabAdpater extends FragmentPagerAdapter
{
    private int totleTabs;
    String tst_id;
    String sub;
    String hours;
    String min;
    String pos;
    String neg;
    String type;
    String reg;
    String irreg;
    String maxreg;

    public NewTestTabAdpater(@NonNull FragmentManager fm, int totleTabs, String tst_id, String sub, String hours, String min, String pos, String neg, String type, String reg, String irreg, String maxreg ) {
        super(fm);
        this.totleTabs = totleTabs;
        this.tst_id= tst_id;
        this.sub = sub;
        this.hours = hours;
        this.min = min;
        this.pos = pos;
        this.neg = neg;
        this.type = type;
        this.reg = reg;
        this.irreg = irreg;
        this.maxreg = maxreg;
    }


    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                MCQTestFragment mcqTest = new MCQTestFragment(tst_id, sub, hours, min, pos, neg, type, reg, irreg, maxreg);
                return mcqTest;
            case 1:
                WrittenTestFragment writtenTest = new WrittenTestFragment(tst_id, sub, hours, min, pos, neg, type, reg, irreg, maxreg);
                return writtenTest;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totleTabs;
    }
}