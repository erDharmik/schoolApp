package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Message extends AppCompatActivity {
    EditText edttitle,edtmessage;
    Button Submit,sent_message;
    Context ctx;
    String target,target_id;

    Spinner spnTeacher;
    ArrayList<Teacher> teacherList =new ArrayList<>();
    ArrayList<String> teachList=new ArrayList<>();
    String tfname,tlname,tid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        spnTeacher=findViewById(R.id.spnTeacher);
        edttitle=findViewById(R.id.edttitle);
        ctx=this;
        edtmessage=findViewById(R.id.edtmessage);
        Submit=findViewById(R.id.Submit);
        sent_message = findViewById(R.id.sent_message);

        loadTeacher();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edttitle.getText().toString().isEmpty() || edtmessage.getText().toString().isEmpty()){
                    Toast.makeText(ctx,"Please Enter Valid Data",Toast.LENGTH_SHORT).show();
                }
                else {
                    SendRequest();
                }
            }
        });
        sent_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx,ViewSentMessage.class));
                finish();
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String sc_id = preferences.getString("sc_id","none").toLowerCase();
        final String class_id = preferences.getString("class_id","none").toLowerCase();
        String WebServiceUrl=Common.GetWebServiceURL()+"getTeacher.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray res=new JSONArray(response);

                        int asize=res.length();
                        for(int i=0;i<asize;i++) {
                            JSONObject object = res.getJSONObject(i);
                            tid = object.getString("t_id");
                            tfname = object.getString("t_fname");
                            tlname = object.getString("t_lname");
                            Teacher t = new Teacher(tfname, tlname, tid);
                            teacherList.add(t);
                            teachList.add(tfname + " " + tlname);

                        }
                        spnTeacher.setAdapter(new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_dropdown_item,teachList));



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
                Log.d("aaa",data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(ctx).add(request);
    }



    private void SendRequest() {
        Common.progressDialogShow(ctx);
        String Webserviceurl=Common.GetWebServiceURL()+"communication.php";
        Log.d("web",Webserviceurl);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stu_id = preferences.getString("stu_id","none");
        StringRequest stringRequest=new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(ctx);
                    JSONArray array=new JSONArray(response);
                    JSONObject object =array.getJSONObject(0);
                    if (object.getString("message").equals("Send Message")){
                        Toast.makeText(ctx,"done",Toast.LENGTH_SHORT).show();
                        edttitle.setText("");
                        edtmessage.setText("");
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
                Map<String, String> data = new HashMap<>();
                /*role=USIDN1&target=abc@aaa.com&title=aaa&message=sdfdfasdff*/
                data.put("role", stu_id);
                data.put("target",target_id);
                data.put("title", TextUtils.htmlEncode(edttitle.getText().toString()));
                data.put("msg",TextUtils.htmlEncode(edtmessage.getText().toString()));
                Log.d("data",data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }


}
