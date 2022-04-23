package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;

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

public class AcademicCalendar extends AppCompatActivity {
    RecyclerView recacademic;
    ArrayList<Academic> academicsList=new ArrayList<>();
    String School_id;
    private static final int REQUEST_CODE =1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_calendar);
        ActivityCompat.requestPermissions(
                AcademicCalendar.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("LLL","Permission is granted");
                //File write logic here
            }
        }


        allocatemeory();
        loadAcademicYear();
    }
    private void loadAcademicYear() {
        Common.progressDialogShow(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String sc_id = preferences.getString("sc_id","none").toUpperCase();
        String ACADEMIC_URL = Common.GetWebServiceURL()+"academic_calender.php" ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ACADEMIC_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Common.progressDialogDismiss(AcademicCalendar.this);
                    JSONArray array = new JSONArray(response);
                    for (int i=0; i<array.length();i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        academicsList.add(new Academic(
                                School_id=jsonObject.getString("sc_id"),
                                jsonObject.getString("ac_year"),
                                jsonObject.getString("date"),
                                jsonObject.getString("img")
                        ));
                        Log.d("AAAAA", jsonObject.getString("ac_year"));
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AcademicCalendar.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("school_id", School_id);
                        editor.commit();
                       AcademicCalendarAdapter academicCalendarAdapter=new AcademicCalendarAdapter(AcademicCalendar.this,academicsList);
                        RecyclerView.LayoutManager manager = new LinearLayoutManager(AcademicCalendar.this);
                        recacademic.setLayoutManager(manager);
                        recacademic.setItemAnimator(new DefaultItemAnimator());
                       recacademic.setAdapter(academicCalendarAdapter);

                    }
                }
                catch (JSONException e){
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
                params.put("sc_id",sc_id);
                return params;
            }
        };
        Volley.newRequestQueue(AcademicCalendar.this).add(stringRequest);
    }

    private void allocatemeory() {
        recacademic=findViewById(R.id.recacademic);
        recacademic.addItemDecoration(new DividerItemDecoration(recacademic.getContext(), DividerItemDecoration.VERTICAL));
    }
}
