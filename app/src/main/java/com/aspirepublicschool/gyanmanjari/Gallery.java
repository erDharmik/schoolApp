package com.aspirepublicschool.gyanmanjari;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Map;

public class Gallery extends AppCompatActivity {
    RecyclerView galleryview;
    Context ctx;
    ArrayList<GalleryModel> galleryModel = new ArrayList<>();
    LinearLayout linearLayout;
    public ProgressDialog p1;
    String id,title,photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        allocatememory();
        if (CheckNetwork.isInternetAvailable(getApplicationContext())) {
         SendRequest();
        }
        else {
            Intent i = new Intent(Gallery.this, NoInternetActivity.class);
            startActivity(i);
            finish();
        }
    }
    private void SendRequest() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String sc_id = preferences.getString("sc_id","none").toUpperCase();
        Log.v("MMMM",sc_id);
        p1 = new ProgressDialog(ctx);
        p1.setTitle("Processing, please wait");
        p1.show();
        //grdcategory.showShimmerAdapter();
        String WebServiceUrl = Common.GetWebServiceURL()+"category.php";
        //step -1 create object of JsonArrayRequest of volleylibrary
        Log.d("abcd", WebServiceUrl);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebServiceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                p1.dismiss();

                try
                {
                    Log.d("AAA",response);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0 ;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        galleryModel.add(new GalleryModel(
                                object.getString("id"),
                                object.getString("title"),
                                object.getString("photo"),
                                object.getString("edate")

                        ));
                        Log.d("product", String.valueOf(object.getInt("id")));
                        GalleryAdapter adapter = new GalleryAdapter(ctx,galleryModel);
                        RecyclerView.LayoutManager manager = new GridLayoutManager(ctx,2);
                        galleryview.setLayoutManager(manager);
                        galleryview.setItemAnimator(new DefaultItemAnimator());
                        galleryview.setAdapter(adapter);

                    }
                }
                catch (JSONException e)
                {
                    Common.ShowError(ctx,e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                p1.dismiss();
               // MyLog.p(error.getMessage());
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


        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sc_id",sc_id);
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }


    private void allocatememory() {
        galleryview = findViewById(R.id.imagelist);
        ctx = this;
        linearLayout = findViewById(R.id.linearlayout);

    }
}
