package com.aspirepublicschool.gyanmanjari.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.tabs.TabLayout;

public class ProfileMainActivity extends AppCompatActivity {

    ViewPager viewhomework;
    TabLayout tabhomework;
    Context ctx;
    String stu_id, number, sc_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main2);

//        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
//        stu_id = mPrefs.getString("stu_id", "none");
//        sc_id = mPrefs.getString("sc_id", "none");
//        number = mPrefs.getString("number", "none");

        number = getIntent().getStringExtra("number");
        stu_id = getIntent().getStringExtra("stu_id");
        sc_id = getIntent().getStringExtra("sc_id");

//        Intent intent = getIntent();
//        String address = intent.getStringExtra("address");
//        String lat = intent.getStringExtra("lat");
//        String longi = intent.getStringExtra("long");

//        Toast.makeText(getApplicationContext(), address + lat + longi, Toast.LENGTH_SHORT).show();

        viewhomework=findViewById(R.id.viewhomework);
        tabhomework=findViewById(R.id.tabhomework);
        ProfileAdapter profileAdapter=new ProfileAdapter(getSupportFragmentManager(),tabhomework.getTabCount(), stu_id, sc_id, number);
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