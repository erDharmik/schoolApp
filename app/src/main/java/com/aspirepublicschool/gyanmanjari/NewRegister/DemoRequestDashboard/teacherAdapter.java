package com.aspirepublicschool.gyanmanjari.NewRegister.DemoRequestDashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.NewRegister.PrefFacultyAdapter;
import com.aspirepublicschool.gyanmanjari.NewRegister.PrefFacultyModel;
import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class teacherAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<teacherModel> teacherList;

    public teacherAdapter(Context context, ArrayList<teacherModel> teacherList) {
        this.context = context;
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(context).inflate(R.layout.teacher_demo_row, null);
        return new teacherAdapter.ViewHolder(MyView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final teacherModel teacherModel = teacherList.get(position);
        final teacherAdapter.ViewHolder container = (teacherAdapter.ViewHolder) holder;

        String fullname = teacherModel.getFirstName() + " " + teacherModel.getLastName();

        container.name.setText(fullname);
        container.email.setText(teacherModel.getEmail());
        container.mobile.setText(teacherModel.getMobile());
        container.subject.setText(teacherModel.getSubject());

        if(teacherModel.getStatus().equals("0")){
            container.status.setText("Requested for Demo");
        }else if (teacherModel.getStatus().equals("1")){
            container.status.setText("Request has been Accepted");
        }else if (teacherModel.getStatus().equals("2")) {
            container.status.setText("Request has been Rejected");
        }else if (teacherModel.getStatus().equals("3")){
            container.status.setText("Demo Completed");
        }

    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, email, mobile, subject, status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            mobile = itemView.findViewById(R.id.mobile);
            email = itemView.findViewById(R.id.email);
            subject = itemView.findViewById(R.id.subject);
            status = itemView.findViewById(R.id.status);

        }
    }

}
