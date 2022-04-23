package com.aspirepublicschool.gyanmanjari.Test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.tabs.TabLayout;

public class TestTabbedActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tabbed);
        frameLayout=findViewById(R.id.frameLayout);
        tabLayout=findViewById(R.id.tabs);
        for (int k = 1; k <10; k++) {
            tabLayout.addTab(tabLayout.newTab().setText("" + k));
        }

      /*  PlansPagerAdapter adapter = new PlansPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        frameLayout.setAdapter(adapter);
        frameLayout.setOffscreenPageLimit(1);
        frameLayout.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));*/
    }
}
