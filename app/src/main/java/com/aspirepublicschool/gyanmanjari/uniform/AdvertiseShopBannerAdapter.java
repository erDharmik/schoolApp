package com.aspirepublicschool.gyanmanjari.uniform;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.uniform.Shops.AllShopDetails;
import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class AdvertiseShopBannerAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<ShopBanner> shopBannerArrayList;

    public AdvertiseShopBannerAdapter(Context ctx, ArrayList<ShopBanner> shopBannerArrayList) {
        this.ctx = ctx;
        this.shopBannerArrayList = shopBannerArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.activity_advertise_shop_banner_row, null);
        ShopViewHolder container = new ShopViewHolder(view);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ShopBanner shopBanner = shopBannerArrayList.get(position);
        final ShopViewHolder container = (ShopViewHolder) holder;
        container.txtshopaddress.setText(shopBanner.getAddress());
        container.txtshopname.setText(shopBanner.getShopname());
        String IMG_URL = Common.GetBaseImageURL()+"shop/" + shopBanner.getImg();
        Glide.with(ctx).load(IMG_URL).into(container.imgadv);
       // container.imgadv.setImageResource(shopBanner.getImg());
        container.relshopban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, AllShopDetails.class);
                intent.putExtra("s_id", shopBanner.getSs_id());
                intent.putExtra("ss_name", shopBanner.getShopname());
                intent.putExtra("s_img", shopBanner.getImg());
                intent.putExtra("s_cont", shopBanner.getS_cont());
                intent.putExtra("ratings", "" + shopBanner.getRatings());
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return shopBannerArrayList.size();
    }
    public class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView imgadv;
        //RelativeLayout relsub;
        TextView txtshopname,txtshopaddress;
        RelativeLayout relshopban;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            txtshopname = itemView.findViewById(R.id.txtshopname);
            imgadv = itemView.findViewById(R.id.imgadv);
            txtshopaddress = itemView.findViewById(R.id.txtshopaddress);
            relshopban = itemView.findViewById(R.id.relshopban);
        }
    }
}
