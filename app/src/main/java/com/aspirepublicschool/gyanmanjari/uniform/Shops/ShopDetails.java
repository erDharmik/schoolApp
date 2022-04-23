package com.aspirepublicschool.gyanmanjari.uniform.Shops;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.uniform.clothes.ShopProductAdapter;
import com.aspirepublicschool.gyanmanjari.uniform.clothes.ShopProductModel;
import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.uniform.Common;
import com.aspirepublicschool.gyanmanjari.uniform.SelectShopTypes;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopDetails extends AppCompatActivity {
    RecyclerView recproduct;
    TextView txtshopname,txtcontact,total_rating;
    CollapsingToolbarLayout toolbar_layout;
    String s_id,ss_name,s_img,s_cont,ratings;
    public static String cat;
    List<ShopProductModel> shopProductModelList = new ArrayList<>();
    ImageView imgshopdetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        allocatememory();
        s_id = getIntent().getStringExtra("s_id");
        ss_name = getIntent().getStringExtra("ss_name");
        s_img = getIntent().getStringExtra("s_img");
        s_cont = getIntent().getStringExtra("s_cont");
        ratings = getIntent().getStringExtra("ratings");
        String ImageUrl = "https://www.aspirepublicschool.net/zocarro/image/shop/"+ SelectShopTypes.s_img;
        Log.v("VVVVV", ImageUrl);
        Glide.with(ShopDetails.this).load(ImageUrl).into(imgshopdetails);
        toolbar_layout.setTitle(ss_name);
        txtcontact.setText(s_cont);
        total_rating.setText(""+ratings);
       //Log.v("s_id", s_id);
        loadProduct();


    }

    private void allocatememory() {
        toolbar_layout =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        recproduct=findViewById(R.id.recproduct);
        txtcontact=findViewById(R.id.txtcontact);
        recproduct.setLayoutManager(new LinearLayoutManager(this));
       // recproduct.addItemDecoration(new DividerItemDecoration(recproduct.getContext(), DividerItemDecoration.VERTICAL));
        imgshopdetails=findViewById(R.id.imgshopdetails);

        // collapsingToolbarLayout.setTitle("My Toolbar Title");
        //toolbar_layout.setContentScrimColor(Color.RED);
        total_rating=findViewById(R.id.total_rating);
    }
    private void loadProduct() {
        Common.progressDialogShow(this);
        String PRODUCT_URL = Common.GetWebServiceURL()+"DisplayProduct.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = preferences.getString("stu_id", "none");
        final String sc_id = preferences.getString("sc_id", "none").toUpperCase();
        final String sc_std = preferences.getString("standard", "none");
        final String med = preferences.getString("med", "none");
        final String gender = preferences.getString("gender", "none");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PRODUCT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(ShopDetails.this);
                    Log.d("ffff",response);
                    JSONArray array = new JSONArray(response);
                    int total=Integer.parseInt(array.getJSONObject(0).getString("total"));
                    if(total==0)
                    {
                        Toast.makeText(ShopDetails.this,"No Product Available",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(ShopDetails.this,SelectShopTypes.class);
                        startActivity(intent);
                        finish();

                    }
                    else {


                        for (int i = 1; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);

                            object.getString("sin");
                            object.getString("p_code");
                            cat = object.getString("cat");
                            object.getString("brand");
                            object.getString("color");
                            object.getString("material");
                            object.getString("size");
                            object.getString("s_id");
                            object.getString("sc_id");
                            object.getString("label");
                            object.getString("stock");
                            object.getString("img1");
                            object.getString("img2");
                            object.getString("img3");
                            object.getString("img4");
                            object.getString("img5");
                            object.getString("img6");

                            object.getString("des");
                            object.getString("price");
                            object.getString("discount");
                            shopProductModelList.add(new ShopProductModel(
                                    object.getString("sin"),
                                    object.getString("label"),
                                    object.getString("p_code"),
                                    object.getString("price"),
                                    object.getString("img1"),
                                    object.getString("s_id"),
                                    object.getString("cat"),
                                    object.getString("discount")
                            ));
                            Log.v("PPP", object.getString("p_code"));
                        }
                        ShopProductAdapter shopProductAdapter = new ShopProductAdapter(ShopDetails.this, shopProductModelList);
                        recproduct.setAdapter(shopProductAdapter);
                    }
                } catch (JSONException e) {
                    Log.v("TTT",e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ERROR", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sc_id",sc_id);
                params.put("sc_std",sc_std);
                params.put("sc_med",med);
                params.put("gender",gender);
               /* params.put("sc_id","SCIDN1");
                params.put("sc_std","1");
                params.put("sc_med","Gujrati");
                params.put("gender","Male");*/
                params.put("s_id",s_id);
                Log.d("@@@",params.toString());
                return params;

            }
        };
        Volley.newRequestQueue(ShopDetails.this).add(stringRequest);
    }
}
