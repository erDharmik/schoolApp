package com.aspirepublicschool.gyanmanjari.Profile.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.NewRegister.PrefFacultyAdapter;
import com.aspirepublicschool.gyanmanjari.NewRegister.PrefFacultyModel;
import com.aspirepublicschool.gyanmanjari.NewRegister.ProfileActivity;
import com.aspirepublicschool.gyanmanjari.OTPLogin;
import com.aspirepublicschool.gyanmanjari.Profile.ProfileMainActivity;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SecurityDetails extends Fragment {

    LinearLayout lnrOldPassword, lnrNewPassword;
    Button btnContinue, createNewPassword;
    EditText oldPassword , newPasswordOne, newPasswordTwo;
    String oldPasswordString , number, newPassword, confirmPassword;
    ImageView secureImg;
    TextView passwordMatchingStatus;
    TextView passwordVerification;
    TextView resetPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_security_details, container, false);

        number = "9586417374";

        passwordMatchingStatus = view.findViewById(R.id.passwordMatchingStatus);
        lnrOldPassword = view.findViewById(R.id.lnrOldPassword);
        lnrNewPassword = view.findViewById(R.id.lnrNewPassword);
        lnrNewPassword.setVisibility(LinearLayout.INVISIBLE);
        btnContinue = view.findViewById(R.id.btnContinue);
        oldPassword = view.findViewById(R.id.oldPassword);
        createNewPassword = view.findViewById(R.id.createNewPassword);
        newPasswordOne = view.findViewById(R.id.editTextPasswordOne);
        newPasswordTwo = view.findViewById(R.id.editTextPasswordTwo);
        secureImg = view.findViewById(R.id.secureImage);
        passwordVerification = view.findViewById(R.id.passwordVerification);
        resetPassword = view.findViewById(R.id.resetPassword);

        createNewPassword.setClickable(false);
        passwordVerification.setVisibility(View.INVISIBLE);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oldPasswordString = oldPassword.getText().toString().trim();
                checkOldPassword();
            }
        });

        newPasswordOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                matchPassword();
                buttonStatus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                matchPassword();
                buttonStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                matchPassword();
                buttonStatus();
            }
        });
        newPasswordTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                matchPassword();
                buttonStatus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                matchPassword();
                buttonStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                matchPassword();
                buttonStatus();
            }
        });

        createNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword = newPasswordOne.getText().toString().trim();
                confirmPassword = newPasswordTwo.getText().toString().trim();
                if (newPassword.equals(confirmPassword)){
                    createNewPasswordMethod();
                }else{
                    Toast.makeText(getContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPasswordMethod();
            }
        });

        return view;
    }


    private void createNewPasswordMethod() {
        final String pass = newPassword;
        final String WebServiceUrl = Common.GetWebServiceURL() + "createNewPassword.php";

        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new com.android.volley.Response.Listener<String>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(String response) {
                if (response.contains("true")){
                    Toast.makeText(getContext(), "Password Changed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), ProfileMainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else if(response.contains("fail")){
                    Toast.makeText(getContext(), "Please Try Again!", Toast.LENGTH_SHORT).show();
                    secureImg.setBackgroundColor(R.color.red);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("newPassword", pass);
                data.put("number", number);
                return data;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);

    }

    private void checkOldPassword() {

        final String oldPassword = oldPasswordString;
        String WebServiceUrl = Common.GetWebServiceURL() + "oldPasswordVerification.php";

        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new com.android.volley.Response.Listener<String>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(String response) {
                if (response.contains("true")){
                lnrOldPassword.setVisibility(LinearLayout.INVISIBLE);
                lnrNewPassword.setVisibility(LinearLayout.VISIBLE);
                passwordVerification.setText("");
                passwordVerification.setVisibility(View.INVISIBLE);
                }
                else if(response.contains("fail")){
                    passwordVerification.setVisibility(View.VISIBLE);
                    passwordVerification.setText("Verification Failed!");
                    passwordVerification.setTextColor(getContext().getResources().getColor(R.color.red));
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("oldPassword", oldPassword);
                data.put("number", number);
                return data;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);


    }

    private void matchPassword() {
        String one = newPasswordOne.getText().toString().trim();
        String two = newPasswordTwo.getText().toString().trim();

        if (!one.equals("") && !two.equals("")){
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
    }

    private void buttonStatus(){
        if (passwordMatchingStatus.getText().toString().equals("Password Matched")){
            createNewPassword.setClickable(true);
        }
        else{
            createNewPassword.setClickable(false);
        }
    }

    private void ResetPasswordMethod() {

        Intent intent = new Intent(getContext(), OTPLogin.class);
        startActivity(intent);

    }

}