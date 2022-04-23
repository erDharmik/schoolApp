package com.aspirepublicschool.gyanmanjari.NewRegister.DemoRequestDashboard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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

public class demoActivity extends AppCompatActivity {

    String number;
    RecyclerView recView;
    ArrayList<teacherModel> teacherList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        getSupportActionBar().setTitle("Demo Request");

        number = getIntent().getStringExtra("number");
        demoRequestAdd();

        recView = findViewById(R.id.recView);

        setRecyclerView();

    }

    private void setRecyclerView() {
        String HOMEWORK_URL = Common.GetWebServiceURL()+"getTeacherForDemo.php";
        Log.v("LINK",HOMEWORK_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOMEWORK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("###",response );
//                    conductedTestList.clear();
                    JSONArray array = new JSONArray(response);


                    int total = Integer.parseInt(array.getJSONObject(0).getString("total"));

                    if (total > 0){
                        int size = array.length();

                        for (int i=1;i<size;i++){

                                JSONObject jsonObject = array.getJSONObject(i);
                                teacherList.add(new teacherModel(
                                        jsonObject.getString("id"),
                                        jsonObject.getString("t_cno"),
                                        jsonObject.getString("t_email"),
                                        jsonObject.getString("t_fname"),
                                        jsonObject.getString("t_lname"),
                                        jsonObject.getString("t_id"),
                                        jsonObject.getString("subject"),
                                        jsonObject.getString("status")));

                                teacherAdapter adapter = new teacherAdapter(getApplicationContext(), teacherList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recView.setLayoutManager(mLayoutManager);
                                recView.setItemAnimator(new DefaultItemAnimator());
                                recView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("number", "9999999999");
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }

    private void demoRequestAdd() {
        String HOMEWORK_URL = Common.GetWebServiceURL()+"demoRequest.php";
        Log.v("LINK",HOMEWORK_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOMEWORK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("###",response );
//                    conductedTestList.clear();
                    JSONArray array = new JSONArray(response);

                    String message = array.getJSONObject(0).getString("status");

                    if (message.equals("true")){
                        Toast.makeText(getApplicationContext(), "Request has been submitted successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),R.string.no_connection_toast,Toast.LENGTH_LONG).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("number", "6355574383");
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }
}