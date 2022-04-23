package com.aspirepublicschool.gyanmanjari.Test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestTine extends AppCompatActivity {
    String tst_id,sub;
    RecyclerView recteststart;
    TextView txttimer;
    public static TextView txtquestion,txttotal,txtunanswered,txttotalunanswered;
    Button btnsubmit,btnabort;
    ArrayList<TestQuestion> testQuestionArrayList=new ArrayList<>();
    String[] mDataset;
    int hours,min;
    int duration,sec;
    long result;
    public static int ansques=0;
    public static int unanswered=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tine);

        SendRequest();
        tst_id=getIntent().getExtras().getString("tst_id");
        sub=getIntent().getExtras().getString("sub");
        hours=Integer.parseInt(getIntent().getExtras().getString("hours"));
        min=Integer.parseInt(getIntent().getExtras().getString("min"));
        duration=(hours*60)+min;
        sec=duration*60;
        result = TimeUnit.SECONDS.toMillis(sec);
        Log.d("!!!",""+sec);
        allocatememory();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toast.makeText(TestTine.this,"Faild!",Toast.LENGTH_LONG).show();

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestTine.this);
                alertDialogBuilder.setMessage("Are you sure, You wanted to submit test?..");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                saveData();

                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
        btnabort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestTine.this,ViewTestToday.class));
                finish();
            }
        });
    }

    private void saveData() {
        Common.progressDialogShow(TestTine.this);
        final String test = new Gson().toJson(TestTimeAdapter.testArrayList);
        Log.d("test",test);
        String Webserviceurl=Common.GetWebServiceURL()+"submitTest.php";
        //String Webserviceurl="http://www.zocarro.net/zocarro_mobile_app/ws/"+"submitTest.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String sc_id = preferences.getString("sc_id","none").toUpperCase();
        final String stu_id = preferences.getString("stu_id","none").toUpperCase();
        final String class_id = preferences.getString("class_id","none").toUpperCase();
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("!!!",response );
                Common.progressDialogDismiss(TestTine.this);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("fail")) {
                        Toast.makeText(TestTine.this, "Sorry! Test is not Submitted",Toast.LENGTH_LONG).show();

                    }
                    else if(object.getString("message").equals("Success"))
                    {
                        Toast.makeText(TestTine.this,"Test Submitted Successfully" ,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(TestTine.this,DownloadTestKey.class);
                        intent.putExtra("tst_id",tst_id);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                   // Toast.makeText(TestTine.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("test",test);
                data.put("sc_id", sc_id);
                data.put("stu_id",stu_id );
                data.put("cid",class_id );
                data.put("tst_id",tst_id );
                Log.d("PPP", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(TestTine.this).add(request);
    }

    private void SendRequest() {
        Common.progressDialogShow(TestTine.this);
        String Webserviceurl=Common.GetWebServiceURL()+"testquestion.php";

        Toast.makeText(TestTine.this, "Faild!", Toast.LENGTH_LONG).show();



        //String Webserviceurl="http://www.zocarro.net/zocarro_mobile_app/ws/"+"testquestion.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        final String stu_id = mPrefs.getString("stu_id","none");
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");



                        new CountDownTimer(result, 1000) {

                            public void onTick(long millisUntilFinished) {
                                long seconds = millisUntilFinished / 1000;
                                long minutes = seconds / 60;
                                long hours = minutes / 60;
                                long days = hours / 24;
                                String time =  hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
                                txttimer.setText("Time remaining: " + time);
                            }

                            public void onFinish() {
                                txttimer.setText("done!");
                                saveData();
                            }
                        }.start();


                    Log.d("aaa",response);
                    testQuestionArrayList.clear();
                    JSONArray array=new JSONArray(response);
                   int total=array.getJSONObject(0).getInt("total");
                    if(total==0)
                    {
                        Toast.makeText(TestTine.this,"No Question",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        txttotal.setText(""+total);
                       // txtquestion.setText(""+ansques);
                        txttotalunanswered.setText("/"+total);
                        txtunanswered.setText(""+unanswered);
                        mDataset=new String[array.length()-1];
                        for(int i=1;i<array.length();i++)
                        {
                            /*  {
                            "qid": "QST1",
                                "question": "Test Question ?",
                                "a": "A",
                                "b": "B",
                                "c": "C",
                                "d": "D"
                        },*/
                            JSONObject object=array.getJSONObject(i);
                          /*  testQuestionArrayList.add(new TestQuestion(object.getString("qid"),object.getString("question"),object.getString("a"),object.getString("b"),
                                    object.getString("c"),object.getString("d"),"Not Set",object.getString("q_img"),object.getString("a_img"),object.getString("b_img")
                                    ,object.getString("c_img"),object.getString("d_img"),false,tst_id,object.getString("subject")));*/
                        }
                        Common.progressDialogDismiss(TestTine.this);
                        TestTimeAdapter adapter=new TestTimeAdapter(TestTine.this,testQuestionArrayList,mDataset);
                        recteststart.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TestTine.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id",sc_id);
                data.put("cid",class_id);
                data.put("tst_id",tst_id);
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(TestTine.this).add(request);
    }

    private void allocatememory() {
        recteststart=findViewById(R.id.recteststart);
        txttimer=findViewById(R.id.txttimer);
        btnsubmit=findViewById(R.id.btnsubmit);
        btnabort=findViewById(R.id.btnabort);
       // txtquestion=findViewById(R.id.txtquestion);
        txttotal=findViewById(R.id.txttotal);
        txttotalunanswered=findViewById(R.id.txttotalunanswered);
        txtunanswered=findViewById(R.id.txtunanswered);
       /* LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true);
        recteststart.setLayoutManager(layoutManager);*/
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TestTine.this);
        recteststart.setLayoutManager(mLayoutManager);
        recteststart.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to exit test?..");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                              saveData();
                                startActivity(new Intent(TestTine.this,ViewTestToday.class));
                                finish();
                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
