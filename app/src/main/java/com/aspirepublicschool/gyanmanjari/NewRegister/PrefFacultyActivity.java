package com.aspirepublicschool.gyanmanjari.NewRegister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.MainActivity;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class PrefFacultyActivity extends AppCompatActivity {

    TextView infotxt, mediumtxt ,standardtxt, boardtxt , subjecttxt, timetxt ;
    Dialog dialog;
    Button dialogClose, btncontwithSub;
    ArrayList sub;
    List<String> time = new ArrayList<String>();
    HashSet<String> hashSet = new HashSet<String>();
    HashSet<String> timehashSet = new HashSet<String>();
    ArrayList<PrefFacultyModel> facList= new ArrayList<>();
    String subtemp = "", timetemp = "";
    String medium, standard, board, subjects, stream, group = " ";
    RecyclerView recView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_faculty);

        //Getting Data From Previous Activity
        medium = getIntent().getStringExtra("medium");
        standard = getIntent().getStringExtra("standard");
        board = getIntent().getStringExtra("board");
        stream = getIntent().getStringExtra("stream");
        group = getIntent().getStringExtra("group");
        Bundle args = getIntent().getBundleExtra("BUNDLE");
        ArrayList<Object> time = (ArrayList<Object>) args.getSerializable("ARRAYLIST");
        Bundle subjects1 = getIntent().getBundleExtra("BUNDLE1");
        ArrayList<Object> subje = (ArrayList<Object>) subjects1.getSerializable("ARRAYLIST1");

        allocateMemory();

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        medium = sharedPreferences.getString("medium", "none");
//        standard = sharedPreferences.getString("standard", "none");
//        board = sharedPreferences.getString("board", "none");



        for(int i=0; i<subje.size();i++){
            subtemp = subtemp + subje.get(i) + ",\n";
        }
        for(int i=0; i<args.size();i++){
            timetemp = timetemp + time.get(i) + ",\n";
        }
        infotxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShow();
            }
        });

        btncontwithSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continuetoProfile();
            }
        });
        getFaculty();
    }

    private void continuetoProfile() {
    
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    private void getFaculty() {

        String WebServiceUrl = Common.GetWebServiceURL() + "getFaculty.php";

        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                        facList.clear();
                    JSONArray res=new JSONArray(response);
                    String message = res.getJSONObject(0).getString("message");
                    if (message.equals("true")){
                    int asize = res.length();
                        for(int i=1 ; i < asize ; i++) {
                            JSONObject object=res.getJSONObject(i);
                            facList.add(new PrefFacultyModel(
                                    object.getString("id"),
                                    object.getString("fname"),
                                    object.getString("lname"),
                                    object.getString("img"),
                                    object.getString("m_no"),
                                    object.getString("board"),
                                    object.getString("standard"),
                                    object.getString("subject"),
                                    object.getString("medium"),
                                    object.getString("time")));
                        }

                        PrefFacultyAdapter adapter=new PrefFacultyAdapter(getApplicationContext(), facList);
//                        recView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
//                    recStudents.addItemDecoration(new DividerItemDecoration(recStudents.getContext(), DividerItemDecoration.VERTICAL));
                        recView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

                return data;
            }
        };
        Volley.newRequestQueue(PrefFacultyActivity.this).add(request);
    }

    private void dialogShow() {
                dialog = new Dialog(PrefFacultyActivity.this);
                dialog.setContentView(R.layout.selected_info_dialog);
//                dialog.getWindow().setLayout(800, 1200);
                dialog.show();

                mediumtxt = dialog.findViewById(R.id.mediumtxt);
                standardtxt = dialog.findViewById(R.id.standardtxt);
                boardtxt = dialog.findViewById(R.id.boardtxt);
                subjecttxt = dialog.findViewById(R.id.subjecttxt);
                dialogClose =dialog.findViewById(R.id.dialogClose);
                timetxt = dialog.findViewById(R.id.timetxt);

                dialogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                mediumtxt.setText(medium);
                boardtxt.setText(board);
                subjecttxt.setText(subtemp);
                timetxt.setText(timetemp);

                // For Set Text in Standard//
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
                ////

            }


    private void allocateMemory() {
        infotxt = findViewById(R.id.infotxt);
        recView = findViewById(R.id.facultyrecView);
        btncontwithSub = findViewById(R.id.btncontwithSub);
    }
}