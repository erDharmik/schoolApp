package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Profile.ProfileMainActivity;

import java.util.HashMap;
import java.util.Map;

public class CreateNewPassword extends AppCompatActivity {

    TextView passwordMatchingStatus;
    Button btnContinue, createNewPassword;
    EditText oldPassword , newPasswordOne, newPasswordTwo;
    String oldPasswordString , number, newPassword, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);

        number = "9586417374";

        passwordMatchingStatus = findViewById(R.id.passwordMatchingStatus);
        btnContinue = findViewById(R.id.btnContinue);
        oldPassword = findViewById(R.id.oldPassword);
        createNewPassword = findViewById(R.id.createNewPassword);
        newPasswordOne = findViewById(R.id.editTextPasswordOne);
        newPasswordTwo = findViewById(R.id.editTextPasswordTwo);
        createNewPassword.setClickable(false);

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
                    Toast.makeText(getApplicationContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void createNewPasswordMethod() {
        final String pass = newPassword;
        final String WebServiceUrl = Common.GetWebServiceURL() + "createNewPassword.php";

        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new com.android.volley.Response.Listener<String>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(String response) {
                if (response.contains("true")){
                    Toast.makeText(getApplicationContext(), "Password Changed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ProfileMainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else if(response.contains("fail")){
                    Toast.makeText(getApplicationContext(), "Please Try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(getApplicationContext()).add(request);

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
}