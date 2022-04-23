package com.aspirepublicschool.gyanmanjari.DoubtSolving;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aspirepublicschool.gyanmanjari.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DoubtSolvingAdapter  extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<Doubt> doubtArrayList;
    String pdffile,file;
    File imageFile;

    public DoubtSolvingAdapter(Context ctx, ArrayList<Doubt> doubtArrayList) {
        this.ctx = ctx;
        this.doubtArrayList = doubtArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_doubt_row, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Doubt doubt= doubtArrayList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        container.txtsub.setText(doubt.getTitle());
        container.txtdate.setText(doubt.getTime());
        container.txttitle.setText( String.valueOf(Html.fromHtml(doubt.getMessage())));
        String url= doubt.getDoc();
        Glide.with(ctx).load(url).into(container.txtdesc);
     /*   container.txtreadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(ctx);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                View mView = inflater.inflate(R.layout.activity_decscribe_doubt, null);
                TextView txtsub,txtdesc;
                txtsub=mView.findViewById(R.id.txtsub);
                txtdesc=mView.findViewById(R.id.txtdesc);
                txtsub.setText(doubt.getSubject());
                txtdesc.setText(doubt.getDescription());
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
        container.update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx,UpdateDoubt.class);
                intent.putExtra("id",doubt.getId());
                intent.putExtra("sc_id",doubt.getSc_id());
                intent.putExtra("cid",doubt.getCid());
                intent.putExtra("doc",doubt.getImg());
                intent.putExtra("sub",doubt.getSubject());
                intent.putExtra("title",doubt.getTitle());
                intent.putExtra("des",doubt.getDescription());
                ctx.startActivity(intent);

            }
        });
        if(doubt.getStatus().equals("0")) {
            container.txtstatus.setText("Not Solved");
        }else
        {
            container.txtstatus.setText("Solved");
            container.update_btn.setVisibility(View.GONE);
        }
*/
        container.imgdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NOTICEBOARD = doubt.getDoc();
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
        return doubtArrayList.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        public TextView txtsub,txtdate,txttitle,txtstatus;
        RelativeLayout relassignment;
        ImageView txtdesc,imgdownload;
        public MyWidgetContainer(View itemView) {
            super(itemView);
            txtsub=itemView.findViewById(R.id.txtsub);
            txtdate=itemView.findViewById(R.id.txtdate);
            txtdesc=itemView.findViewById(R.id.txtdesc);
            relassignment=itemView.findViewById(R.id.relassignment);
            txttitle=itemView.findViewById(R.id.txttitle);
            txtstatus=itemView.findViewById(R.id.txtstatus);
            imgdownload=itemView.findViewById(R.id.imgdownload);




        }
    }

}
