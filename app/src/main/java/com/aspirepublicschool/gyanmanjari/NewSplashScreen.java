package com.aspirepublicschool.gyanmanjari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.PayTM.DuePaymentActivity;
import com.bumptech.glide.Glide;

import org.apache.http.ContentTooLongException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewSplashScreen extends AppCompatActivity {

    ImageView imgsplash;
    //    TextView txtaspire;
    String token ,s_id, sc_id;
    int login;
    String currentDateTime, status, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_splash_screen);

        getSupportActionBar().hide();

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NewSplashScreen.this);
        login = sharedPreferences.getInt("login",0);
        token = sharedPreferences.getString("token","none");
        number = sharedPreferences.getString("number","none");
        s_id = sharedPreferences.getString("stu_id","none");
        sc_id = sharedPreferences.getString("sc_id","SCIDN1");

        Log.d("111",""+login);
        imgsplash = findViewById(R.id.imgsplash);
//        txtaspire = findViewById(R.id.txtaspire);

        SendRequest();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentDateTime = dateFormat.format(new Date()); // Find todays date

    }

    private void SendRequest()
    {
        if(token.equals("none")){

            Glide.with(NewSplashScreen.this).load(R.drawable.logo).into(imgsplash);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                        Intent is = new Intent(NewSplashScreen.this , OTPLogin.class);
                        is.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(is);
                        finish();
                }
            },2000);

            Animation myanim = AnimationUtils.loadAnimation(NewSplashScreen.this,R.anim.mysplashanimation);
            myanim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    Animation bounce = AnimationUtils.loadAnimation(NewSplashScreen.this,R.anim.bounce);
//                            txtaspire.startAnimation(bounce);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            imgsplash.startAnimation(myanim);


        }
        else{
            //String Webserviceurl="http://www.zocarro.net/zocarro_mobile_app/ws/splashscreenimage.php";
            String Webserviceurl=Common.GetWebServiceURL()+"newsplashscreenimagenew.php";
            StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {
                    try {
                        Log.d("aaa", response);
                        JSONArray array = new JSONArray(response);

                        if(array.getJSONObject(0).getString("message").equals("Loggedout")){
                            status = "Loggedout";
                            Glide.with(NewSplashScreen.this).load(R.drawable.logo).into(imgsplash);
                        }
//                    else if (array.getJSONObject(1).getString("total").equals("1"))
//                    {
//                        imgsplash.setImageResource(R.mipmap.ic_launcher_round);
//                    }
                        else {
                            status = array.getJSONObject(0).getString("message");
                            Toast.makeText(getApplicationContext(), "status", Toast.LENGTH_SHORT).show();
                            for (int i = 2; i < array.length(); i++)
                            {

                                JSONObject object = array.getJSONObject(i);
                                String url = "https://mrawideveloper.com/houseofknowledge.net/zocarro/image/"+"splash/" + object.getString("sc_img");
//                            String url = "https://mrawideveloper.com/gyanmanfarividyapith.net/zocarro/image/splash/1649231510354.png";
                                Glide.with(NewSplashScreen.this).load(url).into(imgsplash);
                            }
                        }

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (status.equalsIgnoreCase("LoggedIn")){

                                    Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
                                checkPaymentStatus();

//                                    Intent is = new Intent(NewSplashScreen.this , MainActivity.class);
//                                    is.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(is);
//                                    finish();
                                }else{

//                                    Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();


                                    Intent is = new Intent(NewSplashScreen.this , OTPLogin.class);
                                    is.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(is);
                                    finish();
                                }

                            }
                        },2000);
                        Animation myanim = AnimationUtils.loadAnimation(NewSplashScreen.this,R.anim.mysplashanimation);
                        myanim.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                Animation bounce = AnimationUtils.loadAnimation(NewSplashScreen.this,R.anim.bounce);
//                            txtaspire.startAnimation(bounce);
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
                        Toast.makeText(NewSplashScreen.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        Toast.makeText(NewSplashScreen.this, "e.getMessage()", Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(NewSplashScreen.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> data=new HashMap<>();
                    data.put("sc_id", sc_id);
                    data.put("token", token);
                    data.put("stu_id", s_id);
                    data.put("number", number);
                    return data;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
            Volley.newRequestQueue(NewSplashScreen.this).add(request);
        }

    }

    private void checkPaymentStatus() {

        final String Webserviceurl = Common.GetWebServiceURL() + "checkFeeStatus.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("aaa", response);
                    JSONArray array = new JSONArray(response);

                    String status = array.getJSONObject(0).getString("status");
                    if (status.equals("continue")) {
                       startActivity(new Intent(getApplicationContext(), MainActivity.class).
                               setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                       finish();
                    } else {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class).
                                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewSplashScreen.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("sc_id", sc_id);
                data.put("stu_id", s_id);
                data.put("number", number);
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(NewSplashScreen.this).add(request);
    }

    private void Logoutactivedevice() {
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String s_id = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id", "none");
        final String Webserviceurl = Common.GetWebServiceURL() + "Logoutdeviceid.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("aaa", response);
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
                        Intent i = new Intent(NewSplashScreen.this, Login.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(NewSplashScreen.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewSplashScreen.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();

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
        Volley.newRequestQueue(NewSplashScreen.this).add(request);
    }

}