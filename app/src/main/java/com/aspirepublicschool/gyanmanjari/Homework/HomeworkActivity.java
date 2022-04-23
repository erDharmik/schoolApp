package com.aspirepublicschool.gyanmanjari.Homework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.ImageToPdf.ImagePDFActivity;
import com.aspirepublicschool.gyanmanjari.ImageToPdf.ImageToPdf;
import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeworkActivity extends AppCompatActivity {
    RecyclerView rechomework;
    ArrayList<Homework> homeWorkDetailsList = new ArrayList<>();
    Context ctx=this;
    FloatingActionButton floatingActionButton;
    FloatingActionButton fabHomework,fabPdf;
    LinearLayout lnrHomework, lnrPdf;
    TextView homeworkFabTextView,pdfFabTextView;
    String gr_no;
    private Animation fabOpenAnimation;
    private Animation fabCloseAnimation;
    private boolean isFabMenuOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
        rechomework = findViewById(R.id.rechomework);
        floatingActionButton=findViewById(R.id.floatadd);
        fabPdf=findViewById(R.id.fabPdf);
        fabHomework=findViewById(R.id.fabHomework);
        homeworkFabTextView=findViewById(R.id.homeworkFabTextView);
        pdfFabTextView=findViewById(R.id.pdfFabTextView);
        lnrHomework=findViewById(R.id.lnrHomework);
        lnrPdf=findViewById(R.id.lnrPdf);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        getAnimations();
        loadAssignment();
    }
    private void getAnimations() {

        fabOpenAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_open);

        fabCloseAnimation = AnimationUtils.loadAnimation(this, R.anim.fab_close);

    }
    private void expandFabMenu() {

        ViewCompat.animate(floatingActionButton).rotation(45.0F).withLayer().setDuration(300).setInterpolator(new OvershootInterpolator(10.0F)).start();
        lnrPdf.startAnimation(fabOpenAnimation);
        lnrHomework.startAnimation(fabOpenAnimation);
        fabHomework.setClickable(true);
        fabPdf.setClickable(true);
        isFabMenuOpen = true;
        lnrHomework.setVisibility(View.VISIBLE);
        lnrPdf.setVisibility(View.VISIBLE);


    }

    private void collapseFabMenu() {

        ViewCompat.animate(floatingActionButton).rotation(0.0F).withLayer().setDuration(300).setInterpolator(new OvershootInterpolator(10.0F)).start();
        lnrPdf.startAnimation(fabCloseAnimation);
        lnrHomework.startAnimation(fabCloseAnimation);
        fabHomework.setClickable(false);
        fabPdf.setClickable(false);
        isFabMenuOpen = false;

    }

    public void baseFabClick(View view) {

        if (isFabMenuOpen)
            collapseFabMenu();
        else
            expandFabMenu();


    }

    public void pdfFabClick(View view) {
        Intent intent= new Intent(HomeworkActivity.this, ImagePDFActivity.class);
        startActivity(intent);
        finish();

    }
    public void homeworkFabClick(View view)
    {
        Intent intent= new Intent(HomeworkActivity.this,InsertHomeWork.class);
        intent.putExtra("gr_no",gr_no);
        startActivity(intent);
        finish();

    }
    private void loadAssignment() {
        Common.progressDialogShow(ctx);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        final String stu_id = mPrefs.getString("stu_id","none");
        String HOMEWORK_URL = Common.GetWebServiceURL()+"viewSubmitedHomework.php";
        Log.v("LINK",HOMEWORK_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOMEWORK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(ctx);
                    Log.d("###",response );
/*
                    "error": "no error"
                },
                {
                    "total": 1
                },
                {
                    "sc_id": "SCIDN2",
                        "cid": "CIDN7",
                        "stu_id": "SCIDN2STIDN2557",
                        "hw_id": "HMWK1",
                        "doc": "2020-03-05-10_28_42-ENGLISH.jpg",
                        "remark": "Not Set",
                        "status": "0"
                }*/
                    homeWorkDetailsList.clear();
                    JSONArray array = new JSONArray(response);
                    String error=array.getJSONObject(0).getString("error");
                    if(error.equals("no error"))
                    {
                        int total=array.getJSONObject(1).getInt("total");
                        if(total!=0)
                        {
                            for (int i = 2; i < array.length(); i++)
                            {
                                JSONObject jsonObject = array.getJSONObject(i);
                                homeWorkDetailsList.add(new Homework(
                                        jsonObject.getString("hw_id"),
                                        jsonObject.getString("doc"),
                                        jsonObject.getString("sub"),
                                        jsonObject.getString("remark"),jsonObject.getString("status"),jsonObject.getString("gr_no")));
                                gr_no = jsonObject.getString("gr_no");
                                // loadTeacherData(t_id);
                                HomeworkAdapter adapter = new HomeworkAdapter(ctx, homeWorkDetailsList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx);
                                rechomework.setLayoutManager(mLayoutManager);
                                rechomework.setItemAnimator(new DefaultItemAnimator());
                                rechomework.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                        }
                        else
                        {
                            Toast.makeText(HomeworkActivity.this,"No HomeWork Submitted",Toast.LENGTH_LONG).show();
                        }


                    }
                    else
                    {
                        Toast.makeText(HomeworkActivity.this,R.string.no_connection_toast,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeworkActivity.this,R.string.no_connection_toast,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sc_id",sc_id.toUpperCase());
                params.put("cid",class_id);
                params.put("stu_id",stu_id);

                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }
}
