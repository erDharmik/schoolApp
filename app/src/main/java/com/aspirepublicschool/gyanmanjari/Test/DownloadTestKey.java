package com.aspirepublicschool.gyanmanjari.Test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.FileDownloader;
import com.aspirepublicschool.gyanmanjari.MainActivity;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DownloadTestKey extends AppCompatActivity {
    RelativeLayout reldownload;
    TextView txtdownload;
    String tst_id;
    TextView cur_val;
    String dwnload_file_path;
    String pdffile,file;
    File imageFile;
    private ProgressDialog pDialog;
    String fileName;
    Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_test_key);
        tst_id=getIntent().getExtras().getString("tst_id");
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        allocatememory();
        SendRequest();
        reldownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dwnload_file_path=fileName;
                file = fileName;
                Log.d("files",dwnload_file_path);
                pDialog = new ProgressDialog(ctx);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                downloadPDF();

            }
        });
        txtdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dwnload_file_path=fileName;
                file = fileName;
                Log.d("files",dwnload_file_path);
                pDialog = new ProgressDialog(ctx);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                downloadPDF();

            }
        });
    }

    private void SendRequest() {
        Common.progressDialogShow(ctx);
        String Webserviceurl=Common.GetWebServiceURL()+"keytest.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(ctx);
                try {
                    Log.d("aaa",response );
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(0);
                        fileName=object.getString("testkey");
                    }

                    file=fileName;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("tst_id", tst_id);
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(ctx).add(request);
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
            startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(DownloadTestKey.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
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
            pdffile = strings[1];
// ->five-point-someone-chetan-bhagat_ebook.pdf
            String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File folder = new File(extStorageDirectory);
            folder.mkdir();
            File pdfFile = new File(folder, pdffile);
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
    private void allocatememory() {
        reldownload=findViewById(R.id.reldownload);
        txtdownload=findViewById(R.id.txtdownload);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DownloadTestKey.this, MainActivity.class));
        finish();
    }
}
