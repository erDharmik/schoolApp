package com.aspirepublicschool.gyanmanjari.Test.NEET;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.MainActivity;
import com.aspirepublicschool.gyanmanjari.Test.DBHelper;
import com.aspirepublicschool.gyanmanjari.Test.TestAnswer;
import com.aspirepublicschool.gyanmanjari.Test.TestTimer;
import com.aspirepublicschool.gyanmanjari.Test.ViewTestToday;
import com.aspirepublicschool.gyanmanjari.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NeetTest extends AppCompatActivity {

    int hours,min;
    int duration,sec;
    long result;
    public static int ansques=0;
    public static int unanswered=0;
    private DBHelper mydb ;
    Context ctx=this;
    static boolean dataflags=false;
    Handler handler;
    pyCountDown timer,countDownTimeronResume;
    String time,pos,neg,type;
    String tst_id,sub;
    public static TextView txtquestion,txttotal,txtunanswered,txttotalunanswered,txtposmark,txtnegmark,txtsub,txttimer;
    Button Btnsubmit,btnabort;
    TabHost TabHostWindow;
    String marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neet_test);
        tst_id=getIntent().getExtras().getString("tst_id");
        Log.d("tst", tst_id);
        sub=getIntent().getExtras().getString("sub");
        hours=Integer.parseInt(getIntent().getExtras().getString("hours"));
        min=Integer.parseInt(getIntent().getExtras().getString("min"));

        pos=getIntent().getExtras().getString("pos");
        neg=getIntent().getExtras().getString("neg");
        type=getIntent().getExtras().getString("type");
        getSupportActionBar().setTitle(type);
        duration=(hours*60)+min;
        sec=duration*60;
        result = TimeUnit.SECONDS.toMillis(sec);
        Log.d("!!!",""+sec);
        allocatememory();
        mydb = new DBHelper(ctx);
        txttimer.setText(txttimer.getText() + String.valueOf(result));
        txtposmark.setText(pos);
        txtnegmark.setText(neg);
       // txtsub.setText(sub);
        Cursor rs = mydb.getData(tst_id);
        if (rs.moveToFirst())
        {
            // record exists
            dataflags=true;
            ArrayList<TestTimer> array_list = mydb.getAllCotacts(tst_id);
            for(int i=0; i < array_list.size(); i++)

                if(array_list.get(i).getTest_id().equals(tst_id)) {
                    Log.d("###",array_list.get(i).getTime());
                    timer=(pyCountDown) new pyCountDown(Long.parseLong(array_list.get(i).getTime()), 1000).start();

                }
                else {

                }

        }
        else {
            // record not found
            if(mydb.insertContact(tst_id, String.valueOf(result))) {
                timer=(pyCountDown) new pyCountDown(result, 1000).start();


            }
        }
        if (!rs.isClosed())  {
            rs.close();
        }
        TabHostWindow = (TabHost)findViewById(android.R.id.tabhost);
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState); // state will be bundle your activity state which you get in onCreate
        TabHostWindow.setup(mLocalActivityManager);
        TabHostWindow.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String arg0) {
                for (int i = 0; i < TabHostWindow.getTabWidget().getChildCount(); i++) {
                    TabHostWindow.getTabWidget().getChildAt(i)
                            .setBackgroundColor(Color.parseColor("#F44336")); // unselected
                    TabHostWindow.getTabWidget().getChildAt(i)
                            .setBackgroundResource(R.color.colorAccent);

                }

                TabHostWindow.getTabWidget().getChildAt(TabHostWindow.getCurrentTab())
                        .setBackgroundColor(Color.parseColor("#E02525") );
                TabHostWindow.getTabWidget().getChildAt(TabHostWindow.getCurrentTab()).setBackgroundResource(R.color.buttoncolor); // selected
            }
        });


        //Creating tab menu.
        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("First tab");
        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("Second Tab");
        TabHost.TabSpec TabMenu3 = TabHostWindow.newTabSpec("Third Tab");

        //Setting up tab 1 name.
        TabMenu1.setIndicator("Physics");
        //Set tab 1 activity to tab 1 menu.
        Intent intent=new Intent(this, Physics.class);
        intent.putExtra("tst_id", tst_id);
        intent.putExtra("sub", sub);
        TabMenu1.setContent(intent);

        //Setting up tab 2 name.
        TabMenu2.setIndicator("Chemistry");
        //Set tab 3 activity to tab 1 menu.
        Intent intent1=new Intent(this, Chemistry.class);
        intent1.putExtra("tst_id", tst_id);
        intent1.putExtra("sub", sub);
        TabMenu2.setContent(intent1);

        //Setting up tab 2 name.
        TabMenu3.setIndicator("Biology");
        //Set tab 3 activity to tab 3 menu.
        Intent intent2=new Intent(this, Biology.class);
        intent2.putExtra("tst_id", tst_id);
        intent2.putExtra("sub", sub);
        TabMenu3.setContent(intent2);

        //Adding tab1, tab2, tab3 to tabhost view.

        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);
        TabHostWindow.addTab(TabMenu3);



        Btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
                alertDialogBuilder.setMessage("Are you sure, You wanted to submit test?..");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                timer.cancel();
                                saveData();

                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialogBuilder.setCancelable(true);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        btnabort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
                alertDialogBuilder.setMessage("Are you sure, You wanted to Abort test?..");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                timer.cancel();
                                startActivity(new Intent(ctx, ViewTestToday.class));
                                finish();


                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialogBuilder.setCancelable(true);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
    }
    private class pyCountDown extends CountDownTimer {

        public pyCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long seconds = millisUntilFinished / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            time =  hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
            txttimer.setText("Time remaining: " + time);
            if(mydb.updateContact(tst_id, String.valueOf(millisUntilFinished)))
            {
                /*Toast.makeText(getApplicationContext(), "Update",
                        Toast.LENGTH_SHORT).show();*/
            }
        }

        @Override
        public void onFinish() {

            txttimer.setText("Done...!");
            saveData();
        }

    }
    private void saveData() {
        Common.progressDialogShow(NeetTest.this);
        ArrayList<TestAnswer> answers = mydb.getAllTestData(tst_id);
        final String test = new Gson().toJson(answers);
        Log.d("test",test);
        String Webserviceurl=Common.GetWebServiceURL()+"submitjeeneet.php";
        //String Webserviceurl="https://www.zocarro.com/zocarro_mobile_app/ws/submitTest.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String sc_id = preferences.getString("sc_id","none").toUpperCase();
        final String stu_id = preferences.getString("stu_id","none").toUpperCase();
        final String class_id = preferences.getString("class_id","none").toUpperCase();
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("!!!",response );
                Common.progressDialogDismiss(NeetTest.this);

                try {
                    JSONArray array = new JSONArray(response);
                    for(int i=0;i<array.length();i++) {
                        JSONObject object=array.getJSONObject(i);
                        String message = object.getString("message");
                        Log.d("aaa", message);
                        if (message.equals("fail")) {
                            Toast.makeText(NeetTest.this, "Sorry!! Test is not Submitted", Toast.LENGTH_LONG).show();

                        } else if (message.equals("Success")) {
                            marks = object.getString("mark");
                            Toast.makeText(NeetTest.this,"Test Submitted Successfully" ,Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(NeetTest.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        //SubmitMarks(marks);
                    }
                } catch (JSONException e) {
                    // Toast.makeText(TestTine.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(NeetTest.this);
                Log.d("mess", error.toString());
                Toast.makeText(NeetTest.this,error.toString() ,Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.clear();
                data.put("test",test);
                data.put("sc_id", sc_id.toUpperCase());
                data.put("stu_id",stu_id );
                data.put("cid",class_id );
                data.put("tst_id",tst_id );
                Log.d("PPP", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000,3,1));
        Volley.newRequestQueue(NeetTest.this).add(request);
    }
    public void onDestroy(){
        super.onDestroy();
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }
    private void allocatememory() {
        txttimer=findViewById(R.id.txttimer);
        Btnsubmit=findViewById(R.id.toolbar_overflow_menu_button);
        btnabort=findViewById(R.id.toolbar_overflow_abort_button);
        txtquestion=findViewById(R.id.txtquestion);
        txttotal=findViewById(R.id.txttotal);
        txtposmark=findViewById(R.id.txtposmark);
        txtnegmark=findViewById(R.id.txtnegmark);
       // txtsub=findViewById(R.id.txtsub);

    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NeetTest.this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to Abort test?..");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        timer.cancel();
                        startActivity(new Intent(NeetTest.this,ViewTestToday.class));
                        finish();


                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialogBuilder.setCancelable(true);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
