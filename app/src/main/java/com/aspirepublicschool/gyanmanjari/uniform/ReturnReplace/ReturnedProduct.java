package com.aspirepublicschool.gyanmanjari.uniform.ReturnReplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
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
import java.util.Map;

public class ReturnedProduct extends AppCompatActivity {
    RecyclerView recreturned;
    ArrayList<ReturnedProductModel> returnedProductModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returned_product);
        allocatememory();
        SendRequest();
    }

    private void SendRequest() {
        String WebserviceUrl = Common.GetWebServiceURL() + "returnedProductDisplay.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = preferences.getString("stu_id", "none");
        final String sc_id = preferences.getString("sc_id", "none").toUpperCase();
        final String sc_std = preferences.getString("std", "none");
        final String med = preferences.getString("med", "none");
        final String gender = preferences.getString("gender", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, WebserviceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

             /* {
                    "s_id": "ZCIN3",
                    "order_id": "ODM175",
                    "sin": "USIN22",
                    "quantity": "1",
                    "size": "M",
                    "amount": "300",
                    "direction": "Return",
                    "reason": "broken product",
                    "r_status": "4",
                    "img1": "default.png",
                    "label": "Male Sports full uniform"
                  }*/
                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        returnedProductModels.add(new ReturnedProductModel(object.getString("sin"),object.getString("img1"),object.getString("label"),object.getString("order_id"),
                                object.getString("user_id"),object.getString("s_id"),object.getString("r_status"),object.getString("direction"),object.getString("amount")
                                ,object.getString("quantity"),object.getString("reason"),object.getString("cat")));
                    }
                    ReturnedProductAdapterd adapterd = new ReturnedProductAdapterd(ReturnedProduct.this, returnedProductModels);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recreturned.setLayoutManager(mLayoutManager);
                    recreturned.setItemAnimator(new DefaultItemAnimator());
                    recreturned.setAdapter(adapterd);
                    adapterd.notifyDataSetChanged();
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
                data.put("uid",uid);
                return data;
            }
        };
        Volley.newRequestQueue(ReturnedProduct.this).add(request);
    }

    private void allocatememory() {
        recreturned = findViewById(R.id.recreturned);
    }
}
