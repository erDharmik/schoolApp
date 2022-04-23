package com.aspirepublicschool.gyanmanjari.PayTM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.EmptyActivity;
import com.aspirepublicschool.gyanmanjari.JSONParser;
import com.aspirepublicschool.gyanmanjari.MainActivity;
import com.aspirepublicschool.gyanmanjari.R;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class checkSumActivity extends AppCompatActivity implements PaytmPaymentTransactionCallback {

    ProgressBar pd;
    String status;
    String total = "";
    String courseDesc, price, c_id,number, subject_id, order_id;
    String uid, board, standard, month, medium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_sum);

        pd = findViewById(R.id.progressBar);

        courseDesc = getIntent().getStringExtra("courseDesc");
        price = getIntent().getStringExtra("price");
        order_id = getIntent().getStringExtra("order_id");
        total = getIntent().getStringExtra("price");
        board= getIntent().getStringExtra("board");
        month= getIntent().getStringExtra("month");
        medium = getIntent().getStringExtra("medium");
        standard = getIntent().getStringExtra("standard");
        number = getIntent().getStringExtra("number");

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        number = mPrefs.getString("number", "none");

        total = total + ".00";
        Log.v("@@@", total);
        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String>
    {
        String url = Common.GetWebServiceURL() + "paytmallinone/generateChecksum.php";
        String CHECKSUMHASH = "";

        @Override
        protected void onPreExecute()
        {
            Toast.makeText(getApplicationContext(), "Please Wait!!!", Toast.LENGTH_SHORT).show();
        }
        protected String doInBackground(ArrayList<String>... alldata)
        {
            JSONParser jsonParser = new JSONParser(checkSumActivity.this);
            String param = "MID=" + PaymentUtils.MERCHANT_ID_PRODUCTION + "&ORDER_ID=" + order_id + "&CUST_ID=" + uid +
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
            paramMap.put("ORDER_ID", order_id);
            paramMap.put("CUST_ID", uid);
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
            Service.startPaymentTransaction(checkSumActivity.this, true, true,
                    checkSumActivity.this);
        }
    }

    @Override
    public void onTransactionResponse(Bundle bundle) {
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
            Toast.makeText(checkSumActivity.this, "Payment Successful" + payment_mode, Toast.LENGTH_SHORT).show();
            status = "Success";
            setSuccess(payment_mode, ORDERID, BANKNAME, "Success", TXNDATE, TXNIDPAYTM);
        } else {
            Toast.makeText(checkSumActivity.this, status, Toast.LENGTH_SHORT).show();
//            setSuccess(payment_mode,ORDERID,BANKNAME,"Failed",TXNDATE,TXNIDPAYTM);
        }
    }


    private void setSuccess(final String paymentMode, final String orderid, final String BankName, final String Status, final String time, final String TXNIDPAYTM)
    {
        Common.progressDialogShow(checkSumActivity.this);
        String Webserviceurl = Common.GetWebServiceURL() + "insertPaidTransaction.php";
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("u_id", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(checkSumActivity.this);
                Log.d("response", response);

                if(response.equals("true")){
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(checkSumActivity.this, MainActivity.class));
                    finish();
                }else{

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
                data.put("course_id", subject_id);
                data.put("price", price);
                data.put("discount", courseDesc);
                data.put("c_id", c_id);
                data.put("board", board);
                data.put("medium", medium);
                data.put("standard", standard);
                data.put("month", month);
                Log.d("**********@@@", data.toString());
                return data;
            }
        }

                ;
        request.setRetryPolicy(new

                DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(checkSumActivity .this).

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
}