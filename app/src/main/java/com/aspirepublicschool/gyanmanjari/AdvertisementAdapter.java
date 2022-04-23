package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class AdvertisementAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<Advertisement> advertisementlist=new ArrayList<>();

    public AdvertisementAdapter(Context ctx, ArrayList<Advertisement> advertisementlist) {
        this.ctx = ctx;
        this.advertisementlist = advertisementlist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_adv_row, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Advertisement advertisement = advertisementlist.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        container.imgadv.setImageResource(advertisement.getImg());
    }

    @Override
    public int getItemCount() {
        return advertisementlist.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        ImageView imgadv;
        RelativeLayout relacdemic;
        public MyWidgetContainer(View itemView) {
            super(itemView);
            imgadv=itemView.findViewById(R.id.imgadv);







        }
    }
}
