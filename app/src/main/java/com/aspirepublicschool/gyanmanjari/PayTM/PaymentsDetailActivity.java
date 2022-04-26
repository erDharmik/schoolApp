package com.aspirepublicschool.gyanmanjari.PayTM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
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
import com.aspirepublicschool.gyanmanjari.R;

import java.util.HashMap;
import java.util.Map;

public class PaymentsDetailActivity extends AppCompatActivity {

    String orderId, tranId, status,number,  time, Bankname, std, month, medium, board, cid, price, txnidPaytm, userId, courseId, mode;
    TextView stdtxt , medtxt , boardtxt, timetxt , amountxt, modetxt, courseIdtxt, statustxt, tranIdtxt, monthtxt;
    TextView timerTxt;
    CountDownTimer cTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_detail);
        Intent i = getIntent();
        tranId = i.getStringExtra("tran_id");
        status = i.getStringExtra("status");
        time = i.getStringExtra("time");
        Bankname = i.getStringExtra("BankName");
        txnidPaytm = i.getStringExtra("TXNIDPAYTM");
        userId = i.getStringExtra("user_id");
        courseId = i.getStringExtra("course_id");
        price = i.getStringExtra("price");
        cid = i.getStringExtra("c_id");
        board = i.getStringExtra("board");
        medium = i.getStringExtra("medium");
        std = i.getStringExtra("standard");
        month = i.getStringExtra("month");
        mode = i.getStringExtra("payment_mode");
        number = i.getStringExtra("number");

        allocateMemory();

        stdtxt.setText(std);
        medtxt.setText(medium);
        boardtxt.setText(board);
        timetxt.setText(time);
        amountxt.setText(price);
        courseIdtxt.setText(courseId);
        tranIdtxt.setText(tranId);
        modetxt.setText(mode);
        statustxt.setText(status);
        monthtxt.setText(month);

        updateStatus();

        startTimer();

    }

    private void updateStatus() {

        String Webserviceurl = Common.GetWebServiceURL() + "paymentTextMessage.php";

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = mPrefs.getString("stu_id", "none");
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("response", response);

                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                if(response.equals("true")) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
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
                data.put("tran_id", tranId);
                data.put("time", time);
                data.put("number", number);
                data.put("user_id", userId);
                data.put("price", price);
                Log.d("**********@@@", data.toString());
                return data;
            }
        }

                ;
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(PaymentsDetailActivity .this).

                add(request);
    }

    private void allocateMemory() {

        stdtxt = findViewById(R.id.std);
        medtxt = findViewById(R.id.medium);
        boardtxt = findViewById(R.id.board);
        timetxt = findViewById(R.id.paytime);
        amountxt = findViewById(R.id.price);
        courseIdtxt = findViewById(R.id.courseId);
        tranIdtxt = findViewById(R.id.txnId);
        modetxt = findViewById(R.id.mode);
        monthtxt = findViewById(R.id.month);
        statustxt = findViewById(R.id.status);
        timerTxt = findViewById(R.id.timerTxt);

    }

    void startTimer() {

        cTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {

                timerTxt.setText(String.valueOf(millisUntilFinished/1000));

            }
            public void onFinish() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        };
        cTimer.start();

    }

}