package com.aspirepublicschool.gyanmanjari.OfflineTestResult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

class OfflineResultAdapter extends RecyclerView.Adapter<OfflineResultAdapter.ResultViewHolder>{
    private Context mCtx;
    private ArrayList<OfflineResultModel> resultModelList;

    public OfflineResultAdapter(Context mCtx, ArrayList<OfflineResultModel> resultModelList) {
        this.mCtx = mCtx;
        this.resultModelList = resultModelList;
    }
    @NonNull
    @Override
    public OfflineResultAdapter.ResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.class_result_list, null);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineResultAdapter.ResultViewHolder holder, int position) {
        OfflineResultModel result = resultModelList.get(position);
        holder.type.setText(result.getTest_type());
        holder.subject.setText(result.getSubject());
        if (result.getObtain_marks().equals("AB")){

            holder.total.setText("0" + "/" + result.getTotal_marks());
            holder.txtab.setVisibility(View.VISIBLE);

        }else {
            holder.total.setText(result.getObtain_marks() + "/" + result.getTotal_marks());
        }
        holder.test_date.setText(result.getTest_date());
    }

    @Override
    public int getItemCount() {
        return resultModelList.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView type,total,test_date,subject,txtab;
        RelativeLayout relresult;
        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subject);
            type = itemView.findViewById(R.id.type);
            total = itemView.findViewById(R.id.total);
            test_date = itemView.findViewById(R.id.ex_date);
            relresult=itemView.findViewById(R.id.relresult);
            txtab=itemView.findViewById(R.id.txtab);
        }
    }
}
