package com.aspirepublicschool.gyanmanjari;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
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

import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class NoticeBoardAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<NoticeBoard> noticeboardList;
    String pdffile,file;
    File imageFile;

    public NoticeBoardAdapter(Context ctx, ArrayList<NoticeBoard> noticeboardList) {
        this.ctx = ctx;
        this.noticeboardList = noticeboardList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_notice_row, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final NoticeBoard noticeBoard = noticeboardList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;

        container.txtdate.setText(noticeBoard.getDate());
        try {
            String decoded = new String(noticeBoard.getTitle().getBytes("ISO-8859-1"));
            byte[] b = noticeBoard.getTitle().getBytes();
            String s = new String(b, StandardCharsets.US_ASCII);
            container.txtnotice.setText(decoded);
            System.out.println(s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String URL_IMG_NOTICEBOARD = "https://mrawideveloper.com/gyanmanfarividyapith.net/zocarro/image/noticeboard/"+noticeboardList.get(position).getImg();
        //final String URL_IMG_NOTICEBOARD = noticeboardList.get(position).getImg();
        Log.v("URL_IMG_NOTICEBOARD",URL_IMG_NOTICEBOARD);
        Glide.with(ctx).load(URL_IMG_NOTICEBOARD).centerCrop().into(container.imgnotice);
        container.imgnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String NOTICEBOARD =noticeboardList.get(position).getImg();
                String NOTICEBOARD ="https://mrawideveloper.com/gyanmanfarividyapith.net/zocarro/image/noticeboard/" +noticeboardList.get(position).getImg();
                DownloadImage(NOTICEBOARD);
            }
        });
        container.txtdesc.setText(noticeBoard.getDes());
        container.txtreadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(ctx);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                View mView = inflater.inflate(R.layout.actvity_desc_diaglog, null);
                TextView txtdesc = mView.findViewById(R.id.txtdesc);
               txtdesc.setText(noticeBoard.getDes());
                mBuilder.setView(mView);
                final AlertDialog mDialog = mBuilder.create();
                Button btn_close=mView.findViewById(R.id.btn_close);
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDialog.show();

            }
        });


    }

    public static class StringFormatter {

        // convert UTF-8 to internal Java String format
        public static String convertUTF8ToString(String s) {
            String out = null;
            try {
                out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
            } catch (java.io.UnsupportedEncodingException e) {
                return null;
            }
            return out;
        }

        // convert internal Java String format to UTF-8
        public static String convertStringToUTF8(String s) {
            String out = null;
            try {
                out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
            } catch (java.io.UnsupportedEncodingException e) {
                return null;
            }
            return out;
        }

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
            } catch (IOException e) {
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

    @Override
    public int getItemCount() {
        return noticeboardList.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        public TextView txtnotice,txtdate,txtreadmore,txtdesc;
        ImageView imgnotice,imgdownload;
        RelativeLayout relnotice;
        public MyWidgetContainer(View itemView) {
            super(itemView);
            txtnotice=itemView.findViewById(R.id.txtnotice);
            txtdate=itemView.findViewById(R.id.txtdate);
            txtreadmore=itemView.findViewById(R.id.txtreadmore);
            txtdesc=itemView.findViewById(R.id.txtdesc);
            imgnotice=itemView.findViewById(R.id.imgnotice);
            relnotice=itemView.findViewById(R.id.relnotice);





        }
    }

}
