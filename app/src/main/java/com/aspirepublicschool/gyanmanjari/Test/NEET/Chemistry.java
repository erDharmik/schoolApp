package com.aspirepublicschool.gyanmanjari.Test.NEET;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.Test.DBHelper;
import com.aspirepublicschool.gyanmanjari.Test.TestQuestion;
import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chemistry extends AppCompatActivity {
    private DBHelper mydb ;
    Context ctx=this;
    static ArrayList<TestQuestion> testQuestionArrayList=new ArrayList<>();
    String tst_id,sub;
    static TabLayout tabLayout;
    ViewPager viewPager;
    ChemistryAdpater adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemistry);
        mydb = new DBHelper(ctx);
        tst_id=getIntent().getExtras().getString("tst_id");
        sub=getIntent().getExtras().getString("sub");
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        SendRequest();
    }
    private void SendRequest() {
        Common.progressDialogShow(this);
        String Webserviceurl=Common.GetWebServiceURL()+"testquestion.php";
        //String Webserviceurl="https://www.zocarro.com/zocarro_mobile_app/ws/testquestion.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none").toUpperCase();
        final String stu_id = mPrefs.getString("stu_id","none");
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                    Common.progressDialogDismiss(Chemistry.this);

                    Log.d("aaa",response);
                    testQuestionArrayList.clear();
                    JSONArray array=new JSONArray(response);
                    int total=array.getJSONObject(0).getInt("total");
                    if(total==0)
                    {
                        Toast.makeText(Chemistry.this,"No Question",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                       NeetTest.txtquestion.setText(""+total);
                        for(int i=1;i<array.length();i++)
                        {                            /*  {
                            "qid": "QST1",
                                "question": "Test Question ?",
                                "a": "A",
                                "b": "B",
                                "c": "C",
                                "d": "D"
                        },*/
                            JSONObject object=array.getJSONObject(i);
                            if(object.getString("subject").equals("Chemistry")) {
                                testQuestionArrayList.add(new TestQuestion(object.getString("qid"), object.getString("question"), object.getString("a"), object.getString("b"),
                                        object.getString("c"), object.getString("d"), "Not Set", object.getString("q_img"), object.getString("a_img"), object.getString("b_img")
                                        , object.getString("c_img"), object.getString("d_img"), false, tst_id,object.getString("subject")));
                            }
                        }
                        for(int j=0;j<testQuestionArrayList.size();j++) {
                            if(NeetTest.dataflags==false) {
                                if (mydb.inserttest(testQuestionArrayList.get(j).getTst_id(), testQuestionArrayList.get(j).getQ_id(), testQuestionArrayList.get(j).getQuestion(),
                                        testQuestionArrayList.get(j).getA(), testQuestionArrayList.get(j).getB(), testQuestionArrayList.get(j).getC(), testQuestionArrayList.get(j).getD(), "Not Set", 0,
                                        testQuestionArrayList.get(j).getSub())) {

                                }
                            }
                        }
                        for (int k = 0; k <testQuestionArrayList.size(); k++) {
                            tabLayout.addTab(tabLayout.newTab());
                            TextView tv=(TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_view,null);
                            tv.setTextColor(Color.parseColor("#FFFFFF"));
                            Chemistry.tabLayout.getTabAt(k).setCustomView(tv).setText("" + (k+1));
                        }
                        adapter = new ChemistryAdpater(getSupportFragmentManager(), tabLayout.getTabCount());
                        viewPager.setAdapter(adapter);
                        viewPager.setOffscreenPageLimit(1);
                        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                viewPager.setCurrentItem(tab.getPosition());





                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Chemistry.this, ""+error, Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id",sc_id);
                data.put("cid",class_id);
                data.put("tst_id",tst_id);
                Log.d("data", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(3000,3,1));
        Volley.newRequestQueue(Chemistry.this).add(request);
    }
}
