package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;
import java.util.List;

public class Ask_QueryAdapter extends RecyclerView.Adapter<Ask_QueryAdapter.Ask_QueryViewHolder> {
    private Context mCtx;
    private List<Ask_QueryModel> ask_queryModels = new ArrayList<>();

    public Ask_QueryAdapter(Context mCtx, List<Ask_QueryModel> ask_queryModels) {
        this.mCtx = mCtx;
        this.ask_queryModels = ask_queryModels;
    }

    @NonNull
    @Override
    public Ask_QueryAdapter.Ask_QueryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.message_list,null);
        return new Ask_QueryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Ask_QueryAdapter.Ask_QueryViewHolder ask_queryViewHolder, final int i) {
       final Ask_QueryModel ask_queryModel = ask_queryModels.get(i);
       ask_queryViewHolder.sender.setText(ask_queryModel.getT_fname()+" "+ask_queryModel.getT_lname());
       ask_queryViewHolder.title.setText(Html.fromHtml(ask_queryModel.getTitle()));
      /* ask_queryViewHolder.message.setText(ask_queryModel.getMessage());
       ask_queryViewHolder.time.setText(ask_queryModel.getTime());*/
       ask_queryViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(mCtx,ViewMessage.class);
               intent.putExtra("sender",ask_queryModel.getT_fname()+" "+ask_queryModel.getT_lname());
               intent.putExtra("title",ask_queryModel.getTitle());
               intent.putExtra("message",ask_queryModel.getMessage());
               intent.putExtra("time",ask_queryModel.getTime());
               mCtx.startActivity(intent);
           }
       });

    }

    @Override
    public int getItemCount() {
        return ask_queryModels.size();
    }

    public class Ask_QueryViewHolder extends RecyclerView.ViewHolder {
        public TextView sender,title,message,time;
        LinearLayout relativeLayout;
        public Ask_QueryViewHolder(@NonNull View itemView) {
            super(itemView);
            sender = itemView.findViewById(R.id.sender);
            title = itemView.findViewById(R.id.title);
            /*message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);*/
            relativeLayout = itemView.findViewById(R.id.inbox_relative);
        }
    }
}
