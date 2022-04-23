package com.aspirepublicschool.gyanmanjari.VideoLectures;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
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

public class VideoLectures extends AppCompatActivity {
    RecyclerView rechomework;
    ArrayList<VideoLectureModel> homeWorkDetailsList = new ArrayList<>();
    Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_lectures);
        allocatememory();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
    private void allocatememory() {
        rechomework = findViewById(R.id.rechomework);
        loadAssignment();
    }
    private void loadAssignment() {
        Common.progressDialogShow(ctx);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        String HOMEWORK_URL = Common.GetWebServiceURL()+"videolectures.php";

        Log.v("LINK",HOMEWORK_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOMEWORK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                     Common.progressDialogDismiss(ctx);
                    Log.d("@@@",response );
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        homeWorkDetailsList.add(new VideoLectureModel(
                                jsonObject.getString("sc_id"),
                                jsonObject.getString("cid"),
                                jsonObject.getString("date"),
                                jsonObject.getString("subject"),
                                jsonObject.getString("link"),
                                jsonObject.getString("stime"),
                                jsonObject.getString("etime"),
                                jsonObject.getString("des"),
                                jsonObject.getString("status"),
                                jsonObject.getString("status1"),
                                jsonObject.getString("linkyt"),
                                jsonObject.getString("id")
                        ));
                        // loadTeacherData(t_id);
                        VideoLectureAdapter adapter = new VideoLectureAdapter(ctx, homeWorkDetailsList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx.getApplicationContext());
                        rechomework.setLayoutManager(mLayoutManager);
                        rechomework.setItemAnimator(new DefaultItemAnimator());
                        rechomework.setAdapter(adapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sc_id",sc_id.toUpperCase());
                params.put("cid",class_id);
                Log.d("##",params.toString());

                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }
}
