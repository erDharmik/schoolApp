package com.aspirepublicschool.gyanmanjari.ImageToPdf;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.aspirepublicschool.gyanmanjari.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import im.delight.android.webview.AdvancedWebView;

public class ImageToPdf extends AppCompatActivity implements AdvancedWebView.Listener  {
    private static final int REQUEST_CODE = 111;
    private AdvancedWebView mWebView;
    String stu_id,sc_id,cid;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_to_pdf);
        mWebView = (AdvancedWebView) findViewById(R.id.webview);
        pd = new ProgressDialog(ImageToPdf.this);
        pd.setMessage("Loading website........");
        mWebView.setListener(this, this);
        mWebView.setGeolocationEnabled(true);
        mWebView.getSettings().setAllowFileAccess(false);
        mWebView.setMixedContentAllowed(false);
        mWebView.setDesktopMode(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sc_id = mPrefs.getString("sc_id", "none").toUpperCase();
        cid = mPrefs.getString("class_id", "none").toUpperCase();
        stu_id = mPrefs.getString("stu_id", "none").toUpperCase();
        Log.d("stu_id", stu_id);
        String url="https://gyanmanjarividyapith.net/zocarro_mobile_app/imagetopdf/index.php?user_id="+stu_id;
        Log.d("url", url);
        mWebView.loadUrl(url);
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                AdvancedWebView newWebView = new AdvancedWebView(ImageToPdf.this);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();

                return true;
            }
        });
    }
    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) { return; }
        // ...
        super.onBackPressed();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
       /* pd.setCancelable(false);
        pd.show();*/
    }

    @Override
    public void onPageFinished(String url) {
       /* if (pd.isShowing()) {
            pd.dismiss();
        }*/
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) { }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(url));

        request.allowScanningByMediaScanner();
        String fileName = URLUtil.guessFileName(url,contentDisposition,mimeType);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        String file=stu_id+"-"+date+".pdf";

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,file);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(request);
        Toast.makeText(getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onExternalPageRequest(String url) { }
}
