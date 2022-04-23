package com.aspirepublicschool.gyanmanjari;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AcademicCalendarAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<Academic> academicList;

    ProgressBar pb;
    Dialog dialog;
    int downloadedSize = 0;
    int totalSize = 0;
    String URL_IMG_NOTICEBOARD;
    TextView cur_val;
    String dwnload_file_path;
    String pdffile,file;
    File imageFile;
    private ProgressDialog pDialog;
    String fileName;

    public AcademicCalendarAdapter(Context ctx, ArrayList<Academic> academicList) {
        this.ctx = ctx;
        this.academicList = academicList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_acdemic_row, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final Academic academic = academicList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        container.txtacdtitle.setText(academic.getTitle());
        container.txtdate.setText(academic.getDate());
        pdffile=academic.getTitle();
        URL_IMG_NOTICEBOARD = "http://drive.google.com/viewerng/viewer?embedded=true&url=" + Common.GetBaseImageURL() + "academiccalender/" + academicList.get(position).getFile();
        Log.d("CCC", "" + URL_IMG_NOTICEBOARD);

        container.relacdemic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(ctx);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                final ProgressDialog progressDialog = new ProgressDialog(ctx);
                progressDialog.setMessage("Loading Data...");
                progressDialog.setCancelable(false);
                View mView = inflater.inflate(R.layout.activity_webview_dialog, null);
                WebView webView = mView.findViewById(R.id.webview);
                webView.requestFocus();
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(URL_IMG_NOTICEBOARD);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                webView.setWebChromeClient(new WebChromeClient() {
                    public void onProgressChanged(WebView view, int progress) {
                        if (progress < 100) {
                            progressDialog.show();
                        }
                        if (progress == 100) {
                            progressDialog.dismiss();
                        }
                    }
                });

                mBuilder.setView(mView);
                final AlertDialog mDialog = mBuilder.create();
                ImageView cancelview = mView.findViewById(R.id.imgcancel);
                cancelview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDialog.show();


            }
        });

        container.imgdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* showProgress(dwnload_file_path);

                new Thread(new Runnable() {
                    public void run() {
                        downloadFile();
                    }
                }).start();*/
                /*DownloadImage(dwnload_file_path);*/
                dwnload_file_path = Common.GetBaseImageURL() + "academiccalender/" + academicList.get(position).getFile();
                file = academicList.get(position).getFile();
                pDialog = new ProgressDialog(ctx);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                downloadPDF();


            }
        });

    }
    public void downloadPDF()
    {
        new DownloadFile().execute(dwnload_file_path, file);

    }

    public void viewPDF(String fileName)
    {
        Log.d("file",fileName);
        String filess=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)  + "/"+fileName;
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

    private class DownloadFile extends AsyncTask<String, Void, Void>{

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





   /* void DownloadImage(String ImageUrl) {



        showToast("Downloading Academic Calendar...");
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
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); ; //Creates app specific folder

            if(!path.exists()) {
                path.mkdirs();
            }

            imageFile = new File(path, String.valueOf(System.currentTimeMillis())+".pdf"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try{
                //bm.compress(Bitmap.CompressFormat, 100, out); // Compress Image
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
            showToast("Academic Calendar Saved!");
            Uri uri = Uri.fromFile(imageFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (imageFile.toString().contains(".pdf") ) {
                // JPG file
                intent.setDataAndType(uri, "application/pdf");
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        }
    }
    void showToast(String msg){
        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
    }*/
    void downloadFile(){

        try {
            URL url = new URL(dwnload_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            //connect
            urlConnection.connect();
            //set the path where we want to save the file
            File SDCardRoot = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            //create a new file, to save the downloaded file
            final File file = new File(SDCardRoot,pdffile+".pdf");
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
                    Toast.makeText(ctx,"Academic Calendar is Downloaded Successfully",Toast.LENGTH_LONG).show();
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
        return academicList.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        TextView txtacdtitle,txtdate;
        ImageView imgdownload;
        RelativeLayout relacdemic;
        public MyWidgetContainer(View itemView) {
            super(itemView);
            txtacdtitle=itemView.findViewById(R.id.txtacdtitle);
            txtdate=itemView.findViewById(R.id.txtdate);
            relacdemic=itemView.findViewById(R.id.relacdemic);
            imgdownload=itemView.findViewById(R.id.imgdownload);






        }
    }
}
