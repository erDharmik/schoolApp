package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.aspirepublicschool.gyanmanjari.NewRegister.StudentRegActivity1;
import com.aspirepublicschool.gyanmanjari.Payment.PayTMActivity;
import com.aspirepublicschool.gyanmanjari.Register.RegisterActivity;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText edtmobileno, edtpassword;
    Button btnsignin;
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_SC_ID = "sc_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";
    private String username;
    private String password;
    private String school_id;
    public static String stu_id;
    private ProgressDialog pDialog;
    private String login_url = Common.GetWebServiceURL() + "db_login.php";
    CheckBox TermsandCondition;
    ProgressDialog p1;
    TextView text_termsandcondition,txtsignin;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        allocatememory();
       /* final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("login",1);*/
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean loginstatus = preferences.getBoolean("status", false);
        if (loginstatus == true) {
            loadDashboard();
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(LoginPage.this,MainActivity.class);
                startActivity(i);
                */

                username = edtmobileno.getText().toString().trim();
                password = edtpassword.getText().toString().trim();
                //  school_id = sc_id.getText().toString().toLowerCase().trim();
                if (validateInputs()) {
                    login();
                }
            }
        });
        txtsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, StudentRegActivity1.class);
                startActivity(intent);
            }
        });
    }

    private void allocatememory() {
        edtmobileno = findViewById(R.id.edtmobileno);
        edtpassword = findViewById(R.id.edtpassword);
        btnsignin = findViewById(R.id.btnsignin);
        TermsandCondition=findViewById(R.id.checkbox_TandC);
        txtsignin=findViewById(R.id.txtsignin);



    }

    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("user","user1");
        startActivity(i);
        finish();
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(Login.this);
        pDialog.setMessage("Logging In.. Please wait..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void login() {
        displayLoader();
       Log.v("MMMMM",login_url);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                try {
                    Log.d("%%%",response);
                    JSONObject object = new JSONObject(response);

                    if (object.getInt("loginstatus") == 0) {
                        stu_id = object.getString("stu_id");

                        // Log.v("XXX",stu_id);
                        school_id = object.getString("sc_id").toLowerCase();
                        String token=object.getString("token");
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("login",2);
                        editor.putString("stu_id", stu_id);
                        editor.putBoolean("status", true);
                        editor.putString("sc_id", school_id);
                        editor.putString("token", token);
                        Log.v("CCCCCC", school_id);
                        editor.commit();
                        loadDashboard();

                        Toast.makeText(context, object.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                    }else if (object.getInt("loginstatus") == 7) {
                        Toast.makeText(Login.this, "You can't access account because its already login in another device.Please Verify Account", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Login.this, VerifyAccount.class);
                        intent.putExtra("rand", object.getString("token"));
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();


                    }  else {
                        Toast.makeText(getApplicationContext(),
                                object.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
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
                params.put(KEY_USERNAME, username);
                params.put(KEY_PASSWORD, password);

                return params;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);
    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(username)) {
            edtmobileno.setError("Username cannot be empty");
            edtmobileno.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            edtpassword.setError("Password cannot be empty");
            edtpassword.requestFocus();
            return false;
        }

        if (TermsandCondition.isChecked() == false) {
            TermsandCondition.setError("Please Accept Terms and Condition");
            TermsandCondition.requestFocus();
            return false;
        }
        return true;
    }
}
