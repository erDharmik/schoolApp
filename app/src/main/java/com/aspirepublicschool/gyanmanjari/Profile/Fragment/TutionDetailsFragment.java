package com.aspirepublicschool.gyanmanjari.Profile.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


public class TutionDetailsFragment extends Fragment {

    TextView boardtxt, mediumtxt, standardtxt,timetxt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tution_details, container, false);

        boardtxt = view.findViewById(R.id.boardtxt);
        mediumtxt = view.findViewById(R.id.mediumtxt);
        standardtxt = view.findViewById(R.id.standardtxt);
        timetxt = view.findViewById(R.id.timetxt);

        fetchPersonalData();

        return view;
    }

    protected void fetchPersonalData() {

        String WebServiceUrl = Common.GetWebServiceURL() + "personalDetails.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);
                    String board = jsonArray.getJSONObject(0).getString("board");
                    String medium = jsonArray.getJSONObject(0).getString("med");
                    String standard = jsonArray.getJSONObject(0).getString("std");
                    String time = jsonArray.getJSONObject(0).getString("timeslot");


                     String temp = jsonArray.getJSONObject(0).getString("stream");
                    if (!temp.equals("Not set")){
                        standard = standard + jsonArray.getJSONObject(0).getString("stream") ;
                        String temp12 = jsonArray.getJSONObject(0).getString("st_group");
                        if (!temp12.equals("Not set")){
                            standard = standard + jsonArray.getJSONObject(0).getString("st_group");
                        }
                    }

                    boardtxt.setText(board);
                    mediumtxt.setText(medium);
                    standardtxt.setText(standard);
                    timetxt.setText(time);




                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("number", "9586417374");
                return data;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
    }

}