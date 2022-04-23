package com.aspirepublicschool.gyanmanjari.DoubtSolving;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoubtSolving extends AppCompatActivity {
    RecyclerView recdoubt;
    FloatingActionButton floatingadd;
    ArrayList<Doubt> doubtArrayList=new ArrayList<>();
    String t_id,t_name;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt_solving);
        t_id=getIntent().getExtras().getString("t_id");
        t_name=getIntent().getExtras().getString("t_name");
        allocatememory();
        sendRequest();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
       /* floatingadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DoubtSolving.this,Insert_Doubt.class);
                intent.putExtra("t_id", t_id);
                startActivity(intent);
                finish();
            }
        });*/
    }

    private void sendRequest() {
       // String Webserviceurl= Common.GetWebServiceURL()+"doubt.php";
        String Webserviceurl= Common.GetWebServiceURL()+"doubt.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        final String stu_id = mPrefs.getString("stu_id","none");
        final StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("!!",response);
               /* [{"id":"3","sc_id":"SCIDN2","cid":"CIDN7","sub":"ENG","title":"Chapter 1","des":"topic one","doc":"","date":"23-03-2020"}]*/
                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        doubtArrayList.add(new Doubt(object.getString("id"),object.getString("role"),object.getString("target"),
                                object.getString("title"),object.getString("message"),object.getString("doc"),object.getString("time"),object.getString("status")));

                    }
                    DoubtSolvingAdapter adapter=new DoubtSolvingAdapter(DoubtSolving.this,doubtArrayList);
                    recdoubt.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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
                data.put("stu_id",stu_id);
                data.put("t_id",t_id);
                Log.d("aaa",data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(DoubtSolving.this).add(request);
    }

    private void allocatememory() {
        recdoubt=findViewById(R.id.recdoubt);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DoubtSolving.this);
        recdoubt.setLayoutManager(mLayoutManager);
        recdoubt.setItemAnimator(new DefaultItemAnimator());
       // floatingadd=findViewById(R.id.floatadddoubt);
    }
}
