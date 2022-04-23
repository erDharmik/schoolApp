package com.aspirepublicschool.gyanmanjari.uniform.clothes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class UniformList extends AppCompatActivity {
    RecyclerView recuniform;
    List<ShopProductModel> shopProductModelList = new ArrayList<>();
    String s_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniform_list);
        allocatememory();

        s_id = getIntent().getStringExtra("s_id");
        recuniform.setHasFixedSize(true);
        recuniform.setLayoutManager(new GridLayoutManager(this,2));
        //recuniform.addItemDecoration(new DividerItemDecoration(recuniform.getContext(), DividerItemDecoration.VERTICAL));
        loadProduct();
    }
    private void loadProduct() {
        Common.progressDialogShow(this);
        String PRODUCT_URL = Common.GetWebServiceURL()+"DisplayProduct.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = preferences.getString("stu_id", "none");
        final String sc_id = preferences.getString("sc_id", "none").toUpperCase();
        final String sc_std = preferences.getString("std", "none");
        final String med = preferences.getString("med", "none");
        final String gender = preferences.getString("gender", "none");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PRODUCT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(UniformList.this);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i <array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        object.getString("sin");
                        object.getString("p_code");
                        object.getString("cat");
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
                                object.getString("img1"), object.getString("s_id"),object.getString("cat"),object.getString("discount")
                        ));
                        Log.v("PPP", object.getString("p_code"));
                    }
                    ShopProductAdapter shopProductAdapter = new ShopProductAdapter(UniformList.this, shopProductModelList);
                    recuniform.setAdapter(shopProductAdapter);
                } catch (JSONException e) {
                    Log.v("TTT",e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ERROR", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sc_id",sc_id);
                params.put("sc_std",sc_std);
                params.put("sc_med",med);
                params.put("gender",gender);
                params.put("s_id",s_id);
                return params;

            }
        };
        Volley.newRequestQueue(UniformList.this).add(stringRequest);
    }

    private void allocatememory() {
        recuniform=findViewById(R.id.recuniform);
    }
}
