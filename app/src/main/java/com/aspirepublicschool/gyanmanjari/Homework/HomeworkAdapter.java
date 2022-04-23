package com.aspirepublicschool.gyanmanjari.Homework;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class HomeworkAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<Homework> homeWorkDetailsList;

    public HomeworkAdapter(Context ctx, ArrayList<Homework> homeWorkDetailsList) {
        this.ctx = ctx;
        this.homeWorkDetailsList = homeWorkDetailsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_submit_homework_row, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Homework homeWorkDetails= homeWorkDetailsList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        container.txtsub.setText(homeWorkDetails.getSub());
        container.txthwid.setText(homeWorkDetails.getHw_id());
        container.txtstatus.setText(homeWorkDetails.getRemark());
        if(homeWorkDetails.getStatus().equals("0")) {
            container.txtreview.setText("Submitted");
            container.txtreview.setTextColor(Color.BLACK);
        }
        else if (homeWorkDetails.getStatus().equals("1"))
        {
            container.txtreview.setText("Checked and Completed");
            container.txtreview.setTextColor(Color.GREEN);
        }
        else {
            container.txtreview.setText("Checked and Incompleted");
            container.txtreview.setTextColor(Color.RED);
        }


        container.relassignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return homeWorkDetailsList.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        public TextView txtsub,txthwid,txtreview,txtstatus;
        RelativeLayout relassignment;
        public MyWidgetContainer(View itemView) {
            super(itemView);
            txtsub=itemView.findViewById(R.id.txtsub);
            txthwid=itemView.findViewById(R.id.txthwid);
            txtreview=itemView.findViewById(R.id.txtreview);
            txtstatus=itemView.findViewById(R.id.txtstatus);
            relassignment=itemView.findViewById(R.id.relassignment);




        }
    }
}
