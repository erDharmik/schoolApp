package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class StaffDetails extends AppCompatActivity {
    RecyclerView recteacherdetils;
    ArrayList<Staff> staffList = new ArrayList<>();
    TextView txtteacher, txttcno;
    CircleImageView imgclassteach;
    Context ctx = this;
    String classteach_fname, classteach_lname, class_cno, imgurl, t_id;
    int REQUEST_PHONE_CALL = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_details);
        allocatememory();
        recteacherdetils.addItemDecoration(new DividerItemDecoration(recteacherdetils.getContext(), DividerItemDecoration.VERTICAL));
        if (ContextCompat.checkSelfPermission(ctx.getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(StaffDetails.this, new String[]{Manifest.permission.CALL_PHONE,}, REQUEST_PHONE_CALL);
        } else {

        }
//        loadClassTeacherDetails();
        SendRequest();
    }

    private void loadClassTeacherDetails() {
        Common.progressDialogShow(StaffDetails.this);
        String Webserviceurl = Common.GetWebServiceURL() + "teacherDetail.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String class_id = preferences.getString("class_id", "none");
        final String sc_id = preferences.getString("sc_id", "none").toLowerCase();
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray array = new JSONArray(response);
                    Log.d("resss", response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject result = array.getJSONObject(i);
                        Log.d("QQQQ", response);
                        /*{
                            "t_id": "SCIDN1TIDN1",
                                "t_img": "K.png",
                                "t_fname": "Shubhnagiiii",
                                "t_lname": "Shukla",
                                "t_cont": 9033532883,
                                "t_status": 0
                        }*/
                        t_id = result.getString("t_id");
                        Log.d("t_id", t_id);
                        imgurl = result.getString("t_img");
                        classteach_fname = result.getString("t_fname");
                        classteach_lname = result.getString("t_lname");
                        class_cno = result.getString("t_cont");
                        txtteacher.setText(classteach_fname + " " + classteach_lname);
                       /* txttcno.setText(class_cno);*/
//                        String URL_IMAGE = Common.GetBaseImageURL() + "teacher/" + imgurl;
//                        Glide.with(ctx).load(URL_IMAGE).into(imgclassteach);
                    }
                   /* txttcno.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent SwitchActivity = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + class_cno));
                            int checkPermission = ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE);
                            startActivity(SwitchActivity);
                            finish();

                        }
                    });
*/
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
//                data.put("class_id", class_id);
//                data.put("sc_id", sc_id.toUpperCase());

                data.put("class_id", class_id);
                data.put("sc_id", sc_id);
                data.put("t_id", t_id);
                return data;
            }
        };
        Volley.newRequestQueue(ctx).add(request);


    }

    private void SendRequest() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String class_id = preferences.getString("class_id", "none");
        final String sc_id = preferences.getString("sc_id", "none").toUpperCase();

        String Webserviceurl = Common.GetWebServiceURL() + "classTeacherDetails.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Common.progressDialogDismiss(StaffDetails.this);
                    JSONArray array = new JSONArray(response);
                    staffList.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject result = array.getJSONObject(i);
                       /* {
                            "lecture": "Hindi",
                                "t_lname": "Patel",
                                "t_fname": "Mahir",
                                "t_cont": 8758809709
                        },
*/

                        staffList.add(new Staff(
                                result.getString("t_fname"),
                                result.getString("t_lname"),
                                result.getString("subject"),
                                result.getString("t_img"),
                                result.getString("t_cno")

                        ));
                    }
                    StaffAdapter adapter = new StaffAdapter(ctx, staffList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx);
                    recteacherdetils.setLayoutManager(mLayoutManager);
                    recteacherdetils.setItemAnimator(new DefaultItemAnimator());
                    recteacherdetils.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("sc_id", sc_id);
                data.put("c_id", class_id);
                return data;
            }
        };

        Volley.newRequestQueue(ctx).add(request);

    }

    private void allocatememory() {
        recteacherdetils = findViewById(R.id.recteacherdetils);
        /*txtteacher = findViewById(R.id.txtteacher);
        //txttcno = findViewById(R.id.txttcno);
        imgclassteach = findViewById(R.id.imgclassteach);*/
        recteacherdetils.addItemDecoration(new DividerItemDecoration(recteacherdetils.getContext(), DividerItemDecoration.VERTICAL));
    }

}
