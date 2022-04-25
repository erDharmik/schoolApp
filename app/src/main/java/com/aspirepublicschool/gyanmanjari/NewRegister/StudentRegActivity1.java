package com.aspirepublicschool.gyanmanjari.NewRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.EmptyActivity;
import com.aspirepublicschool.gyanmanjari.Login;
import com.aspirepublicschool.gyanmanjari.OTPLogin;
import com.aspirepublicschool.gyanmanjari.OTPVerificationActivity;
import com.aspirepublicschool.gyanmanjari.PayTM.checkSumActivity;
import com.aspirepublicschool.gyanmanjari.PayTM.checksum;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.empty;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class StudentRegActivity1 extends AppCompatActivity {

    EditText editFirstName,editLastName,editMobile;
    //    EditText editEmail;
    TextView txtSignIn, text_status,text_termsandcondition;
    Button btnGetOtp;
    CheckBox checkbox_TandC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reg1);
        getSupportActionBar().hide();

        // Find View by ID
        editFirstName = findViewById(R.id.editFName);
        editLastName = findViewById(R.id.editLName);
        editMobile = findViewById(R.id.editMobileNumber);
//        editEmail = findViewById(R.id.editEmail);
        txtSignIn = findViewById(R.id.txtSignIn);
        btnGetOtp = findViewById(R.id.btnVerifyOtp);
        checkbox_TandC = findViewById(R.id.checkbox_TandC);
        text_status = findViewById(R.id.text_status);
        text_termsandcondition = findViewById(R.id.text_termsandcondition);
        text_status.setVisibility(View.INVISIBLE);

        btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Testing

                getOTP();
            }
        });

        text_termsandcondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox_TandC.isChecked()){
                    checkbox_TandC.setChecked(false);
                }else{
                    checkbox_TandC.setChecked(true);
                }
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), OTPLogin.class));
//                startActivity(new Intent(getApplicationContext(), empty.class));
//                i.setClass(getApplicationContext() , empty.class);
//                startActivity(i);



            }
        });
    }

    private void getOTP() {
        if (checkbox_TandC.isChecked()) {
            text_status.setVisibility(View.INVISIBLE);
            basicRegister();

        } else {
            text_status.setVisibility(View.VISIBLE);
        }
    }



    private void basicRegister() {
        final String fname = editFirstName.getText().toString().trim();
        final String lname = editLastName.getText().toString().trim();
        final String m_no = editMobile.getText().toString().trim();
//        final String email = editEmail.getText().toString().trim();

        String WebserviceURL = Common.GetWebServiceURL() + "tempRegister.php";
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, WebserviceURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("%%%",response);
                    JSONArray array = new JSONArray(response);
                    String check = array.getJSONObject(0).getString("message");

                    if (check.equals("true")) {

                        String stu_id = array.getJSONObject(1).getString("stu_id");

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(StudentRegActivity1.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("number", m_no);
                        editor.putString("stu_id", stu_id);
                        editor.commit();

                        startActivity(new Intent(getApplicationContext(), OTPRegisterVerificationActivity.class));

                    }  else if(check.equals("fail")){
                        Toast.makeText(getApplicationContext()," Failed !", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fname", fname);
                params.put("lname", lname);
                params.put("m_no", m_no);
//                params.put("email", email);
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);


    }


}