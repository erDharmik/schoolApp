package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HolidayEvents extends AppCompatActivity {
    RecyclerView relholiday;
    ArrayList<Holiday> holidayList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday_events);
        allocatememory();
        SendRequest();
    }

    private void SendRequest() {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final  String sc_id = mPrefs.getString("sc_id","none");
        String Webserviceurl=Common.GetWebServiceURL()+"displayHoliday.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray res=new JSONArray(response);
                    for(int i=0;i<res.length();i++)
                    {
                        JSONObject current=res.getJSONObject(i);
                        holidayList.add(new Holiday(current.getString("date"),current.getString("name")));


                    }
                    HolidayAdapter adapter=new HolidayAdapter(HolidayEvents.this,holidayList);
                    RecyclerView.LayoutManager manager = new LinearLayoutManager(HolidayEvents.this);
                    relholiday.setLayoutManager(manager);
                    relholiday.addItemDecoration(new DividerItemDecoration(relholiday.getContext(), DividerItemDecoration.VERTICAL));
                    relholiday.setItemAnimator(new DefaultItemAnimator());
                    relholiday.setAdapter(adapter);
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
               return data;
            }
        };
        Volley.newRequestQueue(HolidayEvents.this).add(request);
    }

    private void allocatememory() {
        relholiday=findViewById(R.id.relholidays);
    }
}
