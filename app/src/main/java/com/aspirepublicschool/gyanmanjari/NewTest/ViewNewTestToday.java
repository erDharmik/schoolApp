package com.aspirepublicschool.gyanmanjari.NewTest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewNewTestToday extends AppCompatActivity {

    RecyclerView rectest;
    ArrayList<TodayNewTest> testArrayList=new ArrayList<>();
    TextView noTestText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_new_test_today);
        allocatememory();
        SendRequest();
    }
    private void SendRequest() {
        String Webserviceurl= Common.GetWebServiceURL()+"getNewTest.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        final String stu_id = mPrefs.getString("stu_id","none");
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("AAa",response);
                testArrayList.clear();
                try
                {
                    JSONArray array=new JSONArray(response);
                    int total=array.getJSONObject(0).getInt("total");
                    if(total==0)
                    {
                        noTestText.setVisibility(View.VISIBLE);
                        noTestText.setText("Sorry no test Available!");
                    }
                    else
                        {
                        noTestText.setVisibility(View.GONE);
                        for (int i = 1; i < array.length(); i++)
                        {
                            JSONObject object = array.getJSONObject(i);
                            testArrayList.add(new TodayNewTest(
                                    object.getString("tst_id"),
                                    object.getString("subject"),
                                    object.getString("total"),
                                    object.getString("t_date"),
                                    object.getString("des"),
                                    object.getString("stime"),
                                    object.getString("etime"),
                                    object.getString("pos"),
                                    object.getString("neg"),
                                    object.getString("t_type"),
                                    object.getString("total_reg"),
                                    object.getString("total_ireg"),
                                    object.getString("max_reg")));
                        }
                        TodayNewTestAdapter adapter = new TodayNewTestAdapter(ViewNewTestToday.this, testArrayList);
                        rectest.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewNewTestToday.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(ViewNewTestToday.this).add(request);
    }

    private void allocatememory() {
        noTestText = findViewById(R.id.noClassText);
        rectest=findViewById(R.id.rectest);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ViewNewTestToday.this);
        rectest.setLayoutManager(mLayoutManager);
        rectest.setItemAnimator(new DefaultItemAnimator());
    }
}