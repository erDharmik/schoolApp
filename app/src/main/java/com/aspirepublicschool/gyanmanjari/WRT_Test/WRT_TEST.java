package com.aspirepublicschool.gyanmanjari.WRT_Test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.MainActivity;
import com.aspirepublicschool.gyanmanjari.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WRT_TEST extends AppCompatActivity {
    RecyclerView recwrttest;
    ArrayList<WRTTestModel> wrtTestModelArrayList=new ArrayList<>();
    Context ctx=this;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrt__test);
        allocatememory();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        String timeSettings = android.provider.Settings.Global.getString(
                ctx.getContentResolver(),
                android.provider.Settings.Global.AUTO_TIME);
        if (timeSettings.contentEquals("0")) {
            Log.d("Set", "True");
             /*   android.provider.Settings.Global.putString(
                        this.getContentResolver(),
                        android.provider.Settings.Global.AUTO_TIME, "1");*/
            Toast.makeText(ctx, "Sorry date and time changed.Please change in automatic to continue with test.", Toast.LENGTH_LONG).show();

        }
        else
        {
            Log.d("Set", "False");
            SendRequest();
        }
    }

    private void SendRequest() {
        Common.progressDialogShow(WRT_TEST.this);
        String Webserviceurl=Common.GetWebServiceURL()+"getWrtTest.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        final String stu_id = mPrefs.getString("stu_id","none");
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(WRT_TEST.this);
                Log.d("AAa",response);
               wrtTestModelArrayList.clear();

                try {
                    JSONArray array=new JSONArray(response);
                    int total=array.getJSONObject(0).getInt("total");
                    if(total==0) {
                        Toast.makeText(WRT_TEST.this, "Sorry no test Available!!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(WRT_TEST.this, MainActivity.class));
                        finish();
                    }
                    else {

                        for (int i = 1; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                             /*{
                                "sc_id": "SCIDN20",
                                    "t_id": "SCIDN20TIDN18",
                                    "cid": "CIDN108",
                                    "subject": "Chemistry",
                                    "stime": "10:00",
                                    "etime": "12:00",
                                    "q_doc": "Chemistry.pdf",
                                    "sol_doc": "Chemistry.pdf",
                                    "tst_date": "2020-07-01",
                                    "total": "50",
                                    "t_status": "1"
                            }*/
                           wrtTestModelArrayList.add(new WRTTestModel(
                                   object.getString("sc_id"), object.getString("t_id"),
                                    object.getString("cid"), object.getString("subject"),
                                   object.getString("stime"),object.getString("etime"),
                                   object.getString("q_doc"),object.getString("sol_doc"),
                                   object.getString("tst_date"),object.getString("total"),
                                   object.getString("tst_ans_status"),object.getString("obtain"),
                                   object.getString("tst_id"),object.getString("ans_doc"),
                                   object.getString("remark"),object.getString("gr_no")));

                        }
                        WRTTestAdapter adapter = new WRTTestAdapter(WRT_TEST.this,wrtTestModelArrayList);
                        recwrttest.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(WRT_TEST.this);
                Toast.makeText(WRT_TEST.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id",sc_id);
                data.put("cid",class_id);
                data.put("stu_id",stu_id);
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(WRT_TEST.this).add(request);
    }

    private void allocatememory() {
        recwrttest=findViewById(R.id.recwrttest);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(WRT_TEST.this);
        recwrttest.setLayoutManager(mLayoutManager);
        recwrttest.setItemAnimator(new DefaultItemAnimator());
    }
}
