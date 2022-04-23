package com.aspirepublicschool.gyanmanjari.NewRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;

import java.util.HashMap;
import java.util.Map;

public class StudentRegOtpActivity extends AppCompatActivity {

    EditText editOtp;
    TextView txtResendOtp, editTextPasswordOne, editTextPasswordTwo, passwordMatchingStatus;
    Button   btnVerifyOtp, createPassWordBtn;
    LinearLayout lnrotp, lnrpassword;
    ScrollView layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reg_otp);
        getSupportActionBar().hide();

        //SendOTP to User
        final String number = getIntent().getStringExtra("m_no");
//        sendOTP(number);

        lnrotp = findViewById(R.id.lnrOtp);
        lnrpassword = findViewById(R.id.lnrPassword);
        lnrpassword.setVisibility(LinearLayout.GONE);
        editOtp = findViewById(R.id.editOTP);
        txtResendOtp = findViewById(R.id.txtSignIn);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);
        createPassWordBtn = findViewById(R.id.createPassWordBtn);
        editTextPasswordOne = findViewById(R.id.editTextPasswordOne);
        editTextPasswordTwo = findViewById(R.id.editTextPasswordTwo);
        passwordMatchingStatus = findViewById(R.id.passwordMatchingStatus);
        passwordMatchingStatus.setVisibility(View.INVISIBLE);

        createPassWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(StudentRegOtpActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("number" , number);
                editor.commit();

                Intent i = new Intent(getApplicationContext(), TuitionDetailActivity.class);
                startActivity(i);
//                registerPassword(number);
            }
        });

        editTextPasswordOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                matchPassword();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                matchPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {
                matchPassword();
            }
        });

        editTextPasswordTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                matchPassword();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                matchPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {
                matchPassword();
            }
        });

        // Verify OTP
        btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnrotp.setVisibility(LinearLayout.GONE);
//                lnrpassword.setVisibility(LinearLayout.VISIBLE);

                //942022
                startActivity(new Intent(getApplicationContext(),NewTutionDetailsActivity.class));



                if (editOtp.getText().toString().isEmpty()){
//                    Toast.makeText(getApplicationContext(), "Please Enter OTP", Toast.LENGTH_SHORT).show();
                }else{
//                verifyOTP(number);
                }
            }
        });


    }

    private void registerPassword(String number) {
        final String m_no = number;
        final String password = editTextPasswordOne.getText().toString().trim();
        final String password123 = editTextPasswordTwo.getText().toString().trim();
        if (password.equals(password123)){
        String WebserviceURL = Common.GetWebServiceURL() + "createPassword.php";
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, WebserviceURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("true")) {
                    Toast.makeText(getApplicationContext(), "Password Created Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), TuitionDetailActivity.class);
                    startActivity(i);
                }
                else if(response.equals("fail")){
                    Toast.makeText(getApplicationContext(), "Failed! Try Again..", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("number", m_no);
                params.put("password", password);
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
        }
        else{
            Toast.makeText(getApplicationContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ResourceAsColor")
    private void matchPassword() {
        String one = editTextPasswordOne.getText().toString().trim();
        String two = editTextPasswordTwo.getText().toString().trim();

        if(one.equals(two)){
            passwordMatchingStatus.setVisibility(View.VISIBLE);
            passwordMatchingStatus.setText(R.string.password_matched);
            passwordMatchingStatus.setTextColor(this.getResources().getColor(R.color.green));
        }
        else{
            passwordMatchingStatus.setVisibility(View.VISIBLE);
            passwordMatchingStatus.setText(R.string.password_Mismatched);
            passwordMatchingStatus.setTextColor(this.getResources().getColor(R.color.red));
        }
    }

    private void sendOTP(final String number) {

        final String m_no = number;
        String WebserviceURL = Common.GetWebServiceURL() + "getotp.php";
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, WebserviceURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("true")) {

                    Toast.makeText(getApplicationContext(), "OTP has been sent to your Mobile number", Toast.LENGTH_SHORT).show();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("number", m_no);
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    private void verifyOTP(String number) {

        final String m_no = number;

        final String WebserviceURL = Common.GetWebServiceURL() + "verifynumberwithOTP.php";
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, WebserviceURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("true")) {
                    Toast.makeText(StudentRegOtpActivity.this, "OTP Verified", Toast.LENGTH_SHORT).show();
                    lnrotp.setVisibility(LinearLayout.GONE);
                    lnrpassword.setVisibility(LinearLayout.VISIBLE);
                }else if(response.equals("fail")){
                    Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("number", m_no);
                params.put("otp", editOtp.getText().toString().trim());
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);


    }
}