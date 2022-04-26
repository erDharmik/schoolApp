package com.aspirepublicschool.gyanmanjari.PayTM;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.JSONParser;
import com.aspirepublicschool.gyanmanjari.MainActivity;
import com.aspirepublicschool.gyanmanjari.PolicyDashboard.PolicyDashboardMainActivity;
import com.aspirepublicschool.gyanmanjari.R;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DuePaymentActivity extends AppCompatActivity implements PaytmPaymentTransactionCallback {

    TextView board, medium, standard, subject, feeTxt,duePayment,materialstat;
    String expiryDate, subjectString, materialFeeString,finalmon, monthlyFee, pid;
    String materialFeeStatus, price, class_id, material;

    CheckBox materialFee;
    TextView materialFeeText;
    Dialog dialog;
    String mons;
    int tempmon;
    Spinner months;
    TextView totalfee;
    Button continueBtn;
    String number, stu_id;
    String[] monthsdata = {"1","2", "3","4", "5","6","7","8","9","10","11","12"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due_payment);

        getSupportActionBar().setTitle("Due Payment");

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        number = mPrefs.getString("number", "none");
        stu_id = mPrefs.getString("stu_id", "none").toUpperCase();
        class_id = mPrefs.getString("class_id", "none").toUpperCase();

        allocateMemory();
        loadPolicyData();
        setMonthSpinner();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finalmon = String.valueOf(months.getSelectedItem());
                price = totalfee.getText().toString();
                if (materialFee.isChecked()){
                    material = "material";
                }else{
                    material = "no";
                }

                runPaymentMethod();

            }
        });

        months.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    float monthlyFeeFloat = Float.parseFloat(monthlyFee);
                    float mon = Float.parseFloat(String.valueOf(months.getSelectedItem()));
                    float total = monthlyFeeFloat * mon;

                    String finalTotal = String.valueOf(total);
                    totalfee.setText(finalTotal);

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        materialFee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (materialFeeStatus.equals("0")){
                    if (materialFee.isChecked()){
                        try{
                        float temp = Float.parseFloat(totalfee.getText().toString());
                        float materials = Float.parseFloat(materialFeeString);
                        float total = temp + materials;
                        String finalTotal = String.valueOf(total);
                        totalfee.setText(finalTotal);
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        try{
                            float temp = Float.parseFloat(totalfee.getText().toString());
                            float materials = Float.parseFloat(materialFeeString);
                            float total = temp - materials;
                            String finalTotal = String.valueOf(total);
                            totalfee.setText(finalTotal);
                        }catch (Exception e){
                            Log.e("error", e.getMessage());
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });

    }

    private void runPaymentMethod() {
        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void loadPolicyData() {

        String WebServiceURL = Common.GetWebServiceURL()+"getDuePayment.php";
        Log.v("LINK",WebServiceURL);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebServiceURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("###",response );
//                    conductedTestList.clear();
                    JSONArray array = new JSONArray(response);

                    expiryDate = array.getJSONObject(0).getString("expiry");
                    materialFeeStatus = array.getJSONObject(1).getString("material_fee_status");

                    monthlyFee = array.getJSONObject(2).getString("fees");
                    materialFeeString = array.getJSONObject(2).getString("material_fee");


                    feeTxt.setText("INR " + monthlyFee + "/month");

                    float finalFeeTemp = Float.parseFloat(monthlyFee);
                    totalfee.setText(String.valueOf(finalFeeTemp));
                    duePayment.setText(expiryDate);
                    materialFeeText.setText(materialFeeString);

                    pid = array.getJSONObject(2).getString("pid");

                    board.setText(array.getJSONObject(2).getString("board"));
                    medium.setText(array.getJSONObject(2).getString("med"));
                    standard.setText(array.getJSONObject(2).getString("std"));
                    subject.setText(array.getJSONObject(2).getString("subject"));

                    if (materialFeeStatus.equals("1")){
                        materialFee.setChecked(true);
                        materialFee.setClickable(false);
                        materialstat.setVisibility(View.VISIBLE);
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
                params.put("stu_id", stu_id);
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }

    private void allocateMemory() {

        board = findViewById(R.id.board);
        medium = findViewById(R.id.medium);
        standard = findViewById(R.id.standard);
        feeTxt = findViewById(R.id.feeTxt);
        duePayment = findViewById(R.id.duePayment);

        materialFee = findViewById(R.id.materialFee);
        subject = findViewById(R.id.subject);
        materialFeeText = findViewById(R.id.materialFeeText);
        months = findViewById(R.id.months);
        totalfee = findViewById(R.id.totalFee);
        continueBtn = findViewById(R.id.continueBtn);
        materialstat = findViewById(R.id.materialstat);

        materialstat.setVisibility(View.GONE);
    }

    private void setMonthSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, monthsdata);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        months.setAdapter(adapter);
    }

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
                Toast.makeText(DuePaymentActivity.this, "Payment Successful" + payment_mode, Toast.LENGTH_SHORT).show();
                status = "Success";
//                setSuccess(payment_mode, ORDERID, BANKNAME, "Success", TXNDATE, TXNIDPAYTM);
                nextActivity(payment_mode, ORDERID, BANKNAME, "Success", TXNDATE, TXNIDPAYTM);
                setSuccess(payment_mode, ORDERID, BANKNAME, "Success", TXNDATE, TXNIDPAYTM);
            } else {
                Toast.makeText(DuePaymentActivity.this, "Please Try Again!", Toast.LENGTH_SHORT).show();
            setSuccess(payment_mode,ORDERID,BANKNAME,"Failed",TXNDATE,TXNIDPAYTM);
            }
        }
    }

    private void nextActivity(final String paymentMode, final String orderid, final String BankName, final String Status, final String time, final String TXNIDPAYTM)
    {

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("stu_id", "none");

        Intent intent = new Intent(getApplicationContext(), PaymentsDetailActivity.class);
        intent.putExtra("payment_mode", paymentMode);
        intent.putExtra("tran_id", orderid);
        intent.putExtra("status", Status);
        intent.putExtra("time", time);
        intent.putExtra("BankName", BankName);
        intent.putExtra("TXNIDPAYTM", TXNIDPAYTM);
        intent.putExtra("user_id", uid);
        intent.putExtra("course_id", pid);
        intent.putExtra("price", price);
        intent.putExtra("c_id", class_id);
        intent.putExtra("board", board.getText().toString());
        intent.putExtra("medium", medium.getText().toString());
        intent.putExtra("standard", standard.getText().toString());
        intent.putExtra("month", finalmon);
        startActivity(intent);


        finish();

    }

    private void setSuccess(final String paymentMode, final String orderid, final String BankName, final String Status, final String time, final String TXNIDPAYTM)
    {
        String Webserviceurl = Common.GetWebServiceURL() + "insertPaidTransaction.php";

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("stu_id", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("response", response);

                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                if(response.equals("true")) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), PaymentsDetailActivity.class);
                    intent.putExtra("payment_mode", paymentMode);
                    intent.putExtra("tran_id", orderid);
                    intent.putExtra("status", Status);
                    intent.putExtra("time", time);
                    intent.putExtra("BankName", BankName);
                    intent.putExtra("TXNIDPAYTM", TXNIDPAYTM);
                    intent.putExtra("user_id", uid);
                    intent.putExtra("course_id", pid);
                    intent.putExtra("number", number);
                    intent.putExtra("price", price);
                    intent.putExtra("c_id", class_id);
                    intent.putExtra("board", board.getText().toString());
                    intent.putExtra("medium", medium.getText().toString());
                    intent.putExtra("standard", standard.getText().toString());
                    intent.putExtra("month", finalmon);
                    startActivity(intent);
                    finish();

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams () throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                data.put("mode", paymentMode);
                data.put("tran_id", orderid);
                data.put("number", number);
                data.put("status", Status);
                data.put("time", time);
                data.put("BankName", BankName);
                data.put("TXNIDPAYTM", TXNIDPAYTM);
                data.put("user_id", uid);
                data.put("course_id", pid);
                data.put("price", price);
                data.put("material", material);
                data.put("c_id", class_id);
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
        Volley.newRequestQueue(DuePaymentActivity .this).

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
            JSONParser jsonParser = new JSONParser(DuePaymentActivity.this);
            String param = "MID=" + PaymentUtils.MERCHANT_ID_PRODUCTION + "&ORDER_ID=" + orderId + "&CUST_ID=" + stu_id +
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
            paramMap.put("CUST_ID", stu_id);
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
            Service.startPaymentTransaction(DuePaymentActivity.this, true, true,
                    DuePaymentActivity.this);
        }
    }

    @Override
    public void onBackPressed() {

    }
}