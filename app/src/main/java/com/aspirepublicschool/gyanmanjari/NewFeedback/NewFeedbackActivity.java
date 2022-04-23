package com.aspirepublicschool.gyanmanjari.NewFeedback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.NewRegister.PrefFacultyActivity;
import com.aspirepublicschool.gyanmanjari.NewRegister.PrefFacultyAdapter;
import com.aspirepublicschool.gyanmanjari.NewRegister.PrefFacultyModel;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewFeedbackActivity extends AppCompatActivity {

    RecyclerView recView;
    ArrayList<NewFeedbackModel> teacherList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feedback);

        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
        recView = (RecyclerView) findViewById(R.id.feedbackListRecView);

        setRecView();
     }

    private void setRecView() {

        String WebServiceUrl = Common.GetWebServiceURL() + "getTeacherListForFeedback.php";

        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    teacherList.clear();
                    JSONArray res=new JSONArray(response);

                        int asize = res.length();
                        for(int i=0 ; i < asize ; i++) {
                            JSONObject object=res.getJSONObject(i);

                            teacherList.add(new NewFeedbackModel(
                                    object.getString("t_id"),
                                    object.getString("t_fname") + " " + object.getString("t_lname"),
                                    object.getString("subject"),
                                    object.getString("t_img")));

//                            NewFeedbackModel nm = new NewFeedbackModel();
//                            nm.setId(object.getString("id"));
//                            nm.setTeachername(object.getString("t_fname") + " " + object.getString("t_lname"));
//                            nm.setTeachername(object.getString("subject"));

                        }


                        NewFeedbackAdapter adapter=new NewFeedbackAdapter(getApplicationContext(), teacherList);
                        recView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

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
        Volley.newRequestQueue(NewFeedbackActivity.this).add(request);
    }
}