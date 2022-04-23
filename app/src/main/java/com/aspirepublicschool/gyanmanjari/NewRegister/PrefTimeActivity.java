package com.aspirepublicschool.gyanmanjari.NewRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.MainActivity;
import com.aspirepublicschool.gyanmanjari.PayTM.checksum;
import com.aspirepublicschool.gyanmanjari.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class PrefTimeActivity extends AppCompatActivity {

    CheckBox chone , chtwo, chthree, chfour, chfive, chsix, chseven,cheight, chnine, chten,cheleven;
    Button dialogClose;
    ArrayList sub;
    List<String> time = new ArrayList<String>();
    HashSet<String> hashSet = new HashSet<String>();
    HashSet<String> timehashSet = new HashSet<String>();
    Button btncontwithTime, btnprevfromTime;
    TextView infotxt, mediumtxt ,standardtxt , boardtxt, subjecttxt;
    Dialog dialog;
    String medium, standard, board, subjects, stream = "Not set", group = "Not set", number;
    String temp = "";
    ArrayList<Object> getsub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_time);

        medium = getIntent().getStringExtra("medium");
        standard = getIntent().getStringExtra("standard");
        board = getIntent().getStringExtra("board");

        if (Integer.parseInt(standard) > 10){
            stream = getIntent().getStringExtra("stream");
            if (stream.equals("Science")){
                group = getIntent().getStringExtra("group");
            }
        }
        Bundle args = getIntent().getBundleExtra("BUNDLE");
        getsub = (ArrayList<Object>) args.getSerializable("ARRAYLIST");

        allocateMemory();

//         Toast.makeText(getApplicationContext(), sub, Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        number = sharedPreferences.getString("number", "none");
//        medium = sharedPreferences.getString("medium", "none");
//        standard = sharedPreferences.getString("standard", "none");
//        board = sharedPreferences.getString("board", "none");

        for (int i=0; i<getsub.size(); i++){
            temp = temp + getsub.get(i) + ",\n";
        }
        Toast.makeText(getApplicationContext(), String.valueOf(sub), Toast.LENGTH_SHORT).show();
        btncontwithTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                continueRegistraion();
//                RegisterUser();
                startActivity(new Intent(getApplicationContext(), checksum.class));
            }
        });

        infotxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(PrefTimeActivity.this);
                dialog.setContentView(R.layout.selected_info_dialog);
//                dialog.getWindow().setLayout(800, 1200);
                dialog.show();

                mediumtxt = dialog.findViewById(R.id.mediumtxt);
                standardtxt = dialog.findViewById(R.id.standardtxt);
                boardtxt = dialog.findViewById(R.id.boardtxt);
                subjecttxt = dialog.findViewById(R.id.subjecttxt);
                dialogClose =dialog.findViewById(R.id.dialogClose);

                dialogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                mediumtxt.setText(medium);
                boardtxt.setText(board);
                subjecttxt.setText(temp);

                int std = Integer.parseInt(standard);
                if (std < 11){
                    standardtxt.setText(standard);
                }else if (std >= 11){
                    if (stream.equals("Arts") || stream.equals("Commerce")){
                        standardtxt.setText(standard + " " + stream);
                    }else if(stream.equals("Science")){
                        standardtxt.setText(standard + " " + stream + " " + group + " Group");
                    }
                }
            }
        });

        btnprevfromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefTimeActivity.super.onBackPressed();
            }
        });
    }

    private void RegisterUser() {
        if (chone.isChecked()){
            time.add("8:00 to 9:00 AM");
        }
        if(chtwo.isChecked()){
            time.add("9.00AM to 10.00AM");
        }
        if(chthree.isChecked()){
            time.add("10.00AM to 11.00AM");
        }
        if(chfour.isChecked()){
            time.add("11.00AM to 12.00PM");
        }
        if(chfive.isChecked()){
            time.add("12.00PM to 1.00PM");
        }
        if(chsix.isChecked()){
            time.add("1.00PM to 2.00PM");
        }
        if(chseven.isChecked()){
            time.add("2.00PM to 3.00PM");
        }
        if(cheight.isChecked()){
            time.add("3.00PM to 4.00PM");
        }
        if(chnine.isChecked()){
            time.add("4.00PM to 5.00PM");
        }
        if(chten.isChecked()){
            time.add("5.00PM to 6.00PM");
        }
        if(cheleven.isChecked()){
            time.add("6.00PM to 7.00PM");
        }
        timehashSet.addAll(time);
        time.clear();
        time.addAll(timehashSet);
        String timetemp12 = "";
        String subject12 = "";
        for(int i=0; i<time.size(); i++){
            timetemp12 = timetemp12 +  time.get(i) + ",";
        }
        for (int i=0; i<getsub.size(); i++){
            subject12 = subject12 + getsub.get(i) + ",";
        }
        Toast.makeText(getApplicationContext(), timetemp12, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), subject12, Toast.LENGTH_SHORT).show();

        final String timesend = timetemp12;
        final String subsend = subject12;

        String WebserviceURL = Common.GetWebServiceURL() + "registerUserTemp.php";
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, WebserviceURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("true")) {
                    Toast.makeText(getApplicationContext(), "Registration Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
                else if(response.equals("fail")){
                    Toast.makeText(getApplicationContext(), "Failed! Try Again..", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("number", number);
                params.put("time", timesend);
                params.put("standard", standard);
                params.put("medium", medium);
                params.put("board", board);
                params.put("group", group);
                params.put("stream", stream);
                return params;

            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);


    }

    private void continueRegistraion() {
        if (chone.isChecked()){
            time.add("8:00 to 9:00 AM");
        }
        if(chtwo.isChecked()){
            time.add("9.00AM to 10.00AM");
        }
        if(chthree.isChecked()){
            time.add("10.00AM to 11.00AM");
        }
        if(chfour.isChecked()){
            time.add("11.00AM to 12.00PM");
        }
        if(chfive.isChecked()){
            time.add("12.00PM to 1.00PM");
        }
        if(chsix.isChecked()){
            time.add("1.00PM to 2.00PM");
        }
        if(chseven.isChecked()){
            time.add("2.00PM to 3.00PM");
        }
        if(cheight.isChecked()){
            time.add("3.00PM to 4.00PM");
        }
        if(chnine.isChecked()){
            time.add("4.00PM to 5.00PM");
        }
        if(chten.isChecked()){
            time.add("5.00PM to 6.00PM");
        }
        if(cheleven.isChecked()){
            time.add("6.00PM to 7.00PM");
        }
        timehashSet.addAll(time);
        time.clear();
        time.addAll(timehashSet);

        Toast.makeText(getApplicationContext(), String.valueOf(time), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), PrefFacultyActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST",(Serializable)time);
        intent.putExtra("BUNDLE",args);
        Bundle subjects1 = new Bundle();
        subjects1.putSerializable("ARRAYLIST1",getsub);
        intent.putExtra("BUNDLE1",subjects1);
        intent.putExtra("medium" ,medium);
        intent.putExtra("board" ,board);
        intent.putExtra("standard" ,standard);
        intent.putExtra("stream" ,stream);
        intent.putExtra("group" ,group);
        startActivity(intent);
    }

    private void allocateMemory() {
        btncontwithTime = findViewById(R.id.btncontwithTime);
        btnprevfromTime = findViewById(R.id.btnprevfromTime);
        infotxt = findViewById(R.id.infotxt);

        chone = findViewById(R.id.chone);
        chtwo = findViewById(R.id.chtwo);
        chthree = findViewById(R.id.chthree);
        chfour = findViewById(R.id.chfour);
        chfive = findViewById(R.id.chfive);
        chsix = findViewById(R.id.chsix);
        chseven = findViewById(R.id.chseven);
        cheight = findViewById(R.id.cheight);
        chnine = findViewById(R.id.chnine);
        chten = findViewById(R.id.chten);
        cheleven = findViewById(R.id.cheleven);
    }

}