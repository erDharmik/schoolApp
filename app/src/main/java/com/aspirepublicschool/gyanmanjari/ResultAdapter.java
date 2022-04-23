package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.List;

class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder>{
    private Context mCtx;
    private List<ResultModel> resultModelList;

    public ResultAdapter(Context mCtx, List<ResultModel> resultModelList) {
        this.mCtx = mCtx;
        this.resultModelList = resultModelList;
    }
    @NonNull
    @Override
    public ResultAdapter.ResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.result_list, null);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapter.ResultViewHolder holder, int position) {
        ResultModel result = resultModelList.get(position);
        holder.type.setText(result.getTest_type());
        holder.subject.setText(result.getSubject());
        holder.total.setText(result.getObtain_marks()+"/"+result.getTotal_marks());
        holder.test_date.setText(result.getTest_date());

    }

    @Override
    public int getItemCount() {
        return resultModelList.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView type,total,test_date,subject;
        RelativeLayout relresult;
        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subject);
            type = itemView.findViewById(R.id.type);
            total = itemView.findViewById(R.id.total);
            test_date = itemView.findViewById(R.id.ex_date);
            relresult=itemView.findViewById(R.id.relresult);
        }
    }
}
