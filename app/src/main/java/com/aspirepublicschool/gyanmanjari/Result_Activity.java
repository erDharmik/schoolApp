package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.tabs.TabLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result_Activity extends AppCompatActivity {

  /* List<PieEntry>  pieEntries = new ArrayList<>();
   List<BarEntry> entries = new ArrayList<>();*/
    RecyclerView recyclerView;
    //private PieChart pieChart;
    Button btn_progress;
   int obtain=0;
   int total=0;
     int progress=0;
   String PROGRESS;
   static String test_type;
    String subject;
    List<ResultModel> resultModelList = new ArrayList<>();
  static ResultModel resultModel;
    Context ctx = this;
    ViewPager result_details_viewpager;
    TabLayout result_details_tablayout;
   TextView textView_percentage,textView_grad;
    String res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        /*getSupportActionBar().setTitle("Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
      //  stu_id = findViewById(R.id.stu_id);
      //  loadData();
        result_details_tablayout = findViewById(R.id.result_details_tablayout);
        result_details_viewpager = findViewById(R.id.result_details_viewpager);
       // pieChart = findViewById(R.id.piechart);
        /* textView_percentage = findViewById(R.id.text_percentage);
         textView_grad = findViewById(R.id.text_grad);*/
        result_details_viewpager.setAdapter(new ResultDetailAdapter(getSupportFragmentManager(), result_details_tablayout.getTabCount()));
        result_details_viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(result_details_tablayout));
        result_details_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                result_details_viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

       // loadData();
   // btn_progress = findViewById(R.id.btn_ProgressGraph);
     /*   recyclerView = findViewById(R.id.result_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/
      /*  btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //res = stu_id.getText().toString();
                loadData();
            }
        });*/


    /*btn_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Result_Activity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_progress_dialog, null);
               //final PieChart pieChart = mView.findViewById(R.id.piechart);
             final TextView textView_grad = mView.findViewById(R.id.grad);
                final TextView textView_percentage = mView.findViewById(R.id.text_per);
                Common.progressDialogShow(Result_Activity.this);
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                final String stu_id = mPrefs.getString("stu_id", "none");
                final String school_id = mPrefs.getString("sc_id","none");
                Log.v("XYZ",stu_id);
                Log.v("PQR",school_id);
                String URL = Common.GetWebServiceURL()+"result.php";
                Log.v("URL",URL);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Common.progressDialogDismiss(Result_Activity.this);
                            JSONArray array = new JSONArray(response);
                         pieEntries.clear();
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
                                resultModel = new ResultModel( result.getString("stu_id"),
                                        result.getString("cid"),
                                        result.getString("t_id"),
                                        result.getString("type"),
                                        result.getString("subject"),
                                        result.getString("total"),
                                        result.getString("obtain"),
                                        result.getString("test_date"));
                                test_type = result.getString("type");
                                obtain = Integer.parseInt(resultModelList.get(i).getObtain_marks());
                                //Log.v("OBTAIN",String.valueOf(progress));
                                //total = Integer.parseInt(resultModel.getTotal_marks());

                                total = total+ Integer.parseInt(resultModelList.get(i).getTotal_marks());

                                progress = progress + obtain;
                            }
                            int growth=0;
                             growth = (progress*100)/total;
                            Log.v("LLLL",String.valueOf(growth));
                            Log.v("TOTAL",String.valueOf(total));
                            textView_percentage.setText(growth+"%");
                            if (growth>=50 && growth<=60){
                                textView_grad.setText("B");
                            }
                            if (growth>=70 && growth<=80){
                                textView_grad.setText("B+");
                            }
                            if (growth>=90 && growth<=95){
                                textView_grad.setText("A");
                            }
                            if (growth>=96 && growth<=100){
                                textView_grad.setText("A+");
                            }
                            if (growth>=30 && growth<=40){
                                textView_grad.setText("C+");
                            }
                            if (growth>=25 && growth<=29){
                                textView_grad.setText("C+");
                            }
                            if (growth>=20 && growth<=24){
                                textView_grad.setText("D");
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("DDDD",error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("stu_id",stu_id);
                        params.put("sc_id",school_id);
                        return params;
                    }
                };
                Volley.newRequestQueue(ctx).add(stringRequest);
                //     photoView.setImageResource(Glide.with(mCtx).load(URL_IMG_ANNOUNCEMENT).into(announcementHolder.img));
                mBuilder.setView(mView);
                final AlertDialog mDialog = mBuilder.create();
                Button btn_close=mView.findViewById(R.id.btn_close);
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDialog.show();

               // loadData();


            }

        });*/
        loadData();
    }
 private void loadData(){
       Common.progressDialogShow(Result_Activity.this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String stu_id = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id","none");
        Log.v("XYZ",stu_id);
        Log.v("PQR",sc_id);
        String URL = Common.GetWebServiceURL()+"result.php";
        Log.v("URL",URL);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                 Common.progressDialogDismiss(Result_Activity.this);
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

                        resultModel = new ResultModel( result.getString("stu_id"),
                                result.getString("cid"),
                                result.getString("t_id"),
                                result.getString("type"),
                                result.getString("subject"),
                                result.getString("total"),
                                result.getString("obtain"),
                                result.getString("test_date"));
                        //  Log.v("VVVV", ""+test_type);
                        obtain = Integer.parseInt(resultModel.getObtain_marks());
                        //Log.v("OBTAIN",String.valueOf(progress));
                        //total = Integer.parseInt(resultModel.getTotal_marks());

                        total = total+ Integer.parseInt(resultModel.getTotal_marks());

                        progress = progress + obtain;

                    }
                    //resultModelList.clear();
                  /*  int growth=0;
                     growth = (progress*100)/total;
                    Log.v("LLLL",String.valueOf(growth));
                    Log.v("TOTAL",String.valueOf(total));
                    textView_percentage.setText(growth+"% with");
                    if (growth>=50 && growth<=60){
                        textView_grad.setText("B Grad");
                    }
                    if (growth>=70 && growth<=80){
                        textView_grad.setText("B+ Grad");
                    }
                    if (growth>=90 && growth<=95){
                        textView_grad.setText("A Grad");
                    }
                    if (growth>=96 && growth<=100){
                        textView_grad.setText("A+ Grad");
                    }
                    if (growth>=30 && growth<=40){
                        textView_grad.setText("C+ Grad");
                    }
                    if (growth>=25 && growth<=29){
                        textView_grad.setText("C+ Grad");
                    }
                    if (growth>=20 && growth<=24){
                        textView_grad.setText("D Grad");
                    }
                    if (growth>=10 && growth<20){
                        textView_grad.setText("F Grad");
                    }*/
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("DDDD",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("stu_id",stu_id);
                params.put("sc_id",sc_id);
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }

}
