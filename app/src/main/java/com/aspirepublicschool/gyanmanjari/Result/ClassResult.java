package com.aspirepublicschool.gyanmanjari.Result;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;

import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassResult extends AppCompatActivity
{

    ViewPager viewhomework;
    TabLayout tabhomework;
    Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_result);

        viewhomework=findViewById(R.id.viewhomework);
        tabhomework=findViewById(R.id.tabhomework);
        OfflineTestAdapter homeWorkDetails=new OfflineTestAdapter(getSupportFragmentManager(),tabhomework.getTabCount());
        viewhomework.setAdapter(homeWorkDetails);
        viewhomework.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabhomework));
        tabhomework.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewhomework.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });
    }

//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        builder.detectFileUriExposure();
//       SendRequest();

//    private void SendRequest()
//    {
//        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        final String class_id = mPrefs.getString("class_id","none");
//        final String sc_id = mPrefs.getString("sc_id","none");
//        final String stu_id = mPrefs.getString("stu_id","none");
//        String Webserviceurl= Common.GetWebServiceURL()+"classResult.php";
//       // String Webserviceurl="https://www.zocarro.com/zocarro_mobile_app/ws/"+"classResult.php";
//        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>()
//        {
//            @Override
//            public void onResponse(String response)
//            {
//                resultArrayList.clear();
//                try
//                {
//                    Log.d("AAA", response);
//                    JSONArray array=new JSONArray(response);
//                    for (int i=0;i<array.length();i++)
//                    {
//                        JSONObject object=array.getJSONObject(i);
//                        resultArrayList.add(new Result(object.getString("total"),
//                                object.getString("obtain"),
//                                object.getString("subject"),
//                                object.getString("t_date"),
//                                object.getString("tst_id"),
//                                object.getString("status"),
//                                object.getString("t_type"),
//                                object.getString("testkey")));
//                    }
//                    ResultAdapter adapter=new ResultAdapter(ClassResult.this, resultArrayList);
//                    result_recyclerView.setAdapter(adapter);
//                }
//                catch (JSONException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener()
//        {
//            @Override
//            public void onErrorResponse(VolleyError error)
//            {
//
//            }
//        })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError
//            {
//                Map<String,String> data=new HashMap<>();
//                data.put("sc_id", sc_id);
//                data.put("cid", class_id);
//                data.put("stu_id",stu_id);
//                return data;
//            }
//        };
//        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
//        Volley.newRequestQueue(ClassResult.this).add(request);
//    }
//    private void allocatememory()
//    {
//        result_recyclerView=findViewById(R.id.result_recyclerView);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ClassResult.this);
//        result_recyclerView.setLayoutManager(mLayoutManager);
//        result_recyclerView.setItemAnimator(new DefaultItemAnimator());
//    }
}
