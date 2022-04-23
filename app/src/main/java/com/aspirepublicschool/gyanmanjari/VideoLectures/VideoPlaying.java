package com.aspirepublicschool.gyanmanjari.VideoLectures;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.MediaController;

import androidx.appcompat.app.AppCompatActivity;

import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;

public class VideoPlaying extends AppCompatActivity
{
    WebView webview;
    // VideoView videoView;
    protected FrameLayout mFullscreenContainer;
    String video_name,video_path,subject;
    private ProgressDialog bar;
    private String path= "https://mrawideveloper.com/gyanmanfarividyapith.net/zocarro/video/";
    private MediaController ctlr;
    private View mCustomView;
    private int mOriginalSystemUiVisibility;
    private int mOriginalOrientation;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_video_playing);
        allocatememory();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none").toUpperCase();
        final String med = mPrefs.getString("med", "none");
        video_name=getIntent().getExtras().getString("video_name");
        subject=getIntent().getExtras().getString("subject");
//        video_path=path+sc_id+"/"+med+"/"+subject+"/"+video_name;
//        Log.d("!!!",video_path);
        Log.d("videoname",video_name);
        webview.requestFocus();
        webview.setWebViewClient(new Browser_Home());
        webview.setWebChromeClient(new WebChromeClient());
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
       // webview.setWebChromeClient(new MyChrome());
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);
//       webview.loadUrl(video_name);
        String url = "https://www.youtube.com/watch?v=zDyJTKPlfGA";
        String link = Common.GetWebServiceURL() +"videoplaynew.php?link="+video_name;
        webview.loadUrl(link);
        Log.d("logd",link);

        //comment karavani(webview)
        webview.loadUrl("http://aspirepublicschool.net/zocarro_mobile_app/ws/playvideo.php?sc_id="+sc_id+"&med="+med+"&subject="+subject+"&video_name="+video_name);


        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
        webview.setWebChromeClient(new WebChromeClient()
        {
            public void onProgressChanged(WebView view, int progress)
            {

            }
        });
        webview.setWebChromeClient(new WebChromeClient()
        {
            public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback)
            {
                if (mCustomView != null)
                {
                    onHideCustomView();
                    return;
                }

                mCustomView = view;
                mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
                mOriginalOrientation = getRequestedOrientation();

                mCustomViewCallback = callback;

                FrameLayout decor = (FrameLayout) getWindow().getDecorView();
                decor.addView(mCustomView, new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));


                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_IMMERSIVE);
            }

            @Override
            public void onHideCustomView()
            {
                // 1. Remove the custom view
                FrameLayout decor = (FrameLayout) getWindow().getDecorView();
                decor.removeView(mCustomView);
                mCustomView = null;

                // 2. Restore the state to it's original form
                getWindow().getDecorView()
                        .setSystemUiVisibility(mOriginalSystemUiVisibility);
                setRequestedOrientation(mOriginalOrientation);

                mCustomViewCallback.onCustomViewHidden();
                mCustomViewCallback = null;
            }
        });

        Log.d("!!!",video_name);




    }
   /* @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
*/
    private void allocatememory()
    {
       webview=findViewById(R.id.webview);
        //videoView=findViewById(R.id.vide_view);
    }
   /* @Override
    protected void onResume() {
        if (videoView != null)
            videoView.resume();  // <-- this will cause re-buffer.
        super.onResume();
    }*/
   private class Browser_Home extends WebViewClient
   {
       Browser_Home()
       {
           
       }
       @Override
       public void onPageStarted(WebView view, String url, Bitmap favicon)
       {
           super.onPageStarted(view, url, favicon);
       }
       @Override
       public void onPageFinished(WebView view, String url)
       {
           super.onPageFinished(view, url);
       }
   }
    private class MyChrome extends WebChromeClient
    {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }
}
