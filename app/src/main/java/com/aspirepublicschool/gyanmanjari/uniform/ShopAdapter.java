package com.aspirepublicschool.gyanmanjari.uniform;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.uniform.Shops.ShopDetails;
import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {
    Context context;
    List<ShopModel> shopModelList = new ArrayList<>();

    public ShopAdapter(Context context, List<ShopModel> shopModelList) {
        this.context = context;
        this.shopModelList = shopModelList;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_shop_name, null);
        ShopViewHolder container = new ShopViewHolder(view);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopViewHolder holder, int position) {
        final ShopModel shopModel = shopModelList.get(position);
        holder.text_shop_name.setText(shopModel.getSs_name());
        holder.text_shop_contact.setText(shopModel.getS_cont());
        holder.txtaddress.setText(shopModel.getAddr());
        holder.txtrating.setText(""+shopModel.getRatings());
        String IMG_URL = "https://www.aspirepublicschool.net/zocarro/image/shop/"+shopModel.getS_img();
        Glide.with(context).load(IMG_URL).into(holder.shop_img);
        holder.relshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopDetails.class);
                 intent.putExtra("s_id",shopModel.getS_id());
                 intent.putExtra("ss_name",shopModel.getSs_name());
                 intent.putExtra("s_img",shopModel.getS_img());
                 intent.putExtra("s_cont",shopModel.getS_cont());
                 intent.putExtra("ratings",""+shopModel.getRatings());
                context.startActivity(intent);
            }
        });
        if(shopModel.getS_aval().equals("0")) {
            holder.relshop.setAlpha(0.3f);
            holder.txtopen.setText("Close");
            holder.txtopen.setTextColor(Color.RED);
            holder.relshop.setClickable(false );

        }
        else {
            holder.relshop.setAlpha(1);
            holder.txtopen.setText("Open");

        }


    }

    @Override
    public int getItemCount() {
        return shopModelList.size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView shop_img;
        RelativeLayout relshop;
        TextView text_shop_name, text_shop_contact,txtaddress,txtrating,txtopen;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            text_shop_name = itemView.findViewById(R.id.txtshop);
            text_shop_contact = itemView.findViewById(R.id.txtcontact);
            txtaddress = itemView.findViewById(R.id.txtaddress);
            txtrating = itemView.findViewById(R.id.txtrating);
            shop_img = itemView.findViewById(R.id.imgshop);
            relshop = itemView.findViewById(R.id.relshop);
            txtopen = itemView.findViewById(R.id.txtopen);
        }
    }
}
