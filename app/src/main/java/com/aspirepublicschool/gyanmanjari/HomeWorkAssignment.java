package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;

import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.tabs.TabLayout;

public class HomeWorkAssignment extends AppCompatActivity {
    ViewPager viewhomework;
    TabLayout tabhomework;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work_assignment);
        viewhomework=findViewById(R.id.viewhomework);
        tabhomework=findViewById(R.id.tabhomework);
        HomeWorkDetailsAdapter homeWorkDetails=new HomeWorkDetailsAdapter(getSupportFragmentManager(),tabhomework.getTabCount());
        viewhomework.setAdapter(homeWorkDetails);
        viewhomework.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabhomework));
        tabhomework.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewhomework.setCurrentItem(tab.getPosition());
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
