package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentProfile extends AppCompatActivity {
    CircleImageView imgprofile, imgfather, imgmother;
    Context ctx = this;
    TextView txtdob, txtstname, txtstclass, txtgender, txtfathers, txtfcno, txtmother, txtmcno, txtstudentprofile, txtfatheredit, txtmotheredit;
    TextView txtevents, txtavggrade, profile_name, class_id, txtavgatt, rollno, txtsub, txtmarks, readmore;
    ArrayList<student_profileModel> student_profileModelList = new ArrayList();
    String classid, stu_id;
    Bitmap Fixbitmap, Fixbitmap1, Fixbitmap2;
    ByteArrayOutputStream byteArrayOutputStream, byteArrayOutputStream1, byteArrayOutputStream2;
    byte[] byteArray;
    String ConvertImage, Convertfname, Convertmname;
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
    ProgressDialog progressDialog;
    String s_id, sc_id;
    int flagimgupload;
    List<ResultModel> resultModelList = new ArrayList<>();
    ResultModel resultModel;
    int obtain = 0;
    int total = 0;
    int progress = 0;
    int present, absent, totaldays;
    List<AttendanceModel> attendanceModelList = new ArrayList<>();
    int result;
    String name, std, roll_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);

        s_id = mPrefs.getString("stu_id", "none");
        sc_id = mPrefs.getString("sc_id", "none");
        if (CheckNetwork.isInternetAvailable(getApplicationContext())) {
            allocatememory();
            loadStudentData();
            //loadClassresult();
            //loadTodayMenu();
            //loadAnnouncement();
           // loadavgData();


        } else {
            Intent i = new Intent(StudentProfile.this, NoInternetActivity.class);
            startActivity(i);
            finish();
        }
        if (ContextCompat.checkSelfPermission(StudentProfile.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 5);
            }
        }
        txtstudentprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
                flagimgupload = 1;
            }
        });
        txtfatheredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
                flagimgupload = 2;
            }
        });
        txtmotheredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
                flagimgupload = 3;
            }
        });

       /* readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctx.startActivity(new Intent(ctx, Result_Activity.class));

            }
        });*/

    }

    private void loadClassresult() {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String sc_id = mPrefs.getString("sc_id", "none").toUpperCase();
        final String cid = mPrefs.getString("class_id", "none").toUpperCase();
        final String stu_id = mPrefs.getString("stu_id", "none").toUpperCase();

        String Webserviceurl = Common.GetWebServiceURL() + "latestclasstest.php";
        Log.d("@@@web", Webserviceurl);
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject current = array.getJSONObject(i);
                        txtsub.setText(current.getString("subject"));
                        txtmarks.setText(current.getString("obtain") + "/" + current.getString("total"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("sc_id", sc_id.toLowerCase());
                data.put("cid", cid);
                data.put("stu_id", stu_id);
                return data;
            }
        };
        Volley.newRequestQueue(ctx).add(request);
    }

    private void loadAnnouncement() {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String school_id = mPrefs.getString("sc_id", "none").toUpperCase();
        Log.v("LLL", school_id);
        String MENU_URL = Common.GetWebServiceURL() + "recentAnnouncement.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MENU_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(ctx);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i <= array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        txtevents.setText(object.getString("header"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sc_id", school_id.toUpperCase());
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }

    private void loadavgData() {
        // Common.progressDialogShow(Result_Activity.this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stu_id = mPrefs.getString("stu_id", "none");
        final String school_id = mPrefs.getString("sc_id", "none");
        Log.v("XYZ", stu_id);
        Log.v("PQR", school_id);
        String URL = Common.GetWebServiceURL() + "result.php";
        Log.v("URL", URL);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //   Common.progressDialogDismiss(Result_Activity.this);
                    JSONArray array = new JSONArray(response);
                    // pieEntries.clear();
                    resultModelList.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject result = array.getJSONObject(i);
                        resultModelList.add(new ResultModel(
                                result.getString("stu_id"),
                                result.getString("cid"),
                                result.getString("t_id"),
                                result.getString("type"),
                                result.getString("subject"),
                                result.getString("total"),
                                result.getString("obtain"),
                                result.getString("test_date")
                        ));

                        resultModel = new ResultModel(result.getString("stu_id"),
                                result.getString("cid"),
                                result.getString("t_id"),
                                result.getString("type"),
                                result.getString("subject"),
                                result.getString("total"),
                                result.getString("obtain"),
                                result.getString("test_date"));

                        obtain = Integer.parseInt(resultModel.getObtain_marks());
                        Log.d("obtain", "" + obtain);

                        total = total + Integer.parseInt(resultModel.getTotal_marks());


                        progress = progress + obtain;

                    }
                    //resultModelList.clear();
                    if (total == 0) {
                        txtavggrade.setText("No Data Available");
                    } else {
                        int growth = 0;
                        growth = (progress * 100) / total;
                        Log.v("LLLL", String.valueOf(growth));
                        Log.v("TOTAL", String.valueOf(total));
                        //textView_percentage.setText(growth+"% with");


                        if (growth >= 96 && growth <= 100) {
                            txtavggrade.setText("A++");
                        }
                        if (growth >= 90 && growth <= 95) {
                            txtavggrade.setText("A+");
                        }
                        if (growth > 80 && growth < 90) {
                            txtavggrade.setText("A");
                        }
                        if (growth >= 70 && growth <= 80) {
                            txtavggrade.setText("B+");
                        }
                        if (growth > 60 && growth < 70) {
                            txtavggrade.setText("B+");
                        }
                        if (growth >= 50 && growth <= 60) {
                            txtavggrade.setText("B");
                        }
                        if (growth > 40 && growth < 50) {
                            txtavggrade.setText("C+");
                        }
                        if (growth >= 30 && growth <= 40) {
                            txtavggrade.setText("C");
                        }
                        if (growth >= 25 && growth <= 29) {
                            txtavggrade.setText("D+");
                        }
                        if (growth >= 20 && growth <= 24) {
                            txtavggrade.setText("D");
                        }
                        if (growth >= 10 && growth < 20) {
                            txtavggrade.setText("F");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("DDDD", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("stu_id", stu_id);
                params.put("sc_id", school_id);
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }

    private void loadAttendanceStatus() {
        // Common.progressDialogShow(ctx);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stu_id = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id", "none");
        //Log.v("STU_ID",stu_id);
        // String ATT_URL ="http://192.168.0.108:8080/school/ws/displayAttendance.php";
        String ATT_URL = Common.GetWebServiceURL() + "displayAttendance.php";
        Log.v("&&&", ATT_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ATT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Common.progressDialogDismiss(ctx);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i <= array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        attendanceModelList.add(new AttendanceModel(object.getString("attstatus"),
                                object.getString("date")
                        ));
                        AttendanceModel modelClass = new AttendanceModel(object.getString("attstatus"), object.getString("date"));
                        //   Log.v("DATA", modelClass.getDate());
                        if (modelClass.getStatus().equals("absent")) {
                            absent++;

                            String date = modelClass.getDate();
                            int Y = Integer.parseInt(date.substring(6, 10));
                            int M = Integer.parseInt(date.substring(3, 5));
                            int D = Integer.parseInt(date.substring(0, 2));
                            //calendarView.markDate(new DateData(Y, M, D).setMarkStyle(new MarkStyle(MarkStyle.DEFAULT, Color.RED)));
                        }
                        if (modelClass.getStatus().equals("present")) {
                            present++;
                            //  pieEntries.add(new PieEntry(present,modelClass.getStatus()));
                            //Log.v("P_COUNT",String.valueOf(present));
                            //Log.v("PRESENT",modelClass.getDate());
                            String date = modelClass.getDate();
                            int Y = Integer.parseInt(date.substring(6, 10));
                            //Log.v("P_YEAR",String.valueOf(Y));
                            int M = Integer.parseInt(date.substring(3, 5));
                            //Log.v("P_MONTH", String.valueOf(M));
                            int D = Integer.parseInt(date.substring(0, 2));
                            //Log.v("P_DDDD", String.valueOf(D));
                            //calendarView.markDate(new DateData(Y, M, D).setMarkStyle(new MarkStyle(MarkStyle.DEFAULT,Color.rgb(0,181,36))));
                        }
                        //txt_absent.setText(array.getJSONObject(i+1).getString("status"));
                        totaldays = absent + present;
                        Log.d("totaldays", "" + totaldays);
                        result = (present * 100) / totaldays;
                        Log.d("resultatt", "" + result);
                        txtavgatt.setText("" + result + "%");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("stu_id", stu_id);
                params.put("sc_id", sc_id);
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }

    private void allocatememory() {
        imgprofile = findViewById(R.id.imgprofile);
        imgfather = findViewById(R.id.imgfather);
        imgmother = findViewById(R.id.imgmother);
        txtdob = findViewById(R.id.txtdob);
        txtstname = findViewById(R.id.txtstname);
        txtfatheredit = findViewById(R.id.txtfatheredit);
        txtmotheredit = findViewById(R.id.txtmotheredit);
        txtstudentprofile = findViewById(R.id.txteditstudentprofile);
        txtstclass = findViewById(R.id.txtstclass);
        txtgender = findViewById(R.id.txtgender);
        txtfathers = findViewById(R.id.txtfathers);
        txtfcno = findViewById(R.id.txtfcno);
        txtmother = findViewById(R.id.txtmother);
        txtmcno = findViewById(R.id.txtmcno);
        byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream1 = new ByteArrayOutputStream();
        byteArrayOutputStream2 = new ByteArrayOutputStream();
        Fixbitmap = ((BitmapDrawable) imgprofile.getDrawable()).getBitmap();
        Fixbitmap1 = ((BitmapDrawable) imgfather.getDrawable()).getBitmap();
        Fixbitmap2 = ((BitmapDrawable) imgmother.getDrawable()).getBitmap();
        txtevents = findViewById(R.id.txtevents);
        //txtavggrade = findViewById(R.id.txtavggrade);
        txtavgatt = findViewById(R.id.txtavgatt);
        rollno = findViewById(R.id.rollno);
        txtsub = findViewById(R.id.txtsub);
        txtmarks = findViewById(R.id.txtmarks);
        //readmore = findViewById(R.id.readmore);
    }

    private void showPictureDialog() {
        flag = true;
        android.app.AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
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
                    if (flagimgupload == 1) {
                        Fixbitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        UploadImageStudentToServer();
                    } else if (flagimgupload == 2) {
                        Fixbitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        UploadImageFatherToServer();
                    } else {
                        Fixbitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        UploadImageMotherToServer();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(StudentProfile.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {

            //    ShowSelectedImage.setImageBitmap(FixBitmap);
            //  img_update.setVisibility(View.VISIBLE);
            if (flagimgupload == 1) {
                Fixbitmap = (Bitmap) data.getExtras().get("data");
                UploadImageStudentToServer();
            } else if (flagimgupload == 2) {
                Fixbitmap1 = (Bitmap) data.getExtras().get("data");
                UploadImageFatherToServer();
            } else {
                Fixbitmap2 = (Bitmap) data.getExtras().get("data");
                UploadImageMotherToServer();
            }
        }
    }

    private void loadStudentData() {
        Common.progressDialogShow(ctx);

        String STUDENT_PROFILE_URL = Common.GetWebServiceURL() + "student_profile.php";
        Log.v("profile", STUDENT_PROFILE_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, STUDENT_PROFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(ctx);
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject jsonObject = array.getJSONObject(i);

                        student_profileModelList.add(new student_profileModel(
                                jsonObject.getInt("id"),
                                jsonObject.getString("stu_id"),
                                jsonObject.getString("roll_no"),
                                jsonObject.getString("stu_img"),
                                jsonObject.getString("f_img"),
                                jsonObject.getString("m_img"),
                                jsonObject.getString("gr_no"),
                                jsonObject.getString("st_sname"),
                                jsonObject.getString("st_fname"),
                                jsonObject.getString("f_name"),
                                jsonObject.getString("m_name"),
                                jsonObject.getString("st_cno"),
                                jsonObject.getString("f_cno"),
                                jsonObject.getString("m_cno"),
                                jsonObject.getString("st_email"),
                                jsonObject.getString("cid"),
                                jsonObject.getString("gender"),
                                jsonObject.getString("med"),
                                jsonObject.getString("board"),
                                jsonObject.getString("address"),
                                jsonObject.getString("city"),
                                jsonObject.getString("state"),
                                jsonObject.getString("dob"),
                                jsonObject.getString("nation"),
                                jsonObject.getString("stream"),
                                jsonObject.getString("cast"),
                                jsonObject.getString("std"),
                                jsonObject.getString("division")

                        ));
                        txtstname.setText(jsonObject.getString("st_sname") + " " + jsonObject.getString("st_fname") + " " + jsonObject.getString("f_name"));


                        txtstclass.setText(jsonObject.getString("std") + "-" + jsonObject.getString("division"));
                        txtdob.setText(jsonObject.getString("dob"));
                        txtgender.setText(jsonObject.getString("gender"));
                        txtfathers.setText(jsonObject.getString("f_name"));
                        txtfcno.setText(jsonObject.getString("f_cno"));
                        txtmcno.setText(jsonObject.getString("m_cno"));
                        txtmother.setText(jsonObject.getString("m_name"));

                        String DP_URL = " https://gyanmanjarividyapith.net/zocarro/image/" + "student/" + student_profileModelList.get(i).getStu_img();
                        Glide.with(ctx).load(new URL(DP_URL)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(imgprofile);
                        String DPF_URL =  "https://gyanmanjarividyapith.net/zocarro/image/" + "student/father/" + student_profileModelList.get(i).getF_img();
                        Glide.with(ctx).load(new URL(DPF_URL)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(imgfather);
                        String DPM_URL =  "https://gyanmanjarividyapith.net/zocarro/image/" + "student/mother/" + student_profileModelList.get(i).getM_img();
                        Glide.with(ctx).load(new URL(DPM_URL)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(imgmother);
                        classid = jsonObject.getString("cid");
                        stu_id = jsonObject.getString("stu_id");
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("class_id", classid);
                        editor.putString("stu_id", stu_id);
                        editor.putString("st_dp", DP_URL);
                        editor.commit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("stu_id", s_id);
                params.put("sc_id", sc_id);
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }

    public void UploadImageStudentToServer() {
        int width = Fixbitmap.getWidth();
        int height = Fixbitmap.getHeight();
        Log.v("HHH", String.valueOf(width));
        Log.v("HHH", String.valueOf(height));
        Bitmap b2 = Bitmap.createScaledBitmap(Fixbitmap, 487, 379, false);
        Log.v("GGG", String.valueOf(b2.getWidth()));
        Log.v("GGG", String.valueOf(b2.getHeight()));
        b2.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        ConvertImage = null;
        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(StudentProfile.this, "Image is Uploading",
                        "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                progressDialog.dismiss();
                //imageName.setText("");

                Toast.makeText(StudentProfile.this, "Successfully Image Uploaded", Toast.LENGTH_LONG).show();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("st_dp");
                editor.commit();
                trimCache(ctx);

                loadStudentData();
                // editor.remove("status");

                Log.d("@@@@@", string1);
            }

            @Override
            protected String doInBackground(Void... params) {
                StudentProfile.ImageProcessClass imageProcessClass = new StudentProfile.ImageProcessClass();
                HashMap<String, String> HashMapParams = new HashMap<String, String>();
                HashMapParams.put("path", Common.GetBaseImageURL() + "student/");

                HashMapParams.put(ImageName, ConvertImage);

                HashMapParams.put("sc_id", sc_id);
                HashMapParams.put("stu_id", s_id);
                Log.v("TTTT", "" + s_id);
                Log.v("TTTT", "" + ImageName);
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
                String FinalData = imageProcessClass.ImageHttpRequest(Common.GetWebServiceURL()+"updateImage.php", HashMapParams);
                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public void UploadImageFatherToServer() {
        int width = Fixbitmap1.getWidth();
        int height = Fixbitmap1.getHeight();
        Log.v("HHH", String.valueOf(width));
        Log.v("HHH", String.valueOf(height));
        Bitmap b2 = Bitmap.createScaledBitmap(Fixbitmap1, 487, 379, false);
        Log.v("GGG", String.valueOf(b2.getWidth()));
        Log.v("GGG", String.valueOf(b2.getHeight()));
        b2.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream1);
        byteArray = byteArrayOutputStream1.toByteArray();
        Convertfname = Base64.encodeToString(byteArray, Base64.DEFAULT);
        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(StudentProfile.this, "Image is Uploading", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                progressDialog.dismiss();
                //imageName.setText("");

                Toast.makeText(StudentProfile.this, "Successfully Image Uploaded", Toast.LENGTH_LONG).show();
                trimCache(ctx);

                // editor.remove("status");

                Log.d("@@@@@", string1);
            }

            @Override
            protected String doInBackground(Void... params) {
                StudentProfile.ImageProcessClass imageProcessClass = new StudentProfile.ImageProcessClass();
                HashMap<String, String> HashMapParams = new HashMap<String, String>();
                HashMapParams.put("path", Common.GetBaseImageURL() + "student/");

                //    HashMapParams.put(Des,GetDesFromEditText);
                // HashMapParams.put(ImageTag, GetImageNameFromEditText);
                HashMapParams.put(ImageName, Convertfname);

                HashMapParams.put("sc_id", sc_id);
                HashMapParams.put("stu_id", s_id);
                Log.v("TTTT", "" + s_id);
                Log.v("TTTT", "" + ImageName);
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
                String FinalData = imageProcessClass.ImageHttpRequest(Common.GetWebServiceURL()+"updateFatherImage.php", HashMapParams);
                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public void UploadImageMotherToServer() {
        int width = Fixbitmap2.getWidth();
        int height = Fixbitmap2.getHeight();
        Log.v("HHH", String.valueOf(width));
        Log.v("HHH", String.valueOf(height));
        Bitmap b2 = Bitmap.createScaledBitmap(Fixbitmap2, 487, 379, false);
        Log.v("GGG", String.valueOf(b2.getWidth()));
        Log.v("GGG", String.valueOf(b2.getHeight()));
        b2.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream2);
        byteArray = byteArrayOutputStream2.toByteArray();
        Convertmname = Base64.encodeToString(byteArray, Base64.DEFAULT);
        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(StudentProfile.this, "Image is Uploading", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
                progressDialog.dismiss();
                //imageName.setText("");

                Toast.makeText(StudentProfile.this, "Successfully Image Uploaded", Toast.LENGTH_LONG).show();
                trimCache(ctx);
                // editor.remove("status");

                Log.d("@@@@@", string1);
            }

            @Override
            protected String doInBackground(Void... params) {
                StudentProfile.ImageProcessClass imageProcessClass = new StudentProfile.ImageProcessClass();
                HashMap<String, String> HashMapParams = new HashMap<String, String>();
                HashMapParams.put("path", Common.GetBaseImageURL() + "student/");

                //    HashMapParams.put(Des,GetDesFromEditText);
                // HashMapParams.put(ImageTag, GetImageNameFromEditText);
                HashMapParams.put(ImageName, Convertmname);

                HashMapParams.put("sc_id", sc_id);
                HashMapParams.put("stu_id", s_id);
                Log.v("TTTT", "" + s_id);
                Log.v("TTTT", "" + ImageName);
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
                String FinalData = imageProcessClass.ImageHttpRequest(Common.GetWebServiceURL()+"updateMotherImage.php", HashMapParams);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(StudentProfile.this, "Unable to use Camera..Please Allow us to use Camera", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else {
            return false;
        }
    }


}
