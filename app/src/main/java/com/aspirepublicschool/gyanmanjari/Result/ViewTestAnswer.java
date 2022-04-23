package com.aspirepublicschool.gyanmanjari.Result;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewTestAnswer extends AppCompatActivity {
    String tst_id;
    static TabLayout tabLayout;
    ViewPager viewPager;
    QuestionAdapter adapter;
    static ArrayList<TestGivenAnswer> testGivenAnswers=new ArrayList<>();
    private static final String TAG = "ViewTestAnswer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test_answer);
        tst_id=getIntent().getExtras().getString("tst_id");
        getSupportActionBar().setElevation(0);

        allocatememory();
        SendRequest();
    }

    private void SendRequest() {
        Common.progressDialogShow(ViewTestAnswer.this);
        // String Webserviceurl="https://www.zocarro.com/zocarro_mobile_app/ws/testanswers.php";
        String Webserviceurl = Common.GetWebServiceURL() + "testanswers.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String sc_id = preferences.getString("sc_id", "none").toUpperCase();
        final String stu_id = preferences.getString("stu_id", "none").toUpperCase();
        final String class_id = preferences.getString("class_id", "none").toUpperCase();
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(ViewTestAnswer.this);
                    JSONArray array = new JSONArray(response);
                    testGivenAnswers.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        testGivenAnswers.add(new TestGivenAnswer(object.getString("tst_id"), object.getString("qid"), object.getString("question"), object.getString("a"),
                                object.getString("b"), object.getString("c"), object.getString("d"), object.getString("correct"), object.getString("ans"),
                                object.getString("sc_id"), object.getString("cid"), object.getString("solution")));
                    }
                    for (int k = 0; k < testGivenAnswers.size(); k++) {
                        tabLayout.addTab(tabLayout.newTab());
                        TextView tv = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_view, null);
                        tv.setTextColor(Color.parseColor("#FFFFFF"));
                        ViewTestAnswer.tabLayout.getTabAt(k).setCustomView(tv).setText("" + (k + 1));
                    }
                    adapter = new QuestionAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
                Map<String, String> data = new HashMap<>();
                data.put("sc_id", sc_id);
                data.put("stu_id", stu_id);
                data.put("tst_id", tst_id);
                data.put("cid", class_id);
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(ViewTestAnswer.this).add(request);

    }

    private void allocatememory() {
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
    }
}
