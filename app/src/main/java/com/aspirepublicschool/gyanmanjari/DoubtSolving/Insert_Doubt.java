package com.aspirepublicschool.gyanmanjari.DoubtSolving;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.MainActivity;
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

public class Insert_Doubt extends AppCompatActivity {
    Spinner spnsub;
    EditText edttitle, edtdesc;
    ImageView imgupicon, upimg;
    Button btnsubmit;
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
    String subjectsselect, Title, Des;
    ArrayList<String> subList=new ArrayList<>();
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    String t_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert__doubt);
        allocatemeory();
        t_id=getIntent().getExtras().getString("t_id");

        byteArrayOutputStream = new ByteArrayOutputStream();
        Fixbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.school);
        //SetSpinnerSubject();
       /* spnsub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subjectsselect = spnsub.getItemAtPosition(spnsub.getSelectedItemPosition()).toString();
                //Toast.makeText(getApplicationContext(),subjectsselect,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        imgupicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Title= TextUtils.htmlEncode(edttitle.getText().toString());
                Des= TextUtils.htmlEncode(edtdesc.getText().toString());
                //Log.d("@@@",Title);
                Log.d("@@@",Des);

                if(validateInput()) {
                    UploadImageToServer();
                }
            }
        });

    }

    private void allocatemeory() {
        //spnsub = findViewById(R.id.spnsub);
        //edttitle = findViewById(R.id.edttitle);
        edtdesc = findViewById(R.id.edtdesc);
        imgupicon = findViewById(R.id.imgupicon);
        upimg = findViewById(R.id.upimg);
        btnsubmit = findViewById(R.id.btnsubmit);
    }

    private boolean validateInput() {
       /* if (edttitle.getText().toString().isEmpty() == true) {
            edttitle.setError("Title cannot be empty");
            edttitle.requestFocus();
            return false;
        }*/
        if (edtdesc.getText().toString().isEmpty() == true) {
            edtdesc.setError("Description cannot be empty");
            edtdesc.requestFocus();
            return false;
        }

        return true;


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
                           /* case 1:
                                takePhotoFromCamera();
                                break;*/
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
                    Toast.makeText(Insert_Doubt.this, "Failed!", Toast.LENGTH_SHORT).show();
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
       // Title = TextUtils.htmlEncode(edttitle.getText().toString());
        Des = TextUtils.htmlEncode(edtdesc.getText().toString());
      //  Log.d("@@@", Title);
        Log.d("@@@", Des);
        Bitmap b2 = Bitmap.createScaledBitmap(Fixbitmap, 700, 700, false);
        Log.v("GGG", String.valueOf(b2.getWidth()));
        Log.v("GGG", String.valueOf(b2.getHeight()));
        b2.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
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
                progressDialog = ProgressDialog.show(Insert_Doubt.this, "Attachment is Uploading", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                Log.d("AAA", string1);
                progressDialog.dismiss();
                //imageName.setText("");

                Toast.makeText(Insert_Doubt.this, "Successfully Image Uploaded", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Insert_Doubt.this, MainActivity.class));
                finish();

                // editor.remove("status");

                Log.d("@@@@@", string1);
            }

            @Override
            protected String doInBackground(Void... params) {
                Insert_Doubt.ImageProcessClass imageProcessClass = new Insert_Doubt.ImageProcessClass();
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                final String class_id = mPrefs.getString("class_id", "none");
                final String sc_id = mPrefs.getString("sc_id", "none");
                final String stu_id = mPrefs.getString("stu_id", "none");
                HashMap<String, String> HashMapParams = new HashMap<String, String>();
                HashMapParams.put("path", Common.GetBaseImageURL() + "student/");

                HashMapParams.put(ImageName, ConvertImage);
                HashMapParams.put("stu_id", stu_id);
                HashMapParams.put("t_id",t_id );
                HashMapParams.put("title", "test");
                HashMapParams.put("des", "This message have attachment with message:"+Des);
                // Log.v("TTTT", "" + s_id);
                Log.v("TTTT", "" + ConvertImage);
                Log.v("TTTT", "" + sc_id);
                Log.v("TTTT", "" + Common.GetBaseImageURL() + "student/");
                Log.v("VVVVV", String.valueOf(HashMapParams));
                String FinalData = imageProcessClass.ImageHttpRequest("https://gyanmanjarividyapith.net/zocarro_mobile_app/ws/insert_doubt.php", HashMapParams);
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
    private void SetSpinnerSubject() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String sc_id = preferences.getString("sc_id","none").toLowerCase();
        final String class_id = preferences.getString("class_id","none");
        String WebServiceUrl= Common.GetWebServiceURL()+"getSubjects.php";
        Log.d("subject",WebServiceUrl);
        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray res=new JSONArray(response);
                    String error=res.getJSONObject(0).getString("error");
                    if(error.equals("no error")==false)
                    {

                    }
                    else
                    {
                        subList.clear();
                        int asize=res.length();
                        for(int i=1;i<asize;i++) {
                            JSONObject object=res.getJSONObject(i);
                            String subclass=object.getString("subject");
                            subList.add(subclass);

                        }
                        spnsub.setAdapter(new ArrayAdapter<String>(Insert_Doubt.this, android.R.layout.simple_spinner_dropdown_item,subList));


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id",sc_id.toLowerCase());
                data.put("cid",class_id);
                return data;
            }
        };
        Volley.newRequestQueue(Insert_Doubt.this).add(request);
    }
}
