package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.tabs.TabLayout;

public class Announcement extends AppCompatActivity {
    ViewPager viewnoticeboard  ;
    TabLayout tabnoticeboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        viewnoticeboard=findViewById(R.id.viewnoticeboard);
        tabnoticeboard=findViewById(R.id.tabnoticeboard);
        viewnoticeboard.setAdapter(new AnnouncemntDetailsAdapter(getSupportFragmentManager(), tabnoticeboard.getTabCount()));
        viewnoticeboard.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabnoticeboard));
        tabnoticeboard.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewnoticeboard.setCurrentItem(tab.getPosition());
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
