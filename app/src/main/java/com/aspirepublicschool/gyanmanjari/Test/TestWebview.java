package com.aspirepublicschool.gyanmanjari.Test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.Locale;

public class TestWebview extends AppCompatActivity {
    WebView webview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_webview);
        webview = findViewById(R.id.testweb);
        webview.getSettings().setJavaScriptEnabled(true);
        Intent intent = getIntent();
        Uri data = intent.getData();
        webview.clearCache(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        String loc = Locale.getDefault().toString();

        webview.loadUrl("http://exam.jmsofttech.com/Login");
//        webview.loadUrl("https://codewithharry.com/");
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setDomStorageEnabled(true);


        /*webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://exam.jmsofttech.com/Login");
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);

        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webview.setWebViewClient(webViewClient);*/


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class WebViewClient extends android.webkit.WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {

            // TODO Auto-generated method stub

            super.onPageFinished(view, url);

        }

    }




}
