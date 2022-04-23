package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.List;

public class FinalExamAdapter extends RecyclerView.Adapter<FinalExamAdapter.FinalExamViewHolder> {
    private Context mCtx;
    private List<FinalExamModel> finalExamModels;

    public FinalExamAdapter(Context mCtx, List<FinalExamModel> finalExamModels) {
        this.mCtx = mCtx;
        this.finalExamModels = finalExamModels;
    }

    @NonNull
    @Override
    public FinalExamAdapter.FinalExamViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.activity_yearly_row, null);
        return new FinalExamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinalExamAdapter.FinalExamViewHolder finalExamViewHolder, int i) {
      FinalExamModel finalExamModel = finalExamModels.get(i);
      finalExamViewHolder.subject.setText(finalExamModel.getSubject());
      finalExamViewHolder.obtain_marks.setText(finalExamModel.getObtain_mark()+"/"+finalExamModel.getTotal_mark());
    }

    @Override
    public int getItemCount() {
        return finalExamModels.size();
    }

    public class FinalExamViewHolder extends RecyclerView.ViewHolder {
        TextView subject,obtain_marks;
        public FinalExamViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subject);
            obtain_marks = itemView.findViewById(R.id.obtain_marks);
        }
    }
}