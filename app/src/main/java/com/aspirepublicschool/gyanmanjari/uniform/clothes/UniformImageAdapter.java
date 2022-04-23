package com.aspirepublicschool.gyanmanjari.uniform.clothes;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.uniform.Common;

public class UniformImageAdapter extends PagerAdapter {

    private Context mContext;
    private String[] sliderImageId = new String[]{};
    String ImageUrl ;


    public UniformImageAdapter(Context mContext, String[] sliderImageId) {
        this.mContext = mContext;
        this.sliderImageId = sliderImageId;
    }

    @Override
    public int getCount() {
        return sliderImageId.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ImageView) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if(ProductDetails.cat.equals("Full Uniform")) {
            ImageUrl = Common.GetBaseImageURL() + "/uniform/" + sliderImageId[position];
            Log.d("img",ImageUrl);

        }
        else if(ProductDetails.cat.equals("Shirt"))
        {
            ImageUrl = Common.GetBaseImageURL() + "/shirt/" +  sliderImageId[position];
            Log.d("img",ImageUrl);

        }
        else if(ProductDetails.cat.equals("Pant"))
        {
            ImageUrl = Common.GetBaseImageURL() + "/pant/" +  sliderImageId[position];
            Log.d("img",ImageUrl);

        }
        else if(ProductDetails.cat.equals("Other Items"))
        {
            ImageUrl = Common.GetBaseImageURL() + "/otheritems/" + sliderImageId[position];
            Log.d("img",ImageUrl);

        }

        Glide.with(mContext).load(ImageUrl).into(imageView);
      //  imageView.setImageResource(sliderImageId[position]);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
