 package com.aspirepublicschool.gyanmanjari.Homework;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import org.json.JSONArray;
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
import java.util.Map;


public class InsertHomeWork extends AppCompatActivity implements AsyncTaskCompleteListener {

    ImageView imgupload;
    static TextView upimg;
    Spinner spnhwid;
    Button btnsubmit;
    ArrayList<HWID> hwidArrayList=new ArrayList<>();
    ArrayList<String> hwidlist=new ArrayList<>();
    public static String subjectsselect,hw_id,sc_id,cid,doc,stu_id;
    Context ctx=this;
    public static String gr_id;
    ProgressDialog progressDialog;
    static String file;

    //pdf
    private ProgressDialog pd;
    private Button btn;
    private String url = "https://www.google.com";
    private static final int BUFFER_SIZE =  8*1024*1024;
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
        setContentView(R.layout.activity_insert_home_work);
        requestMultiplePermissions();
        imgupload = findViewById(R.id.imgupload);
        btnsubmit = findViewById(R.id.btnsubmit);
        spnhwid = findViewById(R.id.spnhwid);
        upimg=findViewById(R.id.upimg);
        upimg.setText("");
        gr_id= getIntent().getStringExtra("gr_no");
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        cid = mPrefs.getString("class_id","none");
        sc_id = mPrefs.getString("sc_id","none");
        stu_id = mPrefs.getString("stu_id","none");
        setSpinnerHomework();
        spnhwid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subjectsselect = spnhwid.getItemAtPosition(spnhwid.getSelectedItemPosition()).toString();
                hw_id=hwidArrayList.get(position).getHw_id();
                // Toast.makeText(getApplicationContext(),hw_id,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        imgupload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,1);
            }
        });

        upimg.setText(file);
        btnsubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(upimg.getText().toString().equals(""))
                {
                    upimg.setError("Please Select Document");
                    Toast.makeText(InsertHomeWork.this, "Please Select Document", Toast.LENGTH_LONG).show();
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

            // Log.d("ioooo",path);

            pdfPath = getFilePathFromURI(this,uri);
//            uploadPDFfile(path);

        }

        super.onActivityResult(requestCode, resultCode, data);

    }
    private void uploadPDFfile(String path) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("url", Common.GetWebServiceURL()+"homeworkPdf.php");
      // map.put("url", "http://zocarro.net/zocarro_mobile_app/ws/homeworkPdf.php");
        map.put("filename", path);
        map.put("sc_id", sc_id.toUpperCase());
        map.put("cid", cid);
        map.put("stu_id", stu_id);
        map.put("hw_id", hw_id);
        map.put("file", file);
        new MultiPartRequester(InsertHomeWork.this, map, GALLERY, this);
    }
    @Override
    public void onTaskCompleted(String response, int serviceCode)
    {
        Log.d("res", ""+response);
        if (serviceCode == GALLERY)
        {
            try
            {
                JSONObject jsonObject = new JSONObject(response);
                jsonObject.toString().replace("\\\\", "");
                if (jsonObject.getString("status").trim().equals("true"))
                {
                    pd.dismiss();
                    upimg.setText("");
                    Intent intent=new Intent(InsertHomeWork.this,HomeworkActivity.class);
                    startActivity(intent);
                    finish();
                    //SendRequest();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                try
                {
                    if (pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    Toast.makeText(InsertHomeWork.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                }
                catch (Exception e1)
                {

                }

            }
        }
    }

    private void SendRequest()
    {
        Common.progressDialogShow(InsertHomeWork.this);
        String WebServiceurl=Common.GetWebServiceURL()+"submit_homework.php"
                ;
        StringRequest request=new StringRequest(StringRequest.Method.POST,WebServiceurl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                   Common.progressDialogDismiss(InsertHomeWork.this);
                    JSONObject array=new JSONObject(response);
                    String message=array.getString("message");
                    Toast.makeText(InsertHomeWork.this, message, Toast.LENGTH_LONG).show();

                }
                catch (JSONException e)
                {
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
                data.put("sc_id", sc_id.toUpperCase());
                data.put("cid", cid);
                data.put("stu_id", stu_id);
                data.put("hw_id", hw_id);
                data.put("doc", file);

                return  data;
            }
        };
        Volley.newRequestQueue(InsertHomeWork.this).add(request);
    }


    public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        try {
            String fileName = getFileName(contentUri);
            final File folder = new File(Environment.getExternalStorageDirectory()
                    + "/" + Environment.DIRECTORY_DOWNLOADS);
            // have the object build the directory structure, if needed.
            if (!folder.exists()) {
                folder.mkdirs();
            }
            if (!TextUtils.isEmpty(fileName))
            {
                Date today=Calendar.getInstance().getTime();
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH:mm", Locale.getDefault());
                String var= format.format(today);

                ContextWrapper cw = new ContextWrapper(context);
                File directory = cw.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);

                File copyFile = new File(directory + File.separator + gr_id + "-" + var + ".pdf");
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
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
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

    private void setSpinnerHomework() {
        Common.progressDialogShow(ctx);
       String Webserviceurl= Common.GetWebServiceURL()+"gethwid.php";
        //String Webserviceurl= "https://www.zocarro.net/zocarro_mobile_app/ws/"+"gethwid.php";
        Request request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(ctx);
                    Log.d("$$$", response);
                    JSONArray array=new JSONArray(response);
                    String error=array.getJSONObject(0).getString("error");
                    if(error.equals("no error"))
                    {
                       int total=array.getJSONObject(1).getInt("total");
                        if(total!=0) {
                            for (int i = 2; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                hwidArrayList.add(new HWID(object.getString("hw_id"), object.getString("sub")));
                                hwidlist.add(object.getString("hw_id"));
                            }
                        }
                            if(hwidlist.size()==0)
                            {
                                Toast.makeText(InsertHomeWork.this, "Homework Already Submitted", Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(InsertHomeWork.this,HomeworkActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                spnhwid.setAdapter(new ArrayAdapter<String>(InsertHomeWork.this, android.R.layout.simple_spinner_dropdown_item, hwidlist));
                            }

                    }
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
                data.put("sc_id",sc_id);
                data.put("cid",cid);
                data.put("stu_id",stu_id);
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(InsertHomeWork.this).add(request);
    }


}
