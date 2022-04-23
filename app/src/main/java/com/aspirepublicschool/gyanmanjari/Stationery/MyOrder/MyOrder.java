package com.aspirepublicschool.gyanmanjari.Stationery.MyOrder;

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
        String ORDER_URL = com.aspirepublicschool.gyanmanjari.Common.GetWebServiceURL()+"displaybuyproduct.php";
        Log.v("ORDER_URL", ORDER_URL);
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
                    Log.d("resaa",response);
                  /*  {
                        "sin": "USIN22",
                            "order_id": "ODM175",
                            "quantity": "1",
                            "price": "300",
                            "mobileno": "8866774456",
                            "address": "jweles circle ,bhavnagar,Gujarat",
                            "s_id": "ZCIN3",
                            "payment_mode": "COD",
                            "timestamp": "13-03-2020/14:39:45",
                            "order_status": "1",
                            "ss_name": "vaidant Book Stall"
                    },*/
                    orderModelList.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        orderModelList.add(new OrderModel(object.getString("img1"),
                                object.getString("order_id"),
                                object.getString("label"),
                                object.getString("price"),
                               object.getString("sin"),object.getString("quantity")
                        ,object.getString("order_status"),object.getString("ss_name"),object.getString("timestamp"),object.getString("payment_mode")
                        ,object.getString("address"),object.getString("s_id"),object.getString("mobileno")));

                        Log.v("LLLL", object.getString("order_id"));
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
