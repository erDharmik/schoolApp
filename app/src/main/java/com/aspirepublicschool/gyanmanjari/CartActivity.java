package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView_cart;
    TextView textView_total;
    int qty,price;
    Handler handler;
    String total;
    String uid;
    Button btn_buy;
    List<ViewCartModel> viewCartModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView_cart = findViewById(R.id.recproduct);
        recyclerView_cart.setHasFixedSize(true);
        recyclerView_cart.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_cart.addItemDecoration(new DividerItemDecoration(recyclerView_cart.getContext(), DividerItemDecoration.VERTICAL));
     /*   this.handler = new Handler();
       m_runnable.run();*/
        loadCartData();


    }
   /* private final Runnable m_runnable = new Runnable() {
        @Override
        public void run() {
            viewCartModelList.clear();
            loadCartData();
            CartActivity.this.handler.postDelayed(m_runnable,6000);
        }
    };*/
    private void loadCartData() {
        Common.progressDialogShow(CartActivity.this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        uid = preferences.getString("stu_id","none");
        String CART_URL = "http://192.168.43.43:8080/school/ws/viewCart.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CART_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(CartActivity.this);
                    JSONArray array = new JSONArray(response);
                    for (int i=0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        viewCartModelList.add(new ViewCartModel(object.getString("sin"),
                                object.getString("qty"),
                                object.getString("s_id"),
                                object.getString("amount"),
                                object.getString("time"),
                                object.getString("label"),
                                object.getString("img1")));
                        qty = Integer.parseInt(object.getString("qty"));
                        price = Integer.parseInt(object.getString("amount"));
                        // Log.v("LLL", object.getString("s_id")+object.getString("amount")+object.getString("time"));
                    }
                    total = String.valueOf(qty*price);
                    Log.v("LLL",total);
                    textView_total.setText(total);
                    CartViewAdapter cartViewAdapter = new CartViewAdapter(CartActivity.this,viewCartModelList);
                    recyclerView_cart.setAdapter(cartViewAdapter);

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
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                return params;
            }
        };
        Volley.newRequestQueue(CartActivity.this).add(stringRequest);
    }


}
