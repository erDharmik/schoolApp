package com.aspirepublicschool.gyanmanjari.NewRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.aspirepublicschool.gyanmanjari.OTPVerificationActivity;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class NewTutionDetailsActivity extends AppCompatActivity {

    Spinner medium, board, standard, stream, group;
    Button cont, prev;
    HashSet<String> hashSet = new HashSet<>();
    ArrayList<String> boardList = new ArrayList<>();
    ArrayList<String> mediumList = new ArrayList<>();
    ArrayList<String> standardList = new ArrayList<>();
    ArrayList<String> streamList = new ArrayList<>();

    String number;
    ProgressDialog progressdialog;

    TextView str, grp;
    String[] sstream = {"Select Stream","Science", "Commerce", "Arts"} ;
    String[] sgroup = {"Select Group", "A", "B", "Both(A + B)"} ;
    ArrayAdapter<String> mediumadapter, boardadapter, standardadapter,streamadapter, groupadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tution_details);
        getSupportActionBar().hide();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        number = preferences.getString("number", null);

//        number = getIntent().getStringExtra("number");
        allocateMemory();
        setAdaptermedium();
        setAdapterstandard();
        setAdapterboard();
        setAdapter();

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertTutionDetails();


            }
        });


        progressdialog = new ProgressDialog(NewTutionDetailsActivity.this);
        progressdialog.setMessage("Just a Sec...");

        standard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String temp = String.valueOf(parent.getItemAtPosition(position));
                stream.setSelection(0);
                group.setSelection(0);
                if (temp.contains("11")) {
                    stream.setVisibility(View.VISIBLE);
                    str.setVisibility(View.VISIBLE);
                }
                else if(temp.contains("12")){
                    stream.setVisibility(View.VISIBLE);
                    str.setVisibility(View.VISIBLE);
                }
                else{
                    stream.setVisibility(View.GONE);
                    str.setVisibility(View.GONE);
                    group.setVisibility(View.GONE);
                    grp.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        stream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String temp = String.valueOf(parent.getItemAtPosition(position));
                group.setSelection(0);
                if (temp.equals("Science")) {
                    group.setVisibility(View.VISIBLE);
                    grp.setVisibility(View.VISIBLE);
                }
                else{
                    group.setVisibility(View.GONE);
                    grp.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void insertTutionDetails() {

        progressdialog.show();
        String Webserviceurl = Common.GetWebServiceURL() + "tuitionRegister.php";
        Log.d("@@@web", Webserviceurl);
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressdialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    String message = jsonArray.getJSONObject(0).getString("message");
                    if (message.equalsIgnoreCase("fail")){
                        Toast.makeText(getApplicationContext(), "Try Again!", Toast.LENGTH_SHORT).show();
                    }
                    else if(message.equalsIgnoreCase("true")){
                        Intent intent = new Intent(NewTutionDetailsActivity.this,NewTimePrefActivity.class);
                        intent.putExtra("medium", String.valueOf(medium.getSelectedItem()));
                        intent.putExtra("board", String.valueOf(board.getSelectedItem()));
                        intent.putExtra("standard", String.valueOf(standard.getSelectedItem()));
                        intent.putExtra("stream", String.valueOf(stream.getSelectedItem()));
                        intent.putExtra("group", String.valueOf(group.getSelectedItem()));
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
                progressdialog.dismiss();
                Toast.makeText(getApplicationContext(), R.string.no_connection_toast , Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("medium", String.valueOf(medium.getSelectedItem()));
                data.put("board", String.valueOf(board.getSelectedItem()));
                data.put("standard", String.valueOf(standard.getSelectedItem()));
                data.put("stream", String.valueOf(stream.getSelectedItem()));
                data.put("group", String.valueOf(group.getSelectedItem()));
                data.put("number", number);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(NewTutionDetailsActivity.this);
        requestQueue.add(request);

    }

    private void setAdapter() {

        ArrayAdapter<String> streamadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sstream);
        streamadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stream.setAdapter(streamadapter);

        ArrayAdapter<String> groupadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sgroup);
        groupadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        group.setAdapter(groupadapter);
    }


    private void sharedPref() {

        String standardfinal;
        if (stream.getSelectedItem().toString().equals("Select Stream") ){
            standardfinal = standard.getSelectedItem().toString();
            Toast.makeText(getApplicationContext(), standardfinal, Toast.LENGTH_SHORT).show();

        }else if(group.getSelectedItem().toString().equals("Select Group")){
            standardfinal = standard.getSelectedItem().toString() + " " + stream.getSelectedItem().toString();
            Toast.makeText(getApplicationContext(), standardfinal, Toast.LENGTH_SHORT).show();
        }
        else{
            standardfinal = standard.getSelectedItem().toString() + " " + stream.getSelectedItem() + " " + group.getSelectedItem() + " Group";
            Toast.makeText(getApplicationContext(), standardfinal, Toast.LENGTH_SHORT).show();
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NewTutionDetailsActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("medium" , medium.getSelectedItem().toString());
        editor.putString("board" , board.getSelectedItem().toString());
        editor.putString("standard" , standardfinal);
        editor.commit();
    }

    private void registration() {
        int Standard1 = Integer.parseInt(String.valueOf(standard.getSelectedItem()));
        String Group1 = String.valueOf(group.getSelectedItem());
        String Stream1 = String.valueOf(stream.getSelectedItem());

        if(Standard1 <= 10 ){
            continueRegistration();
        }
        else if (Standard1 > 10){
            if (Stream1.equals("Science")){
                if (Group1.equals("B") || Group1.equals("A") || Group1.equals("Both(A + B)")){
                    scienceRegistration();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please Select Group", Toast.LENGTH_SHORT).show();
                }
            }
            else if(Stream1.equals("Commerce")){
                CommerceRegistration();
            }
            else if(Stream1.equals("Arts")){
                ArtsRegistration();
            }
            else{
                Toast.makeText(getApplicationContext(), "Please Select Stream", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void ArtsRegistration() {
        Intent intent = new Intent(getApplicationContext(), NewTimePrefActivity.class);
        intent.putExtra("medium", String.valueOf(medium.getSelectedItem()));
        intent.putExtra("board", String.valueOf(board.getSelectedItem()));
        intent.putExtra("standard", String.valueOf(standard.getSelectedItem()));
        intent.putExtra("stream", String.valueOf(stream.getSelectedItem()));
        startActivity(intent);
    }

    private void CommerceRegistration() {
        Intent intent = new Intent(getApplicationContext(), NewTimePrefActivity.class);
        intent.putExtra("medium", String.valueOf(medium.getSelectedItem()));
        intent.putExtra("board", String.valueOf(board.getSelectedItem()));
        intent.putExtra("standard", String.valueOf(standard.getSelectedItem()));
        intent.putExtra("stream", String.valueOf(stream.getSelectedItem()));
        startActivity(intent);

    }

    private void scienceRegistration() {
        Intent intent = new Intent(getApplicationContext(), NewTimePrefActivity.class);
        intent.putExtra("medium", String.valueOf(medium.getSelectedItem()));
        intent.putExtra("board", String.valueOf(board.getSelectedItem()));
        intent.putExtra("standard", String.valueOf(standard.getSelectedItem()));
        intent.putExtra("stream", String.valueOf(stream.getSelectedItem()));
        intent.putExtra("group", String.valueOf(group.getSelectedItem()));
        startActivity(intent);

    }

    private void continueRegistration() {

        Intent intent = new Intent(getApplicationContext(), NewTimePrefActivity.class);
        intent.putExtra("medium", String.valueOf(medium.getSelectedItem()));
        intent.putExtra("board", String.valueOf(board.getSelectedItem()));
        intent.putExtra("standard", String.valueOf(standard.getSelectedItem()));
        startActivity(intent);

    }

    private void allocateMemory() {
        medium = findViewById(R.id.mediumSpinner);
        board = findViewById(R.id.boardSpinner);
        standard = findViewById(R.id.standardSpinner);
        stream = findViewById(R.id.streamSpinner);
        group = findViewById(R.id.groupSpinner);
        cont = findViewById(R.id.btncont);
        grp = findViewById(R.id.grpTxt);
        str = findViewById(R.id.strTxt);

        stream.setVisibility(View.GONE);
        group.setVisibility(View.GONE);
        grp.setVisibility(View.GONE);
        str.setVisibility(View.GONE);

    }
    private void setAdapterboard(){

        String Webserviceurl= Common.GetWebServiceURL()+"setboard.php";
        //String Webserviceurl= "https://www.zocarro.net/zocarro_mobile_app/ws/"+"gethwid.php";
        Request request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("$$$", response);
                    JSONArray array=new JSONArray(response);

                    String error=array.getJSONObject(0).getString("error");

                    if(error.equals("no error")) {
                        int total = array.getJSONObject(1).getInt("total");
                        if (total != 0) {
                            for (int i = 2; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                boardList.add(object.getString("board"));
                            }

                            hashSet.addAll(boardList);
                            boardList.clear();
                            boardList.addAll(hashSet);
                            hashSet.clear();

                            board.setAdapter(new ArrayAdapter<String>(NewTutionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, boardList));

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id", "SCIDN1");
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(NewTutionDetailsActivity.this).add(request);
    }

    private void setAdapterstandard() {

        String Webserviceurl= Common.GetWebServiceURL()+"setstandard.php";
        //String Webserviceurl= "https://www.zocarro.net/zocarro_mobile_app/ws/"+"gethwid.php";
        Request request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("$$$", response);
                    JSONArray array=new JSONArray(response);

                    String error=array.getJSONObject(0).getString("error");

                    if(error.equals("no error")) {
                        int total = array.getJSONObject(1).getInt("total");
                        if (total != 0) {
                            for (int i = 2; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                standardList.add(object.getString("std"));
                            }

                            hashSet.addAll(standardList);
                            standardList.clear();
                            standardList.addAll(hashSet);
                            hashSet.clear();

                               standard.setAdapter(new ArrayAdapter<String>(NewTutionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, standardList));

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id", "SCIDN1");
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(NewTutionDetailsActivity.this).add(request);
    }

    private void setAdaptermedium() {

        String Webserviceurl= Common.GetWebServiceURL()+"setmedium.php";
        //String Webserviceurl= "https://www.zocarro.net/zocarro_mobile_app/ws/"+"gethwid.php";
        Request request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("$$$", response);
                    JSONArray array=new JSONArray(response);

                    String error=array.getJSONObject(0).getString("error");

                    if(error.equals("no error")) {
                        int total = array.getJSONObject(1).getInt("total");
                        if (total != 0) {
                            for (int i = 2; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                mediumList.add(object.getString("med"));
                            }

                            hashSet.addAll(mediumList);
                            mediumList.clear();
                            mediumList.addAll(hashSet);
                            hashSet.clear();

                            medium.setAdapter(new ArrayAdapter<String>(NewTutionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, mediumList));

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id", "SCIDN1");
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(NewTutionDetailsActivity.this).add(request);
    }
}