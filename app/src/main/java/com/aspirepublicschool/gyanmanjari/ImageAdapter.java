package com.aspirepublicschool.gyanmanjari;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter {
    private Context ctx;
    private ArrayList<ImageContainersModel> imageContainersModel = new ArrayList<>();
    public ImageAdapter(Context ctx, ArrayList<ImageContainersModel> imageContainersModel) {
        this.ctx = ctx;
        this.imageContainersModel = imageContainersModel;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.image_list,null);
        MyWidgetContainer container = new MyWidgetContainer(v);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {

        final MyWidgetContainer container = (MyWidgetContainer) holder;
        final String ImageUrl = Common.GetBaseImageURL() + "gallery/" + imageContainersModel.get(i).getPhoto();
        Log.d("Imageurl",ImageUrl);
        Glide.with(ctx).load(ImageUrl).into(container.imageView);
      /*  container.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
      container.imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              LayoutInflater inflater = LayoutInflater.from(ctx);
              AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
              View mView = inflater.inflate(R.layout.activity_image_dialog, null);
              ImageView photoView = mView.findViewById(R.id.photo_view);
              Glide.with(ctx).load(ImageUrl).into(photoView);

              //     photoView.setImageResource(Glide.with(mCtx).load(URL_IMG_ANNOUNCEMENT).into(announcementHolder.img));
              mBuilder.setView(mView);
              final AlertDialog mDialog = mBuilder.create();
              ImageView cancelview=mView.findViewById(R.id.imgcancel);
              cancelview.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      mDialog.dismiss();
                  }
              });
              mDialog.show();
          }
      });

    }

    @Override
    public int getItemCount() {
        return imageContainersModel.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public LinearLayout linearLayout;
        public MyWidgetContainer(@NonNull View ProductRow) {
            super(ProductRow);
            imageView = ProductRow.findViewById(R.id.imageView);
            linearLayout = ProductRow.findViewById(R.id.linearlayout);
        }
    }
}
