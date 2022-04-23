package com.aspirepublicschool.gyanmanjari.Test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestPdfWise extends AppCompatActivity {
    RecyclerView rectestPdf;
    ArrayList<TestPdfModel> testPdfModelArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pdf_wise);
        allocatememory();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        SendRequest();
    }

    private void SendRequest() {
        Common.progressDialogShow(TestPdfWise.this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        String Webserviceurl=Common.GetWebServiceURL()+"testpdf.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(TestPdfWise.this);
                try {
                    Log.d("$$$",response);
                    JSONArray array=new JSONArray(response);
                    int total=array.getJSONObject(0).getInt("total");
                    if(total==0)
                    {
                        Toast.makeText(TestPdfWise.this, "Sorry!! No Test Available",Toast.LENGTH_LONG).show();
                    }
                    else {
                        for (int i=1;i<array.length();i++)
                        {
                            JSONObject object=array.getJSONObject(i);
                            testPdfModelArrayList.add(new TestPdfModel(object.getString("sub"),object.getString("title"),object.getString("des"),object.getString("doc"),object.getString("date"),
                                    object.getString("t_fname")+" "+object.getString("t_lname")));
                        }
                        TestPdfWiseAdapter adapter=new TestPdfWiseAdapter(TestPdfWise.this,testPdfModelArrayList);
                        rectestPdf.setAdapter(adapter);

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
                data.put("sc_id",sc_id);
                data.put("cid",class_id);
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(TestPdfWise.this).add(request);
    }

    private void allocatememory() {
        rectestPdf=findViewById(R.id.rectestpdf);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TestPdfWise.this);
        rectestPdf.setLayoutManager(mLayoutManager);
        rectestPdf.setItemAnimator(new DefaultItemAnimator());
    }
}
