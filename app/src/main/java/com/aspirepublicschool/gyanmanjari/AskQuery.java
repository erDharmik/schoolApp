package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AskQuery extends AppCompatActivity {
    RecyclerView inbox_recyclerView;
    FloatingActionButton floatingActionButton;
    List<Ask_QueryModel> ask_queryModelList = new ArrayList<>();
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_query);
        ctx=this;
        inbox_recyclerView = findViewById(R.id.inbox_recyclerView);
        floatingActionButton = findViewById(R.id.btn_floating);
        inbox_recyclerView.setHasFixedSize(true);
        inbox_recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        loadMessage();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ctx,Message.class));
            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(inbox_recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        inbox_recyclerView.addItemDecoration(dividerItemDecoration);
    }
    private void loadMessage() {
        Common.progressDialogShow(ctx);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stu_id = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id", "none").toLowerCase();
        Log.v("STU_ID",stu_id);
        String MESSAGE_URL =Common.GetWebServiceURL()+"communicatioInbox.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MESSAGE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(ctx);
                try {
                    JSONArray array = new JSONArray(response);
                    Log.d("asss",response);
                    for (int i=0;i<=array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        ask_queryModelList.add(new Ask_QueryModel(
                                object.getString("role"),
                                String.valueOf(Html.fromHtml(object.getString("title"))),
                                String.valueOf(Html.fromHtml(object.getString("message"))),
                                object.getString("time"),
                                object.getString("t_fname"),
                                object.getString("t_lname")


                        ));
                        Ask_QueryAdapter adapter = new Ask_QueryAdapter(ctx, ask_queryModelList);
                        inbox_recyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("target",stu_id);
                params.put("sc_id",sc_id);
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }
}
