package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdatePassword extends AppCompatActivity {
    EditText old_password,new_password;
    Button btn_update;
    TextView txtpasswordstrength;
    String old_pass,new_pass;
    Boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        allocatememory();

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_pass = old_password.getText().toString().trim();
                new_pass = new_password.getText().toString().trim();
                if(validateInputs()) {
                    if (flag) {
                        old_pass = old_password.getText().toString().trim();
                        new_pass = new_password.getText().toString().trim();
                        sendRequest(old_pass, new_pass);
                    }
                }


            }
        });
        new_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculatePasswordStrength(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private boolean validateInputs() {
        if (old_password.getText().toString().equals("")) {
            old_password.setError("Password cannot be empty");
            old_password.requestFocus();
            return false;
        }
        if (new_password.getText().toString().equals("")) {
            new_password.setError("New Password cannot be empty");
            new_password.requestFocus();
            return false;
        }

        return true;
    }
    private void calculatePasswordStrength(String str) {
        // Now, we need to define a PasswordStrength enum
        // with a calculate static method returning the password strength
        PasswordStrength passwordStrength = PasswordStrength.calculate(str);
        txtpasswordstrength.setText(passwordStrength.msg);
        txtpasswordstrength.setTextColor(passwordStrength.color);
        old_pass = old_password.getText().toString().trim();
        new_pass = new_password.getText().toString().trim();
        if (old_pass.length()!=0 && new_pass.length()!=0 && passwordStrength.msg.equals("Strong")|| passwordStrength.msg.equals("Medium")){
            flag=true;

        }
        else {
            flag=false;
            //Toast.makeText(UpdatePassword.this, "Please Enter Valid Details", Toast.LENGTH_SHORT).show();
        }
    }


    private void sendRequest(final String old_pass, final String new_pass) {
        Common.progressDialogShow(UpdatePassword.this);
        String URL = Common.GetWebServiceURL()+"updateStudentPassword.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String stu_id = mPrefs.getString("stu_id", "none");


        Toast.makeText(UpdatePassword.this, stu_id, Toast.LENGTH_SHORT).show();


        final String sc_id = mPrefs.getString("sc_id", "none").toLowerCase();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(UpdatePassword.this);
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("success"))
                    {
                        Toast.makeText(UpdatePassword.this, "Password Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdatePassword.this,MainActivity.class));
                    }
                    else {
                        Toast.makeText(UpdatePassword.this, "Try Again", Toast.LENGTH_SHORT).show();
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
                Map<String,String> params = new HashMap<>();
                params.put("stu_id",stu_id);
                params.put("opass",old_pass);
                params.put("npass",new_pass);
                Log.d("###",params.toString());
                return params;
            }
        };
        Volley.newRequestQueue(UpdatePassword.this).add(stringRequest);

    }

    private void allocatememory() {
        btn_update = findViewById(R.id.btn_update);
        old_password = findViewById(R.id.old_pass);
        new_password = findViewById(R.id.new_pass);
        txtpasswordstrength = findViewById(R.id.txtpasswordstrength);
    }
    public enum PasswordStrength {

        // we use some color in green tint =>
        //more secure is the password, more darker is the color associated
        WEAK("Weak", Color.parseColor("#c92e12")),
        MEDIUM("Medium", Color.parseColor("#c7b532")),
        STRONG("Strong", Color.parseColor("#3a674f")),
        VERY_STRONG("Very strong", Color.parseColor("#0d633d"));

        public String msg;
        public int color;
        private static int MIN_LENGTH = 5;
        private static int MAX_LENGTH = 15;

        PasswordStrength(String msg, int color) {
            this.msg = msg;
            this.color = color;
        }

        public static PasswordStrength calculate(String password) {
            int score = 0;
            // boolean indicating if password has an upper case
            boolean upper = false;
            // boolean indicating if password has a lower case
            boolean lower = false;
            // boolean indicating if password has at least one digit
            boolean digit = false;
            // boolean indicating if password has a leat one special char
            boolean specialChar = false;

            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);

                if (!specialChar  &&  !Character.isLetterOrDigit(c)) {
                    score++;
                    specialChar = true;
                } else {
                    if (!digit  &&  Character.isDigit(c)) {
                        score++;
                        digit = true;
                    } else {
                        if (!upper || !lower) {
                            if (Character.isUpperCase(c)) {
                                upper = true;
                            } else {
                                lower = true;
                            }

                            if (upper && lower) {
                                score++;
                            }
                        }
                    }
                }
            }

            int length = password.length();

            if (length > MAX_LENGTH) {
                score++;
            } else if (length < MIN_LENGTH) {
                score = 0;
            }

            // return enum following the score
            switch(score) {
                case 0 : return WEAK;
                case 1 : return MEDIUM;
                case 2 : return STRONG;
                case 3 : return VERY_STRONG;
                default:
            }

            return VERY_STRONG;
        }
    }

   /* @Override
    public void onBackPressed() {
        startActivity(new Intent(UpdatePassword.this,MainActivity.class));
        finish();
    }*/
}
