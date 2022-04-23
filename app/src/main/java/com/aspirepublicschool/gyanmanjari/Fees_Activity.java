package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Fees_Activity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE =111 ;
    RecyclerView recpayment;
    Button btnpayfees;
    TextView txtprice;
    String feename,duedate;
    String stu_id, sc_id,medium,termdetails;
    int amount, price, total;
    public ArrayList<fees> FeesList = new ArrayList<>();
    ArrayList<PaymentHistory> paymentHistories=new ArrayList<>();
    Context ctx = this;
    int termfees;
    static ExpandableListView recfees;
    ArrayList<FeesTermPojo> expandableListDetail = new ArrayList<FeesTermPojo>();
    static FeesTermPojo feesTermPojo1,feesTermPojo2;
    static fees fees;
    static ArrayList<fees> Terrm1 = new ArrayList<fees>();
    static ArrayList<fees> Term2 = new ArrayList<fees>();
    static FeesExpandableAdapter expandableAdapter;
    DisplayMetrics metrics;
    int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);
        if (ActivityCompat.shouldShowRequestPermissionRationale(Fees_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(Fees_Activity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(Fees_Activity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stu_name = mPrefs.getString("stu_name", "Fees");

        getSupportActionBar().setTitle(stu_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Common.progressDialogShow(this);
        allocatememory();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        stu_id = preferences.getString("stu_id", "none");
        sc_id = preferences.getString("sc_id", "none").toLowerCase();
        medium = preferences.getString("med", "none");
        //SendRequest();
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        recfees.setIndicatorBounds(width - GetDipsFromPixel(50), width - GetDipsFromPixel(10));
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        final String dayOfTheWeek = sdf.format(d);
        Log.v("TTTT",dayOfTheWeek);
        Common.progressDialogShow(ctx);
        String URL_FEES =Common.GetWebServiceURL() + "getfees.php";
        Log.v("URL_TIMETABLE",URL_FEES);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FEES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Common.progressDialogDismiss(Fees_Activity.this);
                        try {
                            //converting the string to json array object
                            Log.v("URL_TIME",response);
                            JSONArray array = new JSONArray(response);
                            //  ac_year.setText(String.valueOf(array));
                            //traversing through all the object
                            Terrm1.clear();
                            Term2.clear();
                            expandableListDetail.clear();
                            String error = array.getJSONObject(0).getString("error");
                            if (error.equals("no error") == false) {
                            }//   Common.ShowDialog(ctx, error);
                            else //no error
                            {
                                int asize = array.length();
                                for (int i = 1; i < asize; i++) {
                                        /*
                                         {

                                    "order_id": "ORDER1",
                                    "sin": "PSIN4",
                                    "img1": "341566541.jpg",
                                    "label": "Abc Color papers",
                                    "price": "10",
                                    "discount": "1"

                                  }
                                  */
                                    JSONObject current = array.getJSONObject(i);
                                    feename = current.getString("feename");
                                    amount = current.getInt("amount");
                                    duedate = current.getString("due_date");
                                    termdetails = current.getString("term");
                                    fees = new fees(feename, amount, duedate, termdetails);


                                    if (fees.getTermdetails().equals("1")) {

                                        Terrm1.add(fees);
                                    }
                                    if (fees.getTermdetails().equals("2")) {
                                        Term2.add(fees);
                                    }

                                }
                            }



                            feesTermPojo1=new FeesTermPojo("Term 1 Fees ",Terrm1);
                            feesTermPojo2=new FeesTermPojo("Term 2 Fees",Term2);
                            expandableListDetail.add(feesTermPojo1);
                            expandableListDetail.add(feesTermPojo2);
                            Log.d("###", expandableListDetail.toString());



                            FeesExpandableAdapter  expandableAdapter=new FeesExpandableAdapter(ctx,expandableListDetail);
                            recfees.setAdapter(expandableAdapter);
                            expandableAdapter.notifyDataSetChanged();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("stu_id",stu_id);
                params.put("sc_id",sc_id);
                Log.d("aaa",params.toString());
                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(ctx).add(stringRequest);




        btnpayfees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "This service is not Available yet", Toast.LENGTH_SHORT).show();
            }
        });
        String Webserviceurl = Common.GetWebServiceURL() + "totalfees.php?stu_id=" + stu_id + "&sc_id=" + sc_id;
        Log.d("abc", Webserviceurl);
        JsonArrayRequest request = new JsonArrayRequest(Webserviceurl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Common.progressDialogDismiss(Fees_Activity.this);
                    Log.d("shubh", response.toString());
                    String error = response.getJSONObject(0).getString("error");
                    if (error.equals("no error") == false) {
                    }//   Common.ShowDialog(ctx, error);
                    else //no error
                    {
                        price = response.getJSONObject(1).getInt("price");

                    }
                    txtprice.setText("" + price + "/-");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Common.ShowDialog(ctx);
            }
        });
        Volley.newRequestQueue(Fees_Activity.this).add(request);
        //SendRequest();
        PaymentHistory();
    }
    public int GetDipsFromPixel(float pixels)
    {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    private void PaymentHistory() {
        String Webserviceurl=Common.GetWebServiceURL()+"viewFeesTransaction.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array=new JSONArray(response);
                    Log.d("shubh", response.toString());
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        paymentHistories.add(new PaymentHistory(object.getString("term"),object.getString("amount"),object.getString("payment_mode"),object.getString("date"),object.getString("reciept_id")));
                    }
                    recpayment.setHasFixedSize(true);
                    recpayment.setLayoutManager(new LinearLayoutManager(ctx));
                    PaymentHistoryAdapter adapter = new PaymentHistoryAdapter(ctx, paymentHistories);
                    recpayment.setItemAnimator(new DefaultItemAnimator());
                    recpayment.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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
                Map<String,String> data=new HashMap<>();
                data.put("sc_id",sc_id);
                data.put("stu_id",stu_id);
                return data;
            }
        };
        Volley.newRequestQueue(ctx).add(request);
    }

    private void SendRequest() {

        String Webserviceurl = Common.GetWebServiceURL() + "getfees.php?stu_id=" + stu_id + "&sc_id=" + sc_id;
        Log.d("abc", Webserviceurl);
        JsonArrayRequest request = new JsonArrayRequest(Webserviceurl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Common.progressDialogDismiss(Fees_Activity.this);
                    Log.d("shubh", response.toString());
                    String error = response.getJSONObject(0).getString("error");
                    if (error.equals("no error") == false) {
                    }//   Common.ShowDialog(ctx, error);
                    else //no error
                    {
                        int asize = response.length();
                        for (int i = 1; i < asize; i++) {
                                        /*
                                         {

                                    "order_id": "ORDER1",
                                    "sin": "PSIN4",
                                    "img1": "341566541.jpg",
                                    "label": "Abc Color papers",
                                    "price": "10",
                                    "discount": "1"

                                  }
                                  */
                            JSONObject current = response.getJSONObject(i);
                            feename = current.getString("feename");
                            amount = current.getInt("amount");
                            duedate=current.getString("due_date");
                            fees f = new fees(feename, amount,duedate,"aaa");
                            FeesList.add(f);

                        }

                       /* recfees.setHasFixedSize(true);
                        recfees.setLayoutManager(new LinearLayoutManager(ctx));
                        FeeAdapter adapter = new FeeAdapter(ctx, FeesList);
                        recfees.setItemAnimator(new DefaultItemAnimator());
                        recfees.setAdapter(adapter);
                        adapter.notifyDataSetChanged();*/
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Common.ShowDialog(ctx);
            }
        });
        Volley.newRequestQueue(Fees_Activity.this).add(request);
    }

    private void allocatememory() {
        recfees = findViewById(R.id.recfees);
        recpayment = findViewById(R.id.recpayment);
        btnpayfees = findViewById(R.id.btnpayfees);
        txtprice = findViewById(R.id.txtprice);
    }
}
