package com.aspirepublicschool.gyanmanjari.VideoLectures;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.io.File;
import java.util.ArrayList;

public class VideoHistoryAdapter extends RecyclerView.Adapter  {
    Context ctx;
    ArrayList<VideoLectureModel> homeWorkDetailsList;
    ProgressBar pb;
    Dialog dialog;
    int downloadedSize = 0;
    int totalSize = 0;
    String URL_IMG_NOTICEBOARD;
    TextView cur_val;
    String dwnload_file_path;
    String pdffile,file;
    File imageFile;
    public VideoHistoryAdapter(Context ctx, ArrayList<VideoLectureModel> homeWorkDetailsList) {
        this.ctx = ctx;
        this.homeWorkDetailsList = homeWorkDetailsList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_video_lecture, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final VideoLectureModel homeWorkDetails= homeWorkDetailsList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        container.txtsub.setText(homeWorkDetails.getSubject());
        container.txtdate.setText(homeWorkDetails.getDate());
        container.txtdesc.setText( String.valueOf(Html.fromHtml(homeWorkDetails.getDes())));
        container.txtreadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(ctx);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                View mView = inflater.inflate(R.layout.activity_display_video, null);
                TextView txtsub,txtdesc;
                txtsub=mView.findViewById(R.id.txtsub);
                txtdesc=mView.findViewById(R.id.txtdesc);
                txtsub.setText(homeWorkDetails.getSubject());
                txtdesc.setText(homeWorkDetails.getDes());


                mBuilder.setView(mView);
                final AlertDialog mDialog = mBuilder.create();
                Button cancelview=mView.findViewById(R.id.imgcancel);
                cancelview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDialog.show();

            }
        });
        container.txtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ctx,VideoPlaying.class);
                intent.putExtra("video_name",homeWorkDetails.getLink());
                intent.putExtra("subject",homeWorkDetails.getSubject());
                ctx.startActivity(intent);

            }
        });
        container.txtylink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homeWorkDetails.getStatus1().equals("1"))
                {
                    try {
                        Uri uri = Uri.parse(homeWorkDetails.getLinkyt());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        ctx.startActivity(intent);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                    }


                }
                else
                {
                    Toast.makeText(ctx, "Sorry!!,Link has been closed by admin", Toast.LENGTH_LONG).show();
                }
            }
        });




        container.txtstime.setText(homeWorkDetails.getStime());
        container.txtetime.setText(homeWorkDetails.getEtime());



    }
    @Override
    public int getItemCount() {

        return homeWorkDetailsList.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        public TextView txtsub,txtdate,txtdesc,txtreadmore,txtLink,txtstime,txtetime,txtylink;
        RelativeLayout relassignment;
        public MyWidgetContainer(View itemView) {
            super(itemView);
            txtsub=itemView.findViewById(R.id.txtsub);
            txtdate=itemView.findViewById(R.id.txtdate);
            txtreadmore=itemView.findViewById(R.id.txtreadmore);
            txtdesc=itemView.findViewById(R.id.txtdesc);
            txtLink=itemView.findViewById(R.id.txtLink);
            txtylink=itemView.findViewById(R.id.txtylink);
            relassignment=itemView.findViewById(R.id.relassignment);
            txtstime=itemView.findViewById(R.id.txtstime);
            txtetime=itemView.findViewById(R.id.txtetime);




        }
    }

}
