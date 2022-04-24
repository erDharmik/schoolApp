package com.aspirepublicschool.gyanmanjari.Profile.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.DoubtSolving.Insert_Doubt;
import com.aspirepublicschool.gyanmanjari.Profile.Update.PersonalDetailsActivity;
import com.aspirepublicschool.gyanmanjari.R;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalDetails extends Fragment {

    TextView fname, mname, lname, mobile, email, dob, gender;
    CircleImageView profilePic;
    Context ctx;
    String number, stu_id, sc_id;
    FloatingActionButton dpUpdateBtn;
    Dialog dialog;
    String dp_url, id;
    String selectedPicture = "";
    private final int PICK_IMAGE_REQUEST = 71;
    ImageView dpPreview;
    private int GALLERY = 1, CAMERA = 2;
    Bitmap Fixbitmap;
    Button upload;
    RequestQueue requestQueue;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_details, container, false);

        stu_id = getArguments().getString("stu_id");
        sc_id = getArguments().getString("sc_id");
        number = getArguments().getString("number");

        pd = new ProgressDialog(getContext());
        pd.setTitle("Loading, please wait");
        pd.setCancelable(false);

//        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
//        stu_id = mPrefs.getString("stu_id", "none");
//        sc_id = mPrefs.getString("sc_id", "none");
//        number = mPrefs.getString("number", "none");

//        Toast.makeText(ctx, number + stu_id + sc_id, Toast.LENGTH_SHORT).show();
//        number = "6355574383";
//        stu_id = "SCIDN2STIDN10";
//        sc_id = "SCIDN1";

        dpUpdateBtn = view.findViewById(R.id.profilePicUpdateBtn);
        profilePic = view.findViewById(R.id.profilePic);
        fname = view.findViewById(R.id.firstName);
        mname = view.findViewById(R.id.middleName);
        lname = view.findViewById(R.id.lastName);
        dob = view.findViewById(R.id.dob);
        mobile = view.findViewById(R.id.mobile);
        email = view.findViewById(R.id.email);
        gender = view.findViewById(R.id.gender);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfilePicDialog();
            }
        });

        fetchPersonalData();

        dpUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               goToUpdateActivity();

            }
        });

        fname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUpdateActivity();
            }
        });
        lname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUpdateActivity();
            }
        });
        mname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUpdateActivity();
            }
        });
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUpdateActivity();
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUpdateActivity();
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUpdateActivity();
            }
        });
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUpdateActivity();
            }
        });
        fname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUpdateActivity();
            }
        });

        return view;
    }




    private void goToUpdateActivity() {
        Intent intent = new Intent(getContext(), PersonalDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("fname", fname.getText().toString().trim());
        intent.putExtra("lname", lname.getText().toString().trim());
        intent.putExtra("mname", mname.getText().toString().trim());
        intent.putExtra("mobile", mobile.getText().toString().trim());
        intent.putExtra("email", email.getText().toString().trim());
        intent.putExtra("dob", dob.getText().toString().trim());
        intent.putExtra("gender", gender.getText().toString().trim());
        intent.putExtra("id", id);
        intent.putExtra("number", number);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("stu_id", stu_id);
        intent.putExtra("dp_url", dp_url);
        startActivity(intent);
    }

    private void ProfilePicDialog() {

        ImageView dp;

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.profile_pic_dialog_layout);
        dialog.show();

        dp = dialog.findViewById(R.id.profilePicDialog);

        Picasso.get().load(dp_url).placeholder(R.mipmap.ic_launcher_round).into(dp);

    }

    public void fetchPersonalData() {
        pd.show();

        String WebServiceUrl = Common.GetWebServiceURL() + "personalDetails.php";
//        String WebServiceUrl = "https://mrawideveloper.com/gyanmanfarividyapith.net/zocarro_mobile_app/ws/personalDetails.php";
        StringRequest trequest=new StringRequest(Request.Method.POST, WebServiceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    id = jsonArray.getJSONObject(0).getString("id");
                    String fname1 = jsonArray.getJSONObject(0).getString("st_fname");
                    String lastname = jsonArray.getJSONObject(0).getString("st_sname");
                    String middlename = jsonArray.getJSONObject(0).getString("f_name");
                    String m_no = jsonArray.getJSONObject(0).getString("st_cno");
                    String dob12 = jsonArray.getJSONObject(0).getString("dob");
                    String email12 = jsonArray.getJSONObject(0).getString("st_email");
                    String gender1 = jsonArray.getJSONObject(0).getString("gender");

                    fname.setText(fname1);
                    lname.setText(lastname);
                    mname.setText(middlename);
                    dob.setText(dob12);
                    mobile.setText(m_no);
                    email.setText(email12);
                    gender.setText(gender1);

                    dp_url= "https://mrawideveloper.com/gyanmanfarividyapith.net/zocarro/image/student/" + jsonArray.getJSONObject(0).getString("stu_img");
//                    Toast.makeText(ctx, dp_url, Toast.LENGTH_SHORT).show();
                    Picasso.get().load(dp_url).placeholder(R.mipmap.ic_launcher_round).into(profilePic);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage() + "Catch Block", Toast.LENGTH_SHORT).show();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("number", number);
                data.put("stu_id", stu_id);
                data.put("sc_id", sc_id);
                return data;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(trequest);
//        requestQueue.start();
//        Volley.newRequestQueue(ctx).add(request);
    }

}