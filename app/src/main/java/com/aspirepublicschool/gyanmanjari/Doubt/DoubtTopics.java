package com.aspirepublicschool.gyanmanjari.Doubt;

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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.Homework.HomeworkActivity;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoubtTopics extends AppCompatActivity {
    RecyclerView recdoubtsolve;
    ArrayList<SubjectDoubt> subjectDoubts=new ArrayList<>();
    String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt_topics);
        subject=getIntent().getExtras().getString("sub");
        allocatememory();
        SendRequest();
    }

    private void SendRequest() {
        Common.progressDialogShow(DoubtTopics.this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String sc_id = preferences.getString("sc_id","none").toLowerCase();
        final String class_id = preferences.getString("class_id","none");
        String WebServiceUrl= Common.GetWebServiceURL()+"doubtSubject.php";
        Log.d("subject",WebServiceUrl);
        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("!!!",response);
                    Common.progressDialogDismiss(DoubtTopics.this);
                    JSONArray res=new JSONArray(response);

                        subjectDoubts.clear();
                        int asize=res.length();
                        for(int i=0;i<asize;i++) {
                            JSONObject object=res.getJSONObject(i);
                            subjectDoubts.add(new SubjectDoubt(object.getString("t_id"), object.getString("subject")
                                    , object.getString("chapter"), object.getString("topic"), object.getString("data"),object.getString("date")));

                        }
                    DoubtTopicAdapter doubtTopicAdapter=new DoubtTopicAdapter(DoubtTopics.this,subjectDoubts);
                    RecyclerView.LayoutManager manager = new LinearLayoutManager(DoubtTopics.this);
                    recdoubtsolve.setLayoutManager(manager);
                    recdoubtsolve.setItemAnimator(new DefaultItemAnimator());
                    recdoubtsolve.setAdapter(doubtTopicAdapter);




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("cid",class_id);
                data.put("sub",subject);
                data.put("sc_id",sc_id);
                Log.d("!!!", data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(DoubtTopics.this).add(request);
    }

    private void allocatememory() {
        recdoubtsolve=findViewById(R.id.recdoubtsolve);
    }
}
