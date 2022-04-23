package com.aspirepublicschool.gyanmanjari.Test.JEE;

import android.annotation.SuppressLint;
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
import com.aspirepublicschool.gyanmanjari.Test.DBHelper;
import com.aspirepublicschool.gyanmanjari.Test.FinalAnswer;
import com.aspirepublicschool.gyanmanjari.Test.NEET.Neet;
import com.aspirepublicschool.gyanmanjari.Test.TestActivity;
import com.aspirepublicschool.gyanmanjari.Test.TestAnswer;
import com.aspirepublicschool.gyanmanjari.Test.TestQuestion;
import com.aspirepublicschool.gyanmanjari.Test.TestTimer;
import com.aspirepublicschool.gyanmanjari.Test.ViewTestToday;
import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Jee extends AppCompatActivity {

    Button physics, chemistry, maths, abortButton, submitButton;
    TextView textViewSubject, textViewQuestion, textViewPositiveMarks, textViewNegetiveMarks,textViewTimer;
    static ArrayList<TestQuestion> testQuestionArrayList=new ArrayList<>();
    ArrayList<FinalAnswer> finalAnswers=new ArrayList<>();
    String tst_id,sub;
    String[] mDataset;
    int hours,min;
    int duration,sec;
    long result;
    public static int ansques=0;
    public static int unanswered=0;
    DBHelper mydb ;
    Context ctx=this;
    static boolean dataflags=false;
    Handler handler;
    pyCountDown timer,countDownTimeronResume;
    String time,pos,neg,type;
    static TabLayout tabLayout;
    ViewPager viewPager;
    PhysicsAdapter adapter;
    int physicpage=0,chemistrypage=0,mathspage=0,totalpage=0;
    int nextpallet1=0,nextpallet2=0;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_jee);

        AlloateMemory();
        tst_id=getIntent().getExtras().getString("tst_id");
        sub=getIntent().getExtras().getString("sub");
//        hours=Integer.parseInt(getIntent().getExtras().getString("hours"));
//        min=Integer.parseInt(getIntent().getExtras().getString("min"));
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
        mydb = new DBHelper(ctx);
        textViewTimer.setText(textViewTimer.getText() + String.valueOf(result));
        textViewPositiveMarks.setText(pos);
        textViewNegetiveMarks.setText(neg);
        textViewSubject.setText(sub);
        Cursor rs = mydb.getData(tst_id);
        if (rs.moveToFirst())
        {
            // record exists
            /*Toast.makeText(getApplicationContext(), "Data Exists",
                    Toast.LENGTH_SHORT).show();*/
            Log.d("TEST", "true");
            dataflags=true;
            getAnswerBefore(tst_id);
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
            Log.d("TEST", "false");
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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Jee.this);
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
//        abortButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Jee.this);
//                alertDialogBuilder.setMessage("Are you sure, You wanted to Abort test?..");
//                alertDialogBuilder.setPositiveButton("Yes",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface arg0, int arg1) {
//
//                                timer.cancel();
//                                startActivity(new Intent(Jee.this, ViewTestToday.class));
//                                finish();
//
//
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
        physics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPage(0);
            }
        });
        chemistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextpallet1=physicpage+mathspage;
                selectPage(nextpallet1);
            }
        });
        maths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextpallet2=physicpage;
                selectPage(nextpallet2);

            }
        });
    }

    private void getAnswerBefore(final String tst_id) {
        Common.progressDialogShow(Jee.this);
        String Webserviceurl=Common.GetWebServiceURL()+"getAnswergivenBefore.php";
        //String Webserviceurl="https://www.zocarro.com/zocarro_mobile_app/ws/testquestion.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        final String stu_id = mPrefs.getString("stu_id","none");
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                    Common.progressDialogDismiss(Jee.this);

                    Log.d("aaa",response);
                    testQuestionArrayList.clear();
                    JSONArray array=new JSONArray(response);
                    int total=array.getJSONObject(0).getInt("total");
                    if(total==0)
                    {
                        Toast.makeText(Jee.this,"No Question",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        textViewQuestion.setText(""+total);
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
                            if(object.getString("subject").equals("Physics"))
                            {
                                physicpage++;
                            }
                            else if(object.getString("subject").equals("Chemistry"))
                            {
                                chemistrypage++;
                            }
                            else
                            {
                                mathspage++;
                            }
                            totalpage++;
                            /*Log.d("physics", ""+physicpage);
                            Log.d("chemistry", ""+chemistrypage);
                            Log.d("maths", ""+mathspage );*/
                            testQuestionArrayList.add(new TestQuestion(object.getString("qid"),object.getString("question"),object.getString("a"),object.getString("b"),
                                    object.getString("c"),object.getString("d"),object.getString("ans"),object.getString("q_img"),object.getString("a_img"),object.getString("b_img")
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
                        adapter = new PhysicsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
                        viewPager.setAdapter(adapter);
                        viewPager.setOffscreenPageLimit(1);
                        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                            @SuppressLint("ResourceAsColor")
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
                Common.progressDialogDismiss(Jee.this);
                Toast.makeText(Jee.this, ""+error, Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
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
        Volley.newRequestQueue(Jee.this).add(request);
    }

    void selectPage(int pageIndex){
        Log.d("selct page", ""+pageIndex);
        tabLayout.setScrollPosition(pageIndex,0f,true);
        viewPager.setCurrentItem(pageIndex);
    }
    private void AlloateMemory(){
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        physics = findViewById(R.id.physics);
        chemistry = findViewById(R.id.chemistry);
        maths = findViewById(R.id.maths);
//        abortButton = findViewById(R.id.abortButton);
        submitButton = findViewById(R.id.submitButton);
        
        textViewSubject = findViewById(R.id.textViewSubject);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewPositiveMarks = findViewById(R.id.textViewPositiveMarks);
        textViewNegetiveMarks = findViewById(R.id.textViewNegetiveMarks);
        textViewTimer = findViewById(R.id.textViewTimer);

    }
    private void saveData() {
        Common.progressDialogShow(Jee.this);
        finalAnswers.clear();
        ArrayList<TestAnswer> answers = mydb.getAllTestData(tst_id);
        final String test = new Gson().toJson(answers);
        Log.d("test",test);
        mydb.close();
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
                Common.progressDialogDismiss(Jee.this);

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("fail")) {
                        Toast.makeText(Jee.this, "Sorry! Test is not Submitted",Toast.LENGTH_LONG).show();

                    }
                    else if(object.getString("message").equals("Success"))
                    {
                        Toast.makeText(Jee.this,"Test Submitted Successfully" ,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(Jee.this, MainActivity.class);
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
                Common.progressDialogDismiss(Jee.this);
                Log.d("mess", error.toString());
                Toast.makeText(Jee.this,error.toString() ,Toast.LENGTH_LONG).show();

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
         request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       /* request.setRetryPolicy(new DefaultRetryPolicy(5000,3,1));*/
        Volley.newRequestQueue(Jee.this).add(request);
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
            textViewTimer.setText("Time remaining: " + time);
            if(mydb.updateContact(tst_id, String.valueOf(millisUntilFinished)))
            {
                /*Toast.makeText(getApplicationContext(), "Update",
                        Toast.LENGTH_SHORT).show();*/
            }

        }

        @Override
        public void onFinish() {

            textViewTimer.setText("Done...!");
            saveData();
        }

    }
    private void SendRequest() {
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
                try {

                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                    Common.progressDialogDismiss(Jee.this);

                    Log.d("aaa",response);
                    testQuestionArrayList.clear();
                    JSONArray array=new JSONArray(response);
                    int total=array.getJSONObject(0).getInt("total");
                    if(total==0)
                    {
                        Toast.makeText(Jee.this,"No Question",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        textViewQuestion.setText(""+total);
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
                            if(object.getString("subject").equals("Physics"))
                            {
                                physicpage++;
                            }
                            else if(object.getString("subject").equals("Chemistry"))
                            {
                                chemistrypage++;
                            }
                            else
                            {
                                mathspage++;
                            }
                            totalpage++;
                            Log.d("physics", ""+physicpage);
                            Log.d("chemistry", ""+chemistrypage);
                            Log.d("maths", ""+mathspage );
                            testQuestionArrayList.add(new TestQuestion(object.getString("qid"),object.getString("question"),object.getString("a"),object.getString("b"),
                                    object.getString("c"),object.getString("d"),"Not Set",object.getString("q_img"),object.getString("a_img"),object.getString("b_img")
                                    ,object.getString("c_img"),object.getString("d_img"),false,tst_id,object.getString("subject")));
                        }
                        for(int j=0;j<testQuestionArrayList.size();j++) {
                            if(dataflags==false) {
                                if (mydb.inserttest(testQuestionArrayList.get(j).getTst_id(), testQuestionArrayList.get(j).getQ_id(), testQuestionArrayList.get(j).getQuestion(),
                                        testQuestionArrayList.get(j).getA(), testQuestionArrayList.get(j).getB(), testQuestionArrayList.get(j).getC(), testQuestionArrayList.get(j).getD(), "Not Set", 0,
                                        testQuestionArrayList.get(j).getSub())) {

                                }
                            }
                        }
                        for (int k = 0; k <testQuestionArrayList.size(); k++) {
                            tabLayout.addTab(tabLayout.newTab());
                            TextView tv=(TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_view,null);
                            tv.setTextColor(Color.parseColor("#FFFFFF"));
                            tabLayout.getTabAt(k).setCustomView(tv).setText("" + (k+1));
                        }
                        adapter = new PhysicsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
                        viewPager.setAdapter(adapter);
                        viewPager.setOffscreenPageLimit(1);
                        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                            @SuppressLint("ResourceAsColor")
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
                Toast.makeText(Jee.this, ""+error, Toast.LENGTH_LONG).show();

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
        Volley.newRequestQueue(Jee.this).add(request);
    }
    public void onDestroy(){
        super.onDestroy();
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Jee.this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to Abort test?..");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        timer.cancel();
                        startActivity(new Intent(Jee.this,ViewTestToday.class));
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
                Intent intent = new Intent(Jee.this, Jee.class);
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
