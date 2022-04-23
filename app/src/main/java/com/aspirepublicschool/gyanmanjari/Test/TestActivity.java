package com.aspirepublicschool.gyanmanjari.Test;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.MainActivity;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.internet.NetworkChangeReceiver;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TestActivity extends AppCompatActivity
{
    static TabLayout tabLayout;
    ViewPager viewPager;
    TabAdapter adapter;
    static ArrayList<TestQuestion> testQuestionArrayList=new ArrayList<>();
    ArrayList<FinalAnswer> finalAnswers=new ArrayList<>();
    String tst_id,sub;
    TextView txttimer;
    public static TextView txtquestion,txttotal,txtunanswered,txttotalunanswered,txtposmark,txtnegmark,txtsub;
    String[] mDataset;
    int hours,min;
    int duration,sec;
    long result;
    public static int ansques=0;
    public static int unanswered=0;
    private DBHelper mydb ;
    Context ctx=this;
    static boolean dataflags=false;
    Handler handler;
    pyCountDown timer,countDownTimerOnResume;
    String time,pos,neg,type;
    Button Btnsubmit,btnabort;
    //old way to show network alert
//    private BroadcastReceiver mNetworkReceiver;

    //network
    public NetworkChangeReceiver receiver;
    Boolean bl = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        mNetworkReceiver = new NetworkChangeReceiver();
//        registerNetworkBroadcastForNougat();

        //Screen recording flags
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_test);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        tst_id=getIntent().getExtras().getString("tst_id");
        sub=getIntent().getExtras().getString("sub");
        hours=getIntent().getExtras().getInt("hours",0);
        min=getIntent().getExtras().getInt("min",0);
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
        txtsub.setText(sub);

        //shows  dialog if internet is not available
        checkInternet();

        Cursor rs = mydb.getData(tst_id);
        if (rs.moveToFirst())
        {
            // record exists
            /*Toast.makeText(getApplicationContext(), "Data Exists",
                    Toast.LENGTH_SHORT).show();*/
            dataflags=true;
            getAnswerBefore(tst_id);
            ArrayList<TestTimer> array_list = mydb.getAllCotacts(tst_id);
            for(int i=0; i < array_list.size(); i++)

                if(array_list.get(i).getTest_id().equals(tst_id))
                {
                    Log.d("###",array_list.get(i).getTime());
                    timer=(pyCountDown) new pyCountDown(Long.parseLong(array_list.get(i).getTime()), 1000).start();
                }
                else {

                }

        }
        else {
            // record not found
            if(mydb.insertContact(tst_id, String.valueOf(result))) {
             /*   Toast.makeText(getApplicationContext(), "done",
                        Toast.LENGTH_SHORT).show();*/
                timer=(pyCountDown) new pyCountDown(result, 1000).start();
            }
            SendRequest();
        }
        if (!rs.isClosed())  {
            rs.close();
        }
        Btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestActivity.this);
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
//        btnabort.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestActivity.this);
//                alertDialogBuilder.setMessage("Are you sure, You wanted to Abort test?..");
//                alertDialogBuilder.setPositiveButton("Yes",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface arg0, int arg1)
//                            {
//                                timer.cancel();
////                                startActivity(new Intent(TestActivity.this,ViewTestToday.class));
////                                finish();
//                                Intent intent = new Intent(TestActivity.this,TestActivity.class);
//                                intent.putExtra("tst_id",tst_id);
//                                intent.putExtra("sub",sub);
//                                intent.putExtra("hours",hours);
//                                intent.putExtra("min",min);
//                                intent.putExtra("pos",pos);
//                                intent.putExtra("neg",neg);
//                                intent.putExtra("type",type);
//                                startActivity(intent);
//                                finish();
//                            }
//                        });
//
//                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        alertDialogBuilder.setCancelable(true);
//                    }
//                });
//
//                AlertDialog alertDialog = alertDialogBuilder.create();
//                alertDialog.show();
//
//            }
//        });

    }

    private void saveData()
    {
        Common.progressDialogShow(TestActivity.this);
        finalAnswers.clear();
        ArrayList<TestAnswer> answers = mydb.getAllTestData(tst_id);
        final String test = new Gson().toJson(answers);
        Log.d("test",test);
        String Webserviceurl=Common.GetWebServiceURL()+"submitTest.php";
        //String Webserviceurl="https://www.zocarro.com/zocarro_mobile_app/ws/submitTest.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String sc_id = preferences.getString("sc_id","none").toUpperCase();
        final String stu_id = preferences.getString("stu_id","none").toUpperCase();
        final String class_id = preferences.getString("class_id","none").toUpperCase();
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("!!!",response );
                Common.progressDialogDismiss(TestActivity.this);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("fail")) {
                        Toast.makeText(TestActivity.this, "Sorry! Test is not Submitted",Toast.LENGTH_LONG).show();

                    }
                    else if(object.getString("message").equals("Success"))
                    {
                        Toast.makeText(TestActivity.this,"Test Submitted Successfully" ,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(TestActivity.this, MainActivity.class);
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
                Common.progressDialogDismiss(TestActivity.this);
                Log.d("mess", error.toString());
                Toast.makeText(TestActivity.this,error.toString() ,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.clear();
                data.put("test",test);
                data.put("sc_id", sc_id);
                data.put("stu_id",stu_id );
                data.put("cid",class_id );
                data.put("tst_id",tst_id );
                Log.d("PPP", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000,3,1));
        Volley.newRequestQueue(TestActivity.this).add(request);
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
        public void onFinish()
        {
            txttimer.setText("Done...!");
            saveData();
            try
            {
                unregisterReceiver(receiver);
            }
            catch (Exception e)
            {

            }

        }

    }
    private void SendRequest()
    {
        Common.progressDialogShow(this);
        String Webserviceurl=Common.GetWebServiceURL()+"testquestion.php";
        //String Webserviceurl="https://www.zocarro.com/zocarro_mobile_app/ws/testquestion.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        final String stu_id = mPrefs.getString("stu_id","none");
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try
                {

                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                    Common.progressDialogDismiss(TestActivity.this);
                    Log.d("aaa",response);
                    testQuestionArrayList.clear();
                    JSONArray array=new JSONArray(response);
                    int total=array.getJSONObject(0).getInt("total");

                    if(total==0)
                    {
                        Toast.makeText(TestActivity.this,"No Question",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        txtquestion.setText(""+total);
                        mDataset=new String[array.length()-1];
                        for(int i=1;i<array.length();i++)
                        {                            /*  {
                            "qid": "QST1",
                                "question": "Test Question ?",
                                "a": "A",
                                "b": "B",
                                "c": "C",
                                "d": "D"
                        },*/
                            JSONObject object=array.getJSONObject(i);
                            testQuestionArrayList.add(new TestQuestion(object.getString("qid"),
                                    object.getString("question"),
                                    object.getString("a"),
                                    object.getString("b"),
                                    object.getString("c")
                                    ,object.getString("d"),
                                    "Not Set",object.getString("q_img"),
                                    object.getString("a_img"),
                                    object.getString("b_img")
                                    ,object.getString("c_img"),
                                    object.getString("d_img"),
                                    false,
                                    tst_id,object.getString("subject")));
                        }
                        for(int j=0;j<testQuestionArrayList.size();j++)
                        {
                            if(dataflags==false)
                            {

                                if (mydb.inserttest(testQuestionArrayList.get(j).getTst_id(), testQuestionArrayList.get(j).getQ_id(), testQuestionArrayList.get(j).getQuestion(),
                                        testQuestionArrayList.get(j).getA(), testQuestionArrayList.get(j).getB(), testQuestionArrayList.get(j).getC(), testQuestionArrayList.get(j).getD(), "Not Set", 0,
                                        testQuestionArrayList.get(j).getSub()))
                                {

                                }
                            }
                        }
                        for (int k = 0; k <testQuestionArrayList.size(); k++) {
                            tabLayout.addTab(tabLayout.newTab());
                            TextView tv=(TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_view,null);
                            tv.setTextColor(Color.parseColor("#FFFFFF"));
                            TestActivity.tabLayout.getTabAt(k).setCustomView(tv).setText("" + (k+1));
                        }
                        adapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
                        viewPager.setAdapter(adapter);
                        viewPager.setOffscreenPageLimit(1);
                        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                viewPager.setCurrentItem(tab.getPosition());
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TestActivity.this, ""+error, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id",sc_id);
                data.put("cid",class_id);
                data.put("tst_id",tst_id);
                Log.d("data", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(3000,3,1));
        Volley.newRequestQueue(TestActivity.this).add(request);
    }

    public void onDestroy(){
        super.onDestroy();
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
//        super.onPause();
        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        super.onPause();
        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        super.onPause();
        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkInternet();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkInternet();
    }

    private void allocatememory() {
        txttimer=findViewById(R.id.txttimer);
        Btnsubmit=findViewById(R.id.toolbar_overflow_menu_button);
        //     btnabort=findViewById(R.id.toolbar_overflow_abort_button);
        txtquestion=findViewById(R.id.txtquestion);
        txttotal=findViewById(R.id.txttotal);
        txttotalunanswered=findViewById(R.id.txttotalunanswered);
        txtunanswered=findViewById(R.id.txtunanswered);
        txtposmark=findViewById(R.id.txtposmark);
        txtnegmark=findViewById(R.id.txtnegmark);
        txtsub=findViewById(R.id.txtsub);
       /* LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true);
        recteststart.setLayoutManager(layoutManager);*/
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestActivity.this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to Abort test?..");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        timer.cancel();
                        startActivity(new Intent(TestActivity.this,ViewTestToday.class));
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

    private void getAnswerBefore(final String tst_id) {
        Common.progressDialogShow(this);
        String Webserviceurl=Common.GetWebServiceURL()+"getAnswergivenBefore.php";
        //String Webserviceurl="https://www.zocarro.com/zocarro_mobile_app/ws/testquestion.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        final String stu_id = mPrefs.getString("stu_id","none");
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                testQuestionArrayList.clear();
                try
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                    Common.progressDialogDismiss(TestActivity.this);
                    Log.d("aaa",response);
                    JSONArray array=new JSONArray(response);
                    int total=array.getJSONObject(0).getInt("total");
                    if(total==0)
                    {
                        Toast.makeText(TestActivity.this,"No Question",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        txtquestion.setText(""+total);
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
                            testQuestionArrayList.add(new TestQuestion(object.getString("qid"),object.getString("question"),object.getString("a"),object.getString("b"),
                                    object.getString("c"),object.getString("d"),object.getString("ans"),object.getString("q_img"),object.getString("a_img"),
                                    object.getString("b_img")
                                    ,object.getString("c_img"),object.getString("d_img"),object.getBoolean("mark"),tst_id,object.getString("subject")));
                        }
                       /* for(int j=0;j<testQuestionArrayList.size();j++) {
                            if(dataflags==false) {
                                if (mydb.inserttest(testQuestionArrayList.get(j).getTst_id(), testQuestionArrayList.get(j).getQ_id(), testQuestionArrayList.get(j).getQuestion(),
                                        testQuestionArrayList.get(j).getA(), testQuestionArrayList.get(j).getB(), testQuestionArrayList.get(j).getC(), testQuestionArrayList.get(j).getD(), "Not Set", 0,
                                        testQuestionArrayList.get(j).getSub())) {

                                }
                            }
                        }*/
                        for (int k = 0; k <testQuestionArrayList.size(); k++) {
                            tabLayout.addTab(tabLayout.newTab());
                            TextView tv=(TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_view,null);
                            tv.setTextColor(Color.parseColor("#FFFFFF"));
                            tabLayout.getTabAt(k).setCustomView(tv).setText("" + (k+1));
                        }
                        adapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
                        viewPager.setAdapter(adapter);
                        viewPager.setOffscreenPageLimit(1);
                        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onTabSelected(TabLayout.Tab tab)
                            {
                                viewPager.setCurrentItem(tab.getPosition());
                            }
                            @Override
                            public void onTabUnselected(TabLayout.Tab tab)
                            {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab)
                            {

                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(TestActivity.this, ""+error, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id",sc_id);
                data.put("cid",class_id);
                data.put("tst_id",tst_id);
                data.put("stu_id",stu_id);
                Log.d("data", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(3000,3,1));
        Volley.newRequestQueue(TestActivity.this).add(request);
    }

    public void checkInternet()
    {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver(this);
        registerReceiver(receiver, filter);
        bl = receiver.is_connected();
        Log.d("Boolean ", bl.toString());
    }

    /*private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh, menu);
        MenuItem menuItem  = menu.findItem(R.id.notification).setEnabled(true);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.notification:
                Intent intent = new Intent(TestActivity.this, TestActivity.class);
                intent.putExtra("tst_id", tst_id);
                intent.putExtra("sub", sub);
                intent.putExtra("hours", hours);
                intent.putExtra("min", min);
                intent.putExtra("pos", pos);
                intent.putExtra("neg", neg);
                intent.putExtra("type", type);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
