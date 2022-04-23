package com.aspirepublicschool.gyanmanjari.OfflineTestResult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.StrictMode;

import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.tabs.TabLayout;

public class OfflineTestResultActivity extends AppCompatActivity {

    ViewPager result_details_viewpager;
    TabLayout result_details_tablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_test_result);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        result_details_tablayout = findViewById(R.id.result_details_tablayout);
        result_details_viewpager = findViewById(R.id.result_details_viewpager);
        result_details_viewpager.setAdapter(new OfflineResultDetailAdapter(getSupportFragmentManager(), result_details_tablayout.getTabCount()));
        result_details_viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(result_details_tablayout));
        result_details_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                result_details_viewpager.setCurrentItem(tab.getPosition());
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