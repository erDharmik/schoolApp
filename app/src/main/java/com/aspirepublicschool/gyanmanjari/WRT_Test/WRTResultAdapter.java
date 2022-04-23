package com.aspirepublicschool.gyanmanjari.WRT_Test;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WRTResultAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<WRTResultModel> wrtResultModels;
    String time;

    public WRTResultAdapter(Context ctx, ArrayList<WRTResultModel> wrtResultModels) {
        this.ctx = ctx;
        this.wrtResultModels = wrtResultModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.layout_wrt_result, null);
        WRTResultHolder container = new WRTResultHolder(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final WRTResultModel test= wrtResultModels.get(position);
        final WRTResultHolder container = (WRTResultHolder) holder;
        container.txtsub.setText(test.getSubject());
        container.txtstime.setText(Html.fromHtml(test.getStime()));
        container.txtetime.setText(Html.fromHtml(test.getEtime()));
        container.txttotal.setText(Html.fromHtml(test.getTotal()));
        container.txtdate.setText(test.getTst_date());
        container.txtobtainmark.setText(test.getObtain());
        container.txtremark.setText(test.getRemarks());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        time = sdf.format(new Date());
        container.btnattempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager downloadmanager = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(test.getAns_doc());
                try {

                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setTitle("Checked Paper-" +test.getSubject()+time);
                    request.setDescription("Downloading");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "School");
                    downloadmanager.enqueue(request);

                }catch (Exception e){
                    Toast.makeText(ctx,R.string.no_connection_toast,Toast.LENGTH_LONG).show();
                }



            }
        });
        container.btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager downloadmanager = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(test.getSol_doc());
                try {

                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle("Solution Paper-"+test.getSubject()+time);
                request.setDescription("Downloading");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"School");
                downloadmanager.enqueue(request);

                }catch (Exception e){
                    Toast.makeText(ctx,R.string.no_connection_toast,Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return wrtResultModels.size();
    }
    class WRTResultHolder extends RecyclerView.ViewHolder {
        public TextView txtsub,txtstime,txtetime,txttotal,txtdate,txtobtainmark,txtremark;
        RelativeLayout relassignment;
        CardView cardtest;
        Button btnattempt,btndownload;
        LinearLayout lnrobtained,lnrremarks;
        public WRTResultHolder(View itemView) {
            super(itemView);
            txtsub=itemView.findViewById(R.id.txtsub);
            txtstime=itemView.findViewById(R.id.txtstime);
            txtetime=itemView.findViewById(R.id.txtetime);
            relassignment=itemView.findViewById(R.id.relassignment);
            txttotal=itemView.findViewById(R.id.txttotal);
            txtdate=itemView.findViewById(R.id.txtdate);
            txtobtainmark=itemView.findViewById(R.id.txtobtainmark);
            txtremark=itemView.findViewById(R.id.txtremark);
            cardtest=itemView.findViewById(R.id.cardtest);
            btnattempt=itemView.findViewById(R.id.btnattempt);
            btndownload=itemView.findViewById(R.id.btndownload);
           /* lnrobtained=itemView.findViewById(R.id.lnrobtained);
            lnrremarks=itemView.findViewById(R.id.lnrremarks);*/




        }
    }
}
