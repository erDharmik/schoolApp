package com.aspirepublicschool.gyanmanjari.Profile.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.MainActivity;
import com.aspirepublicschool.gyanmanjari.NewRegister.PrefFacultyActivity;
import com.aspirepublicschool.gyanmanjari.NewRegister.PrefFacultyAdapter;
import com.aspirepublicschool.gyanmanjari.NewRegister.PrefFacultyModel;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class FacultyDetails extends Fragment {

    ArrayList<PrefFacultyModel> facList= new ArrayList<>();
    RecyclerView recView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty_details, container, false);

        recView = view.findViewById(R.id.facultyrecView);
        getFaculty();

        return view;
    }


    private void continuetoProfile() {

        Intent intent = new Intent(getContext(), MainActivity.class);
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

                        PrefFacultyAdapter adapter=new PrefFacultyAdapter(getContext(), facList);
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
                Toast.makeText(getContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();

                return data;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
    }

}