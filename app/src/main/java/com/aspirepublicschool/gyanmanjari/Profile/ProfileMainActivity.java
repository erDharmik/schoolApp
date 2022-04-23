package com.aspirepublicschool.gyanmanjari.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.tabs.TabLayout;

public class ProfileMainActivity extends AppCompatActivity {

    ViewPager viewhomework;
    TabLayout tabhomework;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main2);

//        Intent intent = getIntent();
//        String address = intent.getStringExtra("address");
//        String lat = intent.getStringExtra("lat");
//        String longi = intent.getStringExtra("long");

//        Toast.makeText(getApplicationContext(), address + lat + longi, Toast.LENGTH_SHORT).show();

        viewhomework=findViewById(R.id.viewhomework);
        tabhomework=findViewById(R.id.tabhomework);
        ProfileAdapter profileAdapter=new ProfileAdapter(getSupportFragmentManager(),tabhomework.getTabCount());
        viewhomework.setAdapter(profileAdapter);
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