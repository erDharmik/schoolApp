package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
import com.aspirepublicschool.gyanmanjari.NewRegister.StudentRegActivity1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OTPLogin extends AppCompatActivity {

    EditText mobile;
    Button continueBtn;
    Dialog dialog;
    EditText t1, otp_edittext;
    Button b1, otp_button;
    String number, otp, sc_id, class_id, stu_id, currentDateTime;
    TextView otp_mobilenumber, warningMsg_otp, txtSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otplogin);

        getSupportActionBar().hide();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        sc_id = preferences.getString("sc_id", "none").toUpperCase();
        class_id = preferences.getString("class_id", "none");
        stu_id = preferences.getString("stu_id", "none");



//        isLoggedIn();

        continueBtn = findViewById(R.id.btnCont);
        mobile = findViewById(R.id.editTextMobile);
        txtSignIn = findViewById(R.id.txtsignin);

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StudentRegActivity1.class));
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = mobile.getText().toString().trim();
                continueLogin();
            }
        });

    }


    private void dialogmethod() {
        dialog=new Dialog(OTPLogin.this);
        dialog.setContentView(R.layout.otpverification);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        otp_edittext = dialog.findViewById(R.id.otp_edittext);
        warningMsg_otp = dialog.findViewById(R.id.warningMsg_otp);
        otp_mobilenumber = dialog.findViewById(R.id.otp_mobilenumber);
        otp_button = dialog.findViewById(R.id.otp_button);
        String finalnumber = "Mobile no. - " + number;
        otp_mobilenumber.setText(finalnumber);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                backpressfun();
            }
        });
        otp = otp_edittext.getText().toString();

        otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = otp_edittext.getText().toString();
                if (temp.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter Valid OTP First", Toast.LENGTH_SHORT).show();
                }else{
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    currentDateTime = dateFormat.format(new Date()); // Find todays date
                    verifyotp();
                }

            }
        });

    }

    private void verifyotp() {

        String Webserviceurl = Common.GetWebServiceURL() + "verifynumberwithOTP.php";
        Log.d("@@@web", Webserviceurl);
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    String message = jsonArray.getJSONObject(0).getString("message");
                    if (message.equalsIgnoreCase("fail")){
                        Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }
                    else if(message.equalsIgnoreCase("true")){

                        Toast.makeText(getApplicationContext(), currentDateTime, Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(OTPLogin.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("number",mobile.getText().toString().trim());
                        editor.putString("stu_id",jsonArray.getJSONObject(1).getString("stu_id"));
                        editor.putString("class_id",jsonArray.getJSONObject(1).getString("cid"));
                        editor.putString("sc_id",jsonArray.getJSONObject(1).getString("sc_id"));
                        editor.putString("token",currentDateTime);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

//                        String paid = jsonArray.getJSONObject(1).getString("paid");
//
//                        if (paid.equals("1")){
//                            Toast.makeText(getApplicationContext(), "Verified Successfully", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//                        }
//                        else{
//                            Toast.makeText(getApplicationContext(), "Pay Fees First", Toast.LENGTH_SHORT).show();
//                        }


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
                data.put("otp", otp_edittext.getText().toString());
                data.put("token", currentDateTime);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(OTPLogin.this);
        requestQueue.add(request);
    }


    private void backpressfun() {
        warningMsg_otp.setText("Please Do Not Click Back while Verification is InProgress");

    }

    private void continueLogin() {
        if (number.length() == 10){
//            sendOTP();
//            dialogmethod();

            startActivity(new Intent(getApplicationContext(), OTPVerificationActivity.class).
                    putExtra("number", number).
                    putExtra("next", "login"));

        }else{
            Toast.makeText(getApplicationContext(), "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendOTP() {

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
                data.put("number", mobile.getText().toString().trim());
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(OTPLogin.this);
        requestQueue.add(request);
    }


    private void isLoggedIn() {

        String Webserviceurl = Common.GetWebServiceURL() + "isLoggedIn.php";
        Log.d("@@@web", Webserviceurl);
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    String message = jsonArray.getJSONObject(0).getString("message");
                    if (message.equalsIgnoreCase("fail")){
                        Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                    }
                    else if(message.equalsIgnoreCase("true")){

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(OTPLogin.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("number",mobile.getText().toString().trim());
                        editor.putString("stu_id",jsonArray.getJSONObject(1).getString("stu_id"));
                        editor.putString("class_id",jsonArray.getJSONObject(1).getString("cid"));
                        editor.putString("sc_id",jsonArray.getJSONObject(1).getString("sc_id"));
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

//                        String paid = jsonArray.getJSONObject(1).getString("paid");
//
//                        if (paid.equals("1")){
//                            Toast.makeText(getApplicationContext(), "Verified Successfully", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//                        }
//                        else{
//                            Toast.makeText(getApplicationContext(), "Pay Fees First", Toast.LENGTH_SHORT).show();
//                        }


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
                data.put("stu_id", stu_id);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(OTPLogin.this);
        requestQueue.add(request);

    }

}