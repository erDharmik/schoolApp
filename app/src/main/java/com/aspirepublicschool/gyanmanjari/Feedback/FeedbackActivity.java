package com.aspirepublicschool.gyanmanjari.Feedback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedbackActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    String question, q_id,t_id;
    static String answer;
    static List<String> ans = new ArrayList<>();
    static List<ansList> ansListArrayList = new ArrayList<>();
    List<QuestionList> questionListArrayList = new ArrayList<>();
    String sc_id,feeds, stu_id;
    Button btn_submit;
    QuestionList questionList;
    RelativeLayout relfeedback,relteacher;
    CircleImageView imgclassteach;
    TextView txtteacher,txttcno;
    String[] mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbacks);
        allocatememory();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        sc_id = preferences.getString("sc_id","none").toUpperCase();
        stu_id = preferences.getString("stu_id","none").toUpperCase();
        feeds=getIntent().getExtras().getString("feeds");
        if(feeds.equals("Teacher")==true)
        {
            t_id=getIntent().getExtras().getString("t_id");
            relteacher.setVisibility(View.VISIBLE);
            teacherdetails();
        }
        else
        {
            t_id=sc_id.toUpperCase();
            relteacher.setVisibility(View.GONE);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        loadQuestion();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAns();
            }
        });
    }

    private void teacherdetails()
    {
        String Webserviceurl=Common.GetWebServiceURL()+"teacher_feedback.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        /*[{"t_id":"SCIDN14TIDN1","t_img":"B.png","t_fname":"Brijesh","t_lname":"Patel","t_cont":"9966885544"}]*/
                        txtteacher.setText(object.getString("t_fname")+object.getString("t_lname"));
                        txttcno.setText(object.getString("t_cont"));
                        String img_url=Common.GetBaseImageURL()+"teacher/"+object.getString("t_img");
                        Glide.with(FeedbackActivity.this).load(img_url).into(imgclassteach);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String> data=new HashMap<>();
               data.put("t_id",t_id);
               data.put("sc_id",sc_id);
               Log.d("cont",data.toString());
               return data;
            }
        };
        Volley.newRequestQueue(FeedbackActivity.this).add(request);
    }

    private void allocatememory() {
        btn_submit = findViewById(R.id.btn_submit);
        recyclerView = findViewById(R.id.recyclerView);
        relfeedback = findViewById(R.id.relfeedback);
        relteacher = findViewById(R.id.relteacher);
        imgclassteach = findViewById(R.id.imgclassteach);
        txtteacher = findViewById(R.id.txtteacher);
        txttcno = findViewById(R.id.txttcno);
    }

    private void submitAns() {
        Common.progressDialogShow(FeedbackActivity.this);
        final String qid = new Gson().toJson(FeedbackAdapter.questionListList);

        String ANS_URL = Common.GetWebServiceURL()+"submitAns.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ANS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(FeedbackActivity.this);
             try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("fail")) {

                        Snackbar snackbar= Snackbar.make(relfeedback,"Feedback is Already Submitted for this Teacher.",Snackbar.LENGTH_LONG);
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        View sbView = snackbarView;
                        sbView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        snackbar.show();
                    }
                    else
                    {
                        Toast.makeText(FeedbackActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(FeedbackActivity.this,Feedback.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(FeedbackActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("qid", qid);
                params.put("t_id", t_id);
                params.put("sc_id", sc_id);
                params.put("u_id",stu_id );
                Log.v("PPP", params.toString());
                return params;
            }
        };
        Volley.newRequestQueue(FeedbackActivity.this).add(stringRequest);
    }

    private void loadQuestion() {
        Common.progressDialogShow(FeedbackActivity.this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String sc_id = preferences.getString("sc_id","none").toUpperCase();
        String Webserviceurl =Common.GetWebServiceURL()+"feedbackquestion.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(FeedbackActivity.this);
                    JSONArray res = new JSONArray(response);
                    mDataset=new String[response.length()];
                    questionListArrayList.clear();
                    for (int i = 0; i < res.length(); i++) {
                        JSONObject object = res.getJSONObject(i);
                        question = object.getString("question");
                        q_id = object.getString("q_id");


                        Log.v("QQQ", question);
                        questionList = new QuestionList(question, q_id,"not set");
                        questionListArrayList.add(questionList);
                    }
                    Log.v("GGG", String.valueOf(questionListArrayList));
                    FeedbackAdapter adapter = new FeedbackAdapter(FeedbackActivity.this, questionListArrayList,mDataset);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

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
                data.put("sc_id", sc_id);
                data.put("role",feeds);
                return data;
            }
        };
        Volley.newRequestQueue(FeedbackActivity.this).add(request);
    }

    private void loadAns(final String q_id, final String sc_id) {
        String ans_url = Common.GetWebServiceURL()+"feedbackoption.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, ans_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray res = new JSONArray(response);
                    ansListArrayList.clear();


                    ans.clear();
                    for (int i = 0; i < res.length(); i++) {
                        JSONObject object = res.getJSONObject(i);

                        answer = object.getString("answer");
                        ans.add(object.getString("answer"));
                        Log.v("LLLL", answer);

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
                data.put("sc_id", sc_id);
                data.put("q_id", q_id);
                return data;
            }
        };
        Volley.newRequestQueue(FeedbackActivity.this).add(request);
    }


}
