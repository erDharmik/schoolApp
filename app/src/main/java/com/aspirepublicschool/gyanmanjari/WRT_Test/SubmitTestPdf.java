package com.aspirepublicschool.gyanmanjari.WRT_Test;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.Homework.AsyncTaskCompleteListener;

import com.aspirepublicschool.gyanmanjari.Homework.InsertHomeWork;
import com.aspirepublicschool.gyanmanjari.Homework.MultiPartRequester;
import com.aspirepublicschool.gyanmanjari.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SubmitTestPdf extends AppCompatActivity implements AsyncTaskCompleteListener {
    static String tst_id,gr_no;
    public static String subjectsselect,hw_id,sc_id,cid,doc,stu_id;
    ImageView imgupload;
    static TextView upimg;
    Button btnsubmit;
    Context ctx=this;
    static String file;
    //pdf
    private ProgressDialog pd;
    private Button btn;
    private String url = "https://www.google.com";
    private static final int BUFFER_SIZE = 8*1024*1024;
    private ArrayList<HashMap<String, String>> arraylist;
    private static final String IMAGE_DIRECTORY = "/image";
    private final int GALLERY = 1;
    String pdfPath = "";
    HttpURLConnection httpURLConnection ;
    //    URL url;
    OutputStream outputStream;
    BufferedWriter bufferedWriter;
    int RC;
    boolean flag;
    String ImageName = "image_data" ;
    BufferedReader bufferedReader;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_test_pdf);
        tst_id=getIntent().getExtras().getString("tst_id");
        gr_no = getIntent().getExtras().getString("gr_no");

        imgupload = findViewById(R.id.imgupload);
        btnsubmit = findViewById(R.id.btnsubmit);
        upimg=findViewById(R.id.upimg);
        upimg.setText("");
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        cid = mPrefs.getString("class_id","none");
        sc_id = mPrefs.getString("sc_id","none");
        stu_id = mPrefs.getString("stu_id","none");
        imgupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,1);
            }
        });
        upimg.setText(file);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(upimg.getText().toString().equals(""))
                {
                    upimg.setError("Please Select Documnet");
                    Toast.makeText(SubmitTestPdf.this, "Please Select Documnet", Toast.LENGTH_LONG).show();
                }
                else {
                    uploadPDFfile(pdfPath);
                    pd = ProgressDialog.show(ctx, "Uploading", "Please Wait");
//                Intent i = new Intent(Insert_HomeWork.this, TestActivity.class);
//                startActivity(i);
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();

            String path = getFilePathFromURI(SubmitTestPdf.this,uri);
            Log.d("ioooo",path);

            pdfPath = path;
//            uploadPDFfile(path);
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadPDFfile(String path)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("url", Common.GetWebServiceURL()+"answerPaperPdf.php");
        map.put("filename", path);
        map.put("sc_id", sc_id.toUpperCase());
        map.put("cid", cid);
        map.put("stu_id", stu_id);
        map.put("tst_id", tst_id);
        map.put("file", file);
        new MultiPartRequester(SubmitTestPdf.this, map, GALLERY, this);
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {

        Log.d("res", ""+response);
        if (serviceCode == GALLERY) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                jsonObject.toString().replace("\\\\", "");
                if (jsonObject.getString("status").trim().equals("true")) {
                    pd.dismiss();
                    upimg.setText("");
                    Intent intent=new Intent(SubmitTestPdf.this, WRT_TEST.class);
                    startActivity(intent);
                    finish();
                    //SendRequest();


                }
            } catch (Exception e) {
                e.printStackTrace();

                if (pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(SubmitTestPdf.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
            }
        }
    }

    public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        try
        {
            String fileName = getFileName(contentUri);
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOWNLOADS);
            // have the object build the directory structure, if needed.
            if (!wallpaperDirectory.exists())
            {
                wallpaperDirectory.mkdirs();
            }
            if (!TextUtils.isEmpty(fileName))
            {
                Date today= Calendar.getInstance().getTime();
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
                String var= format.format(today);
//                File copyFile = new File(wallpaperDirectory + File.separator +stu_id+"-"+tst_id+"-"+var+"-answer"+ ".pdf");
                File copyFile = new File(wallpaperDirectory + File.separator +gr_no+"-"+var+".pdf");
                // create folder if not exists
                fileName = copyFile.getName();
                upimg.setText(fileName);
                file=fileName;
                Toast.makeText(context, fileName, Toast.LENGTH_LONG).show();
                copy(context, contentUri, copyFile);
                return copyFile.getAbsolutePath();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int copystream(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
        return count;
    }

    // Ask user for permisssions once.
    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report)
                    {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
//                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
}
