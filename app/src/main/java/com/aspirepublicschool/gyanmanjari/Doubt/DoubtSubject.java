package com.aspirepublicschool.gyanmanjari.Doubt;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoubtSubject extends AppCompatActivity {
    Spinner spnsub;
    String subjectsselect, Title, Des,subject;
    ArrayList<String> subList=new ArrayList<>();
    Button btnsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt_subject);
        allocatememory();
        SetSpinnerSubject();
        spnsub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subjectsselect = spnsub.getItemAtPosition(spnsub.getSelectedItemPosition()).toString();
                //Toast.makeText(getApplicationContext(),subjectsselect,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DoubtSubject.this,DoubtTopics.class);
                intent.putExtra("sub", subjectsselect);
                startActivity(intent);
            }
        });
    }

    private void allocatememory() {
        try {
            spnsub = findViewById(R.id.spnsub);
            btnsubmit = findViewById(R.id.btnsubmit);
        }catch (Exception e)
        {
            Toast.makeText(DoubtSubject.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
        }
    }
    private void SetSpinnerSubject() {
        Common.progressDialogShow(DoubtSubject.this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String sc_id = preferences.getString("sc_id","none").toLowerCase();
        final String class_id = preferences.getString("class_id","none");
        String WebServiceUrl= Common.GetWebServiceURL()+"getSubjects.php";
        Log.d("subject",WebServiceUrl);
        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(DoubtSubject.this);
                    JSONArray res=new JSONArray(response);
                    String error=res.getJSONObject(0).getString("error");
                    if(error.equals("no error")==false)
                    {

                    }
                    else
                    {
                        subList.clear();
                        int asize=res.length();
                        for(int i=1;i<asize;i++) {
                            JSONObject object=res.getJSONObject(i);
                            String subclass=object.getString("subject");
                            subList.add(subclass);

                        }
                        spnsub.setAdapter(new ArrayAdapter<String>(DoubtSubject.this, android.R.layout.simple_spinner_dropdown_item,subList));
                        ArrayAdapter myAdap = (ArrayAdapter) spnsub.getAdapter();
                        int spinnerPosition = myAdap.getPosition(subject);
                        spnsub.setSelection(spinnerPosition);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id",sc_id.toLowerCase());
                data.put("cid",class_id);
                return data;
            }
        };
        Volley.newRequestQueue(DoubtSubject.this).add(request);
    }
}
