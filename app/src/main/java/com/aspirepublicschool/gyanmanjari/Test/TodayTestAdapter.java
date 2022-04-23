package com.aspirepublicschool.gyanmanjari.Test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TodayTestAdapter extends RecyclerView.Adapter {

    Context ctx;
    ArrayList<TodayTest> testArrayList;
    int days,hours,min;
    String totalTimeString,mins;
    String pos,neg;

    public TodayTestAdapter(Context ctx, ArrayList<TodayTest> testArrayList) {
        this.ctx = ctx;
        this.testArrayList = testArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyView = LayoutInflater.from(ctx).inflate(R.layout.activity_test_row, null);
        MyWidgetContainer container = new MyWidgetContainer(MyView);
        return container;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TodayTest test= testArrayList.get(position);
        final MyWidgetContainer container = (MyWidgetContainer) holder;
        container.txtsub.setText(test.getSubject());
        container.txtstime.setText(Html.fromHtml(test.getStime()));
        container.txtetime.setText(Html.fromHtml(test.getEtime()));
        container.txttotal.setText(Html.fromHtml(test.getTotal()));
        container.txtdate.setText(test.getT_date());
        container.txtpos.setText(test.getPos());
        container.txtneg.setText(test.getNeg());
        container.txttype.setText(test.getT_type());


        container.btnattempt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(ctx,TestWeb.class);
                ctx.startActivity(intent);


            }


        });

        pos=test.getPos();
        neg=test.getNeg();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date startDate = null;
        try {
            String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            startDate = simpleDateFormat.parse(currentTime);
            Date endDate = simpleDateFormat.parse(test.getEtime());

          /*  long difference = endDate.getTime() - startDate.getTime();
            if(difference<0)
            {
                Date dateMax = simpleDateFormat.parse("24:00");
                Date dateMin = simpleDateFormat.parse("00:00");
                difference=(dateMax.getTime() -startDate.getTime() )+(endDate.getTime()-dateMin.getTime());
            }
            days = (int) (difference / (1000*60*60*24));
            hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
            min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
            Log.i("log_tag","Hours: "+hours+", Mins: "+min);
            totalTimeString = String.format("%02d", hours);
            mins=String.format("%02d",min);*/

            try {
                startDate = simpleDateFormat.parse(currentTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                endDate = simpleDateFormat.parse(test.getEtime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long difference = endDate.getTime() - startDate.getTime();
            days = (int) (difference / (1000 * 60 * 60 * 24));
            hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours))
                    / (1000 * 60);
            hours = (hours < 0 ? -hours : hours);
            Log.i("log_tag","Hours: "+hours+", Mins: "+min);
            totalTimeString = String.format("%02d", hours);
            mins=String.format("%02d",min);

            Log.i("log_tag","Hours: "+hours+", Mins: "+mins);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        container.cardtest.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                String timeSettings = android.provider.Settings.Global.getString(
                        ctx.getContentResolver(),
                        android.provider.Settings.Global.AUTO_TIME);
                if (timeSettings.contentEquals("0")) {
                    Log.d("Set", "True");
             /*   android.provider.Settings.Global.putString(
                        this.getContentResolver(),
                        android.provider.Settings.Global.AUTO_TIME, "1");*/
                    Toast.makeText(ctx, "Sorry date and time changed.Please changed in automatic to continue with test.", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Log.d("Set", "False");
                    checkTestGiven(test.getTst_id(),test.getStime(),test.getEtime(),test.getTotal(),test.getSubject(),test.getDes(), test.getT_type());

                }

            }
        });


        container.relassignment.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {


                String timeSettings = android.provider.Settings.Global.getString(
                        ctx.getContentResolver(),
                        android.provider.Settings.Global.AUTO_TIME);
                if (timeSettings.contentEquals("0")) {
                    Log.d("Set", "True");
             /*   android.provider.Settings.Global.putString(
                        this.getContentResolver(),
                        android.provider.Settings.Global.AUTO_TIME, "1");*/
                    Toast.makeText(ctx, "Sorry date and time changed.Please changed in automatic to countinue with test.", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Log.d("Set", "False");
                    checkTestGiven(test.getTst_id(),test.getStime(),test.getEtime(),test.getTotal(),test.getSubject(),test.getDes(), test.getT_type());

                }



            }
        });


        // attempttest check

        container.btnattempt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String timeSettings = android.provider.Settings.Global.getString(
                        ctx.getContentResolver(),
                        android.provider.Settings.Global.AUTO_TIME);
                if (timeSettings.contentEquals("0")) {
                   // Log.d("Set", "True");
             /*   android.provider.Settings.Global.putString(
                        this.getContentResolver(),
                        android.provider.Settings.Global.AUTO_TIME, "1");*/
                    Toast.makeText(ctx, "Sorry date and time changed.Please changed in automatic to countinue with test.", Toast.LENGTH_LONG).show();

                }
                else
                {
                    //Log.d("Set", "False");
                    checkTestGiven(test.getTst_id(),test.getStime(),test.getEtime(),test.getTotal(),test.getSubject(),test.getDes(), test.getT_type());

                }



                }


        });
    }

    private void checkTestGiven(final String tst_id, final String stime, final String etime, String total, final String subject, final String des, final String t_type) {
        Common.progressDialogShow(ctx);
        String WebServiceurl=Common.GetWebServiceURL()+"checkTestGiven.php";
       // String WebServiceurl="https://www.zocarro.com/zocarro_mobile_app/ws/"+"checkTestGiven.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String sc_id = preferences.getString("sc_id","none").toUpperCase();
        final String stu_id = preferences.getString("stu_id","none").toUpperCase();
        final String class_id = preferences.getString("class_id","none").toUpperCase();
        StringRequest request=new StringRequest(StringRequest.Method.POST,WebServiceurl, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array=new JSONArray(response);
                    Log.d("@@@",response);
                    String message=array.getJSONObject(0).getString("messgae");
                    if(message.equals("Test already given"))
                    {
                        Common.progressDialogDismiss(ctx);
                        Toast.makeText(ctx,"Sorry!!,Test is already submitted!!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        try {
                            Date time1 = new SimpleDateFormat("HH:mm").parse(stime);
                            java.util.Calendar calendar1 = java.util.Calendar.getInstance();
                            calendar1.setTime(time1);
                            calendar1.add(Calendar.DATE, 1);

                            Date time2 = new SimpleDateFormat("HH:mm").parse(etime);
                            java.util.Calendar calendar2 = java.util.Calendar.getInstance();
                            calendar2.setTime(time2);
                            calendar2.add(Calendar.DATE, 1);

                            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                            java.util.Calendar cal = java.util.Calendar.getInstance();
                            String localTime= dateFormat.format(cal.getTime());
                            Log.d("time now", localTime);

                            Date d = new SimpleDateFormat("HH:mm").parse(localTime);
                            java.util.Calendar calendar3 = java.util.Calendar.getInstance();
                            calendar3.setTime(d);
                            calendar3.add(Calendar.DATE, 1);

                            Date x = calendar3.getTime();
                            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                                //checkes whether the current time is between 14:49:00 and 20:11:13.
                                //System.out.println("true");
                                Log.d("!!","TRuE");
                                SendRequest(tst_id,subject,des,t_type);
                            }
                            else
                            {
                                Common.progressDialogDismiss(ctx);
                                Log.d("!!","False");
                                Toast.makeText(ctx,"Sorry!!,Test can be displayed only in given time.",Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                       /* LocalTime start = LocalTime.parse( stime);
                        LocalTime stop = LocalTime.parse( etime );
                        @SuppressLint({"NewApi", "LocalSuppress"}) final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                        final String date = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
                        Log.d("@@@@",date);
                        LocalTime target = LocalTime.parse( date );
                        Boolean isTargetAfterStartAndBeforeStop = ( target.isAfter( start ) && target.isBefore( stop ) ) ;
                        if(isTargetAfterStartAndBeforeStop)
                        {
                            Log.d("!!","TRuE");
                            SendRequest(tst_id,subject,des,t_type);

                        }
                        else {
                            Common.progressDialogDismiss(ctx);
                            Log.d("!!","False");
                            Toast.makeText(ctx,"Sorry!!,Test can be displayed only in given time.",Toast.LENGTH_LONG).show();
                        }*/

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(ctx);
                Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id", sc_id);
                data.put("stu_id",stu_id );
                data.put("cid",class_id );
                data.put("tst_id",tst_id );
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(ctx).add(request);
    }

    private void SendRequest(final String tst_id, final String subject, final String des, final String t_type) {
        String WebServiceurl=Common.GetWebServiceURL()+"test.php";

        // String WebServiceurl="https://www.zocarro.com/zocarro_mobile_app/ws/"+"lt.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String sc_id = preferences.getString("sc_id","none").toUpperCase();
        final String stu_id = preferences.getString("stu_id","none").toUpperCase();
        final String class_id = preferences.getString("class_id","none").toUpperCase();
        StringRequest request = new StringRequest(StringRequest.Method.POST,WebServiceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(ctx);
                Log.d("aaa", response);
                JSONArray array= null;
                try {
                    array = new JSONArray(response);
                    Log.d("@@@test",response);
                    String message=array.getJSONObject(0).getString("message");
                    if(message.equals("Fail"))
                    {
                        Common.progressDialogDismiss(ctx);
                        Toast.makeText(ctx,"Sorry!!,Test is already submitted!!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent intent = new Intent(ctx, TestDescription.class);
                        intent.putExtra("tst_id", tst_id);
                        intent.putExtra("sub", subject);
                        intent.putExtra("hours", totalTimeString);
                        intent.putExtra("min", mins);
                        intent.putExtra("des", des);
                        intent.putExtra("pos", pos);
                        intent.putExtra("neg", neg);
                        intent.putExtra("t_type", t_type);
                        ctx.startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(ctx);
                Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id", sc_id);
                data.put("cid", class_id);
                data.put("stu_id", stu_id);
                data.put("tst_id",tst_id);
                Log.d("datares", data.toString());
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(ctx).add(request);

    }


    @Override
    public int getItemCount() {
        return testArrayList.size();
    }
    class MyWidgetContainer extends RecyclerView.ViewHolder {
        public TextView txtsub,txtstime,txtetime,txttotal,txtdesc,txtdate,txtpos,txtneg,txttype;
        RelativeLayout relassignment;
        CardView cardtest;
        Button btnattempt;
        public MyWidgetContainer(View itemView)
        {
            super(itemView);
            txtsub=itemView.findViewById(R.id.txtsub);
            txtstime=itemView.findViewById(R.id.txtstime);
            txtetime=itemView.findViewById(R.id.txtetime);
            txtpos=itemView.findViewById(R.id.txtposmark);
            txtneg=itemView.findViewById(R.id.txtnegmark);
            relassignment=itemView.findViewById(R.id.relassignment);
            txttotal=itemView.findViewById(R.id.txttotal);
            txtdesc=itemView.findViewById(R.id.txtdesc);
            txtdate=itemView.findViewById(R.id.txtdate);
            txttype=itemView.findViewById(R.id.txttype);
            cardtest=itemView.findViewById(R.id.cardtest);
            btnattempt=itemView.findViewById(R.id.btnattempt);

        }
    }
}
