package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {

    ImageView imgsplash;
    TextView txtaspire;
    String token ,stu_id;
    int login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
            login=sharedPreferences.getInt("login",0);
            token=sharedPreferences.getString("token","none");
            stu_id=sharedPreferences.getString("stu_id","none");
            Log.d("111",""+login);
            imgsplash = findViewById(R.id.imgsplash);
            txtaspire = findViewById(R.id.txtaspire);
            SendRequest();
    }

    private void SendRequest()
    {
        Common.progressDialogShow(SplashScreen.this);
        //String Webserviceurl="http://www.zocarro.net/zocarro_mobile_app/ws/splashscreenimage.php";
        String Webserviceurl=Common.GetWebServiceURL()+"splashscreenimagenew.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Common.progressDialogDismiss(SplashScreen.this);
                try {
                    Log.d("aaa", response);
                    JSONArray array = new JSONArray(response);
                    if(array.getJSONObject(0).getString("message").equals("Logout")){
//                        Common.Logoutactivedevice(SplashScreen.this);
                        Logoutactivedevice();
                    }
                    else if (array.getJSONObject(1).getString("total").equals("1"))
                    {
                        imgsplash.setImageResource(R.drawable.gm);
                    } else {
                        for (int i = 2; i < array.length(); i++)
                        {
                            JSONObject object = array.getJSONObject(i);
                            String url = "https://mrawideveloper.com/gyanmanfarividyapith.net/zocarro/image/"+"splash/" + object.getString("sc_img");
                            Glide.with(SplashScreen.this).load(url).into(imgsplash);
                        }
                    }

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent is = new Intent(SplashScreen.this , Login.class);
                            startActivity(is);
                            finish();
                        }
                    },2000);
                    Animation myanim = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.mysplashanimation);
                    myanim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            Animation bounce = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.bounce);
                            txtaspire.startAnimation(bounce);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    imgsplash.startAnimation(myanim);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SplashScreen.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(SplashScreen.this);
                Toast.makeText(SplashScreen.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id", "SCIDN20");
                if(stu_id.equals("none")){
                    data.put("token", "First Time");
                    data.put("stu_id", "First Time");
                } else{
                    data.put("token", token);
                    data.put("stu_id", stu_id);
                }
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(SplashScreen.this).add(request);
    }
    private void Logoutactivedevice() {
        Common.progressDialogShow(SplashScreen.this);
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String s_id = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id", "none");
        final String Webserviceurl = Common.GetWebServiceURL() + "Logoutdeviceid.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("aaa", response);
                    Common.progressDialogDismiss(SplashScreen.this);
                    JSONObject object = new JSONObject(response);
                    String messsage = object.getString("message");
                    if (messsage.equals("Submitted")) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("stu_id");
                        editor.remove("status");
                        editor.remove("class_id");
                        editor.remove("stu_name");
                        editor.remove("std");
                        editor.remove("sc_id");
                        editor.remove("st_dp");
                        editor.remove("city");
                        editor.remove("address");
                        editor.remove("mobile");
                        editor.remove("token");
                        editor.commit();
                        Intent i = new Intent(SplashScreen.this, Login.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(SplashScreen.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SplashScreen.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("sc_id", sc_id);
                data.put("stu_id", s_id);
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(SplashScreen.this).add(request);
    }

}
