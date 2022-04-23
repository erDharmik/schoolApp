package com.aspirepublicschool.gyanmanjari.uniform;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.aspirepublicschool.gyanmanjari.R;

public class MainActivity extends AppCompatActivity {
    LinearLayout lnrupper,lnrlower,lnrwinter,lnrfootwear,sliderDotspanel;
    ViewPager viewUniform;
    private int dotscount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniform_main);
        allocatememory();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewUniform.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewUniform.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void allocatememory() {
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        lnrfootwear=findViewById(R.id.lnrfootwear);
        lnrupper=findViewById(R.id.lnrupper);
        lnrlower=findViewById(R.id.lnrlower);
        lnrwinter=findViewById(R.id.lnrwinter);
        viewUniform=findViewById(R.id.viewUniform);
    }
}
