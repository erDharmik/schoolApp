package com.aspirepublicschool.gyanmanjari.DoubtSolving;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

public class NewChatAdapter extends RecyclerView.Adapter{
    Context ctx;
    ArrayList<newchatmodel> newchatmodel;
    String id, name , number;

    public NewChatAdapter(Context ctx, ArrayList<newchatmodel> newchatmodel) {
        this.ctx = ctx;
        this.newchatmodel = newchatmodel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.new_chat_student_row, null);
        return new holder(MyView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final newchatmodel studentList= newchatmodel.get(position);
        final NewChatAdapter.holder container = (NewChatAdapter.holder) holder;

        String fullname = studentList.getSt_fname() + " " + studentList.getSt_sname();
        container.stuName.setText(fullname);


        ((holder) holder).cardStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), "Clicked!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ctx, ChatActivity.class);

                intent.putExtra("t_id",id);
                intent.putExtra("tname",name);
                intent.putExtra("mno",number);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);

            }
        });

    }



    @Override
    public int getItemCount() {
        return newchatmodel.size();
    }


    public class holder extends RecyclerView.ViewHolder {
        ImageView StudentDP;
        TextView stuName;
        CardView cardStudent;
        public holder(@NonNull View itemView) {
            super(itemView);
            StudentDP = itemView.findViewById(R.id.StudentDP);
            stuName = itemView.findViewById(R.id.StuName);
            cardStudent = itemView.findViewById(R.id.cardStudent);
        }
    }
    private void startchat() {
        Toast.makeText(ctx, id + " "+name+ " "+ number, Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(ctx, ChatActivity.class);
        intent.putExtra("t_id", id);
        intent.putExtra("tname", name);
        intent.putExtra("mno", number);
        ctx.startActivity(intent);
    }

}
