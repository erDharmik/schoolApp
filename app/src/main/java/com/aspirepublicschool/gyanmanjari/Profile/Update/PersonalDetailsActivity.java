package com.aspirepublicschool.gyanmanjari.Profile.Update;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.Profile.ProfileMainActivity;
import com.aspirepublicschool.gyanmanjari.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText fname, mname, lname, mobile, email, dob, gender;
    CircleImageView profilePic;
    Context ctx;
    Dialog dialog;
    String dp_url, id;
    Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        profilePic = findViewById(R.id.profilePic);
        fname = findViewById(R.id.firstName);
        mname = findViewById(R.id.middleName);
        lname = findViewById(R.id.lastName);
        dob = findViewById(R.id.dob);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        gender = findViewById(R.id.gender);
        updateBtn = findViewById(R.id.personalDetailUpdateBtn);


        Intent i = getIntent();
        fname.setText(i.getStringExtra("fname"));
        lname.setText(i.getStringExtra("lname"));
        mname.setText(i.getStringExtra("mname"));
        mobile.setText(i.getStringExtra("mobile"));
        gender.setText(i.getStringExtra("gender"));
        email.setText(i.getStringExtra("email"));
        dob.setText(i.getStringExtra("dob"));
        dp_url = i.getStringExtra("dp_url");
        id = i.getStringExtra("id");

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfilePicDialog();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePersonalInformation();
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.aspirepublicschool.gyanmanjari.DatePicker mDatePickerDialogFragment = new com.aspirepublicschool.gyanmanjari.DatePicker();
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            }
        });


    }

    private void UpdatePersonalInformation() {

        String WebServiceUrl = Common.GetWebServiceURL() + "updatePersonalDetails.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    if (response.equalsIgnoreCase("true")){
                        Toast.makeText(ctx, "Details Updated Successfully.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ProfileMainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }else if (response.equalsIgnoreCase("fail")){
                        Toast.makeText(ctx, "Please Try Again!", Toast.LENGTH_SHORT).show();
                    }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("id", id);
                params.put("fname", fname.getText().toString().trim());
                params.put("mname", mname.getText().toString().trim());
                params.put("lname", lname.getText().toString().trim());
                params.put("gender", gender.getText().toString().trim());
                params.put("dob", dob.getText().toString().trim());
                params.put("mobile", mobile.getText().toString().trim());
                params.put("email", email.getText().toString().trim());
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

    private void ProfilePicDialog() {

        ImageView dp;

        dialog = new Dialog(getApplicationContext());
        dialog.setContentView(R.layout.profile_pic_dialog_layout);
        dialog.show();

        dp = dialog.findViewById(R.id.profilePicDialog);

        Picasso.get().load(dp_url).into(dp);

    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat =new SimpleDateFormat(myFormat, Locale.CANADA);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        dob.setText(dateFormat.format(mCalendar.getTime()));
    }

}