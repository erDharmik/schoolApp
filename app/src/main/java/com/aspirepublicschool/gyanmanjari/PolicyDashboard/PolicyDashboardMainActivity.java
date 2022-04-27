package com.aspirepublicschool.gyanmanjari.PolicyDashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.JSONParser;
import com.aspirepublicschool.gyanmanjari.MainActivity;
import com.aspirepublicschool.gyanmanjari.NewRegister.DemoRequestDashboard.demoActivity;
import com.aspirepublicschool.gyanmanjari.NewRegister.NewTimePrefActivity;
import com.aspirepublicschool.gyanmanjari.OTPVerificationActivity;
import com.aspirepublicschool.gyanmanjari.PayTM.DuePaymentActivity;
import com.aspirepublicschool.gyanmanjari.PayTM.PaymentUtils;
import com.aspirepublicschool.gyanmanjari.PayTM.checkSumActivity;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.Common;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PolicyDashboardMainActivity extends AppCompatActivity implements PaytmPaymentTransactionCallback {

    TextView board, medium, standard, expertise, email, contact ,education, policyPrice, nameFaculty;
    Spinner policySpinner;
    ArrayList<String> policyList = new ArrayList<>();
    ArrayList<String> policyIdList = new ArrayList<>();
    ArrayList<String> policyPriceList = new ArrayList<>();
    ArrayList<String> materialFeeList = new ArrayList<>();
    Button paymentBtn;
    Button demoBtn;
    float monthlyprice;
    RecyclerView recView;
    RadioGroup policyRadio;
    LinearLayout lnrSpinner;
    String token, finalpid;

    String smed, sboard, sstd, sstream, stimeslot, sgroup;

    TextView infotxt;
    TextView mediumtxt;
    TextView standardtxt;
    TextView boardtxt ;
    TextView subjecttxt ;
    Button dialogClose;

    LinearLayout lnrPrice;

    CheckBox materialFee;
    TextView materialFeeText, timetxt;
    String materialFeeString, finalmon, price, policyId;
    Dialog dialog;
    String mons;
    int tempmon;
    Spinner months;
    TextView totalfee;
    Button continueBtn;
    String number;
    String[] monthsdata = {"1","2", "3","4", "5","6","7","8","9","10","11","12"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilicy_dashboard_main);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        number = mPrefs.getString("number", "none");

        final SharedPreferences notification = PreferenceManager.getDefaultSharedPreferences(PolicyDashboardMainActivity.this);
        token = notification.getString("regid", "none");

        board = findViewById(R.id.board);
        medium = findViewById(R.id.medium);
        standard = findViewById(R.id.standard);

        infotxt = findViewById(R.id.infotxt);
        lnrPrice = findViewById(R.id.pricelnr);
        policySpinner = findViewById(R.id.policySpinner);
        policyRadio = findViewById(R.id.policyRadioGroup);
        paymentBtn = findViewById(R.id.paymentBtn);
        demoBtn = findViewById(R.id.demoBtn);
        policyPrice = findViewById(R.id.policyPrice);
        lnrSpinner = findViewById(R.id.lnrSpinner);
        lnrSpinner.setVisibility(LinearLayout.GONE);

        lnrPrice.setVisibility(View.INVISIBLE);
//        loadPolicyData();

        loadStudentPolicywithDetails();

        policySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int position = policySpinner.getSelectedItemPosition();

                policyId = policyIdList.get(position);
                monthlyprice = Float.parseFloat(policyPriceList.get(position));
                finalpid = policyIdList.get(position);
                String temp = "INR " + policyPriceList.get(position) + "/month";
                materialFeeString = materialFeeList.get(position);
                Toast.makeText(getApplicationContext(), materialFeeString, Toast.LENGTH_SHORT).show();
                policyPrice.setText(temp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        demoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demoRequestAdd();
            }
        });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                totalfeedialog();
            }
        });

        infotxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(PolicyDashboardMainActivity.this);
                dialog.setContentView(R.layout.selected_info_dialog);
//                dialog.getWindow().setLayout(800, 1200);
                dialog.show();

                mediumtxt = dialog.findViewById(R.id.mediumtxt);
                standardtxt = dialog.findViewById(R.id.standardtxt);
                boardtxt = dialog.findViewById(R.id.boardtxt);
                subjecttxt = dialog.findViewById(R.id.subjecttxt);
                dialogClose =dialog.findViewById(R.id.dialogClose);
                timetxt = dialog.findViewById(R.id.timetxt);

                dialogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                mediumtxt.setText(smed);
                boardtxt.setText(sboard);
                timetxt.setText(stimeslot);

                if (sstream.contains("Select") || sstream.contains("set") || sstream.contains("Set")){
                    standardtxt.setText(sstd);
                }else if (sgroup.contains("Select") || sgroup.contains("set") || sgroup.contains("Set")){
                    standardtxt.setText(sstd +" "+ sstream);
                }else{
                    standardtxt.setText(sstd +" "+ sstream + " " + sgroup);
                }

            }
        });

        policyRadio.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override

                    // The flow will come here when
                    // any of the radio buttons in the radioGroup
                    // has been clicked

                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId)
                    {
                        RadioButton solo = (RadioButton) policyRadio.findViewById(R.id.solo);
                        RadioButton groupB = (RadioButton) policyRadio.findViewById(R.id.group);
                        switch(checkedId){
                            case R.id.solo:
                                lnrSpinner.setVisibility(LinearLayout.GONE);
                                try{
                                    int position = policyList.indexOf("1");

                                    float temp = Float.parseFloat(policyPriceList.get(position));
                                    finalpid = policyIdList.get(Integer.parseInt(policyPriceList.get(position)));
                                    monthlyprice = temp;
                                    String finalSinglePrice = "INR " +String.valueOf(temp) + "/month";
                                    policyPrice.setText(finalSinglePrice);
                                }catch (Exception e){
                                    Toast.makeText(getApplicationContext(), "Retry", Toast.LENGTH_SHORT).show();
                                }
//                                Toast.makeText(getApplicationContext(), "Solo Req. ", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.group:
//                                Toast.makeText(getApplicationContext(), "Group Req.  ", Toast.LENGTH_SHORT).show();
                                lnrSpinner.setVisibility(LinearLayout.VISIBLE);
                                String item = policySpinner.getSelectedItem().toString();
                                int position = policyList.indexOf(item);
                                float temp = Float.parseFloat(policyPriceList.get(position));
                                finalpid = policyIdList.get(position);
                                String finalSinglePrice = "INR " +String.valueOf(temp) + "/month";
//                                Toast.makeText(getApplicationContext(),
//                                        String.valueOf(temp) + " " +String.valueOf(position) + " "+ finalSinglePrice, Toast.LENGTH_SHORT).show();
                                policyPrice.setText(finalSinglePrice);
                                break;
                        }
                    }
                });
    }

    private void demoRequestAdd() {
        String HOMEWORK_URL = Common.GetWebServiceURL()+"demoRequest.php";
        Log.v("LINK",HOMEWORK_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOMEWORK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("###",response );
//                    conductedTestList.clear();
                    JSONArray array = new JSONArray(response);

                    String message = array.getJSONObject(0).getString("status");

                    if (message.equals("true")){
                        String status = "demo";
                        Toast.makeText(getApplicationContext(), "Demo Request has been submitted successfully", Toast.LENGTH_SHORT).show();
                        sendNotification();
                        sendTextMessage();

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PolicyDashboardMainActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("status",status);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("status", status);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),R.string.no_connection_toast,Toast.LENGTH_LONG).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("number", number);
                params.put("pid", finalpid);
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }

    public void sendNotification(){
        String notification = Common.GetWebServiceURL() + "demoNotification.php";

        StringRequest request = new StringRequest(Request.Method.POST, notification, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        })
            {
                @Override

                protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();

                param.put("token", token);


                return param;
            }
        };


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


    }

    public void sendTextMessage(){
        String notification = Common.GetWebServiceURL() + "demoTextMessage.php";

        StringRequest request = new StringRequest(Request.Method.POST, notification, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();

                param.put("cno", number);
                return param;
            }
        };


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


    }



    private void loadStudentPolicywithDetails() {
        String HOMEWORK_URL = Common.GetWebServiceURL() + "policySelection.php";
        Log.v("LINK",HOMEWORK_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOMEWORK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("###",response);

                    JSONArray array = new JSONArray(response);

                    String message = array.getJSONObject(0).getString("message");
                    int total = Integer.parseInt(array.getJSONObject(1).getString("total"));

                    if (message.equals("true")){

                        smed = array.getJSONObject(2).getString("med");
                        sboard = array.getJSONObject(3).getString("board");
                        sstd = array.getJSONObject(4).getString("std");
                        sstream = array.getJSONObject(5).getString("stream");
                        sgroup = array.getJSONObject(6).getString("group");
                        stimeslot = array.getJSONObject(7).getString("timeslot");

                        medium.setText(smed);
                        board.setText(sboard);
                        standard.setText(sstd);

                        if (total!=0){

                            for (int i = 8; i < array.length() ; i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                policyList.add(jsonObject.getString("stu_policy"));
                                policyIdList.add(jsonObject.getString("pid"));
                                policyPriceList.add(jsonObject.getString("fees"));
                                materialFeeList.add(jsonObject.getString("material_fee"));

                                policySpinner.setAdapter(new ArrayAdapter<String>(PolicyDashboardMainActivity.this, android.R.layout.simple_spinner_dropdown_item, policyList));
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "No Teaching Details Available for Your Preferences", Toast.LENGTH_SHORT).show();
                        }

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),R.string.no_connection_toast,Toast.LENGTH_LONG).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("number", number);
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }

    private void totalfeedialog() {
        final float tempfee = monthlyprice;

        dialog=new Dialog(PolicyDashboardMainActivity.this);
        dialog.setContentView(R.layout.fee_row_dialog);
        dialog.show();

        materialFee = dialog.findViewById(R.id.materialFee);
        materialFeeText = dialog.findViewById(R.id.materialFeeText);
        months = dialog.findViewById(R.id.months);
        totalfee = dialog.findViewById(R.id.totalFee);
        continueBtn = dialog.findViewById(R.id.continueBtn);

        materialFeeText.setText(materialFeeString);

        materialFee.setChecked(true);
        float totalfees = tempfee * tempmon;
        float newTemp = + Float.parseFloat(materialFeeString);
        float totalofmaterial = totalfees + newTemp;
        totalfee.setText(String.valueOf(totalofmaterial));


        materialFee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (materialFee.isChecked()){
                    float totalfees = tempfee * tempmon;
                    float newTemp = Float.parseFloat(materialFeeString);
                    float totalofmaterial = totalfees + newTemp;
                    totalfee.setText(String.valueOf(totalofmaterial));
                }else{
                    float totalfees = Float.parseFloat(totalfee.getText().toString().trim());
                    float newTemp = Float.parseFloat(materialFeeString);
                    float totalofmaterial = totalfees - newTemp;
                    String matFee = String.valueOf(new DecimalFormat("##.#").format(totalofmaterial));
                    totalfee.setText(matFee);
                }
            }
        });

        ArrayAdapter<String> mon = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, monthsdata);
        mon.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        months.setAdapter(mon);

        months.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mons =  months.getSelectedItem().toString();
                tempmon = Integer.parseInt(mons);

                float totalfees;
                if (materialFee.isChecked()){
                    float mat = Float.parseFloat(materialFeeString);
                     totalfees = tempfee * tempmon + mat;
                }else{
                    totalfees = tempfee * tempmon;
                }
                String temp = String.valueOf(totalfees);
                totalfee.setText(temp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentMethod();
            }
        });
    }

    private void runPaymentMethod() {
        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void PaymentMethod() {


        finalmon = String.valueOf(months.getSelectedItem());
        price = totalfee.getText().toString();
        runPaymentMethod();


        long time= System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        String title = board.getText().toString() + " " + medium.getText().toString() + " " + standard.getText().toString()+ sdf;
        String transactionId = String.valueOf(time);
        String orderId = "ORDS" + date + time;
        String billId = date + number;
        String courseDesc = "";
        String price = totalfee.getText().toString();
        String oldprice = "";


//        Intent intent = new Intent(PolicyDashboardMainActivity.this, checkSumActivity.class);
//        intent.putExtra("courseTitle", title);
//        intent.putExtra("transaction_id", transactionId);
//        intent.putExtra("order_id", orderId);
//        intent.putExtra("bill_id", billId);
//        intent.putExtra("courseDesc", courseDesc);
//        intent.putExtra("price", price);
//        intent.putExtra("oldprice", oldprice);
//        intent.putExtra("month", mons);
//        intent.putExtra("board", board.getText().toString());
//        intent.putExtra("medium", medium.getText().toString());
//        intent.putExtra("std", standard.getText().toString());
//        intent.putExtra("number", number);
//        startActivity(intent);



    }

//    private void loadPolicyData() {
//        String HOMEWORK_URL = Common.GetWebServiceURL()+"teacher/policyDetails.php";
//        Log.v("LINK",HOMEWORK_URL);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOMEWORK_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    Log.d("###",response );
////                    conductedTestList.clear();
//                    JSONArray array = new JSONArray(response);
//
//                    int teacherCount = Integer.parseInt(array.getJSONObject(1).getString("teacherCount"));
//                    int policyCount = Integer.parseInt(array.getJSONObject(2).getString("policyCount"));
//
//                    board.setText(array.getJSONObject(3).getString("board"));
//                    medium.setText(array.getJSONObject(3).getString("med"));
//                    standard.setText(array.getJSONObject(3).getString("std"));
//
////                    for (int i = 3; i<3 + teacherCount; i++ ){
////                        JSONObject jsonObject = array.getJSONObject(i);
//
////                        policyTeacherList.add(new policyTeacherModel(
////                                jsonObject.getString("id"),
////                                jsonObject.getString("t_fname"),
////                                jsonObject.getString("t_lname"),
////                                jsonObject.getString("t_cont"),
////                                jsonObject.getString("t_email"),
////                                jsonObject.getString("t_spsubject"),
////                                jsonObject.getString("t_study"),
////                                jsonObject.getString("t_img")));
////
////                        // loadTeacherData(t_id);s
////                        policyTeacherAdapter adapter = new policyTeacherAdapter(getApplicationContext(), policyTeacherList);
////                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
////                        recView.setLayoutManager(mLayoutManager);
////                        recView.addItemDecoration(new DividerItemDecoration(recView.getContext(), DividerItemDecoration.VERTICAL));
//////                                rechomework.setItemAnimator(new DefaultItemAnimator());
////                        recView.setAdapter(adapter);
////
////                        adapter.notifyDataSetChanged();
////                    }
//
////                    Toast.makeText(getApplicationContext(),array.getJSONObject(5).getString("stu_policy") , Toast.LENGTH_SHORT).show();
//
//
//                    for (int i = 4 + teacherCount; i <= 3 + teacherCount + policyCount ; i++){
//                        JSONObject jsonObject = array.getJSONObject(i);
//                        policyList.add(jsonObject.getString("stu_policy"));
//                        policyIdList.add(jsonObject.getString("pid"));
//                        policyPriceList.add(jsonObject.getString("fees"));
//                        materialFeeList.add(jsonObject.getString("material_fee"));
//                        policySpinner.setAdapter(new ArrayAdapter<String>(PolicyDashboardMainActivity.this, android.R.layout.simple_spinner_dropdown_item, policyList));
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(),R.string.no_connection_toast,Toast.LENGTH_LONG).show();
//            }
//
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                return params;
//            }
//        };
//        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
//
//    }

    @Override
    public void onTransactionResponse(Bundle bundle) {
        {
            Log.e("checksum ", " respon true " + bundle.toString());
            String payment_mode = bundle.getString("PAYMENTMODE");
            String Res_msg = bundle.getString("RESPMSG");
            String ORDERID = bundle.getString("ORDERID");
            String BANKNAME = bundle.getString("BANKNAME");
            String TXNDATE = bundle.getString("TXNDATE");
            String TXNIDPAYTM = bundle.getString("TXNID");
            String status = bundle.getString("STATUS");

            if (BANKNAME == null)
            {
                BANKNAME = "UPI";
            }
            if (status.equalsIgnoreCase("TXN_SUCCESS"))
            {
                Log.d("PAYMENTMODE", payment_mode);
                Toast.makeText(PolicyDashboardMainActivity.this, "Payment Successful" + payment_mode, Toast.LENGTH_SHORT).show();
                status = "Success";
                setSuccess(payment_mode, ORDERID, BANKNAME, "Success", TXNDATE, TXNIDPAYTM);
            } else {
                Toast.makeText(PolicyDashboardMainActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
                setSuccess(payment_mode,ORDERID,BANKNAME,"Failed",TXNDATE,TXNIDPAYTM);
            }
        }
    }

    private void setSuccess(final String paymentMode, final String orderid, final String BankName, final String Status, final String time, final String TXNIDPAYTM)
    {
        String Webserviceurl = Common.GetWebServiceURL() + "insertPaidTransaction.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("response", response);

                if(response.equals("true")){
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PolicyDashboardMainActivity.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams () throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("payment_mode", paymentMode);
                data.put("tran_id", orderid);
                data.put("status", Status);
                data.put("time", time);
                data.put("BankName", BankName);
                data.put("TXNIDPAYTM", TXNIDPAYTM);
                data.put("user_id", uid);
                data.put("course_id", policyId);
                data.put("price", price);
                data.put("c_id", number);
                data.put("board", board.getText().toString());
                data.put("medium", medium.getText().toString());
                data.put("standard", standard.getText().toString());
                data.put("month", finalmon);
                Log.d("**********@@@", data.toString());
                return data;
            }
        }

                ;
        request.setRetryPolicy(new

                DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(PolicyDashboardMainActivity.this).

                add(request);
    }


    @Override
    public void networkNotAvailable() {

    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {

    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {

    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {

    }

    @Override
    public void onBackPressedCancelTransaction() {

    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {

    }

    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String>
    {
        String url = Common.GetWebServiceURL() + "paytmallinone/generateChecksum.php";
        String CHECKSUMHASH = "";

        long time= System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        String title = board.getText().toString() + " " + medium.getText().toString() + " " + standard.getText().toString()+ sdf;
        String transactionId = String.valueOf(time);
        String orderId = "ORDS" + date + time;
        String billId = date + number;
        String courseDesc = "";
        String oldprice = "";

        @Override
        protected void onPreExecute()
        {
            Toast.makeText(getApplicationContext(), "Please Wait!!!", Toast.LENGTH_SHORT).show();
        }
        protected String doInBackground(ArrayList<String>... alldata)
        {
            JSONParser jsonParser = new JSONParser(PolicyDashboardMainActivity.this);
            String param = "MID=" + PaymentUtils.MERCHANT_ID_PRODUCTION + "&ORDER_ID=" + orderId + "&CUST_ID=" + number +
                    "&CHANNEL_ID=" + PaymentUtils.CHANNEL_ID + "&TXN_AMOUNT=" + price
                    + "&WEBSITE=" + PaymentUtils.WEBSITE_PRDO +
                    "&CALLBACK_URL=" + PaymentUtils.CALLBACK_URL + "&INDUSTRY_TYPE_ID=" + PaymentUtils.INDUSTRY_TYPE_ID;
            Log.e("CheckSum param >>", param);
            Log.v("Total Amount >>>>", price);
            JSONObject jsonObject = jsonParser.makeHttpRequest(url, "POST", param);


            // Testing

//            JSONParser jsonParser = new JSONParser(checksum.this);
//            String param = "MID=" + PaymentUtils.MERCHANT_ID + "&ORDER_ID=" + order_id + "&CUST_ID=" + uid +
//                    "&CHANNEL_ID=" + PaymentUtils.CHANNEL_ID + "&TXN_AMOUNT=" + price
//                    + "&WEBSITE=" + PaymentUtils.WEBSITE +
//                    "&CALLBACK_URL=" + PaymentUtils.CALLBACK_URL + "&INDUSTRY_TYPE_ID=" + PaymentUtils.INDUSTRY_TYPE_ID;
//            Log.e("CheckSum param >>", param);
//            Log.v("Total Amount >>>>", price);
//            JSONObject jsonObject = jsonParser.makeHttpRequest(url, "POST", param);
            if (jsonObject != null) {
                try {
                    CHECKSUMHASH = jsonObject.has("CHECKSUMHASH") ? jsonObject.getString("CHECKSUMHASH") : "";
                    Log.e("CheckSum result>>", CHECKSUMHASH);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return CHECKSUMHASH;
        }

        @Override
        protected void onPostExecute(String result)
        {
            Log.e(" setup acc ", "  signup result  " + result);

            PaytmPGService Service = PaytmPGService.getProductionService();
//            PaytmPGService Service = PaytmPGService.getProductionService();
            HashMap<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("MID", PaymentUtils.MERCHANT_ID_PRODUCTION); //MID provided by paytm
//            paramMap.put("MID", PaymentUtils.MERCHANT_ID); //MID provided by paytm
            paramMap.put("ORDER_ID", orderId);
            paramMap.put("CUST_ID", number);
            paramMap.put("CHANNEL_ID", PaymentUtils.CHANNEL_ID);
            paramMap.put("TXN_AMOUNT", price);
            paramMap.put("WEBSITE", PaymentUtils.WEBSITE_PRDO);
//            paramMap.put("WEBSITE", PaymentUtils.WEBSITE);
            paramMap.put("CALLBACK_URL", PaymentUtils.CALLBACK_URL);
            paramMap.put("CHECKSUMHASH", CHECKSUMHASH);
            paramMap.put("INDUSTRY_TYPE_ID", PaymentUtils.INDUSTRY_TYPE_ID);
            PaytmOrder Order = new PaytmOrder(paramMap);
            Log.e("checksum^&^&", "param " + paramMap.toString());
            Service.initialize(Order, null);
            // start payment service call here
            Service.startPaymentTransaction(PolicyDashboardMainActivity.this, true, true,
                    PolicyDashboardMainActivity.this);
        }
    }

}