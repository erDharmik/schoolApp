package com.aspirepublicschool.gyanmanjari.DoubtSolving;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatDoubt extends AppCompatActivity {
    RecyclerView recteacher;
    ArrayList<TeacherDoubt> teacherDoubts=new ArrayList<>();
//    FloatingActionButton newChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_doubt);
        allocatememory();
//        newChat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), NewChatActivity.class));
//            }
//        });
        SendRequest();
    }

    private void SendRequest() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String sc_id = preferences.getString("sc_id","none").toLowerCase();
        final String class_id = preferences.getString("class_id","none");

        Toast.makeText(getApplicationContext(), sc_id + " " + class_id, Toast.LENGTH_SHORT).show();

        String WebServiceUrl= Common.GetWebServiceURL()+"getTeacher.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray res=new JSONArray(response);
                    int asize=res.length();

                    for(int i=0;i<asize;i++) {
                        JSONObject object=res.getJSONObject(i);
                        teacherDoubts.add(new TeacherDoubt(object.getString("t_id"),
                                object.getString("t_fname")+" "+object.getString("t_lname"),
                                object.getString("t_img"),
                                object.getString("t_cno"),
                                object.getString("subject")));

                    }
                    ChatAdapter adapter=new ChatAdapter(ChatDoubt.this, teacherDoubts);
                    recteacher.setLayoutManager(new LinearLayoutManager(ChatDoubt.this));
                    recteacher.addItemDecoration(new DividerItemDecoration(recteacher.getContext(), DividerItemDecoration.VERTICAL));
                    recteacher.setAdapter(adapter);


                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage().toString() + "Catch", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(ChatDoubt.this);
                Toast.makeText(ChatDoubt.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();

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
        Volley.newRequestQueue(ChatDoubt.this).add(request);
    }

    private void allocatememory() {
        recteacher=findViewById(R.id.recteacher);
//        newChat=findViewById(R.id.newChat);
    }
}
