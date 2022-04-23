package com.aspirepublicschool.gyanmanjari.Test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
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
import com.aspirepublicschool.gyanmanjari.MainActivity;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewTestToday extends AppCompatActivity {
    RecyclerView rectest;
    ArrayList<TodayTest> testArrayList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test_today);
        allocatememory();
       /* LocationManager locMan = (LocationManager) ViewTestToday.this.getSystemService(this.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        long time = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getTime();
        Log.d("time", ""+time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());

        long offset = 0;
        long sinceMid=0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Calendar rightNow = Calendar.getInstance();
            offset = rightNow.get(Calendar.ZONE_OFFSET) +
                    rightNow.get(Calendar.DST_OFFSET);
            sinceMid = (rightNow.getTimeInMillis() + offset) %
                    (24 * 60 * 60 * 1000);
            Log.d("changed time", ""+sinceMid);
        }
        if(time==sinceMid)
        {
            SendRequest();
        }
        else
        {
            Toast.makeText(ViewTestToday.this, "Sorry date and time changed.", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent();
        intent.setAction("com.zocarro.gyanmanjari.TimeChangedReceiver");
        sendBroadcast(intent);*/

            String timeSettings = android.provider.Settings.Global.getString(
                    this.getContentResolver(),
                    android.provider.Settings.Global.AUTO_TIME);
            if (timeSettings.contentEquals("0")) {
                Log.d("Set", "True");
             /*   android.provider.Settings.Global.putString(
                        this.getContentResolver(),
                        android.provider.Settings.Global.AUTO_TIME, "1");*/
                Toast.makeText(ViewTestToday.this, "Sorry date and time changed.Please change in automatic to continue with test.", Toast.LENGTH_LONG).show();

            }
            else
            {
                Log.d("Set", "False");
                SendRequest();

            }



    }

    private void SendRequest() {
        Common.progressDialogShow(ViewTestToday.this);
        String Webserviceurl=Common.GetWebServiceURL()+"getTest.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        final String stu_id = mPrefs.getString("stu_id","none");
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(ViewTestToday.this);
                Log.d("AAa",response);
                testArrayList.clear();

                try {
                    JSONArray array=new JSONArray(response);
                    int total=array.getJSONObject(0).getInt("total");
                    if(total==0) {
                        Toast.makeText(ViewTestToday.this, "Sorry no test Available!!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ViewTestToday.this, MainActivity.class));
                        finish();
                    }
                    else {
                        for (int i = 1; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            testArrayList.add(new TodayTest(object.getString("tst_id"), object.getString("subject"),
                                    object.getString("total"), object.getString("t_date"),object.getString("des"),
                                    object.getString("stime"),object.getString("etime"),object.getString("pos"),
                                    object.getString("neg"),object.getString("t_type")));
                        }
                        TodayTestAdapter adapter = new TodayTestAdapter(ViewTestToday.this, testArrayList);
                        rectest.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(ViewTestToday.this);
                Toast.makeText(ViewTestToday.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id",sc_id);
                data.put("cid",class_id);
                data.put("stu_id",stu_id);
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(ViewTestToday.this).add(request);
    }

    private void allocatememory() {
        rectest=findViewById(R.id.rectest);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ViewTestToday.this);
        rectest.setLayoutManager(mLayoutManager);
        rectest.setItemAnimator(new DefaultItemAnimator());
    }
}
