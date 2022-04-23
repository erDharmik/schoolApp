package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerifyAccount extends AppCompatActivity {
    EditText pinEditText;
    Button submitButton;
    String rand,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);
        pinEditText=findViewById(R.id.pinEditText);
        submitButton=findViewById(R.id.submitButton);
        rand=getIntent().getExtras().getString("rand");
        username=getIntent().getExtras().getString("username");
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    if(rand.equals(pinEditText.getText().toString()))
                    {
                        //changestatus(rand);
                        Logoutactivedevice();
//                        Toast.makeText(VerifyAccount.this, R.string.logout_message, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void Logoutactivedevice() {
        Common.progressDialogShow(VerifyAccount.this);
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String sc_id = mPrefs.getString("sc_id", "none");
        String Webserviceurl = Common.GetWebServiceURL() + "alreadylogin.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("aaa", response);
                    Common.progressDialogDismiss(VerifyAccount.this);
                    JSONObject object = new JSONObject(response);
                    String messsage = object.getString("message");
                    if (messsage.equals("Submitted")) {
                        Common.checkvalidlogin(VerifyAccount.this);
                        startActivity(new Intent(VerifyAccount.this,Login.class));
                        finish();
                        /*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("stu_id");
                        editor.remove("status");
                        editor.remove("class_id");
                        editor.remove("stu_name");
                        editor.remove("std");
                        editor.remove("sc_id");
                        editor.remove("st_dp");
                        editor.remove("city");
                        editor.remove("address");
                        editor.remove("mobile");
                        editor.commit();*/
                       /* SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token",rand);
                        editor.commit();
                        Intent intent=new Intent(VerifyAccount.this,Login.class);
                        startActivity(intent);
                        finish();*/


                    } else {
                        Toast.makeText(VerifyAccount.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VerifyAccount.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("sc_id", sc_id);
                data.put("stu_id", username);
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(VerifyAccount.this).add(request);
    }
    private boolean validateInputs() {
        if (pinEditText.getText().toString().isEmpty()) {
            pinEditText.setError("Verification Pin cannot be empty");
            pinEditText.requestFocus();
            return false;
        }

        return true;
    }
}
