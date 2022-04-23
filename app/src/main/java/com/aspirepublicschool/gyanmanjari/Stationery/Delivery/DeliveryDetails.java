package com.aspirepublicschool.gyanmanjari.Stationery.Delivery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Stationery.Address;
import com.aspirepublicschool.gyanmanjari.uniform.Common;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.Stationery.DetailsPackage;
import com.aspirepublicschool.gyanmanjari.Stationery.FullPackage;
import com.aspirepublicschool.gyanmanjari.Stationery.FullPackageAdapter;
import com.aspirepublicschool.gyanmanjari.Stationery.StationeryPackage;
import com.aspirepublicschool.gyanmanjari.uniform.Promocode.PromocodeAdapter;
import com.aspirepublicschool.gyanmanjari.uniform.Promocode.PromocodeModal;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class DeliveryDetails extends AppCompatActivity {
    RadioButton radioButton_online, radioButton_cod;
    Button btn_order, btnchange;
    public static TextView txtapply, text_total;
    public static ImageView imgcancel;
    TextView txtaddress, txtdelivery;
    String payment_mode;
    String time;
    String sin, total_amount;
    RecyclerView recproductcart;
    ArrayList<DeliveryModel> deliveryModelArrayList = new ArrayList<>();
    String orderId = "", mid = "", first, second, third, fourth;
    public static int totalamount1 = 0;
    int totalamount = 0;
    LinearLayout lnrapply;
    AlertDialog.Builder mBuilder;
    public static AlertDialog mDialog;
    public static int deliverycharges;
    ArrayList<PromocodeModal> promocodeModalArrayList = new ArrayList<>();
    public static String package_id, seller_id;
    int discount,price,originalprice,total_price,qty;
    ArrayList<DetailsPackage> fullPackageModalArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);
        allocatememory();
        // text_total.setText(total_amount);
        // total_amount = getIntent().getExtras().getString("total_amount");
        package_id = getIntent().getExtras().getString("package_id");
        seller_id = getIntent().getExtras().getString("seller_id");
        UUID odid = UUID.randomUUID();
        payment_mode = "Cash On Delivery";
        String[] parts = odid.toString().split("");
        first = parts[5];
        second = parts[6];
        third = parts[7];
        String oodid = "ODM" + first + second + third;
        orderId = String.valueOf(oodid);
        Log.v("UUU", "" + total_amount);
        if (com.aspirepublicschool.gyanmanjari.uniform.Address.flag == 0) {

            checkaddress();

        } else {
            txtaddress.setText(getIntent().getExtras().getString("address"));
        }
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!radioButton_online.isChecked() && !radioButton_cod.isChecked()) {
                    Toast.makeText(DeliveryDetails.this, "Please Select any one Payment Mode", Toast.LENGTH_SHORT).show();
                } else {
                    if (radioButton_cod.isChecked()) {
                        payment_mode = "COD";
                    } else {
                        payment_mode = "online";
                    }
                    buypackage();
                }
            }
        });
        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryDetails.this, Address.class);
                intent.putExtra("total_amount", total_amount);
                intent.putExtra("sin", sin);
                startActivity(intent);
                finish();
            }
        });
        loadCartRecycler();

        lnrapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtapply.getText().equals("Apply Promocode")) {
                    // startActivity(new Intent(DeliveryDetails.this, Promocode.class));
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    final String sc_id = preferences.getString("sc_id", "none");
                    final String class_id = preferences.getString("class_id", "none");
                    mBuilder = new AlertDialog.Builder(DeliveryDetails.this);
                    View mView = getLayoutInflater().inflate(R.layout.activity_promocode, null);
                    final RecyclerView promocodelist;
                    promocodelist = mView.findViewById(R.id.promocodelist);
                    com.aspirepublicschool.gyanmanjari.Common.progressDialogShow(DeliveryDetails.this);

                    String Webserviceurl = Common.GetWebServiceURL() + "promocodedisplay.php";
                    StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                /*{
                    "promocode": "Textbook50",
                        "heading": "50 Rs cashback",
                        "priceper": "1",
                        "pricerup": "100",
                        "termandcond": "erstdfyguhij"
                }*/
                            try {
                                JSONArray array = new JSONArray(response);
                                Log.d("%%%", response);
                                promocodeModalArrayList.clear();
                                com.aspirepublicschool.gyanmanjari.Common.progressDialogDismiss(DeliveryDetails.this);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    promocodeModalArrayList.add(new PromocodeModal(object.getString("promocode"),
                                            object.getString("heading"), object.getString("priceper"),
                                            object.getString("pricerup"), object.getString("termsandcond")));
                                }
                                PromocodeAdapter cartViewAdapter = new PromocodeAdapter(DeliveryDetails.this, promocodeModalArrayList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                promocodelist.setLayoutManager(mLayoutManager);
                                promocodelist.setItemAnimator(new DefaultItemAnimator());
                                promocodelist.setAdapter(cartViewAdapter);
                                cartViewAdapter.notifyDataSetChanged();
                                //promocodelist.setAdapter(cartViewAdapter);

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
                            Map<String, String> data = new HashMap<>();
                            data.put("s_id",seller_id);
                            return data;
                        }
                    };
                    Volley.newRequestQueue(DeliveryDetails.this).add(request);
                    mBuilder.setView(mView);
                    mDialog = mBuilder.create();
                    ImageView btn_close = mView.findViewById(R.id.close);
                    btn_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                        }
                    });
                    mDialog.show();

                }
            }
        });
        imgcancel.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (!txtapply.getText().equals("Apply Promocode")) {
                    txtapply.setText("Apply Promocode");
                    totalamount1 = totalamount;
                    text_total.setText("" + (totalamount + deliverycharges) + " ₹");
                    imgcancel.setVisibility(View.GONE);
                }
            }
        });

    }

    private void buypackage() {
        com.aspirepublicschool.gyanmanjari.Common.progressDialogShow(DeliveryDetails.this);
        String Webserviceurl = com.aspirepublicschool.gyanmanjari.Common.GetWebServiceURL() + "buyPackage.php";
        final int min = 2020;
        final int max = 8080;
        final int random = new Random().nextInt((max - min) + 1) + min;
        final String code = String.valueOf(random);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        time = sdf.format(new Date());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = preferences.getString("stu_id", "none");
        final String sc_id = preferences.getString("sc_id", "none").toUpperCase();
        final String sc_std = preferences.getString("std", "none");
        final String med = preferences.getString("med", "none");
        final String gender = preferences.getString("gender", "none");
        final String address = preferences.getString("address", "none");
        final String mobile = preferences.getString("mobile", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    com.aspirepublicschool.gyanmanjari.Common.progressDialogDismiss(DeliveryDetails.this);
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");
                    Toast.makeText(DeliveryDetails.this, message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(DeliveryDetails.this, StationeryPackage.class));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DeliveryDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                String details = new Gson().toJson(fullPackageModalArrayList);
                data.put("list", details);
                data.put("order_id", orderId);
                data.put("total_price",""+total_price);
                data.put("uid",uid);
                data.put("mobileno", mobile);
                String add;
                if (com.aspirepublicschool.gyanmanjari.uniform.Address.flag == 0) {

                    add = address;

                } else {
                    add = txtaddress.getText().toString();
                }
                data.put("address", add);
                data.put("vcode", code);
                data.put("payment_mode", payment_mode);
                data.put("status", "0");
                data.put("time", time);
                data.put("order_status", "1");
                Log.d("data@@", data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(DeliveryDetails.this).add(request);

    }

    private void loadCartRecycler() {
        com.aspirepublicschool.gyanmanjari.Common.progressDialogShow(DeliveryDetails.this);
        String Webserviceurl = com.aspirepublicschool.gyanmanjari.Common.GetWebServiceURL() + "displayproductpackage.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = preferences.getString("stu_id", "none");
        final String sc_id = preferences.getString("sc_id", "none").toUpperCase();
        final String sc_std = preferences.getString("standard", "none");
        final String med = preferences.getString("med", "none");
        final String gender = preferences.getString("gender", "none");
        final String board = preferences.getString("board", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray array = new JSONArray(response);
                    Log.d("json", response);
                    deliveryModelArrayList.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                       /* int stock=Integer.parseInt(object.getString("stock"));
                        price=Integer.parseInt(object.getString("price"));
                        discount=Integer.parseInt(object.getString("discount"));
                        if(stock>0) {
                            originalprice=price - ((price * discount) / 100);
                            total_price = total_price +originalprice ;

                        }*/


                    }
                    com.aspirepublicschool.gyanmanjari.Common.progressDialogDismiss(DeliveryDetails.this);
                    for(int j=0;j< FullPackageAdapter.fullPacakgeList.size();j++)
                    {
                        int stocksdeatils=Integer.parseInt(FullPackageAdapter.fullPacakgeList.get(j).getStock());
                        price=Integer.parseInt(FullPackageAdapter.fullPacakgeList.get(j).getProce());
                        discount=Integer.parseInt(FullPackageAdapter.fullPacakgeList.get(j).getDiscount());
                        qty=Integer.parseInt(FullPackageAdapter.fullPacakgeList.get(j).getQty());
                        if(FullPackageAdapter.fullPacakgeList.get(j).isIschecked()==true && stocksdeatils>0)
                        {
                            fullPackageModalArrayList.add(FullPackageAdapter.fullPacakgeList.get(j));
                            originalprice=price - ((price * discount) / 100);
                            total_price = total_price +(originalprice*qty) ;
                        }
                    }
                    recproductcart.setLayoutManager(new LinearLayoutManager(DeliveryDetails.this));
                    recproductcart.addItemDecoration(new DividerItemDecoration(recproductcart.getContext(), DividerItemDecoration.VERTICAL));
                    DeliveryAdapter adapter = new DeliveryAdapter(DeliveryDetails.this, fullPackageModalArrayList);
                    recproductcart.setAdapter(adapter);


                    deliverycharges = 25;

                    totalamount1 = total_price;
                    txtdelivery.setText("" + deliverycharges + " ₹");
                    text_total.setText("" + (total_price + deliverycharges) + " ₹");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DeliveryDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("sc_id", sc_id);
                data.put("sc_std",sc_std);
                data.put("sc_med",med);
                data.put("board",board);
                data.put("s_id",FullPackage.seller_id);
                Log.d("##",FullPackage.seller_id);
                data.put("ss_id",FullPackage.shop_id);
                data.put("module_name",FullPackage.module_name);
                Log.d("###",data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(DeliveryDetails.this).add(request);
    }

    private void checkaddress() {
        String ORDER_URL = Common.GetWebServiceURL() + "address.php";
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
                    com.aspirepublicschool.gyanmanjari.Common.progressDialogDismiss(DeliveryDetails.this);
                    Log.d("add", response);
                    JSONArray array = new JSONArray(response);
                  /*
                    {
                        "error": "no error"
                    },
                    {
                        "total": "1"
                    },
                    {
                        "address": "bhavnagar"
                    }*/
                    String error = array.getJSONObject(0).getString("error");
                    if (error.equals("no error") == true) {
                        int total = Integer.parseInt(array.getJSONObject(1).getString("total"));
                        if (total == 0) {
                            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                            final String address = mPrefs.getString("address", "none");
                            Log.d("address",address);
                            txtaddress.setText(address);

                        } else  {
                            for (int i = 2; i < array.length(); i++)
                            {
                                JSONObject object = array.getJSONObject(i);
                                Log.d("addreee",object.getString("address"));
                                txtaddress.setText(object.getString("address"));
                            }

                        }
                    }

                } catch (JSONException e) {

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
                params.put("user_id",uid);
                return params;
            }
        };
        Volley.newRequestQueue(DeliveryDetails.this).add(stringRequest);

    }

    /*private void doOrder() {
        Common.progressDialogShow(DeliveryDetails.this);
        sin = getIntent().getStringExtra("sin");
        total_amount = getIntent().getStringExtra("total_amount");
        final int min = 2020;
        final int max = 8080;
        final int random = new Random().nextInt((max - min) + 1) + min;
        final String code = String.valueOf(random);

        String ORDER_URL = Common.GetWebServiceURL() + "Order.php";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss", Locale.getDefault());

        //You can change "yyyyMMdd_HHmmss as per your requirement

        time = sdf.format(new Date());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = preferences.getString("stu_id", "none");
        final String sc_id = preferences.getString("sc_id", "none").toUpperCase();
        final String sc_std = preferences.getString("std", "none");
        final String med = preferences.getString("med", "none");
        final String gender = preferences.getString("gender", "none");
        final String address = preferences.getString("address", "none");
        final String mobile = preferences.getString("mobile", "none");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ORDER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(DeliveryDetails.this);
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("success")) {
                        Toast.makeText(DeliveryDetails.this, "Order Place..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DeliveryDetails.this, SelectShopTypes.class));
                        finish();

                    } else {
                        Toast.makeText(DeliveryDetails.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

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
                params.put("order_id", orderId);
                //params.put("qty", "1");
               // params.put("price", total_amount);
                params.put("uid", "UIDN1");
                params.put("mobile",mobile);
                String add;
                if (Address.flag == 0) {

                   add=address;

                } else {
                    add=txtaddress.getText().toString();
                }
                params.put("address", add);
                params.put("vcode", code);
                //params.put("s_id", "ZCIN1");
                params.put("payment_mode", payment_mode);
                params.put("status", "0");
                params.put("time", time);
                params.put("s_id", "ZCIN1");
                //params.put("sin", sin);
                params.put("order_status", "0");
                Log.d("$$$",params.toString());
                return params;
            }
        };
        Volley.newRequestQueue(DeliveryDetails.this).add(stringRequest);
    }*/

    private void allocatememory() {
        btn_order = findViewById(R.id.btncontinue);
        btnchange = findViewById(R.id.btnchange);
        txtaddress = findViewById(R.id.txtaddress);
        text_total = findViewById(R.id.txtamount);
        txtapply = findViewById(R.id.txtapply);
        radioButton_online = findViewById(R.id.rdnet);
        radioButton_cod = findViewById(R.id.rdCOD);
        recproductcart = findViewById(R.id.recproductcart);
        txtdelivery = findViewById(R.id.txtdelivery);
        lnrapply = findViewById(R.id.lnrapply);
        imgcancel = findViewById(R.id.imgcancel);
    }
}