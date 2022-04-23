package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.tabs.TabLayout;

public class DailyTimeTable extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_time_table);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(DailyTimeTable.this);
        final String std = mPrefs.getString("std", "none");

        getSupportActionBar().setTitle("Timetable "+"("+std+")");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager =findViewById(R.id.test_timetable_viewpager);
        tabLayout =findViewById(R.id.test_timetable_tablayout);
        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
