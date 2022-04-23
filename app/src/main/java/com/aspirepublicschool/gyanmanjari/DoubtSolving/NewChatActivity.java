package com.aspirepublicschool.gyanmanjari.DoubtSolving;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
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

public class NewChatActivity extends AppCompatActivity {

    RecyclerView recStudents;
    ArrayList<newchatmodel> newChatList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);
        allocatememory();
        SendRequest();
        getSupportActionBar().hide();


    }

    private void SendRequest() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String sc_id = preferences.getString("sc_id","none").toLowerCase();
        final String class_id = preferences.getString("class_id","none");
        String WebServiceUrl= Common.GetWebServiceURL()+"teacher/getStudents.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray res=new JSONArray(response);
                    int asize = res.length();
                    for(int i=1 ; i < asize ; i++) {
                        JSONObject object=res.getJSONObject(i);
                        newChatList.add(new newchatmodel(
                                object.getString("stu_id"),
                                object.getString("st_sname"),
                                object.getString("st_fname"),
                                object.getString("f_cno")));

                    }
                    NewChatAdapter adapter=new NewChatAdapter(getApplicationContext(), newChatList);
                    recStudents.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                    recStudents.addItemDecoration(new DividerItemDecoration(recStudents.getContext(), DividerItemDecoration.VERTICAL));
                    recStudents.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(getApplicationContext());
                Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id",sc_id.toUpperCase());
                data.put("c_id",class_id);
                Log.d("sss",data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

    private void allocatememory() {
        recStudents=findViewById(R.id.recStudents);
    }

}