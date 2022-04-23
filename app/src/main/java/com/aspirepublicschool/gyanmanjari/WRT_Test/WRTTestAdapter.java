package com.aspirepublicschool.gyanmanjari.WRT_Test;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.FileDownloader;
import com.aspirepublicschool.gyanmanjari.R;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WRTTestAdapter extends RecyclerView.Adapter
{

    Context ctx;
    ArrayList<WRTTestModel> testArrayList;
    String time;
    String dwnload_file_path,file,fileName;
    private ProgressDialog pDialog;


    public WRTTestAdapter(Context ctx, ArrayList<WRTTestModel> testArrayList)
    {
        this.ctx = ctx;
        this.testArrayList = testArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_wrt_test_row, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final WRTTestModel test= testArrayList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        container.txtsub.setText(test.getSubject());
        container.txtstime.setText(Html.fromHtml(test.getStime()));
        container.txtetime.setText(Html.fromHtml(test.getEtime()));
        container.txttotal.setText(Html.fromHtml(test.getTotal()));
        container.txtdate.setText(test.getTst_date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        time = sdf.format(new Date());

        if(test.getTst_ans_status().equals("0") || test.getTst_ans_status().equals("1") )
        {
            container.btnattempt.setVisibility(View.GONE);
        }
     /*   if(test.getTst_ans_status().equals("1"))
        {
            container.btndownload.setText("Solution Paper");
            container.btnattempt.setText("Checked Paper");
            container.lnrobtained.setVisibility(View.VISIBLE);
            container.lnrremarks.setVisibility(View.VISIBLE);
            container.txtobtainmark.setText(test.getObtain());
            container.txtremark.setText(test.getRemarks());
        }*/
        try {
            Date time1 = new SimpleDateFormat("HH:mm").parse(test.getStime());
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);


            Date time2 = new SimpleDateFormat("HH:mm").parse(test.getEtime());
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);


            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Calendar cal = Calendar.getInstance();
            String localTime= dateFormat.format(cal.getTime());
            Log.d("time now", localTime);

            Date d = new SimpleDateFormat("HH:mm").parse(localTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //checkes whether the current time is between 14:49:00 and 20:11:13.
                //System.out.println("true");



            }
            else
            {
                if(test.getTst_ans_status().equals("Not Set"))
                {
                    container.btnattempt.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
        }

        container.btnattempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Date time1 = new SimpleDateFormat("HH:mm").parse(test.getStime());
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(time1);
                    calendar1.add(Calendar.DATE, 1);


                    Date time2 = new SimpleDateFormat("HH:mm").parse(test.getEtime());
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(time2);
                    calendar2.add(Calendar.DATE, 1);


                    DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    Calendar cal = Calendar.getInstance();
                    String localTime= dateFormat.format(cal.getTime());
                    Log.d("time now", localTime);

                    Date d = new SimpleDateFormat("HH:mm").parse(localTime);
                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.setTime(d);
                    calendar3.add(Calendar.DATE, 1);

                    Date x = calendar3.getTime();
                    if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                        //checkes whether the current time is between 14:49:00 and 20:11:13.
                        //System.out.println("true");
                        if(container.btnattempt.getText().toString().equals("Submit Paper"))
                        {
                            Intent intent = new Intent(ctx, SubmitTestPdf.class);
                            intent.putExtra("tst_id", test.getTst_id());
                            intent.putExtra("gr_no",test.getGr_no());
                            ctx.startActivity(intent);
                            ((Activity)ctx).finish();
                        }
                       /* else
                        {
                            DownloadManager downloadmanager = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(test.getAns_doc());

                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setTitle("Checked Paper-" +test.getSubject()+time);
                            request.setDescription("Downloading");
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "School");
                            downloadmanager.enqueue(request);
                        }*/




                    }
                    else
                    {
                        System.out.println("true");
                       /* if(test.getTst_ans_status().equals("1")) {
                            DownloadManager downloadmanager = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(test.getAns_doc());

                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setTitle("Checked Paper-" +test.getSubject()+time);
                            request.setDescription("Downloading");
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "School");
                            downloadmanager.enqueue(request);
                        }
                        else
                        {
                            Toast.makeText(ctx, "Sorry!!Your Paper is not checked", Toast.LENGTH_LONG).show();
                        }
*/

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                }



            }
        });
        container.btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Date time1 = new SimpleDateFormat("HH:mm").parse(test.getStime());
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(time1);
                    calendar1.add(Calendar.DATE, 1);


                    Date time2 = new SimpleDateFormat("HH:mm").parse(test.getEtime());
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(time2);
                    calendar2.add(Calendar.DATE, 1);

                    DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    Calendar cal = Calendar.getInstance();
                    String localTime= dateFormat.format(cal.getTime());
                    Log.d("time now", localTime);

                    Date d = new SimpleDateFormat("HH:mm").parse(localTime);
                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.setTime(d);
                    calendar3.add(Calendar.DATE, 1);

                    Date x = calendar3.getTime();
                    if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                        //checkes whether the current time is between 14:49:00 and 20:11:13.
                        //System.out.println("true");
//                        if(container.btndownload.getText().toString().equals("Question Paper"))
//                        {


//                        String URL =test.getQ_doc();
//                        Log.d("Download", "onClick: " + URL);
//                        ctx.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(URL)));

                        /*    DownloadManager downloadmanager = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(test.getQ_doc());

                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setTitle("Question_Paper-"+test.getSubject()+time);
                            request.setDescription("Downloading");
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,test.getSubject()+time);
                            downloadmanager.enqueue(request);
                            */

                        dwnload_file_path = test.getQ_doc();
                        String url = null;
                        url = test.getQ_doc();
                        String filename1 = url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('.'));
                        String fileExtension = url.substring(url.lastIndexOf("."));
                        file = filename1 + fileExtension;
                        Log.d("files", file);
                        Log.d("files", test.getQ_doc());
                        pDialog = new ProgressDialog(ctx);
                        pDialog.setMessage("Please wait...");
                        pDialog.setCancelable(false);
                        downloadPDF();

//                        }
//                        else
//                        {
                            /*DownloadManager downloadmanager = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(test.getSol_doc());

                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setTitle("Solution Paper-"+test.getSubject()+time);
                            request.setDescription("Downloading");
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"School");
                            downloadmanager.enqueue(request);*/
//                        }

                    }
                    else
                    {
                        System.out.println("true");
                        /*if(test.getTst_ans_status().equals("1")) {
                            DownloadManager downloadmanager = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(test.getSol_doc());

                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setTitle("Solution Paper-"+test.getSubject()+time);
                            request.setDescription("Downloading");
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"School");
                            downloadmanager.enqueue(request);
                        }
                        else
                        {
                            Toast.makeText(ctx, "Sorry!!Test will open only in time.", Toast.LENGTH_LONG).show();
                        }*/

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    public void downloadPDF()
    {
        new DownloadFile().execute(dwnload_file_path, file);
    }

    public void viewPDF(String fileName)
    {
        Log.d("file", fileName);
        String filess =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName;
        File pdfFile = new File(filess);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            ctx.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(ctx, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showpDialog();
        }

        @Override
        protected Void doInBackground(String... strings) {

            String fileUrl = strings[0];
// -> https://letuscsolutions.files.wordpress.com/2015/07/five-point-someone-chetan-bhagat_ebook.pdf
            fileName = strings[1];
// ->five-point-someone-chetan-bhagat_ebook.pdf
            String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File folder = new File(extStorageDirectory);
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            hidepDialog();
            Toast.makeText(ctx, "Download PDf successfully", Toast.LENGTH_SHORT).show();
            viewPDF(fileName);

            Log.d("Download complete", "----------");
        }
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public int getItemCount() {
        return testArrayList.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        public TextView txtsub,txtstime,txtetime,txttotal,txtdate,txtobtainmark,txtremark;
        RelativeLayout relassignment;
        CardView cardtest;
        Button btnattempt,btndownload;
        LinearLayout lnrobtained,lnrremarks;
        public MyWidgetContainer(View itemView) {
            super(itemView);
            txtsub=itemView.findViewById(R.id.txtsub);
            txtstime=itemView.findViewById(R.id.txtstime);
            txtetime=itemView.findViewById(R.id.txtetime);
            relassignment=itemView.findViewById(R.id.relassignment);
            txttotal=itemView.findViewById(R.id.txttotal);
            txtdate=itemView.findViewById(R.id.txtdate);
            /*txtobtainmark=itemView.findViewById(R.id.txtobtainmark);
            txtremark=itemView.findViewById(R.id.txtremark);*/
            cardtest=itemView.findViewById(R.id.cardtest);
            btnattempt=itemView.findViewById(R.id.btnattempt);
            btndownload=itemView.findViewById(R.id.btndownload);
           /* lnrobtained=itemView.findViewById(R.id.lnrobtained);
            lnrremarks=itemView.findViewById(R.id.lnrremarks);*/
        }
    }
}
