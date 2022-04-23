package com.aspirepublicschool.gyanmanjari.NewRegister;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PrefFacultyAdapter extends RecyclerView.Adapter {

    Context ctx;
    ArrayList<PrefFacultyModel> PrefFacultyModel;

    public PrefFacultyAdapter(Context ctx, ArrayList<com.aspirepublicschool.gyanmanjari.NewRegister.PrefFacultyModel> prefFacultyModel) {
        this.ctx = ctx;
        PrefFacultyModel = prefFacultyModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.faculty_selection_row, null);
        return new holder(MyView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        final PrefFacultyModel studentList= PrefFacultyModel.get(position);
        final PrefFacultyAdapter.holder container = (PrefFacultyAdapter.holder) holder;

        String fullname = studentList.getFname() + " " + studentList.getLname();
        String desc = studentList.getMedium() + " " + studentList.getStandard() + " " +  studentList.getSubject();
        container.uname.setText(fullname);
        container.desc.setText(desc);
        container.boardFaculty.setText(studentList.getBoard());

        container.facultyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacultyDetailsDialog();
            }
        });

       /* String img_url= Common.GetBaseImageURL() +"teacher/"+teacherDoubt.getT_img();
        Glide.with(ctx).load(img_url).into(container.imgclassteach);*/
        String img_url= Common.GetBaseImageURL() + "img/faculty/" + studentList.getImg();
        Glide.with(ctx).load(img_url).into(container.facultyDP);

    }


    @Override
    public int getItemCount() {
        return PrefFacultyModel.size();
    }

    class holder extends RecyclerView.ViewHolder{

        CardView facultyDetails;
        TextView uname, desc,boardFaculty;
        CircleImageView facultyDP;
        public holder(@NonNull View itemView) {
            super(itemView);
            facultyDetails = itemView.findViewById(R.id.facultyCard);
            uname = itemView.findViewById(R.id.facultyName);
            facultyDP = itemView.findViewById(R.id.facultyDP);
            desc = itemView.findViewById(R.id.facultyDesc);
            boardFaculty = itemView.findViewById(R.id.boardFaculty);

        }
    }


    private void FacultyDetailsDialog() {
        Toast.makeText(ctx, "Clicked!", Toast.LENGTH_SHORT).show();
    }

}
