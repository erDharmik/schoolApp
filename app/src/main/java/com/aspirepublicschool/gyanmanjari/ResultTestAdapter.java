package com.aspirepublicschool.gyanmanjari;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ResultTestAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<HomeWorkDetails> homeWorkDetailsList;
    ProgressBar pb;
    Dialog dialog;
    int downloadedSize = 0;
    int totalSize = 0;
    TextView cur_val;
    String dwnload_file_path;
    String pdffile,file;
    File imageFile;

    public ResultTestAdapter(Context ctx, ArrayList<HomeWorkDetails> homeWorkDetailsList) {
        this.ctx = ctx;
        this.homeWorkDetailsList = homeWorkDetailsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_homework_work, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final HomeWorkDetails homeWorkDetails= homeWorkDetailsList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        container.txtsub.setText(homeWorkDetails.getSub());
        container.txttitle.setText( String.valueOf(Html.fromHtml(homeWorkDetails.getTitle())));
        container.txtdate.setText(homeWorkDetails.getDate());
        container.txtdesc.setText( String.valueOf(Html.fromHtml(homeWorkDetails.getDesc())));
        container.txtsubdate.setText(homeWorkDetails.getSubdate());
        container.txtteacherdet.setText(homeWorkDetails.getT_name());

        container.txtreadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(ctx);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                View mView = inflater.inflate(R.layout.activity_assignment_display, null);
                TextView txtsub,txttitle,txtdate,txtdesc,txtteacherdet,txtsubdate;
                txtsub=mView.findViewById(R.id.txtsub);
                txttitle=mView.findViewById(R.id.txttitle);
                // txtdate=mView.findViewById(R.id.txtdate);
                txtteacherdet=mView.findViewById(R.id.txtteacherdet);
                txtsubdate=mView.findViewById(R.id.txtsubdate);
                txtdesc=mView.findViewById(R.id.txtdesc);
                txtsub.setText(homeWorkDetails.getSub());
                txttitle.setText(homeWorkDetails.getTitle());
                //txtdate.setText(assignment.getDate());
                txtdesc.setText(homeWorkDetails.getDesc());
               // txtsubdate.setText(homeWorkDetails.getSubdate());

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
        container.txthwid.setText(homeWorkDetails.getH_id());

        container.relassignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        container.imgdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dwnload_file_path = homeWorkDetails.getDoc();
                Log.d("file",dwnload_file_path);
                DownloadImage(dwnload_file_path);
            }
        });

    }
    void DownloadImage(String ImageUrl) {

        showToast("Downloading Image...");
        //Asynctask to create a thread to downlaod image in the background
        new DownloadsImage().execute(ImageUrl);

    }
    class DownloadsImage extends AsyncTask<String, Void,Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); ; //Creates app specific folder

            if(!path.exists()) {
                path.mkdirs();
            }

            imageFile = new File(path, String.valueOf(System.currentTimeMillis())+".jpeg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try{
                bm.compress(Bitmap.CompressFormat.JPEG, 100, out); // Compress Image
                out.flush();
                out.close();
                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(ctx,new String[] { imageFile.getAbsolutePath() }, null,new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        // Log.i("ExternalStorage", "Scanned " + path + ":");
                        //    Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
            } catch(Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            showToast("Image Saved!");
            Uri uri = Uri.fromFile(imageFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (imageFile.toString().contains(".jpg") || imageFile.toString().contains(".jpeg") || imageFile.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/*");
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        }
    }
    void showToast(String msg){
        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
    }
    void downloadFile(){

        try {
            URL url = new URL(dwnload_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            //connect
            urlConnection.connect();
            //set the path where we want to save the file
            File SDCardRoot = Environment.getExternalStorageDirectory();
            //create a new file, to save the downloaded file
            final File file = new File(SDCardRoot,"Download/"+pdffile);
            FileOutputStream fileOutput = new FileOutputStream(file);
            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();
            //this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();
            ((Activity)ctx).runOnUiThread(new Runnable() {
                public void run() {
                    pb.setMax(totalSize);

                }
            });
            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
                ((Activity)ctx).runOnUiThread(new Runnable() {
                    public void run() {
                        pb.setProgress(downloadedSize);
                        float per = ((float)downloadedSize/totalSize) * 100;
                        cur_val.setText("Downloaded " + downloadedSize + "KB / " + totalSize + "KB (" + (int)per + "%)" );
                    }
                });
            }
            ((Activity)ctx).runOnUiThread(new Runnable() {
                public void run() {
                    // if you want close it..
                    //pb.setVisibility(View.GONE);
                    dialog.dismiss();
                    Toast.makeText(ctx,"Homework is Downloaded Successfully",Toast.LENGTH_LONG).show();
                }
            });
            //close the output stream when complete //
            fileOutput.close();


        } catch (final MalformedURLException e) {
            showError("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (final IOException e) {
            showError("Error : IOException " + e);
            e.printStackTrace();
        }
        catch (final Exception e) {
            showError("Error : Please check your internet connection " + e);
        }
    }

    void showError(final String err){
        ((Activity)ctx).runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(ctx, err, Toast.LENGTH_LONG).show();
            }
        });
    }

    void showProgress(String file_path){
        dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.myprogressdialog);
        dialog.setTitle("Download Progress");

        //TextView text = (TextView) dialog.findViewById(R.id.tv1);
        //text.setText("Downloading file from ... " + file_path);
        cur_val = (TextView) dialog.findViewById(R.id.cur_pg_tv);
        cur_val.setText("Starting download...");
        dialog.show();

        pb = (ProgressBar)dialog.findViewById(R.id.progress_bar);
        pb.setMax(100);
        pb.setProgressDrawable(ctx.getResources().getDrawable(R.drawable.green_progress));
    }

    @Override
    public int getItemCount() {
        return homeWorkDetailsList.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        public TextView txtsub,txttitle,txtdate,txtdesc,txtteacherdet,txtsubdate,txtreadmore,txthwid;
        RelativeLayout relassignment;
        TextView imgdownload;
        public MyWidgetContainer(View itemView) {
            super(itemView);
            txtsub=itemView.findViewById(R.id.txtsub);
            txttitle=itemView.findViewById(R.id.txttitle);
            txtdate=itemView.findViewById(R.id.txtdate);
            txtteacherdet=itemView.findViewById(R.id.txtteacherdet);
            txtsubdate=itemView.findViewById(R.id.txtsubdate);
            txtreadmore=itemView.findViewById(R.id.txtreadmore);
            txtdesc=itemView.findViewById(R.id.txtdesc);
            txthwid=itemView.findViewById(R.id.txthwid);
            relassignment=itemView.findViewById(R.id.relassignment);
            imgdownload=itemView.findViewById(R.id.imgdownload);

        }
    }
}
