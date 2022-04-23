package com.aspirepublicschool.gyanmanjari.uniform;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.uniform.Delivery.DeliveryDetails;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Address extends AppCompatActivity {
    EditText edtmobile,edtplotno,edtstreet,edtlandmark,edtcity,edtstate,edtpincode;
    Button btnaddress;
    public static int flag=0;
    String total_amount,sin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        allocatememory();
        total_amount=getIntent().getExtras().getString("total_amount");
        sin = getIntent().getStringExtra("sin");
        btnaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validaeInput())
                {
                    SendRequest();
                }
            }
        });

    }

    private boolean validaeInput() {
        if (edtmobile.getText().toString().isEmpty()==true) {
            edtmobile.setError("Mobile No cannot be empty");
            edtmobile.requestFocus();
            return false;
        }
        if(edtmobile.getText().toString().length()<10)
        {
            edtmobile.setError("Mobile No cannot be less than 10");
            edtmobile.requestFocus();
            return false;

        }
        if (edtplotno.getText().toString().isEmpty()==true) {
            edtplotno.setError("Plot no. cannot be empty");
            edtplotno.requestFocus();
            return false;
        }
        if (edtstreet.getText().toString().isEmpty()==true) {
            edtstreet.setError("Street cannot be empty");
            edtstreet.requestFocus();
            return false;
        }
        if (edtlandmark.getText().toString().isEmpty()==true) {
            edtlandmark.setError("Landmark cannot be empty");
            edtlandmark.requestFocus();
            return false;
        }
        if (edtcity.getText().toString().isEmpty()==true) {
            edtcity.setError("City cannot be empty");
            edtcity.requestFocus();
            return false;
        }
        if (edtstate.getText().toString().isEmpty()==true) {
            edtstate.setError("State cannot be empty");
            edtstate.requestFocus();
            return false;
        }
        if (edtpincode.getText().toString().isEmpty()==true) {
            edtpincode.setError("Pincode cannot be empty");
            edtpincode.requestFocus();
            return false;
        }
        if (edtpincode.getText().toString().length()<6) {
            edtpincode.setError("Pincode cannot be less than 6");
            edtpincode.requestFocus();
            return false;
        }
            return true;

    }

    private void SendRequest() {
        Common.progressDialogShow(this);
        String Webserviceurl=Common.GetWebServiceURL()+"changeaddress.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = preferences.getString("stu_id", "none");
        final String sc_id = preferences.getString("sc_id", "none").toLowerCase();
        final String sc_std = preferences.getString("std", "none");
        final String med = preferences.getString("med", "none");
        final String gender = preferences.getString("gender", "none");
        StringRequest request=new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(Address.this);
                    JSONObject object=new JSONObject(response);
                    Log.d("aaaa",response);
                    String message=object.getString("message");
                    Toast.makeText(Address.this,message,Toast.LENGTH_LONG).show();
                    if(message.equals("Address Changed")==true)
                    {
                        Intent intent=new Intent(Address.this, DeliveryDetails.class);
                        intent.putExtra("address",edtplotno.getText().toString()+","+edtstreet.getText().toString()+","+edtlandmark.getText().toString()+","+edtcity.getText().toString()+","+edtstate.getText().toString()+"-"+edtpincode.getText().toString());
                        intent.putExtra("total_amount",total_amount);
                        intent.putExtra("sin",sin);
                        startActivity(intent);
                        flag=1;
                    }
                    else
                    {

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
              data.put("user_id",uid);
              data.put("sc_id",sc_id);
              data.put("mobileno",edtmobile.getText().toString());
              data.put("address",edtplotno.getText().toString()+","+edtstreet.getText().toString()+","+edtlandmark.getText().toString());
              data.put("pin",edtpincode.getText().toString());
              data.put("city",edtcity.getText().toString());
              data.put("zone",edtlandmark.getText().toString());
              data.put("state",edtstate.getText().toString());
                Log.d("aaa",data.toString());
              return data;
            }
        };
        Volley.newRequestQueue(Address.this).add(request);
    }

    private void allocatememory() {
        edtmobile=findViewById(R.id.edtmobile);
        edtplotno=findViewById(R.id.edtplotno);
        edtstreet=findViewById(R.id.edtstreet);
        edtlandmark=findViewById(R.id.edtlandmark);
        edtcity=findViewById(R.id.edtcity);
        edtstate=findViewById(R.id.edtstate);
        edtpincode=findViewById(R.id.edtpincode);
        btnaddress=findViewById(R.id.btnaddress);


    }
}
