package com.aspirepublicschool.gyanmanjari;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.List;

public class TimetableViewAdapter extends RecyclerView.Adapter<TimetableViewAdapter.TimetableViewHolder> {
    private Context mCtx;
    private List<DetailInfo> timeTableModels;

    public TimetableViewAdapter(Context mCtx, List<DetailInfo> timeTableModels) {
        this.mCtx = mCtx;
        this.timeTableModels = timeTableModels;
    }

    @NonNull
    @Override
    public TimetableViewAdapter.TimetableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.timetable_layout, null);
        return new TimetableViewAdapter.TimetableViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TimetableViewAdapter.TimetableViewHolder timetableViewHolder, int i) {
      DetailInfo timeTableModel = timeTableModels.get(i);

        timetableViewHolder.time.setText(timeTableModel.getTime());
        Log.d("TIMEII",timeTableModel.getTime());
        timetableViewHolder.lecture.setText(timeTableModel.getLecture());
        if(timeTableModel.getLecture().equals("Extra Activity")==true || timeTableModel.getLecture().equals("Break")==true || timeTableModel.getLecture().equals("Test AND Exams")==true) {
            timetableViewHolder.t_name.setText("------");
        }
        else
        {
            timetableViewHolder.t_name.setText(timeTableModel.getT_fname()+" "+timeTableModel.getT_lname());
        }

     // timetableViewHolder.day.setText(timeTableModel.getDay());
    }

    @Override
    public int getItemCount() {
        return timeTableModels.size();
    }

    public class TimetableViewHolder extends RecyclerView.ViewHolder {
   TextView time,lecture,t_name;
        public TimetableViewHolder(@NonNull View itemView) {
            super(itemView);
           // day = itemView.findViewById(R.id.text_day);
            time = itemView.findViewById(R.id.text_time);
            lecture = itemView.findViewById(R.id.text_lecture);
            t_name = itemView.findViewById(R.id.t_name);
        }
    }
}
