package com.aspirepublicschool.gyanmanjari.DoubtSolving;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<TeacherDoubt> teacherDoubts;

    public ChatAdapter(Context ctx, ArrayList<TeacherDoubt> teacherDoubts) {
        this.ctx = ctx;
        this.teacherDoubts = teacherDoubts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.layout_teacher_dobut, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TeacherDoubt teacherDoubt= teacherDoubts.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
       /* String img_url= Common.GetBaseImageURL() +"teacher/"+teacherDoubt.getT_img();
        Glide.with(ctx).load(img_url).into(container.imgclassteach);*/
        container.txtteacher.setText(teacherDoubt.getSubject());
        container.relteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx,ChatActivity.class);
                intent.putExtra("t_id", teacherDoubt.getT_id());
                intent.putExtra("tname", teacherDoubt.getSubject());
                intent.putExtra("mno", teacherDoubt.getNumber());
                ctx.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return teacherDoubts.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        public TextView txtteacher;
        RelativeLayout relteacher;
        CircleImageView imgclassteach;
        public MyWidgetContainer(View itemView) {
            super(itemView);
            txtteacher=itemView.findViewById(R.id.txtteacher);
            //imgclassteach=itemView.findViewById(R.id.imgclassteach);
            relteacher=itemView.findViewById(R.id.relteacher);





        }
    }

}
