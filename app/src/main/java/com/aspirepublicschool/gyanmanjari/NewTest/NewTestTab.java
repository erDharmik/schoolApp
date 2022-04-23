package com.aspirepublicschool.gyanmanjari.NewTest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.Test.TestActivity;
import com.google.android.material.tabs.TabLayout;

public class NewTestTab extends AppCompatActivity
{
    TabLayout tabLayout;
    ViewPager viewPager;

    String tst_id,sub,hours,min,pos,neg,type,reg,irreg,maxreg;
Toolbar toolbar;
    NewTestTabAdpater adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test_tab);

                    /*intent.putExtra("tst_id", tst_id);
                    intent.putExtra("sub", sub);
                    intent.putExtra("hours", "" + hours);
                    intent.putExtra("min", "" + min);
                    intent.putExtra("pos", pos);
                    intent.putExtra("neg", neg);
                    intent.putExtra("type", t_type);
                    intent.putExtra("reg", reg);
                    intent.putExtra("irreg", irreg);
                    intent.putExtra("maxreg", maxreg);*/
        tst_id = getIntent().getStringExtra("tst_id");
        sub = getIntent().getStringExtra("sub");
        hours = getIntent().getStringExtra("hours");
        min = getIntent().getStringExtra("min");
        pos = getIntent().getStringExtra("pos");
        neg = getIntent().getStringExtra("neg");
        type = getIntent().getStringExtra("type");
        reg = getIntent().getStringExtra("reg");
        irreg = getIntent().getStringExtra("irreg");
        maxreg = getIntent().getStringExtra("regmax");
        Log.d("TAG", "onCreatemaxreg: "+maxreg);

        viewPager = findViewById(R.id.viewnewTest);
        tabLayout = findViewById(R.id.tabnewTest);

        viewPager.setAdapter(new NewTestTabAdpater(getSupportFragmentManager(), tabLayout.getTabCount(), tst_id, sub, hours, min, pos, neg, type, reg, irreg, maxreg));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
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

    @Override
    public void onBackPressed()
    {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to Abort test?..");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
//                        timer.cancel();
                        startActivity(new Intent(NewTestTab.this, ViewNewTestToday.class));
                        finish();
                    }
                });
        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                alertDialogBuilder.setCancelable(true);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh, menu);
        MenuItem menuItem  = menu.findItem(R.id.notification).setEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.notification:
                Intent intent = new Intent(NewTestTab.this, NewTestTab.class);
                intent.putExtra("tst_id", tst_id);
                intent.putExtra("sub", sub);
                intent.putExtra("hours", hours);
                intent.putExtra("min", min);
                intent.putExtra("pos", pos);
                intent.putExtra("neg", neg);
                intent.putExtra("type", type);
                intent.putExtra("type", reg);
                intent.putExtra("type", irreg);
                intent.putExtra("type", maxreg);
                intent.putExtra("type",type);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}