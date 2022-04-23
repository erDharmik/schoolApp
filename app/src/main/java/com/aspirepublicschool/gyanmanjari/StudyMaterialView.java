package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aspirepublicschool.gyanmanjari.R;

public class StudyMaterialView extends AppCompatActivity {

    WebView webView;
    ProgressDialog pDialog;
    String doc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_material_view);
        doc=getIntent().getExtras().getString("doc");
        webView=findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        pDialog = new ProgressDialog(StudyMaterialView.this);
        pDialog.setTitle("PDF");
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        //String url = "http://docs.google.com/gview?embedded=true&url="+doc;
        String url = "https://www.google.com";
        Log.d("url", url);

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

            }
        });

    }
}
