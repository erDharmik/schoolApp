package com.aspirepublicschool.gyanmanjari;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ImageContainers extends AppCompatActivity {
    String ImageId;
    Context ctx = this;
    RecyclerView recyclerView;
    ArrayList<ImageContainersModel> imageContainersModel = new ArrayList<>();
    ProgressDialog pd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_containears);
        ImageId = this.getIntent().getExtras().getString("categoryid");
        recyclerView = findViewById(R.id.recycleView);
        SendRequest();
        getSupportActionBar().setTitle("Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void SendRequest() {
        final String WebServiceUrl = Common.GetWebServiceURL() + "product.php?categoryid=" + ImageId;

        //  MyLog.p();
        pd1 = new ProgressDialog(ctx);
        pd1.setTitle("Fetching Product, please wait");
        pd1.show();
        //http://localhost:8080/android_batch_23/ws/product.php?categoryid=4

        StringRequest stringRequest = new StringRequest(Request.Method.GET, WebServiceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pd1.dismiss();
                    JSONArray array = new JSONArray(response);

                    Log.v("array", String.valueOf(array.length()));
                    for (int i = 2; i < array.length(); i++) {
                        JSONObject product = array.getJSONObject(i);
                        imageContainersModel.add(new ImageContainersModel(
                                product.getString("id"),
                                product.getString("photo")
                        ));
                    }
                    //Log.d("product", String.valueOf(product.getInt("id")));
                    ImageAdapter adapter = new ImageAdapter(ctx, imageContainersModel);
                    RecyclerView.LayoutManager manager = new GridLayoutManager(ctx, 2);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                    //   grdcategory.hideShimmerAdapter();

                } catch (JSONException e) {
                    Common.ShowError(ctx, e.getMessage());
                }
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd1.dismiss();
             //   MyLog.p(error.getMessage());
                AlertDialog.Builder b1 = new AlertDialog.Builder(ctx);
                b1.setTitle("Error ");
                b1.setMessage(Common.GetTimeoutMessage());
                b1.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SendRequest();
                    }
                });
                b1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                b1.create().show();
            }
        });

        Volley.newRequestQueue(ctx).add(stringRequest);

    }
}
