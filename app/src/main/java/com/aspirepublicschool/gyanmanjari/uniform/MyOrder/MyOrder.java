package com.aspirepublicschool.gyanmanjari.uniform.MyOrder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.uniform.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyOrder extends AppCompatActivity {
    RecyclerView recorder;
    public static List<OrderModel> orderModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        allocatememory();
        recorder.setHasFixedSize(true);
        recorder.setLayoutManager(new LinearLayoutManager(this));
        recorder.addItemDecoration(new DividerItemDecoration(recorder.getContext(), DividerItemDecoration.VERTICAL));
        loadOrder();
    }

    private void allocatememory() {
        recorder = findViewById(R.id.recorder);
    }
    private void loadOrder() {
        Common.progressDialogShow(this);
        String ORDER_URL = Common.GetWebServiceURL()+"DisplayOrder.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = preferences.getString("stu_id", "none");
        final String sc_id = preferences.getString("sc_id", "none").toUpperCase();
        final String sc_std = preferences.getString("std", "none");
        final String med = preferences.getString("med", "none");
        final String gender = preferences.getString("gender", "none");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ORDER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(MyOrder.this);
                    JSONArray array = new JSONArray(response);
                    orderModelList.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        orderModelList.add(new OrderModel(object.getString("order_id"),
                                object.getString("quantity"),
                                object.getString("price"),
                                object.getString("mobileno"),
                                object.getString("address"),
                                object.getString("s_id"),
                                object.getString("payment_mode"),
                                object.getString("timestamp"),
                                object.getString("sin"),
                                object.getString("order_status"),
                                object.getString("label"),
                                object.getString("img1"),object.getString("ss_name"),object.getString("cat"),object.getString("size")));

                        Log.v("LLLL", object.getString("size"));
                    }
                    OrderAdapter orderAdapter = new OrderAdapter(MyOrder.this, orderModelList);
                    recorder.setAdapter(orderAdapter);
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
                Map<String, String> params = new HashMap<>();
                params.put("uid",uid) ;
                return params;
            }
        };
        Volley.newRequestQueue(MyOrder.this).add(stringRequest);
    }
}
