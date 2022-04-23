package com.aspirepublicschool.gyanmanjari.Payment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;

import java.util.HashMap;
import java.util.Map;

public class PayTMActivity extends AppCompatActivity {

    WebView webview;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_tmactivity);
        webview = findViewById(R.id.paymentwebview);
//        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
//        final String sc_id = mPrefs.getString("sc_id", "none").toUpperCase();
//        final String cid = mPrefs.getString("class_id", "none").toUpperCase();
//        final String stu_id = mPrefs.getString("stu_id", "none").toUpperCase();

        String url = "https://tuition.zocarro.in/web/zocarro/student/payment/RedirectM.php?StuID=SCIDN2STIDN1fg";

        webview.loadUrl(url);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());



    }
}
