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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PublicEventsAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<PublicEventsModel> publicEventsList=new ArrayList<>();
    String pdffile,file;
    File imageFile;


    public PublicEventsAdapter(Context ctx, ArrayList<PublicEventsModel> publicEventsList) {
        this.ctx = ctx;
        this.publicEventsList = publicEventsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.actvity_public_event, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final PublicEventsModel publicEventsModel = publicEventsList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        container.txteventtitle.setText(publicEventsModel.getTitle());
        container.txtplace.setText(publicEventsModel.getPlace());
        container.txtdate.setText(publicEventsModel.getDate());
        final String URL_IMG_NOTICEBOARD = "https://mrawideveloper.com/gyanmanfarividyapith.net/zocarro/image/noticeboard/"+ publicEventsList.get(position).getImg();
        
        Log.d("noticepath",URL_IMG_NOTICEBOARD);
        
        Glide.with(ctx).load(URL_IMG_NOTICEBOARD).into(container.imgnotice);
        container.txtweblink.setText(publicEventsModel.getWeblink());
        
        container.imgnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(ctx);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                View mView = inflater.inflate(R.layout.activity_image_dialog, null);
                ImageView photoView = mView.findViewById(R.id.photo_view);
                final String URL_IMG_NOTICEBOARD = publicEventsList.get(position).getImg();
                Glide.with(ctx).load(URL_IMG_NOTICEBOARD).into(photoView);
                mBuilder.setView(mView);
                final AlertDialog mDialog = mBuilder.create();
                ImageView cancelview=mView.findViewById(R.id.imgcancel);
                cancelview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDialog.show();
            }
        });

        container.relpublicevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent SwitchActivity=new Intent(ctx,AssignmentDisplay.class);
                SwitchActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(SwitchActivity);*/
            }
        });
        container.imgdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NOTICEBOARD =  "https://mrawideveloper.com/gyanmanfarividyapith.net/zocarro/image/noticeboard/"+ publicEventsList.get(position).getImg();
                DownloadImage(NOTICEBOARD);

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
        return publicEventsList.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        TextView txteventtitle, txtplace, txtdate, txtweblink;
        RelativeLayout relpublicevent;
        ImageView imgnotice,imgdownload;

        public MyWidgetContainer(View itemView) {
            super(itemView);
            txteventtitle = itemView.findViewById(R.id.txteventtitle);
            txtdate = itemView.findViewById(R.id.txtdate);
            txtplace = itemView.findViewById(R.id.txtplace);
            txtweblink = itemView.findViewById(R.id.txtweblink);
            imgnotice = itemView.findViewById(R.id.imgnotice);
            relpublicevent = itemView.findViewById(R.id.relpublicevent);
            imgdownload=itemView.findViewById(R.id.imgdownload);


        }
    }
}
