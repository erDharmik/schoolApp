package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.NewRegister.NewTutionDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OTPVerificationActivity extends AppCompatActivity {

//    EditText inputotp1, inputotp2, inputotp3, inputotp4, inputotp5, inputotp6;
    EditText otpEdit;
    TextView textresendotp,timer;
    String currentDateTime, number, otp, status, nextCall = "none";
    Button verifyotp;

    ProgressDialog progressdialog;

    private CountDownTimer cTimer;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        getSupportActionBar().hide();

        verifyotp = findViewById(R.id.verifyotp);

        number = getIntent().getStringExtra("number");
        nextCall = getIntent().getStringExtra("next");

        sendOTP(number);

        otpEdit = findViewById(R.id.otpEdit);
        timer = findViewById(R.id.timer);
        textresendotp = findViewById(R.id.textresendotp);

        progressdialog = new ProgressDialog(OTPVerificationActivity.this);
        progressdialog.setMessage("Verifying...");

        TextView textView = findViewById(R.id.textmobileshownumber);
        textView.setText(number);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentDateTime = dateFormat.format(new Date()); // Find todays date

        textresendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;

                if (count<4){
                    sendOTP(number);
                    startTimer(count);
                }else if (count == 4){
                    sendOTP(number);
                }else{
                    Toast.makeText(getApplicationContext(), "Try after some time!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (otpEdit.getText().toString().length() == 6){
                    if (nextCall.equals("register")){
                        RegistrationVerification(otpEdit.getText().toString().trim());
                    }else if (nextCall.equals("login")){
                        verifyOTPMethod(otpEdit.getText().toString().trim());
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Enter 6 digit OTP", Toast.LENGTH_SHORT).show();
                }

            }
        });

        autoVerifyFunction();

    }

    private void RegistrationVerification(String otp) {
        final String finalOTP = otp;

        progressdialog.show();
        verifyotp.setFocusable(false);

        String Webserviceurl = Common.GetWebServiceURL() + "registerOtpVerification.php";
        Log.d("@@@web", Webserviceurl);
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressdialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    String message = jsonArray.getJSONObject(0).getString("message");
                    if (message.equalsIgnoreCase("fail")){
                        verifyotp.setFocusable(true);
                        Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }
                    else if(message.equalsIgnoreCase("true")){

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(OTPVerificationActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("number",number);
                        editor.putString("sc_id",jsonArray.getJSONObject(1).getString("sc_id"));
                        editor.putString("token",currentDateTime);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), NewTutionDetailsActivity.class);
                        intent.putExtra("number", number);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                verifyotp.setFocusable(true);
                Toast.makeText(getApplicationContext(), error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("number", number);
                data.put("otp", finalOTP);
                data.put("token", currentDateTime);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(OTPVerificationActivity.this);
        requestQueue.add(request);

    }

    private void verifyOTPMethod(String tempOTP) {

        progressdialog.show();
        verifyotp.setFocusable(false);

        final String finalOTP = tempOTP;
            String Webserviceurl = Common.GetWebServiceURL() + "verifynumberwithOTP.php";
            Log.d("@@@web", Webserviceurl);
            StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressdialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        String message = jsonArray.getJSONObject(0).getString("message");
                        if (message.equalsIgnoreCase("fail")){
                            verifyotp.setFocusable(true);
                            Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();

                        }
                        else if (message.equalsIgnoreCase("true_t")){

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(OTPVerificationActivity.this);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("number",number);
                            editor.putString("sc_id",jsonArray.getJSONObject(1).getString("sc_id"));
                            editor.putString("token",currentDateTime);
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), SimpleDashboardActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                        else if(message.equalsIgnoreCase("true_m")){

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(OTPVerificationActivity.this);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("number",number);
                            editor.putString("stu_id",jsonArray.getJSONObject(1).getString("stu_id"));
                            editor.putString("class_id",jsonArray.getJSONObject(1).getString("cid"));
                            editor.putString("sc_id",jsonArray.getJSONObject(1).getString("sc_id"));
                            editor.putString("token",currentDateTime);
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressdialog.dismiss();
                    verifyotp.setFocusable(true);
                    Toast.makeText(getApplicationContext(), error.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("number", number);
                    data.put("otp", finalOTP);
                    data.put("token", currentDateTime);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(OTPVerificationActivity.this);
            requestQueue.add(request);

    }

    private void autoVerifyFunction() {
        otpEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(otpEdit.getText().toString().length() == 6){
                    status = "auto";
                    if (nextCall.equals("register")){
                        RegistrationVerification(otpEdit.getText().toString().trim());
                    }else if (nextCall.equals("login")){
                        verifyOTPMethod(otpEdit.getText().toString().trim());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void sendOTP(String number) {

        final String mobile = number;
        String Webserviceurl = Common.GetWebServiceURL() + "getotp.php";
        Log.d("@@@web", Webserviceurl);
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("true")){
                    Toast.makeText(getApplicationContext(), "OTP has been sent to your mobile number.", Toast.LENGTH_SHORT).show();
                }
                else if (response.equalsIgnoreCase("nodata")){
                    Toast.makeText(getApplicationContext(), "Please Enter Registered Mobile Number", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getIntent()));
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
                Map<String, String> data = new HashMap<>();
                data.put("number", mobile);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(OTPVerificationActivity.this);
        requestQueue.add(request);
    }


    void startTimer(int count) {
            int inter = 10000;
            int temptime = inter * count;
            cTimer = new CountDownTimer(temptime, 1000) {
                public void onTick(long millisUntilFinished) {

                    textresendotp.setText("Retry after: " +String.valueOf(millisUntilFinished/1000));
                    textresendotp.setEnabled(false);

                }
                public void onFinish() {
//                    textresendotp.setVisibility(View.VISIBLE);
//                    timer.setVisibility(View.INVISIBLE);
                    textresendotp.setText("RESEND OTP");
                    textresendotp.setEnabled(true);
                }
            };
            cTimer.start();

    }

}