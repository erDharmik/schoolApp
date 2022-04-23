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
import java.util.List;

public class TestTimeTableViewAdapter extends RecyclerView.Adapter<TestTimeTableViewAdapter.TimeTableViewHolder> {
    Context context;
    List<TestTimeTableModel> testTimeTableModelList = new ArrayList<>();

    public TestTimeTableViewAdapter(Context context, List<TestTimeTableModel> testTimeTableModelList) {
        this.context = context;
        this.testTimeTableModelList = testTimeTableModelList;
    }


    @NonNull
    @Override
    public TimeTableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View MyView = LayoutInflater.from(context).inflate(R.layout.test_timetable_row, null);
         TimeTableViewHolder timeTableViewHolder = new TimeTableViewHolder(MyView);
         return  timeTableViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestTimeTableViewAdapter.TimeTableViewHolder timeTableViewHolder, int i) {
      TestTimeTableModel testTimeTableModel = testTimeTableModelList.get(i);
       timeTableViewHolder.test_type.setText(testTimeTableModel.getTest_type());
       timeTableViewHolder.test_subject.setText(testTimeTableModel.getTest_subject());
      // timeTableViewHolder.test_mark.setText(testTimeTableModel.getTest_total());
       timeTableViewHolder.test_date.setText(testTimeTableModel.getTest_date());
       timeTableViewHolder.test_time.setText(testTimeTableModel.getTest_time());
      // timeTableViewHolder.test_t_name.setText(testTimeTableModel.getT_fname()+" "+testTimeTableModel.getT_lname());
    }

    @Override
    public int getItemCount() {
        return testTimeTableModelList.size();
    }

    public class TimeTableViewHolder extends RecyclerView.ViewHolder {
        TextView test_type,test_subject,test_mark,test_date,test_time,test_t_name;

        public TimeTableViewHolder(@NonNull View itemView) {
            super(itemView);
            test_type = itemView.findViewById(R.id.test_type);
            test_subject = itemView.findViewById(R.id.test_subject);
         //   test_mark = itemView.findViewById(R.id.test_mark);
            test_date = itemView.findViewById(R.id.test_date);
            test_time = itemView.findViewById(R.id.test_time);
         //   test_t_name = itemView.findViewById(R.id.test_t_name);
        }
    }
}
