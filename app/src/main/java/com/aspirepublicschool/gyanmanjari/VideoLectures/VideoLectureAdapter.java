package com.aspirepublicschool.gyanmanjari.VideoLectures;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.TimeZone.getTimeZone;

public class VideoLectureAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<VideoLectureModel> homeWorkDetailsList;
    ProgressBar pb;
    Dialog dialog;
    int downloadedSize = 0;
    int totalSize = 0;
    String URL_IMG_NOTICEBOARD;
    TextView cur_val;
    String dwnload_file_path;
    String pdffile,file;
    File imageFile;

    public VideoLectureAdapter(Context ctx, ArrayList<VideoLectureModel> homeWorkDetailsList) {
        this.ctx = ctx;
        this.homeWorkDetailsList = homeWorkDetailsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_video_lecture, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final VideoLectureModel homeWorkDetails= homeWorkDetailsList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;

        container.txtsub.setText(homeWorkDetails.getSubject());
        container.txtdate.setText(homeWorkDetails.getDate());
        container.txtdesc.setText( String.valueOf(Html.fromHtml(homeWorkDetails.getDes())));

        container.txtreadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(ctx);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                View mView = inflater.inflate(R.layout.activity_display_video, null);

                TextView txtsub,txtdesc;
                txtsub=mView.findViewById(R.id.txtsub);
                txtdesc=mView.findViewById(R.id.txtdesc);
                txtsub.setText(homeWorkDetails.getSubject());
                txtdesc.setText(homeWorkDetails.getDes());


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

        // onclick on link code

        container.txtLink.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if(homeWorkDetails.getStatus().equals("1")) {
                    try {
                        Date time1 = new SimpleDateFormat("HH:mm").parse(homeWorkDetails.getStime());
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.setTime(time1);
                        calendar1.add(Calendar.DATE, 1);


                        Date time2 = new SimpleDateFormat("HH:mm").parse(homeWorkDetails.getEtime());
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
                            attendancestudentlink1( homeWorkDetails.getLink(),homeWorkDetails.getSubject(),homeWorkDetails.getId());
                        }
                        else
                        {
                            Toast.makeText(ctx, "Sorry!!,Video can be played only on given time,You Can watch it Later in History Tab", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(ctx, "Sorry!!,Video can't be played only!!", Toast.LENGTH_LONG).show();
                }


            }
        });

        // onclick on youtube link

        container.txtylink.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if(homeWorkDetails.getStatus1().equals("1"))
                {


                  /*  try {
                        LocalTime start = LocalTime.parse(homeWorkDetails.getStime());
                        LocalTime stop = LocalTime.parse(homeWorkDetails.getEtime());
                        @SuppressLint({"NewApi", "LocalSuppress"}) final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                        final String date = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                        Log.d("####", date);
                        LocalTime target = LocalTime.parse(date);
                        Boolean isTargetAfterStartAndBeforeStop = (target.isAfter(start) && target.isBefore(stop));
                        if (isTargetAfterStartAndBeforeStop) {
                            Log.d("!!", "TRuE");
                            attendancestudentlink2( homeWorkDetails.getLinkyt(),homeWorkDetails.getSubject(),homeWorkDetails.getId());


                        } else {
                            Log.d("!!", "False");
                            Toast.makeText(ctx, "Sorry!!,Video can be played only on given time,You Can watch it Later in History Tab", Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception e)
                    {
                        Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                    }
*/

                    try {



                        Date time1 = new SimpleDateFormat("HH:mm").parse(homeWorkDetails.getStime());

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.setTime(time1);
                        calendar1.add(Calendar.DATE, 1);

                        Date time2 = new SimpleDateFormat("HH:mm").parse(homeWorkDetails.getEtime());
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
//                        if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                        if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                            //checkes whether the current time is between 14:49:00 and 20:11:13.
                            //System.out.println("true");
                            attendancestudentlink2( homeWorkDetails.getLinkyt(),homeWorkDetails.getSubject(),homeWorkDetails.getId());

                        }
                        else
                        {

                            Toast.makeText(ctx, "Sorry!!,Video can be played only on given time,You Can watch it Later in History Tab", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                       //Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                        Toast.makeText(ctx, "not", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(ctx, "Sorry!!,Link has been closed by admin", Toast.LENGTH_LONG).show();
                }
            }
        });

         container.txtstime.setText(homeWorkDetails.getStime());
         container.txtetime.setText(homeWorkDetails.getEtime());

    }
    private void attendancestudentlink2(final String link, final String subject, final String id) {
        Common.progressDialogShow(ctx);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        final String stu_id = mPrefs.getString("stu_id","none");
        String HOMEWORK_URL = Common.GetWebServiceURL()+"attendlecture.php";
        //String HOMEWORK_URL = "http://www.zocarro.net/zocarro_mobile_app/ws/attendlecture.php";

        Log.v("LINK",HOMEWORK_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOMEWORK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {



                    Common.progressDialogDismiss(ctx);
                    Log.d("@@@",response );
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        String message=jsonObject.getString("message");

                        if(message.equals("Attend")) {

                            Uri uri = Uri.parse(link);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            ctx.startActivity(intent);
                        }
                        else if(message.equals("Already Submitted"))
                        {

                            Uri uri = Uri.parse(link);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            ctx.startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(ctx, "Try Again", Toast.LENGTH_LONG).show();
                        }



                    }
                    // loadTeacherData(t_id);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(ctx);
                Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sc_id",sc_id.toUpperCase());
                params.put("cid",class_id);
                params.put("stu_id",stu_id);
                params.put("sub",subject);
                params.put("id",id);
                Log.d("##",params.toString());

                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }

    private void attendancestudentlink1(final String link, final String subject, final String id) {
        Common.progressDialogShow(ctx);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        final String stu_id = mPrefs.getString("stu_id","none");
        String HOMEWORK_URL = Common.GetWebServiceURL()+"attendlecture.php";
        //String HOMEWORK_URL = "http://www.zocarro.net/zocarro_mobile_app/ws/attendlecture.php";

        Log.v("LINK",HOMEWORK_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOMEWORK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(ctx);
                    Log.d("@@@",response );
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        String message=jsonObject.getString("message");
                        if(message.equals("Attend"))
                        {
                            Intent intent = new Intent(ctx, VideoPlaying.class);
                            intent.putExtra("video_name", link);
                            intent.putExtra("subject", subject);
                            ctx.startActivity(intent);
                        }
                        else if(message.equals("Already Submitted"))
                        {
                            Intent intent = new Intent(ctx, VideoPlaying.class);
                            intent.putExtra("video_name", link);
                            intent.putExtra("subject", subject);
                            ctx.startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(ctx, "Try Again", Toast.LENGTH_LONG).show();
                        }



                    }
                    // loadTeacherData(t_id);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(ctx);
                Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sc_id",sc_id.toUpperCase());
                params.put("cid",class_id);
                params.put("stu_id",stu_id);
                params.put("sub",subject);
                params.put("id",id);
                Log.d("##",params.toString());

                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
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

    @Override
    public int getItemCount() {
        return homeWorkDetailsList.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        public TextView txtsub,txtdate,txtdesc,txtreadmore,txtLink,txtstime,txtetime,txtylink;
        RelativeLayout relassignment;
        public MyWidgetContainer(View itemView) {
            super(itemView);


            txtsub=itemView.findViewById(R.id.txtsub);
            txtdate=itemView.findViewById(R.id.txtdate);
            txtreadmore=itemView.findViewById(R.id.txtreadmore);
            txtdesc=itemView.findViewById(R.id.txtdesc);
            txtLink=itemView.findViewById(R.id.txtLink);
            txtylink=itemView.findViewById(R.id.txtylink);
            relassignment=itemView.findViewById(R.id.relassignment);
            txtstime=itemView.findViewById(R.id.txtstime);
            txtetime=itemView.findViewById(R.id.txtetime);
        }
    }

}
