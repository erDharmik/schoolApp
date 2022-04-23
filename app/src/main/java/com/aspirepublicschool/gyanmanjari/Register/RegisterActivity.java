package com.aspirepublicschool.gyanmanjari.Register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.Login;
import com.aspirepublicschool.gyanmanjari.Payment.PayTMActivity;
import com.aspirepublicschool.gyanmanjari.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final String KEY_EMPTY = "";
     private static final String KEY_FIRST_NAME = "first_name";
     private static final String KEY_LAST_NAME= "last_name";
     private static final String KEY_EMAIL = "email";
     private static final String KEY_NUMBER = "number";
     private static final String KEY_ENROLLMENT = "enrollment";
     private static final String KEY_PASSWORD = "password";
    private String first_name,last_name,number,address,password,number2,father,emailid;
    ArrayList<POJO> pojos;
    ArrayList<String> boardArray;
    private ProgressDialog pDialog;
    String registration= Common.GetWebServiceURL()+"register_student.php";
   // String registration= "https://www.zocarro.net/zocarro_mobile_app/ws/"+"register_student.php";
    String sc_id, class_id, med, stds, board,groups,district;
    TextView sigh_in_txt;
    EditText edt_fname1,edt_lname1,edt_number,email,edtschool,edtadd,edtdistrict,fathername;
    Button sigh_up_btn;
    CheckBox edt_checkBox;
    LinearLayout lnrspngrp;
    RadioGroup rdgmedium,rdgstd,rdggroup;
    Context ctx=this;
    RelativeLayout relregister;
    String myurl = "https://mrawideveloper.com/gyanmanfarividyapith.net/zocarro_mobile_app/ws/register_student.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        allocatememory();

        // Spinner Drop down elements
        rdgstd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final String value =
                        ((RadioButton)findViewById(rdgstd.getCheckedRadioButtonId()))
                                .getText().toString();
                Toast.makeText(RegisterActivity.this,""+value,Toast.LENGTH_LONG).show();
                switch(checkedId) {

                    case R.id.rd11:
                        stds="11-Science";
                        break;
                    case R.id.rd12:
                        stds="12-Science";
                        break;

                }
            }

        });
        rdgmedium.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final String value =
                        ((RadioButton)findViewById(rdgmedium.getCheckedRadioButtonId()))
                                .getText().toString();
                Toast.makeText(RegisterActivity.this,""+value,Toast.LENGTH_LONG).show();
                    switch(checkedId) {
                        case R.id.rdGuj:
                            med="Gujarati";
                            break;
                        case R.id.rdEng:
                            med="English";
                            break;

                    }
                }


        });
        rdggroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final String value =
                        ((RadioButton)findViewById(rdggroup.getCheckedRadioButtonId()))
                                .getText().toString();
                Toast.makeText(RegisterActivity.this,""+value,Toast.LENGTH_LONG).show();

                switch(checkedId) {
                    case R.id.rda:
                        groups="A Group";
                        break;
                    case R.id.rdb:
                        groups="B Group";
                        break;

                }
            }


        });


        sigh_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_name = edt_fname1.getText().toString();
                last_name = edt_lname1.getText().toString();
                number = edt_number.getText().toString();
                emailid = email.getText().toString();
                district=edtdistrict.getText().toString();
                address=edtadd.getText().toString();
                father=fathername.getText().toString();

                if(stds.equals("11-Science"))
                {
                    if(med.equals("Gujarati"))
                    {
                        if(groups.equals("A Group"))
                        {
                            class_id="CIDN126";
//                            class_id = "SCIDN1";
                        }
                        else {
                            class_id="CIDN127";
//                            class_id = "SCIDN1";
                        }
                    }
                    else {

                        if(groups.equals("A Group"))
                        {
                            class_id="CIDN124";
//                            class_id = "SCIDN1";

                        }
                        else {
                            class_id="CIDN125";
//                            class_id = "SCIDN1";
                        }

                    }
                }
                else
                {
                    if(med.equals("Gujarati"))
                    {
                        if(groups.equals("A Group"))
                        {
                            class_id="CIDN121";
//                            class_id = "SCIDN1";
                        }
                        else {
                            class_id="CIDN120";
//                            class_id = "SCIDN1";
                        }
                    }
                    else {

                        if(groups.equals("A Group"))
                        {
                            class_id="CIDN123";
//                            class_id = "SCIDN1";
                        }
                        else {
                            class_id="CIDN122";
//                            class_id = "SCIDN1";
                        }

                    }

                }
                // checkBox=edt_checkBox.getText().toString();


                if(validateInputs()) {
                   // postClassData(registration);
                    postClassData(registration);
                }

            }
        });

        sigh_in_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, Login.class);
                startActivity(intent);
            }
        });


    }

    private void allocatememory() {
        pojos = new ArrayList<>();
        sigh_in_txt = findViewById(R.id.sigh_In_txt);
        edtschool = findViewById(R.id.edtschool);
        sigh_up_btn = findViewById(R.id.sigh_up_btn);
        edt_checkBox = findViewById(R.id.checkBox);
        edtadd = findViewById(R.id.address);

        edt_fname1 = findViewById(R.id.fName);
        edt_lname1 = findViewById(R.id.lName);
        edt_number = findViewById(R.id.phone1);
        email = findViewById(R.id.email);
        edtdistrict = findViewById(R.id.district);
        fathername = findViewById(R.id.fathername);
        rdggroup = findViewById(R.id.rdggroup);
        rdgmedium = findViewById(R.id.rdgmedium);
        rdgstd = findViewById(R.id.rdgstd);
        relregister = findViewById(R.id.relregister);


        lnrspngrp = findViewById(R.id.lnrspngrp);

    }

    private void displayLoader() {
        pDialog = new ProgressDialog(RegisterActivity.this);
        pDialog.setMessage("Sign up .. Please wait..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private boolean validateInputs() {

        Pattern p = Pattern.compile(Utils.regEx);
        //Pattern pp = Pattern.compile(Utils.password);
        Pattern pn = Pattern.compile(Utils.phonenumber);
       //Matcher mp = pp.matcher(password);
        Matcher np = pn.matcher(number);

        if (KEY_EMPTY.equals(first_name)) {
            edt_fname1.setError("Username cannot be empty");
            edt_fname1.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(last_name)) {
            edt_lname1.setError("Password cannot be empty");
            edt_lname1.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(address)) {
            edtadd.setError("Address cannot be empty");
            return false;
        }
        else if (KEY_EMPTY.equals(number)) {
            edt_number.setError("Mobile Number cannot be empty");
            return false;
        }
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//        if (KEY_EMPTY.equals(emailid)) {
//            email.setError("Email Id  cannot be empty");
//            return false;
//        }

//        else if (!email.getText().toString().trim().matches(emailPattern))
//        {
//            email.setError("Invalid Email Address");
//            return false;
//
//        }
        if (KEY_EMPTY.equals(father)) {
            fathername.setError("Father's  cannot be empty");
            return false;
        }
        if (KEY_EMPTY.equals(district)) {
            edtdistrict.setError("District  cannot be empty");
            return false;
        }

     /*   if (!mp.find())
        {
            Toast.makeText(RegisterActivity.this,"Enter Valid Password",Toast.LENGTH_LONG);
            edt_password1.setError("one character one number and one special character");
            return false;
        }*/
        if (np.find() && np.group().equals(number) )
        {

        }
        else {
            Toast.makeText(RegisterActivity.this,"Enter Valid number",Toast.LENGTH_LONG);
            edt_number.setError("Enter Valid Number");
            return false;

        }

        if (!edt_checkBox.isChecked()) {
            Toast.makeText(RegisterActivity.this, "Please select Terms and Conditions.", Toast.LENGTH_LONG);
            edt_checkBox.setError("Please select Terms and Conditions.");
            return false;
        }
        return true;
    }



    private void postClassData(String registration) {
        Common.progressDialogShow(RegisterActivity.this);
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, registration, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(RegisterActivity.this);
                    JSONObject object = new JSONObject(response);
                    String message=object.getString("message");
                    if(message.equals("success")) {
                        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("login",1);
                       // Snackbar.make(relregister, "Registration Successful.Username and password  will be provided by SMS after approval of admin.", Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(ctx, "Registration Successful.Username and password  will be provided by SMS after approval of admin.",Toast.LENGTH_LONG ).show();
                        final Toast toast = Toast.makeText(ctx, "Registration Successful.Username and password  will be provided by SMS after approval of admin.", Toast.LENGTH_SHORT);
                        toast.show();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast.cancel();
                            }
                        }, 10000);


//                        Intent intent=new Intent(ctx,Login.class);
//                        startActivity(intent);
//                        finish();

                        Intent intent=new Intent(ctx, PayTMActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else if(message.equals("Data Already Exist"))
                    {
                        final Toast toast = Toast.makeText(ctx, "Data Already Exist", Toast.LENGTH_SHORT);
                        toast.show();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast.cancel();
                            }
                        }, 10000);
                        Intent intent=new Intent(ctx,Login.class);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        final Toast toast = Toast.makeText(ctx, "Error", Toast.LENGTH_SHORT);
                        toast.show();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast.cancel();
                            }
                        }, 10000);
                        Intent intent=new Intent(ctx,Login.class);
                        startActivity(intent);
                        finish();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(RegisterActivity.this);
                error.printStackTrace();

    			Toast.makeText(ctx,R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
                Toast.makeText(ctx,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();


                data.put("cid", class_id);
                data.put("st_sname",edt_lname1.getText().toString());
                data.put("st_fname",edt_fname1.getText().toString());
                data.put("mobile_no",edt_number.getText().toString());
                data.put("address",edtadd.getText().toString());
                data.put("district",edtdistrict.getText().toString());
                data.put("f_name",fathername.getText().toString());
                data.put("email",email.getText().toString());


                data.put("med",med);
                data.put("std",stds);
                data.put("board","GSEB");
                data.put("group",groups);
                data.put("p_scname",edtschool.getText().toString());
                Log.d("###", data.toString());
                return data;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}
