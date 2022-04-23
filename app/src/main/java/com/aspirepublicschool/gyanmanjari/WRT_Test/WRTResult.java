package com.aspirepublicschool.gyanmanjari.WRT_Test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WRTResult extends AppCompatActivity {
    RecyclerView wrt_result;
    ArrayList<WRTResultModel> wrtResultModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrtresult);
        allocatememory();
        getWRTresult();
    }

    private void getWRTresult() {
        Common.progressDialogShow(WRTResult.this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        final String stu_id = mPrefs.getString("stu_id","none");
        String Webserviceurl=Common.GetWebServiceURL()+"getWrtResult.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(WRTResult.this);
                try {
                    Log.d("response", response);
                    wrtResultModels.clear();
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        wrtResultModels.add(new WRTResultModel(object.getString("sc_id"), object.getString("t_id"),
                                object.getString("cid"), object.getString("subject"),
                                object.getString("stime"),object.getString("etime"),
                                object.getString("q_doc"),object.getString("sol_doc"),
                                object.getString("tst_date"),object.getString("total"),
                                object.getString("tst_ans_status"),object.getString("obtain"),
                                object.getString("tst_id"),object.getString("ans_doc"),
                                object.getString("remark")));
                    }
                    WRTResultAdapter adapter = new WRTResultAdapter(WRTResult.this,wrtResultModels);
                    wrt_result.setAdapter(adapter);

                } catch (Exception e) {
                    e.printStackTrace();
                    Common.progressDialogDismiss(WRTResult.this);
                    Toast.makeText(  WRTResult.this, R.string.no_connection_toast,Toast.LENGTH_LONG ).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(WRTResult.this);
                Toast.makeText(  WRTResult.this, R.string.no_connection_toast,Toast.LENGTH_LONG ).show();

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
        Volley.newRequestQueue(WRTResult.this).add(request);
    }

    private void allocatememory() {
        wrt_result=findViewById(R.id.wrt_result);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(WRTResult.this);
        wrt_result.setLayoutManager(mLayoutManager);
        wrt_result.setItemAnimator(new DefaultItemAnimator());
    }
}
