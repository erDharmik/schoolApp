package com.aspirepublicschool.gyanmanjari.Homework;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class InsertImageHomework extends AppCompatActivity {
    Spinner spnhwid;
    ImageView imgupload,upimg;
    Button btnsubmit;
    ArrayList<HWID> hwidArrayList=new ArrayList<>();
    ArrayList<String> hwidlist=new ArrayList<>();
    public static String subjectsselect,hw_id,sc_id,cid,doc,stu_id;
    Context ctx=this;
    Bitmap Fixbitmap;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;
    String ConvertImage;
    HttpURLConnection httpURLConnection;
    URL url;
    OutputStream outputStream;
    BufferedWriter bufferedWriter;
    int RC;
    boolean flag;
    String ImageName = "image_data";
    BufferedReader bufferedReader;
    StringBuilder stringBuilder;
    boolean check = true;
    private int GALLERY = 1, CAMERA = 2;
    int PICK_IMAGE_REQUEST = 111;
    ProgressDialog progressDialog;
    int flagimgupload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_image_homework);
        allocatememory();
        setSpinnerHomework();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        cid = mPrefs.getString("class_id", "none");
        sc_id = mPrefs.getString("sc_id", "none");
        stu_id = mPrefs.getString("stu_id", "none");
        byteArrayOutputStream = new ByteArrayOutputStream();
        spnhwid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subjectsselect = spnhwid.getItemAtPosition(spnhwid.getSelectedItemPosition()).toString();
                hw_id=hwidArrayList.get(position).getHw_id();
                // Toast.makeText(getApplicationContext(),hw_id,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        imgupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Fixbitmap==null) {
                    Toast.makeText(ctx,"Please,Select image to upload",Toast.LENGTH_LONG).show();
                }
                else
                {
                    UploadImageToServer();
                }
            }
        });

    }
    private void showPictureDialog() {
        flag = true;
        //Fixbitmap=null;
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {


                    // ShowSelectedImage.setImageResource(0);
                    // ShowSelectedImage.setImageBitmap(FixBitmap);
                    //    img_update.setVisibility(View.VISIBLE);

                    Fixbitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    upimg.setImageBitmap(Fixbitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(InsertImageHomework.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {

            //    ShowSelectedImage.setImageBitmap(FixBitmap);
            //  img_update.setVisibility(View.VISIBLE);

            Fixbitmap = (Bitmap) data.getExtras().get("data");
            upimg.setImageBitmap(Fixbitmap);


        }
    }

    public void UploadImageToServer() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int width = Fixbitmap.getWidth();
        int height = Fixbitmap.getHeight();
        Log.v("HHH", String.valueOf(width));
        Log.v("HHH", String.valueOf(height));
        Bitmap b2 = Bitmap.createScaledBitmap(Fixbitmap, 487, 379, false);
        Log.v("GGG", String.valueOf(b2.getWidth()));
        Log.v("GGG", String.valueOf(b2.getHeight()));
        b2.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        // ConvertImage=null;
        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

     /*   else {
            Fixbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.school);
            int width = Fixbitmap.getWidth();
            int height = Fixbitmap.getHeight();
            Log.v("HHH",String.valueOf(width));
            Log.v("HHH",String.valueOf(height));
            Title= TextUtils.htmlEncode(title.getText().toString());
            Des= TextUtils.htmlEncode(des.getText().toString());
            Log.d("@@@",Title);
            Log.d("@@@",Des);
            Timeline= timeline.getText().toString();
            Bitmap b2 = Bitmap.createScaledBitmap(Fixbitmap, 487, 379, false);
            Log.v("GGG",String.valueOf(b2.getWidth()));
            Log.v("GGG",String.valueOf(b2.getHeight()));
            b2.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
            byteArray = byteArrayOutputStream.toByteArray();
            ConvertImage=null;
            ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }*/

        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(InsertImageHomework.this, "Homework is Submitting", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                progressDialog.dismiss();
                //imageName.setText("");

                Toast.makeText(InsertImageHomework.this, "Successfully Image Uploaded", Toast.LENGTH_LONG).show();
                startActivity(new Intent(InsertImageHomework.this, HomeworkActivity.class));
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("st_dp");
                editor.commit();

                // editor.remove("status");

                Log.d("@@@@@", string1);
            }

            @Override
            protected String doInBackground(Void... params) {
                InsertImageHomework.ImageProcessClass imageProcessClass = new InsertImageHomework.ImageProcessClass();
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                final String class_id = mPrefs.getString("class_id", "none");
                final String sc_id = mPrefs.getString("sc_id", "none").toUpperCase();
                final String stu_id = mPrefs.getString("stu_id", "none");
                HashMap<String, String> HashMapParams = new HashMap<String, String>();
                HashMapParams.put("path", Common.GetBaseImageURL() + "student/");

                //    HashMapParams.put(Des,GetDesFromEditText);
                // HashMapParams.put(ImageTag, GetImageNameFromEditText);
                HashMapParams.put(ImageName, ConvertImage);

                HashMapParams.put("sc_id", sc_id);
                HashMapParams.put("cid", class_id);
                HashMapParams.put("stu_id", stu_id);
                HashMapParams.put("hw_id", hw_id);
                // Log.v("TTTT", "" + s_id);
                Log.v("TTTT", "" + ConvertImage);
                Log.v("TTTT", "" + sc_id);
                Log.v("TTTT", "" + Common.GetBaseImageURL() + "student/");
                Log.v("VVVVV", String.valueOf(HashMapParams));
                //     HashMapParams.put(Date,GetdatefromEditText);
/*                cid.setText("");
                sc_id.setText("");
                ac_year.setText("");
                ac_expdate.setText("");
                date.setText("");
                imageName.setText("");*/
                String FinalData = imageProcessClass.ImageHttpRequest(Common.GetWebServiceURL()+"submit_homework.php", HashMapParams);
                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass {
        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                url = new URL(requestURL);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(20000);
                httpURLConnection.setConnectTimeout(20000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                outputStream = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(bufferedWriterDataFN(PData));
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                RC = httpURLConnection.getResponseCode();
                if (RC == HttpsURLConnection.HTTP_OK) {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    stringBuilder = new StringBuilder();
                    String RC2;
                    while ((RC2 = bufferedReader.readLine()) != null) {
                        stringBuilder.append(RC2);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {
            stringBuilder = new StringBuilder();
            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilder.append("&");
                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }
            return stringBuilder.toString();
        }
    }



    private void setSpinnerHomework() {
        String Webserviceurl= Common.GetWebServiceURL()+"gethwid.php";
        Request request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("aaa", response);
                    JSONArray array=new JSONArray(response);
                    String error=array.getJSONObject(0).getString("error");
                    if(error.equals("no error"))
                    {
                        int total=array.getJSONObject(1).getInt("total");
                        if(total!=0)
                        {
                            for(int i=2;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                hwidArrayList.add(new HWID(object.getString("hw_id"),object.getString("sub")));
                                hwidlist.add(object.getString("hw_id"));
                            }
                            spnhwid.setAdapter(new ArrayAdapter<String>(InsertImageHomework.this, android.R.layout.simple_spinner_dropdown_item,hwidlist));
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
        Volley.newRequestQueue(InsertImageHomework.this).add(request);
    }

    private void allocatememory() {
        spnhwid=findViewById(R.id.spnhwid);
        imgupload=findViewById(R.id.imgupicon);
        upimg=findViewById(R.id.upimg);
        btnsubmit=findViewById(R.id.btnsubmit);
    }

}
