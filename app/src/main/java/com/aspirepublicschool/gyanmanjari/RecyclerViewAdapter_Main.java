package com.aspirepublicschool.gyanmanjari;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.Doubt.DoubtSubject;
import com.aspirepublicschool.gyanmanjari.DoubtSolving.ChatDoubt;
import com.aspirepublicschool.gyanmanjari.Homework.HomeworkActivity;
import com.aspirepublicschool.gyanmanjari.NewFeedback.NewFeedbackActivity;
import com.aspirepublicschool.gyanmanjari.NewTest.ViewNewTestToday;
import com.aspirepublicschool.gyanmanjari.OfflineTestResult.OfflineTestResultActivity;
import com.aspirepublicschool.gyanmanjari.Result.ClassResult;
import com.aspirepublicschool.gyanmanjari.Test.ViewTestToday;
import com.aspirepublicschool.gyanmanjari.VideoLectures.VideoTabbed;
import com.aspirepublicschool.gyanmanjari.WRT_Test.WRTResult;
import com.aspirepublicschool.gyanmanjari.WRT_Test.WRT_TEST;

import java.util.List;

public class RecyclerViewAdapter_Main extends RecyclerView.Adapter<RecyclerViewAdapter_Main.MyViewHolder>
{
//    SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
//    String sc_id= mPrefs.getString("sc_id", "none").toUpperCase();
//    String status = mPrefs.getString("status", "none");
//    String stu_id = mPrefs.getString("stu_id", "none").toUpperCase();

    private Context mContext;
    private List<Mainpage_design> mData;
    public RecyclerViewAdapter_Main(Context mContext, List<Mainpage_design> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflate = LayoutInflater.from(mContext);
        view = mInflate.inflate(R.layout.mainpage_design,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.book_txt.setText(mData.get(position).getTitle());
        holder.book_img.setImageResource(mData.get(position).getThumbnail());
        holder.linearLayout_main.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Toast.makeText(mContext,mData.get(position).getTitle(),Toast.LENGTH_SHORT).show();

//            if (status.e)
            Intent intent = null;
            switch (position){

                case 0:
                    intent =  new Intent(mContext, Announcement.class);
                    mContext.startActivity(intent);
                    break;
                case 1:
                    intent =  new Intent(mContext, VideoTabbed.class);
                    mContext.startActivity(intent);
                    break;
                case 2:
                    intent =  new Intent(mContext, HomeWorkAssignment.class);
                    mContext.startActivity(intent);
                    break;
                case 3:
                    intent =  new Intent(mContext, HomeworkActivity.class);
                    mContext.startActivity(intent);
                    break;
                case 4:
                    intent =  new Intent(mContext, DoubtSubject.class);
                    mContext.startActivity(intent);
                    break;
                case 5:
                    intent =  new Intent(mContext, ChatDoubt.class);
                    mContext.startActivity(intent);
                    break;
                case 6:
                    intent =  new Intent(mContext, DailyTimeTable.class);
                    mContext.startActivity(intent);
                    break;
                case 7:
                    intent =  new Intent(mContext, StaffDetails.class);
                    mContext.startActivity(intent);
                    break;
                case 8:
                    intent =  new Intent(mContext, ViewTestToday.class);
                    mContext.startActivity(intent);
                    break;

                case 9:
                    intent =  new Intent(mContext, WRT_TEST.class);
                    mContext.startActivity(intent);
                    break;
                case 10:
                    intent =  new Intent(mContext, ViewNewTestToday.class);
                    mContext.startActivity(intent);
                    break;

                case 11:
                    intent =  new Intent(mContext, ClassResult.class);
                    mContext.startActivity(intent);
                    break;
                case 12:
                    intent =  new Intent(mContext, WRTResult.class);
                    mContext.startActivity(intent);
                    break;
                case 13:
                    intent =  new Intent(mContext, OfflineTestResultActivity.class);
                    mContext.startActivity(intent);
                    break;
                case 14:
                    intent =  new Intent(mContext, NewFeedbackActivity.class);
                    mContext.startActivity(intent);
                    break;

                /*case 9:
                    Toast.makeText(mContext,"Feedback session is not started by admin.!!", Toast.LENGTH_LONG).show();
                   *//* intent =  new Intent(mContext, Feedback.class);
                    mContext.startActivity(intent);*//*
                    break;*/
               /* case 9:
                    intent =  new Intent(mContext, StaffDetails.class);
                    mContext.startActivity(intent);
                    break;*/
               /* case 10:
                    intent =  new Intent(mContext, Lunch_Activity.class);
                    mContext.startActivity(intent);
                    break;
                case 11:
                    intent =  new Intent(mContext, MapsActivity.class);
                    mContext.startActivity(intent);
                    break;
              case 12:
                    intent =  new Intent(mContext, VideoLectures.class);
                    mContext.startActivity(intent);
                    break;*/
                 /* case 13:
                    intent =  new Intent(mContext, StationeryPackage.class);
                    mContext.startActivity(intent);
                    break;*/

                default:
                    //intent =  new Intent(mContext, MainActivity.class);
                    break;

            }
        }
    });
        //click method

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView book_txt;
        ImageView book_img;
    CardView cardview_id;
        LinearLayout linearLayout_main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_txt = (TextView)itemView.findViewById(R.id.book_txt);
            book_img = (ImageView)itemView.findViewById(R.id.book_img);
            cardview_id = (CardView)itemView.findViewById(R.id.cardview_id);
            linearLayout_main = itemView.findViewById(R.id.main_id);
        }
    }
}
