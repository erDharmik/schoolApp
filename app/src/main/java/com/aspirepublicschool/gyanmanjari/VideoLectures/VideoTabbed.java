package com.aspirepublicschool.gyanmanjari.VideoLectures;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.tabs.TabLayout;

public class VideoTabbed extends AppCompatActivity {
    TabLayout tabbedvideo;
    ViewPager video_viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_tabbed);
        allocatememory();
        getSupportActionBar().setElevation(0);
        video_viewpager.setAdapter(new VideoAdapter(getSupportFragmentManager(), tabbedvideo.getTabCount()));
        video_viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabbedvideo));
        tabbedvideo.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                video_viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void allocatememory() {
        tabbedvideo =findViewById(R.id.tabbedvideo);
        video_viewpager =findViewById(R.id.video_viewpager);
    }
}
