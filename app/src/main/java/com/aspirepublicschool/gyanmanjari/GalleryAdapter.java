package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter {
    private Context ctx;
    private ArrayList<GalleryModel> galleryModel = new ArrayList<>();

    public GalleryAdapter(Context ctx, ArrayList<GalleryModel> galleryModel) {
        this.ctx = ctx;
        this.galleryModel = galleryModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.gallery_list,null);
        WidgetContainer container = new WidgetContainer(v);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final WidgetContainer container = (WidgetContainer) holder;
        container.title.setText(galleryModel.get(position).getTitle());
        String ImageUrl = Common.GetBaseImageURL() + "gallery/" + galleryModel.get(position).getPhoto();
        Log.v("VVVVV",ImageUrl);
        container.txtedate.setText(galleryModel.get(position).getDate());
        Glide.with(ctx).load(ImageUrl).into(container.imageView);
        container.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent SwitchIntent = new Intent(ctx,ImageContainers.class);
                SwitchIntent.putExtra("categoryid",galleryModel.get(position).getId());
                Log.d("categoryid",galleryModel.get(position).getId());
                ctx.startActivity(SwitchIntent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return galleryModel.size();
    }
    class WidgetContainer extends RecyclerView.ViewHolder
    {
        public TextView title,txtedate;
        public ImageView imageView;
        public LinearLayout linearLayout;
        public WidgetContainer(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.lblcategorytitle);
            txtedate = itemView.findViewById(R.id.txtedate);
            imageView = itemView.findViewById(R.id.imgcategory);
            linearLayout = itemView.findViewById(R.id.linearlayout);
        }
    }
}
