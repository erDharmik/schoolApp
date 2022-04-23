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

import com.aspirepublicschool.gyanmanjari.uniform.CategorieWiseShop.CategoryWiseShop;
import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class SubcategoriesAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<Subcategories> subcategoriesList;

    public SubcategoriesAdapter(Context ctx, ArrayList<Subcategories> subcategoriesList) {
        this.ctx = ctx;
        this.subcategoriesList = subcategoriesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.activity_subcategories_row, null);
        ShopViewHolder container = new ShopViewHolder(view);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Subcategories subcategories = subcategoriesList.get(position);
        final ShopViewHolder container = (ShopViewHolder) holder;
        container.txtsubcat.setText(subcategories.getSub());
        container.imgsub.setImageResource(subcategories.getImg());
        container.relsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx, CategoryWiseShop.class);
                intent.putExtra("cat",subcategories.getSub());
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subcategoriesList.size();
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
