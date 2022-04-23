package com.aspirepublicschool.gyanmanjari.Stationery.ReturnReplace;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.uniform.Common;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;


public class ReasonReplace extends AppCompatActivity {
    String order_id,sin,addressdata,mobile_no,s_id,qty,payment_method,p_code,price,size;
    RadioGroup rdgreasons;
    EditText edtresons,edt_quantity;
    Button btnsumbit;
    String direction="Return";
    String orderId="", mid="",first,second,third,fourth,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason_replace);
        order_id=getIntent().getExtras().getString("order_id");
        sin=getIntent().getExtras().getString("sin");
        addressdata=getIntent().getExtras().getString("address");
        mobile_no=getIntent().getExtras().getString("mobileno");
        s_id=getIntent().getExtras().getString("s_id");
        qty=getIntent().getExtras().getString("qty");
        payment_method=getIntent().getExtras().getString("payment_method");
        p_code=getIntent().getExtras().getString("p_code");
        price=String.valueOf(getIntent().getExtras().getInt("price"));
        size=String.valueOf(getIntent().getExtras().getString("size"));
        allocatememory();
        UUID odid  = UUID.randomUUID();
        String[] parts = odid.toString().split("");
        first = parts[5] ;
        second = parts[6];
        third = parts[7];
        String oodid =  "ODM" + first + second +third;
        orderId = String.valueOf(oodid);
        rdgreasons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rdreturn:
                        // do operations specific to this selection
                        direction="Return";
                        break;
                    case R.id.rdreplace:
                        // do operations specific to this selection
                        direction="Replace";
                        break;
                }
            }
        });
        btnsumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validInput())
                {
                    SendRequest();
                }
            }
        });
    }

    private boolean validInput() {
        if (edtresons.getText().toString().isEmpty()==true) {
            edtresons.setError("Reason cannot be empty");
            edtresons.requestFocus();
            return false;
        }
        if (edt_quantity.getText().toString().isEmpty()==true) {
            edt_quantity.setError("Quatity cannot be empty");
            edt_quantity.requestFocus();
            return false;
        }
        int quantity=Integer.parseInt(edt_quantity.getText().toString());
        if(quantity>Integer.parseInt(qty))
        {
            edt_quantity.setError("Quatity cannot be greater than booked quantity");
            edt_quantity.requestFocus();
            return false;
        }

        return true;
    }

    private void SendRequest() {
        String Webserviceurl= Common.GetWebServiceURL()+"returnProduct.php";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        time = sdf.format(new Date());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = preferences.getString("stu_id", "none");
        final String sc_id = preferences.getString("sc_id", "none").toUpperCase();
        final String sc_std = preferences.getString("std", "none");
        final String med = preferences.getString("med", "none");
        final String gender = preferences.getString("gender", "none");
        final String address = preferences.getString("address", "none");
        final String mobile = preferences.getString("mobile", "none");
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    String message=object.getString("message");
                    if(message.equals("success")==true)
                    {
                        Toast.makeText(ReasonReplace.this,message,Toast.LENGTH_LONG).show();
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
                data.put("order_id", orderId);
                data.put("uid", uid);
                data.put("mobile",mobile_no);
                data.put("address", addressdata);
                data.put("sin", sin);
                data.put("size", size);
                data.put("amount", price);
                data.put("quantity",edt_quantity.getText().toString());
                data.put("payment_mode", payment_method);
                data.put("status", "0");
                data.put("time", time);
                data.put("s_id", s_id);
                data.put("order_status", "Not Set");
                data.put("direction", direction);
                data.put("reason", edtresons.getText().toString());
                data.put("r_status","0");
                data.put("s_reason","Not Set");
                Log.d("$$$",data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(ReasonReplace.this).add(request);
    }

    private void allocatememory() {
        rdgreasons=findViewById(R.id.rdgreasons);
        edtresons=findViewById(R.id.edtresons);
        btnsumbit=findViewById(R.id.btnsumbit);
        edt_quantity=findViewById(R.id.edt_quantity);
    }
}
