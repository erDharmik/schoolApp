package com.aspirepublicschool.gyanmanjari.Result;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.FileDownloader;
import com.aspirepublicschool.gyanmanjari.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<Result> resultArrayList;
    String dwnload_file_path;
    String pdffile,file;
    File imageFile;
    private ProgressDialog pDialog;
    String fileName;

    public ResultAdapter(Context ctx, ArrayList<Result> resultArrayList) {
        this.ctx = ctx;
        this.resultArrayList = resultArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.result_list, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        final Result result= resultArrayList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        if(result.getStatus().equals("1"))
        {
            container.test_date.setText(result.getT_date());
            container.total.setText("Result Yet to be Generated");
            container.subject.setText(result.getSubject());
            container.type.setText(result.getType());
            container.txview.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(ctx, "You can view answer after result generated", Toast.LENGTH_LONG).show();
                }
            });
/*
            container.txdownloadKey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(ctx, "You can download key after result generated", Toast.LENGTH_LONG).show();

                }
            });
*/
        }
        else if(result.getStatus().equals("2"))
        {
            container.test_date.setText(result.getT_date());
            container.total.setText(result.getObtain()+"/"+result.getTotal());
            container.subject.setText(result.getSubject());
            container.type.setText(result.getType());
            container.txview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ctx,ViewTestAnswer.class);
                    intent.putExtra("tst_id", result.getTst_id());
                    ctx.startActivity(intent);
                }
            });
/*
            container.txdownloadKey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dwnload_file_path =result.getKey();
                    String url = null;
                    url = result.getKey();
                    try {
                        String filename1 = url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('.'));
                        String fileExtension = url.substring(url.lastIndexOf("."));
                        file=filename1+fileExtension;
                        Log.d("files", file);
                        Log.d("files", result.getKey());
                        pDialog = new ProgressDialog(ctx);
                        pDialog.setMessage("Please wait...");
                        pDialog.setCancelable(false);
                        downloadPDF();
                    } catch (Exception e) {
                        hidepDialog();
                        Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                    }
                }
            });
*/
        }
        else {

        }



    }
    public void downloadPDF()
    {
        new DownloadFile().execute(dwnload_file_path, file);

    }
    public void viewPDF(String fileName)
    {
       // Log.d("file",fileName);
        String filess= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)  + "/"+fileName;
        File pdfFile =new File(filess);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try{
            ctx.startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){
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
            Toast.makeText(ctx, "Download PDF successfully", Toast.LENGTH_SHORT).show();
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
    public int getItemCount()
    {
        return resultArrayList.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder
    {
        TextView type,total,test_date,subject,txview,txdownloadKey;
        RelativeLayout relresult;
        public MyWidgetContainer(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subject);
            type = itemView.findViewById(R.id.type);
            total = itemView.findViewById(R.id.total);
            test_date = itemView.findViewById(R.id.ex_date);
            txview = itemView.findViewById(R.id.txview);
            relresult=itemView.findViewById(R.id.relresult);
//            txdownloadKey=itemView.findViewById(R.id.txdownloadKey);
        }
    }
}
