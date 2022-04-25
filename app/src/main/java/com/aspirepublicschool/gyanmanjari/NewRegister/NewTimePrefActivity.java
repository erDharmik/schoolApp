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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.MainActivity;
import com.aspirepublicschool.gyanmanjari.NewRegister.Address.AddressActivity;
import com.aspirepublicschool.gyanmanjari.PolicyDashboard.PolicyDashboardMainActivity;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class NewTimePrefActivity extends AppCompatActivity {

    CheckBox chzero, chone , chtwo, chthree, chfour, chfive, chsix, chseven,cheight, chnine,chfourteen, chten,cheleven, chtwelve, chthirteen;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_time_pref);

        getSupportActionBar().hide();

        allocateMemory();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        number = preferences.getString("number", null);

        medium = getIntent().getStringExtra("medium");
        standard = getIntent().getStringExtra("standard");
        board = getIntent().getStringExtra("board");
        stream = getIntent().getStringExtra("stream");
        group = getIntent().getStringExtra("group");
//        number = getIntent().getStringExtra("number");

//        if (standard.contains("11") || standard.contains("12")){
//            stream = getIntent().getStringExtra("stream");
//            if (stream.equals("Science")){
//                group = getIntent().getStringExtra("group");
//            }else{
//                group = " ";
//            }
//        }else{
//            stream = " ";
//            group = " ";
//        }


        Toast.makeText(getApplicationContext(), String.valueOf(sub), Toast.LENGTH_SHORT).show();

        infotxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(NewTimePrefActivity.this);
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
                if (stream.contains("Select")){
                    standardtxt.setText(standard);
                }else if (stream.contains("Select")){
                    standardtxt.setText(standard +" "+ stream);
                }else{
                    standardtxt.setText(standard +" "+ stream + " " + group);
                }

            }
        });

        btnprevfromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTimePrefActivity.super.onBackPressed();
            }
        });

        btncontwithTime = findViewById(R.id.btncontwithTime);

        btncontwithTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(NewTimePrefActivity.this, AddressActivity.class));

            }
        });
    }


    private void RegisterUser() {
        if(chzero.isChecked()){
            time.add("7.00SAM to 8.00AM");
        }
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
        if(chtwelve.isChecked()){
            time.add("7.00PM to 8.00PM");
        }
        if(chthirteen.isChecked()){
            time.add("8.00PM to 9.00PM");
        }
        if(chfourteen.isChecked()){
            time.add("9.00PM to 10.00PM");
        }
        timehashSet.addAll(time);
        time.clear();
        time.addAll(timehashSet);
        String timetemp12 = "";
        String subject12 = "";
        for(int i=0; i<time.size(); i++){
            timetemp12 = timetemp12 +  time.get(i) + ",";
        }
        Toast.makeText(getApplicationContext(), timetemp12, Toast.LENGTH_SHORT).show();

        final String timesend = timetemp12;
        final String subsend = subject12;

        String WebserviceURL = Common.GetWebServiceURL() + "registerUserFinal.php";
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, WebserviceURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    String message = jsonArray.getJSONObject(0).getString("message");

                    if (message.equalsIgnoreCase("fail")){
                        Toast.makeText(getApplicationContext(), "Try Again!", Toast.LENGTH_SHORT).show();
                    }
                    else if(message.equalsIgnoreCase("true")){
                        Intent intent = new Intent(NewTimePrefActivity.this, AddressActivity.class);
                        intent.putExtra("number", number);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                return params;

            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);


    }

//    private void continueRegistraion() {
//        if (chone.isChecked()){
//            time.add("8:00 to 9:00 AM");
//        }
//        if(chtwo.isChecked()){
//            time.add("9.00AM to 10.00AM");
//        }
//        if(chthree.isChecked()){
//            time.add("10.00AM to 11.00AM");
//        }
//        if(chfour.isChecked()){
//            time.add("11.00AM to 12.00PM");
//        }
//        if(chfive.isChecked()){
//            time.add("12.00PM to 1.00PM");
//        }
//        if(chsix.isChecked()){
//            time.add("1.00PM to 2.00PM");
//        }
//        if(chseven.isChecked()){
//            time.add("2.00PM to 3.00PM");
//        }
//        if(cheight.isChecked()){
//            time.add("3.00PM to 4.00PM");
//        }
//        if(chnine.isChecked()){
//            time.add("4.00PM to 5.00PM");
//        }
//        if(chten.isChecked()){
//            time.add("5.00PM to 6.00PM");
//        }
//        if(cheleven.isChecked()){
//            time.add("6.00PM to 7.00PM");
//        }
//        timehashSet.addAll(time);
//        time.clear();
//        time.addAll(timehashSet);
//
//        Toast.makeText(getApplicationContext(), String.valueOf(time), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getApplicationContext(), AddressActivity.class);
//        Bundle args = new Bundle();
//        args.putSerializable("ARRAYLIST",(Serializable)time);
//        intent.putExtra("BUNDLE",args);
//        intent.putExtra("medium" ,medium);
//        intent.putExtra("board" ,board);
//        intent.putExtra("standard" ,standard);
//        intent.putExtra("stream" ,stream);
//        intent.putExtra("group" ,group);
//        startActivity(intent);
//    }

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
        chtwelve = findViewById(R.id.chtwelve);
        chthirteen = findViewById(R.id.chthirteen);
        chfourteen = findViewById(R.id.chfourteen);
        chzero = findViewById(R.id.chzero);
    }


}