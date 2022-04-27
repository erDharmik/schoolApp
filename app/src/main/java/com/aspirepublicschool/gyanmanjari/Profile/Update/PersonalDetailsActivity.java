package com.aspirepublicschool.gyanmanjari.Profile.Update;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.DoubtSolving.ChatAdapter;
import com.aspirepublicschool.gyanmanjari.DoubtSolving.Insert_Doubt;
import com.aspirepublicschool.gyanmanjari.DoubtSolving.TeacherDoubt;
import com.aspirepublicschool.gyanmanjari.MainActivity;
import com.aspirepublicschool.gyanmanjari.Profile.ProfileMainActivity;
import com.aspirepublicschool.gyanmanjari.R;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final int GALLERY = 1;
    private static final int CAMERA = 2;
    String selectedPicture;
    EditText fname, mname, lname, mobile, email, dob, gender;
    CircleImageView profilePic;
    Context ctx;
    FloatingActionButton dpupdateBtn;
    Dialog dialog;
    String dp_url, id;
    Button updateBtn;
    String stu_id;
    String sc_id;
    String number;
    RequestQueue requestQueue;
    private Bitmap Fixbitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);


        dpupdateBtn = findViewById(R.id.profilePicUpdateBtn);
        profilePic = findViewById(R.id.profilePic);
        fname = findViewById(R.id.firstName);
        mname = findViewById(R.id.middleName);
        lname = findViewById(R.id.lastName);
        dob = findViewById(R.id.dob);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        gender = findViewById(R.id.gender);
        updateBtn = findViewById(R.id.personalDetailUpdateBtn);

        mobile.setEnabled(false);
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "You can't edit mobile number! Contact Admin for same", Toast.LENGTH_SHORT).show();
            }
        });

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
        number = i.getStringExtra("number");
        sc_id = i.getStringExtra("sc_id");
        stu_id = i.getStringExtra("stu_id");

        Picasso.get().load(dp_url).placeholder(R.mipmap.ic_launcher_round).into(profilePic);


        dob.setFocusable(false);
        dob.setClickable(false);

        dpupdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });


        dpupdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });



        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fname.getText().toString().trim().equals(getIntent().getStringExtra("fname")) &&
                    lname.getText().toString().trim().equals(getIntent().getStringExtra("lname")) &&
                    mname.getText().toString().trim().equals(getIntent().getStringExtra("mname")) &&
                    mobile.getText().toString().trim().equals(getIntent().getStringExtra("mobile")) &&
                    gender.getText().toString().trim().equals(getIntent().getStringExtra("gender")) &&
                    dob.getText().toString().trim().equals(getIntent().getStringExtra("dob"))&&
                    email.getText().toString().trim().equals(getIntent().getStringExtra("email")))
                {
                    Toast.makeText(getApplicationContext(), "You have no changes to be made", Toast.LENGTH_SHORT).show();
                }
                else{
                    UpdatePersonalInformation();
                }

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


    private void showPictureDialog() {
//        flag = true;
        //Fixbitmap=null;
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Gallery"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                           /* case 1:
                                takePhotoFromCamera();
                                break;*/
                        }
                    }
                });
        pictureDialog.show();
    }

    private void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    // ShowSelectedImage.setImageResource(0);
                    // ShowSelectedImage.setImageBitmap(FixBitmap);
                    //    img_update.setVisibility(View.VISIBLE);

                    Fixbitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    profilePic.setImageBitmap(Fixbitmap);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Fixbitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    selectedPicture = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    byte[] bytesImage = Base64.decode(selectedPicture, Base64.DEFAULT);

                    UpdateProfilePic();
//                    Toast.makeText(getApplicationContext(), selectedPicture, Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(PersonalDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void UpdateProfilePic() {


        String WebServiceUrl = Common.GetWebServiceURL() + "updateDP.php";
//        String WebServiceUrl = "https://mrawideveloper.com/gyanmanfarividyapith.net/zocarro_mobile_app/ws/personalDetails.php";
        StringRequest trequest=new StringRequest(Request.Method.POST, WebServiceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    String message = jsonArray.getJSONObject(0).getString("message");
                    if (message.equals("true")){
                        Toast.makeText(getApplicationContext(), "DP Updated", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                Map<String,String> data=new HashMap<>();
                data.put("number", number);
                data.put("stu_id", stu_id);
                data.put("sc_id", sc_id);
                data.put("image_data", selectedPicture);
                return data;
            }
        };
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(trequest);
//        requestQueue.start();
//        Volley.newRequestQueue(ctx).add(request);
    }

    private void UpdatePersonalInformation() {


        String WebServiceUrl= Common.GetWebServiceURL()+"studentUpdatePersonalDetails.php";

        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("true")){
                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(getApplicationContext());
                Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("id", id);
                data.put("fname", fname.getText().toString().trim());
                data.put("lname", lname.getText().toString().trim());
                data.put("mname", mname.getText().toString().trim());
                data.put("email", email.getText().toString().trim());
                data.put("dob", dob.getText().toString().trim());
                data.put("gender", gender.getText().toString().trim());
                data.put("mobile", mobile.getText().toString().trim());
                data.put("stu_id", stu_id);
                return data;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

//    private void ProfilePicDialog() {
//
////        ImageView dp;
////
////        dialog = new Dialog(getApplicationContext());
////        dialog.setContentView(R.layout.profile_pic_dialog_layout);
////        dialog.show();
////
////        dp = dialog.findViewById(R.id.profilePicDialog);
////
////        Picasso.get().load(dp_url).into(dp);
//
//    }

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