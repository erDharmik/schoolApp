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
import androidx.cardview.widget.CardView;
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

public class AssignmnetAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<Assignment> assignmentList;

    ProgressBar pb;
    Dialog dialog;
    int downloadedSize = 0;
    int totalSize = 0;
    String URL_IMG_NOTICEBOARD;
    TextView cur_val;
    String dwnload_file_path;
    String pdffile, file;
    File imageFile;
    String fileName;
    private ProgressDialog pDialog;

    public AssignmnetAdapter(Context ctx, ArrayList<Assignment> assignmentList) {
        this.ctx = ctx;
        this.assignmentList = assignmentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_assignment_row, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Assignment assignment = assignmentList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        container.txtsub.setText(assignment.getSub());
        container.txttitle.setText(String.valueOf(Html.fromHtml(assignment.getTitle())));
        container.txtdate.setText(assignment.getDate());
        container.txtdesc.setText(String.valueOf(Html.fromHtml(assignment.getDesc())));

        container.txtreadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(ctx);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                View mView = inflater.inflate(R.layout.activity_assignment_display, null);
                TextView txtsub, txttitle, txtdate, txtdesc, txtteacherdet, txtsubdate;
                txtsub = mView.findViewById(R.id.txtsub);
                txttitle = mView.findViewById(R.id.txttitle);
                txtdesc = mView.findViewById(R.id.txtdesc);
                txtsub.setText(assignment.getSub());
                txttitle.setText(assignment.getTitle());
                txtdesc.setText(assignment.getDesc());


                mBuilder.setView(mView);
                final AlertDialog mDialog = mBuilder.create();
                Button cancelview = mView.findViewById(R.id.imgcancel);
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
        container.cardstudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, StudyMaterialView.class);
                intent.putExtra("doc", assignment.getDoc());
                ctx.startActivity(intent);

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

                dwnload_file_path = assignment.getDoc();
                String url = null;

                url = assignment.getDoc();
                String filename1 = url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('.'));
                String fileExtension = url.substring(url.lastIndexOf("."));
                file=filename1+fileExtension;

                Log.d("files", file);
                Log.d("files", assignment.getDoc());
                pDialog = new ProgressDialog(ctx);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                downloadPDF();
            }
        });


    }

    public void downloadPDF() {
        new DownloadFile().execute(dwnload_file_path, file);

    }

    public void viewPDF(String fileName) {
        Log.d("file", fileName);
        String filess = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName;
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

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        try {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
        catch (Exception e)
        {
            pDialog.dismiss();
            Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
        }

    }

    void downloadFile()
    {

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
            final File file = new File(SDCardRoot, "Download/" + pdffile);
            FileOutputStream fileOutput = new FileOutputStream(file);
            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();
            //this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();
            ((Activity) ctx).runOnUiThread(new Runnable() {
                public void run() {
                    pb.setMax(totalSize);

                }
            });
            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
                ((Activity) ctx).runOnUiThread(new Runnable() {
                    public void run() {
                        pb.setProgress(downloadedSize);
                        float per = ((float) downloadedSize / totalSize) * 100;
                        cur_val.setText("Downloaded " + downloadedSize + "KB / " + totalSize + "KB (" + (int) per + "%)");
                    }
                });
            }
            ((Activity) ctx).runOnUiThread(new Runnable() {
                public void run() {
                    // if you want close it..
                    //pb.setVisibility(View.GONE);
                    dialog.dismiss();
                    Toast.makeText(ctx, "Projectwork is Downloaded Successfully", Toast.LENGTH_LONG).show();
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
        } catch (final Exception e)
        {
            showError("Error : Please check your internet connection " + e);
        }
    }

    void showError(final String err) {
        ((Activity) ctx).runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(ctx, err, Toast.LENGTH_LONG).show();
            }
        });
    }

    void showProgress(String file_path) {
        dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.myprogressdialog);
        dialog.setTitle("Download Progress");

        //TextView text = (TextView) dialog.findViewById(R.id.tv1);
        //text.setText("Downloading file from ... " + file_path);
        cur_val = (TextView) dialog.findViewById(R.id.cur_pg_tv);
        cur_val.setText("Starting download...");
        dialog.show();

        pb = (ProgressBar) dialog.findViewById(R.id.progress_bar);
        pb.setMax(100);
        pb.setProgressDrawable(ctx.getResources().getDrawable(R.drawable.green_progress));
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
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
            Log.d("AAA", fileName);
// ->five-point-someone-chetan-bhagat_ebook.pdf
            String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File folder = new File(extStorageDirectory);
            folder.mkdir();
            File pdfFile = new File(folder, fileName);
            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
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

    class MyWidgetContainer extends RecyclerView.ViewHolder {
        public TextView txtsub, txttitle, txtdate, txtdesc, txtsubdate, txtreadmore, txtLink;
        RelativeLayout relassignment;
        TextView imgdownload;
        CardView cardstudy;

        public MyWidgetContainer(View mView) {
            super(mView);
            txtsub = mView.findViewById(R.id.txtsub);
            txttitle = mView.findViewById(R.id.txttitle);
            txtdate = mView.findViewById(R.id.txtdate);
            txtsubdate = mView.findViewById(R.id.txtsubdate);
            txtreadmore = mView.findViewById(R.id.txtreadmore);
            txtdesc = mView.findViewById(R.id.txtdesc);
            // txtLink=mView.findViewById(R.id.txtLink);
            relassignment = mView.findViewById(R.id.relassignment);
            imgdownload = mView.findViewById(R.id.imgdownload);
            cardstudy = mView.findViewById(R.id.cardstudy);


        }
    }

}
