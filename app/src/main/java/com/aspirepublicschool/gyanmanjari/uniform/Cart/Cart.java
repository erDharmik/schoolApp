package com.aspirepublicschool.gyanmanjari.uniform.Cart;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import com.aspirepublicschool.gyanmanjari.uniform.Delivery.DeliveryDetails;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.uniform.Common;
import com.aspirepublicschool.gyanmanjari.uniform.SelectShopTypes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart extends AppCompatActivity {
    RecyclerView recproduct;
    TextView textView_total;
    int qty,price;
    Handler handler;
    String total;
    String sin;
    String uid;
    public static String s_id;
    Button btn_buy,btnproceed;
    List<ViewCartModel> viewCartModelList = new ArrayList<>();
    ViewCartModel viewCartModel;
    int total_val = 0;
String total_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        alloactememory();
        btnproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart.this, DeliveryDetails.class);
                intent.putExtra("sin",sin);
                //intent.putExtra("total_amount",total);
                startActivity(intent);
            }
        });
      /*  if (!viewCartModelList.isEmpty() && viewCartModelList!=null){


        }
        else {
            Toast.makeText(Cart.this,"Cart is Empty",Toast.LENGTH_SHORT).show();
            Snackbar.make(getWindow().getDecorView().getRootView(),"Your Cart is Empty",Snackbar.LENGTH_LONG).show();
        }*/
        loadCartData();
        loadCartPrice();
    }

    private void loadCartPrice() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        uid = preferences.getString("stu_id","none");
        String Webserviceurl=Common.GetWebServiceURL()+"cartcount.php";
        final StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                        Log.d("aaa",response);
                    JSONArray array = new JSONArray(response);
                    for(int i = 0; i<array.length();i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        total_val=Integer.parseInt(object.getString("total"));
                    }
                    Log.d("aaa",""+total_val);
                    if(total_val==0)
                    {
                        btnproceed.setEnabled(false);
                        final AlertDialog.Builder alert=new AlertDialog.Builder(Cart.this);
                        alert.setTitle("Warning");
                        alert.setMessage("The Value of Cart is Less than 300rs.").setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Cart.this,SelectShopTypes.class));
                                finish();
                            }
                        });
                        AlertDialog alertdialog = alert.create();
                        //Setting the title manually
                        alertdialog.setTitle("Warning !!!!");
                        alertdialog.show();

                    }
                    else if(total_val<300)
                    {
                        btnproceed.setEnabled(false);
                        final AlertDialog.Builder alert=new AlertDialog.Builder(Cart.this);
                        alert.setTitle("Warning");
                        alert.setMessage("The Value of Cart is Less than 300rs.").setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Cart.this,SelectShopTypes.class));
                                finish();
                            }
                        });
                        AlertDialog alertdialog = alert.create();
                        alertdialog.setTitle("Warning !!!!");
                        alertdialog.show();

                    }
                    else {
                        btnproceed.setEnabled(true);
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
                Map<String,String> data=new HashMap<>();
                data.put("uid", uid);
                return data;
            }
        };
        Volley.newRequestQueue(Cart.this).add(request);
    }

    private void alloactememory() {
        recproduct=findViewById(R.id.recproduct);
        btnproceed=findViewById(R.id.btnproceed);
        recproduct.setHasFixedSize(true);
        recproduct.setLayoutManager(new LinearLayoutManager(this));
        recproduct.addItemDecoration(new DividerItemDecoration(recproduct.getContext(), DividerItemDecoration.VERTICAL));
     /*   this.handler = new Handler();
       m_runnable.run();*/

    }
    private void loadCartData() {
        Common.progressDialogShow(Cart.this);
         SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        uid = preferences.getString("stu_id","none");
        String CART_URL = Common.GetWebServiceURL()+"viewCart.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CART_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(Cart.this);
                    viewCartModelList.clear();
                    JSONArray array = new JSONArray(response);
                    Log.d("response",response);
                    int total_num=Integer.parseInt(array.getJSONObject(0).getString("total"));
                    if(total_num==0) {
                        Toast.makeText(Cart.this,"No Products in Cart",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Cart.this, SelectShopTypes.class));
                        finish();
                    }
                    else {
                        for (int i = 1; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            viewCartModelList.add(new ViewCartModel(object.getString("sin"),
                                    object.getString("qty"),
                                    object.getString("size"),
                                    object.getString("s_id"),
                                    object.getString("amount"),
                                    object.getString("time"),
                                    object.getString("label"),
                                    object.getString("img1"), object.getString("ss_name"), object.getString("cat"), object.getString("p_code")));
                            qty = Integer.parseInt(object.getString("qty"));
                            price = Integer.parseInt(object.getString("amount"));
                            total_amount = object.getString("amount");
                            sin = object.getString("sin");
                            s_id = object.getString("s_id");
                            Log.v("LLAAL", sin);


                            // Log.v("LLL", object.getString("s_id")+object.getString("amount")+object.getString("time"));
                        }
                    }

                    total = String.valueOf(qty*price);
                    Log.v("LLL",total);
                   // textView_total.setText(total);
                    CartViewAdapter cartViewAdapter = new CartViewAdapter(Cart.this,viewCartModelList);
                    recproduct.setAdapter(cartViewAdapter);

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
        Volley.newRequestQueue(Cart.this).add(stringRequest);
    }
}
