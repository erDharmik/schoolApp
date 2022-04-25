package com.aspirepublicschool.gyanmanjari;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.holder> {

    notificationModel data[];
    Context context;

    public notificationAdapter(notificationModel[] data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        View view = layout.inflate(R.layout.layout_notification_item ,parent , false);
        return new holder(view);
    }

    @Override


    public void onBindViewHolder(@NonNull final holder holder, @SuppressLint("RecyclerView") final int position) {

        holder.title.setText(data[position].getTitle());
        holder.des.setText(data[position].getDes());
        holder.date.setText(data[position].getDate());

        holder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.expand.getTag().toString().equalsIgnoreCase("collaps")){
                    holder.notificationImg.setVisibility(View.VISIBLE);
                    holder.expand.setTag("expand");
                    holder.expand.setRotation(180);

//                    Toast.makeText(context.getApplicationContext(), model.getLink().toString() , Toast.LENGTH_SHORT).show();

                    Glide.with(holder.title.getContext()).load(data[position].getImgLink()).into(holder.notificationImg);
                    Toast.makeText(context.getApplicationContext(), data[position].getImgLink() , Toast.LENGTH_LONG).show();

                }

                else {
                    holder.notificationImg.setVisibility(View.GONE);
                    holder.expand.setTag("collaps");
                    holder.expand.setRotation(0);
                }
//                Glide.with(holder.dateTextView).load(model.getLink()).into(holder.notiImg);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class holder extends RecyclerView.ViewHolder{


        TextView title , des , date;
        ImageView notificationImg , expand;
        public holder(@NonNull View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.headingTextView);
            des = itemView.findViewById(R.id.descriptionTextView);
            date = itemView.findViewById(R.id.dateTextView);
            notificationImg = itemView.findViewById(R.id.notificationImg);
            expand = itemView.findViewById(R.id.expand);

        }
    }
}
