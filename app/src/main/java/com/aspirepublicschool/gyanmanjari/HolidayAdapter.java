package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class HolidayAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<Holiday> holidayList;

    public HolidayAdapter(Context ctx, ArrayList<Holiday> holidayList) {
        this.ctx = ctx;
        this.holidayList = holidayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_holiday_row, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Holiday holiday= holidayList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        container.txtevent.setText(holiday.getEvents());
        container.txteventdate.setText(holiday.getDate());

    }

    @Override
    public int getItemCount() {
        return holidayList.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        public TextView txtevent,txteventdate;

        public MyWidgetContainer(View itemView) {
            super(itemView);
            txtevent=itemView.findViewById(R.id.txtevents);
            txteventdate=itemView.findViewById(R.id.txteventdate);





        }
    }
}
