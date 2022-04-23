package com.aspirepublicschool.gyanmanjari.NewFeedback;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.NewRegister.PrefFacultyAdapter;
import com.aspirepublicschool.gyanmanjari.NewRegister.PrefFacultyModel;
import com.aspirepublicschool.gyanmanjari.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewFeedbackAdapter extends RecyclerView.Adapter {

    Context ctx;
    ArrayList<NewFeedbackModel> teacherModel;

    public NewFeedbackAdapter(Context ctx, ArrayList<NewFeedbackModel> teacherModel) {
        this.ctx = ctx;
        this.teacherModel = teacherModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.feedback_row, null, false);
        return new NewFeedbackAdapter.viewHolder(MyView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final NewFeedbackModel teacherList= teacherModel.get(position);
        final NewFeedbackAdapter.viewHolder container = (NewFeedbackAdapter.viewHolder) holder;

        container.name.setText(teacherList.getTeachername());
        container.subject.setText(teacherList.getSubject());

        Glide.with(ctx).load(teacherList.getImg()).placeholder(R.drawable.ic_person).into(container.dp);

        container.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String latitude = "21.75859641", longitude = "72.85749612";
//                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                ctx.startActivity(intent);

//                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                if (mapIntent.resolveActivity(ctx.getPackageManager()) != null) {
//                    ctx.startActivity(mapIntent);
//                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return teacherModel.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        TextView name, subject;
        CircleImageView dp;
        RatingBar rb;
        CardView cardView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teacherName);
            subject = itemView.findViewById(R.id.teacherSub);
            dp = itemView.findViewById(R.id.dp);
            rb = itemView.findViewById(R.id.ratingBar);
            cardView = itemView.findViewById(R.id.feedbackCard);

        }
    }

}
