package com.aspirepublicschool.gyanmanjari.Feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.Teacher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {
    Spinner spnfeedback,spnTeacher;
    Button btnsub;
    String feeds;
    LinearLayout lnrteacher;
    ArrayList<Teacher> teacherList =new ArrayList<>();
    ArrayList<String> teachList=new ArrayList<>();
    String tfname,tlname,tid,target,target_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        allocatememory();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.feedback_Type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnfeedback.setAdapter(adapter);
        spnfeedback.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                feeds= spnfeedback.getItemAtPosition(spnfeedback.getSelectedItemPosition()).toString();
                if(feeds.equals("Teacher")==true)
                {
                    lnrteacher.setVisibility(View.VISIBLE);
                    Log.v("feeds",feeds);
                    loadTeacher();
                }

                //Log.v("ROLE",target);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //feeds = spnfeedback.getSelectedItem().toString();

        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Feedback.this,FeedbackActivity.class);
                if (feeds.equals("Teacher")==true) {
                    intent.putExtra("feeds", feeds);
                    intent.putExtra("t_id",target_id);
                }
                else
                {
                    intent.putExtra("feeds", feeds);
                }
                startActivity(intent);

            }
        });
        spnTeacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                target= spnTeacher.getItemAtPosition(spnTeacher.getSelectedItemPosition()).toString();
                target_id = teacherList.get(position).getTid();
                Log.v("TID",target_id);
                Log.v("ROLE",target);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void loadTeacher() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String sc_id = preferences.getString("sc_id","none").toLowerCase();
        final String class_id = preferences.getString("class_id","none");
        String WebServiceUrl= Common.GetWebServiceURL()+"getTeacher.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                                                 JSONArray res=new JSONArray(response);
                  /*  String error=res.getJSONObject(0).getString("error");
                    if(error.equals("no error")==false)
                    {

                    }
                    else
                    {*/
                        int asize=res.length();
                        for(int i=0;i<asize;i++) {
                            JSONObject object=res.getJSONObject(i);
                            tid=object.getString("t_id");
                            tfname=object.getString("t_fname");
                            tlname=object.getString("t_lname");
                            Teacher t=new Teacher(tfname,tlname,tid);
                            teacherList.add(t);
                            teachList.add(tfname+" "+tlname);

                        }
                        spnTeacher.setAdapter(new ArrayAdapter<String>(Feedback.this, android.R.layout.simple_spinner_dropdown_item,teachList));


                    //}
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
                data.put("sc_id",sc_id);
                data.put("c_id",class_id);
                Log.d("sss",data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(Feedback.this).add(request);
    }
    private void allocatememory() {
        spnfeedback=findViewById(R.id.spnfeedback);
        spnTeacher=findViewById(R.id.spnTeacher);
        lnrteacher=findViewById(R.id.lnrteacher);
        btnsub=findViewById(R.id.btnsub);
    }

}
