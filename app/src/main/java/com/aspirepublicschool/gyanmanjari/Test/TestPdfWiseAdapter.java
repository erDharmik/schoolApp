package com.aspirepublicschool.gyanmanjari.Test;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.FileDownloader;
import com.aspirepublicschool.gyanmanjari.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestPdfWiseAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<TestPdfModel> pdfModelArrayList;
    String fileName;
    String dwnload_file_path;
    String pdffile,file;
    private ProgressDialog pDialog;
    int downloadedSize = 0;
    int totalSize = 0;

    public TestPdfWiseAdapter(Context ctx, ArrayList<TestPdfModel> pdfModelArrayList) {
        this.ctx = ctx;
        this.pdfModelArrayList = pdfModelArrayList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_test_material, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TestPdfModel testPdfModel = pdfModelArrayList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        container.txtsub.setText(testPdfModel.getSub());
        container.txttitle.setText( String.valueOf(Html.fromHtml(testPdfModel.getTitle())));
        container.txtdate.setText(testPdfModel.getDate());
        container.txtdesc.setText( String.valueOf(Html.fromHtml(testPdfModel.getDes())));
        container.txtteacherdet.setText(testPdfModel.getT_name());

        container.txtreadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(ctx);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                View mView = inflater.inflate(R.layout.activity_assignment_display, null);
                TextView txtsub,txttitle,txtdate,txtdesc,txtteacherdet,txtsubdate;
                txtsub=mView.findViewById(R.id.txtsub);
                txttitle=mView.findViewById(R.id.txttitle);
                txtdesc=mView.findViewById(R.id.txtdesc);
                txtsub.setText(testPdfModel.getSub());
                txttitle.setText(testPdfModel.getTitle());
                txtdesc.setText(testPdfModel.getDes());


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
      /*  container.txtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://drive.google.com/file/d/1GapN86gSi5a6CSO4QcXq5yogZ5I6T6xF/view");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                ctx.startActivity(intent);
            }
        });*/

        container.imgdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* showProgress(dwnload_file_path);

                new Thread(new Runnable() {
                    public void run() {
                        downloadFile();
                    }
                }).start();*/
                dwnload_file_path = Common.GetBaseImageURL() + "test/" + testPdfModel.getDoc();
                file = testPdfModel.getDoc();
                Log.d("files",dwnload_file_path);
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

   /* void downloadFile(){

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
                    Toast.makeText(ctx,"Projectwork is Downloaded Successfully",Toast.LENGTH_LONG).show();
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
    }*/


    @Override
    public int getItemCount() {
        return pdfModelArrayList.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        public TextView txtsub,txttitle,txtdate,txtdesc,txtteacherdet,txtsubdate,txtreadmore;
        RelativeLayout relassignment;
        ImageView imgdownload;
        public MyWidgetContainer(View mView) {
            super(mView);
            txtsub=mView.findViewById(R.id.txtsub);
            txttitle=mView.findViewById(R.id.txttitle);
            txtdate=mView.findViewById(R.id.txtdate);
            txtteacherdet=mView.findViewById(R.id.txtteacherdet);
            txtsubdate=mView.findViewById(R.id.txtsubdate);
            txtreadmore=mView.findViewById(R.id.txtreadmore);
            txtdesc=mView.findViewById(R.id.txtdesc);
            relassignment=mView.findViewById(R.id.relassignment);
            imgdownload=mView.findViewById(R.id.imgdownload);





        }
    }

}
