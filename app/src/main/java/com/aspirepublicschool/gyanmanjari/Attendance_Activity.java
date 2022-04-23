package com.aspirepublicschool.gyanmanjari;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Attendance_Activity extends AppCompatActivity {
/*CalendarView calendarView;
Calendar calendar;*/
Button btn_attendance_report;
LinearLayout linearLayout;
   /* PieChart pieChart;
    List<PieEntry> pieEntries = new ArrayList<>();*/
    int present,absent;
    final int[] pieColors = {
            Color.rgb(0,199,0), Color.rgb(199,0,0)
    };
String date;
int year,month,day;
List<AttendanceModel> attendanceModelList = new ArrayList<>();
//List<EventDay> eventDays = new ArrayList<>();
//MCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);


        if (CheckNetwork.isInternetAvailable(getApplicationContext())) {
            loadAttendanceStatus();
        }
        else {
            Intent i = new Intent(Attendance_Activity.this, NoInternetActivity.class);
            startActivity(i);
            finish();
        }

   //   loadAttendanceStatus();
      linearLayout = findViewById(R.id.linear);

        Random rnd = new Random();
        int currentColor = Color.argb(90, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        linearLayout.setBackgroundColor(currentColor);

        // calendarView = ((MCalendarView) findViewById(R.id.attendance_calender));
        Random rnd1 = new Random();
      /*  int calenderColor = Color.argb(50, rnd.nextInt(256), rnd1.nextInt(256), rnd1.nextInt(256));
            calendarView.setBackgroundColor(calenderColor);*/
        //btn_attendance_report = findViewById(R.id.btn_attendance_report);
        // calendarView.markDate(new DateData(2019, 12, 6).setMarkStyle(new MarkStyle(MarkStyle.DEFAULT, Color.GREEN)));
     //   MCalendarView calendarView = ((MCalendarView) findViewById(R.id.attendance_calender));
        //calendarView.markDate(new DateData(2019, 12, 5).setMarkStyle(new MarkStyle(MarkStyle.DEFAULT, Color.RED)));
     /*  btn_attendance_report.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             *//*  Intent intent = new Intent(Attendance_Activity.this,Attendance_Graph.class);
               intent.putExtra("present",present);
               intent.putExtra("absent",absent);
               startActivity(intent);*//*

               AlertDialog.Builder mBuilder = new AlertDialog.Builder(Attendance_Activity.this);
               View mView = getLayoutInflater().inflate(R.layout.activity_progress_dialog, null);
               final PieChart pieChart = mView.findViewById(R.id.piechart);
               pieEntries.clear();
               pieEntries.add(new PieEntry(present,"Present"));
               pieEntries.add(new PieEntry(absent,"Absent"));

               pieChart.animateXY(5000,5000);
               PieDataSet pieDataSet = new PieDataSet(pieEntries,"Attendance Graph in days");
               pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
               pieDataSet.setColors(pieColors);
               PieData pieData = new PieData(pieDataSet);

               pieChart.setData(pieData);
               Description description = new Description();
               description.setText("Attendance Graph in days");
               pieChart.setDescription(description);
               pieChart.invalidate();
               //     photoView.setImageResource(Glide.with(mCtx).load(URL_IMG_ANNOUNCEMENT).into(announcementHolder.img));
               mBuilder.setView(mView);
               final AlertDialog mDialog = mBuilder.create();
               Button btn_close=mView.findViewById(R.id.btn_close);
               btn_close.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       mDialog.dismiss();
                   }
               });
               mDialog.show();

           }
       });*/

    }

    private void loadAttendanceStatus() {
        Common.progressDialogShow(this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String stu_id = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id","none");
        //Log.v("STU_ID",stu_id);
       // String ATT_URL ="http://192.168.0.108:8080/school/ws/displayAttendance.php";
       String ATT_URL = Common.GetWebServiceURL()+"displayAttendance.php";
        Log.v("&&&",ATT_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ATT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(Attendance_Activity.this);
                    JSONArray array = new JSONArray(response);
                    for (int i=0;i<=array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        attendanceModelList.add(new AttendanceModel(object.getString("attstatus"),
                                object.getString("date")
                        ));
                        AttendanceModel modelClass = new AttendanceModel(object.getString("attstatus"),object.getString("date"));
                     //   Log.v("DATA", modelClass.getDate());
                        if (modelClass.getStatus().equals("absent")){
                            absent++;
                          //  pieEntries.add(new PieEntry(absent,modelClass.getStatus()));
                           // Log.v("A_COUNT",String.valueOf(absent));
                            //Log.v("ABSENT",modelClass.getDate());
                            String date = modelClass.getDate();
                            int Y = Integer.parseInt(date.substring(6,10));
                            //Log.v("YEAR",String.valueOf(Y));
                            int M = Integer.parseInt(date.substring(3,5));
                            //Log.v("MONTH", String.valueOf(M));
                            int D = Integer.parseInt(date.substring(0,2));
                           // Log.v("DDDD", String.valueOf(D));
                            //calendarView.markDate(new DateData(Y, M, D).setMarkStyle(new MarkStyle(MarkStyle.DEFAULT, Color.RED)));
                        }
                        if (modelClass.getStatus().equals("present")){
                            present++;
                          //  pieEntries.add(new PieEntry(present,modelClass.getStatus()));
                            //Log.v("P_COUNT",String.valueOf(present));
                            //Log.v("PRESENT",modelClass.getDate());
                            String date = modelClass.getDate();
                            int Y = Integer.parseInt(date.substring(6,10));
                            //Log.v("P_YEAR",String.valueOf(Y));
                            int M = Integer.parseInt(date.substring(3,5));
                            //Log.v("P_MONTH", String.valueOf(M));
                            int D = Integer.parseInt(date.substring(0,2));
                            //Log.v("P_DDDD", String.valueOf(D));
                            //calendarView.markDate(new DateData(Y, M, D).setMarkStyle(new MarkStyle(MarkStyle.DEFAULT, Color.rgb(0,181,36))));
                        }
                        //txt_absent.setText(array.getJSONObject(i+1).getString("status"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Attendance_Activity.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("stu_id",stu_id);
                params.put("sc_id",sc_id);
                return params;
            }
        };
        Volley.newRequestQueue(Attendance_Activity.this).add(stringRequest);
    }
}
