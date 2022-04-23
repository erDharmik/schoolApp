package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SimpleDashboardActivity extends AppCompatActivity {

    String number, currentDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_dashboard);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        number = mPrefs.getString("number", "none");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentDateTime = dateFormat.format(new Date()); // Find todays date

        checkStatus();

    }

    private void checkStatus() {

        String Webserviceurl = Common.GetWebServiceURL() + "checkStatusofClass.php";
        Log.d("@@@web", Webserviceurl);
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    String message = jsonArray.getJSONObject(0).getString("message");
                    if (message.equalsIgnoreCase("fail")){
                        Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();
                    }

                    else if (message.equalsIgnoreCase("true")){

                        String cid = jsonArray.getJSONObject(1).getString("cid");
                        if (cid.equals("nocid")){
                        }else if(!cid.isEmpty()){
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SimpleDashboardActivity.this);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("number",number);
                            editor.putString("class_id",cid);
                            editor.putString("token",currentDateTime);
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("number", number);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SimpleDashboardActivity.this);
        requestQueue.add(request);

    }
}