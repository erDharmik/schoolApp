package com.aspirepublicschool.gyanmanjari.uniform;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.uniform.clothes.ProductDetails;
import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class AdvertiseAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<Advertise> advertiseArrayList;
    String URL;

    public AdvertiseAdapter(Context ctx, ArrayList<Advertise> advertiseArrayList) {
        this.ctx = ctx;
        this.advertiseArrayList = advertiseArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.activity_advertise_row, null);
        ShopViewHolder container = new ShopViewHolder(view);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Advertise advertise = advertiseArrayList.get(position);
        final ShopViewHolder container = (ShopViewHolder) holder;
        container.txtsubcat.setText(advertise.getLabel());
        if(advertise.getCat().equals("Full Uniform")) {
            URL = Common.GetBaseImageURL() + "/uniform/" + advertise.getImg();
            Log.d("img",URL);

        }
        else if(advertise.getCat().equals("Shirt"))
        {
            URL = Common.GetBaseImageURL() + "/shirt/" + advertise.getImg();
            Log.d("img",URL);

        }
        else if(advertise.getCat().equals("Pant"))
        {
            URL = Common.GetBaseImageURL() + "/pant/" + advertise.getImg();
            Log.d("img",URL);

        }
        else if(advertise.getCat().equals("Other Items"))
        {
            URL = Common.GetBaseImageURL() + "/otheritems/" + advertise.getImg();
            Log.d("img",URL);

        }
        Glide.with(ctx).load(URL).into(container.imgsub);
        container.relsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx, ProductDetails.class);
                intent.putExtra("sin",advertise.getSin());
                intent.putExtra("s_id",advertise.getS_id());
                intent.putExtra("p_code",advertise.getP_code());
                intent.putExtra("cat",advertise.getCat());
                ctx.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return advertiseArrayList.size();
    }
    public class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView imgsub;
        RelativeLayout relsub;
        TextView txtsubcat;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            txtsubcat = itemView.findViewById(R.id.txtsubcat);
            imgsub = itemView.findViewById(R.id.imgsub);
            relsub = itemView.findViewById(R.id.relsub);
        }
    }
}
