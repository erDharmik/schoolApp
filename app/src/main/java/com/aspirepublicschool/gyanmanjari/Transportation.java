package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

public class Transportation extends AppCompatActivity {
    TextView t_type,d_name,d_cont,v_no,route;

    SharedPreferences mPrefs= PreferenceManager.getDefaultSharedPreferences(getBaseContext());;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);




        t_type = findViewById(R.id.t_type);
        d_name = findViewById(R.id.d_name);
        d_cont = findViewById(R.id.d_cont);
        v_no = findViewById(R.id.v_no);
        route = findViewById(R.id.route);
        loadData();
    }
    private void loadData() {

        final String s_id = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id","none");

        String URL = "http://zocarro.com/zocarro_mobile/school/ws/transport.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    JSONObject object =array.getJSONObject(0);
                    t_type.setText(object.getString("t_type"));
                    d_name.setText(object.getString("d_name"));
                    d_cont.setText(object.getString("d_cont"));
                    object.getString("v_id");
                    v_no.setText(object.getString("v_no"));
                    route.setText(object.getString("route"));

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
                Map<String,String> params = new HashMap<>();
                params.put("sc_id",sc_id);
                params.put("stu_id",s_id);
                return params;
            }
        };
        Volley.newRequestQueue(Transportation.this).add(stringRequest);
    }
}
