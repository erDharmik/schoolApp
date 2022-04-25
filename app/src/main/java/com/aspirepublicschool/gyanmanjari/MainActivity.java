package com.aspirepublicschool.gyanmanjari;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Doubt.DoubtSubject;
import com.aspirepublicschool.gyanmanjari.DoubtSolving.ChatDoubt;
import com.aspirepublicschool.gyanmanjari.DoubtSolving.Utils;
import com.aspirepublicschool.gyanmanjari.Homework.HomeworkActivity;
import com.aspirepublicschool.gyanmanjari.NewTest.NewTestTab;
import com.aspirepublicschool.gyanmanjari.NewTest.ViewNewTestToday;
import com.aspirepublicschool.gyanmanjari.Payment.PayTMActivity;
import com.aspirepublicschool.gyanmanjari.Profile.ProfileMainActivity;
import com.aspirepublicschool.gyanmanjari.Result.ClassResult;
import com.aspirepublicschool.gyanmanjari.Test.ViewTestToday;
import com.aspirepublicschool.gyanmanjari.VideoLectures.VideoTabbed;
import com.aspirepublicschool.gyanmanjari.WRT_Test.WRTResult;
import com.aspirepublicschool.gyanmanjari.WRT_Test.WRT_TEST;
import com.aspirepublicschool.gyanmanjari.internet.NetworkChangeReceiver;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    Context ctx = this;
    private static final String CHANNEL_ID = "101";

    DrawerLayout drawer;
    NavigationView navigationView;
    BottomNavigationView navigation;
    SharedPreferences preferences;
    ArrayList<student_profileModel> student_profileModelList = new ArrayList();
    String stu_id, classid, name, std, standard, number;
    Toolbar toolbar;
    String image_url;
    String DP_URL;
    String s_id = null;
    String sc_id = null;
    String user;
    private static final int REQUEST_CODE = 111;
    private String SD_YeaR, SD_MontH, SD_DaY, SD_HouR, SD_MinutE;
    ArrayList<Remainder> remainders = new ArrayList<>();
    static Timer timer;
    TimerTask timerTask;
    String[] perms =
            {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.CALL_PHONE
            };
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trimCache(ctx);

        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        s_id = mPrefs.getString("stu_id", "none");
        Log.d("s_id", s_id);
        sc_id = mPrefs.getString("sc_id", "none");
        number = mPrefs.getString("number", "none");

        getToken();



//        getToken();
//        createnotificationchannel();
//        repeat();
        //loadStudentData();

        if (s_id.equalsIgnoreCase("none") && sc_id.equalsIgnoreCase("none"))
        {
            loadStudentData();
        }
//       SendAppVersion();
        //FirebaseInstanceId.getInstance().getToken();
//        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        Toast.makeText(getApplicationContext(), number + " " + stu_id + " " + sc_id, Toast.LENGTH_SHORT).show();

       /*  ActivityCompat.requestPermissions(
                MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE}, REQUEST_CODE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }



        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            for (int i = 0; i < perms.length; i++) {
                if (ContextCompat.checkSelfPermission(this, perms[i]) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, perms, REQUEST_CODE);
                    break;
                }
            }

        } else {
            // Implement this feature without material design
        }
        //SendRequest();

        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        image_url = preferences.getString("st_dp", "null");
        final Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_arrow_drop_down);
        toolbar.setOverflowIcon(drawable);
        setSupportActionBar(toolbar);
        loadFragment(new HomeFragment());
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        String name1 = preferences.getString("stu_name", "User");
        String std = preferences.getString("std", "Standard");
        //String image_url=preferences.getString("st_dp","null");
        Log.d("nnnn", image_url);


        //loadStudentData();
        RelativeLayout linearLayout = headerview.findViewById(R.id.lnrprofile);
        TextView username = headerview.findViewById(R.id.txtnameprofile);
        TextView id = headerview.findViewById(R.id.txtemail);
        CircleImageView imgurl = headerview.findViewById(R.id.imgnavprofile);
        try {
            Glide.with(MainActivity.this).load(new URL(image_url)).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(imgurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        username.setText(name1);
        id.setText(std);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 31/03/2022
//                 startActivity(new Intent(MainActivity.this, StudentProfile.class));
                Intent intent = new Intent(ctx, ProfileMainActivity.class);
                intent.putExtra("number", number);
                intent.putExtra("stu_id", s_id);
                intent.putExtra("sc_id", sc_id);
                startActivity(intent);
                //drawer.closeDrawer(GravityCompat.START);
            }
        });
        navigationView.setItemIconTintList(null);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().findItem(R.id.navigation_home).setChecked(true);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void getToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Gyan", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        final String token = task.getResult().getToken(); //return firebase id

//                        sendRegistrationToServer(token);

                        //FirebaseMessaging.getInstance().subscribeToTopic("global");
//                        FirebaseInstanceId.getInstance().getToken();


                        Log.d("Gyan","firebase regid (token) " + token);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("regid",token);
                        editor.commit();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            CharSequence name = "firebaseNotificationnChannel";
                            String description = "Received notification channel";
                            int importance = NotificationManager.IMPORTANCE_DEFAULT;
                            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                            channel.setDescription(description);
                            // Register the channel with the system; you can't change the importance
                            // or other notification behaviors after this
                            NotificationManager notificationManager = getSystemService(NotificationManager.class);
                            notificationManager.createNotificationChannel(channel);
                        }


                    }
                });

    }


//    private void getToken() {
//
//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
//            @Override
//            public void onComplete(@NonNull Task<String> task) {
//                if (!task.isSuccessful()) {
//                    Log.d("push notification", "failed to get token");
//                }
//                String token = task.getResult();
//                Log.d("token", token);
//
////                String Webserviceurl = Common.GetWebServiceURL() + "updateFirebasetoken.php";
//////                    String Webserviceurl ="http://livebookss.com/videobook/App/ws/updateFirebasetoken.php";
////                final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
////                final String user_id = mPrefs.getString("u_id", "none");
////                StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
////                    @Override
////                    public void onResponse(String response) {
////                        Log.d(TAG, response);
////                    }
////                }, new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        error.printStackTrace();
////
////                    }
////                }) {
////                    @Override
////                    protected Map<String, String> getParams() throws AuthFailureError {
////                        Map<String, String> data = new HashMap<>();
////                        data.put("token", token);
////                        data.put("user_id", user_id);
////                        Log.d(TAG, "getParams: " + data);
////                        return data;
////                    }
////
////                };
////                request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
////                Volley.newRequestQueue(getApplicationContext()).add(request);
//            }
//        });
//    }

//    private void createnotificationchannel(){
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "firebaseNotificationnChannel";
//            String description = "Received notification channel";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//}

    private void repeat() {
        timer = new Timer();
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {

                    try {
                        if (Utils.isNetworkAvailable(MainActivity.this)) {
                            Common.checkvalidlogin(MainActivity.this);
                        } else {
                            Toast.makeText(getBaseContext(), "No network available", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        }
        timer.scheduleAtFixedRate(timerTask, 5000, 2400000);
    }


    private void loadStudentData() {
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        s_id = mPrefs.getString("stu_id", "none");
        sc_id = mPrefs.getString("sc_id", "none");
        String STUDENT_PROFILE_URL = Common.GetWebServiceURL() + "student_profile.php";
        Log.v("profile", STUDENT_PROFILE_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, STUDENT_PROFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Common.progressDialogDismiss(MainActivity.this);
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject jsonObject = array.getJSONObject(i);

                        student_profileModelList.add(new student_profileModel(
                                jsonObject.getInt("id"),
                                jsonObject.getString("stu_id"),
                                jsonObject.getString("roll_no"),
                                jsonObject.getString("stu_img"),
                                jsonObject.getString("f_img"),
                                jsonObject.getString("m_img"),
                                jsonObject.getString("gr_no"),
                                jsonObject.getString("st_sname"),
                                jsonObject.getString("st_fname"),
                                jsonObject.getString("f_name"),
                                jsonObject.getString("m_name"),
                                jsonObject.getString("st_cno"),
                                jsonObject.getString("f_cno"),
                                jsonObject.getString("m_cno"),
                                jsonObject.getString("st_email"),
                                jsonObject.getString("cid"),
                                jsonObject.getString("gender"),
                                jsonObject.getString("med"),
                                jsonObject.getString("board"),
                                jsonObject.getString("address"),
                                jsonObject.getString("city"),
                                jsonObject.getString("state"),
                                jsonObject.getString("dob"),
                                jsonObject.getString("nation"),
                                jsonObject.getString("stream"),
                                jsonObject.getString("cast"),
                                jsonObject.getString("std"),
                                jsonObject.getString("division")
                        ));
                        name = jsonObject.getString("st_sname") + " " + jsonObject.getString("st_fname");
                        Log.v("name", String.valueOf(name));

                        stu_id = jsonObject.getString("stu_id");
                        std = jsonObject.getString("std") + "-" + jsonObject.getString("division");
                        standard = jsonObject.getString("std");
                        DP_URL = "https://gyanmanjarividyapith.net/zocarro/image/" + "student/" + student_profileModelList.get(i).getStu_img();
                        Log.d("URL", DP_URL);
                        classid = jsonObject.getString("cid");

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("class_id", classid);
                        editor.putString("stu_name", name);
                        editor.putString("std", std);
                        editor.putString("standard", standard);
                        editor.putString("st_dp", DP_URL);


                        editor.commit();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Common.progressDialogDismiss(MainActivity.this);
                Toast.makeText(MainActivity.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("stu_id", s_id);
                params.put("sc_id", sc_id);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(ctx).add(stringRequest);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Intent intent;
        if (id == R.id.nav_submission) {
            intent = new Intent(ctx, HomeworkActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_homework) {
            intent = new Intent(ctx, HomeWorkAssignment.class);
            startActivity(intent);
        } else if (id == R.id.nav_timetable) {
            intent = new Intent(ctx, DailyTimeTable.class);
            startActivity(intent);
        } else if (id == R.id.nav_exam) {
            intent = new Intent(ctx, ClassResult.class);
            startActivity(intent);
        } else if (id == R.id.nav_noticeboard) {
            intent = new Intent(ctx, Announcement.class);
            startActivity(intent);
        } else if (id == R.id.nav_chat) {
            intent = new Intent(ctx, ChatDoubt.class);
            startActivity(intent);
            //Toast.makeText(MainActivity.this,"Feedback session is not started by admin.!!", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_staff) {
            intent = new Intent(ctx, StaffDetails.class);
            startActivity(intent);
        } else if (id == R.id.nav_video) {
            intent = new Intent(ctx, VideoTabbed.class);
            startActivity(intent);
        } else if (id == R.id.nav_test) {
            intent = new Intent(ctx, ViewTestToday.class);
            startActivity(intent);
        } else if (id == R.id.nav_doubt) {
            intent = new Intent(ctx, DoubtSubject.class);
            startActivity(intent);
        } else if (id == R.id.nav_wrt) {
            intent = new Intent(ctx, WRT_TEST.class);
            startActivity(intent);
        } else if (id == R.id.nav_wrtresult) {
            intent = new Intent(ctx, WRTResult.class);
            startActivity(intent);
        } else if (id == R.id.nav_newtest) {
            intent = new Intent(ctx, ViewNewTestToday.class);
            startActivity(intent);
        }
      /*  else if (id == R.id.nav_Uniform) {
            Toast.makeText(ctx, "Coming Soon", Toast.LENGTH_SHORT).show();
          */
      /*  intent = new Intent(ctx, UniformActivity.class);
            startActivity(intent);*//*



        }*/
        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Home");
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_timetable:
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                    Date d = new Date();
                    final String dayOfTheWeek = sdf.format(d);
                    Log.v("TTTT", dayOfTheWeek);
                    toolbar.setTitle(" Timetable");
                    fragment = new TodayTimeTable();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_noticeboard:
                    toolbar.setTitle("Notice");
                    fragment = new NoticeboardFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_chat:
                    toolbar.setTitle("Chat with Faculty");
                    fragment = new Chat();
                    loadFragment(fragment);
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_image);
        MenuItem notification = menu.findItem(R.id.action_notification);
        View view = MenuItemCompat.getActionView(menuItem);
        View notify_view = MenuItemCompat.getActionView(notification);
        CircleImageView profile_imageview = view.findViewById(R.id.toolbar_profile);
        CircleImageView notification_imageview = notify_view.findViewById(R.id.toolbar_notification);
        try {
            Glide.with(ctx).load(new URL(image_url)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    .into(profile_imageview);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        notification_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        profile_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

//                intent = new Intent(ctx, StudentProfile.class);
                intent = new Intent(ctx, ProfileMainActivity.class);
                intent.putExtra("number", number);
                intent.putExtra("stu_id", s_id);
                intent.putExtra("sc_id", sc_id);
                startActivity(intent);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
           /* case R.id.action_message:
                intent = new Intent(ctx, AskQuery.class);
                startActivity(intent);
                // do something
                return true;*/
           /* case R.id.action_account:
                intent = new Intent(ctx, Login2.class);
                startActivity(intent);
               *//* Bundle bundle = getIntent().getExtras();
                String user=bundle.getString("user");
                if(user.equals("user1")) {
                    intent = new Intent(ctx, Login.class);
                    startActivity(intent);
                }*//*
             *//*else if(user.equals("user2"))
                {
                    intent = new Intent(ctx, Login2.class)
                    startActivity(intent);
                }*//*
                // do something
                return true;
            case R.id.action_account1:

                    intent = new Intent(ctx, Login.class);
                    startActivity(intent);

                *//*else if(user.equals("user2"))
                {
                    intent = new Intent(ctx, Login2.class);
                    startActivity(intent);
                }*//*
                // do something
                return true;*/
            case R.id.action_Logout:
                Logoutactivedevice();
              /*  startActivity(new Intent(MainActivity.this,Login.class));
                finish();
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
                editor.putInt("login",1);
                editor.commit();*/

                // do something
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    private void SendAppVersion()
    {
        String url = Common.GetWebServiceURL() + "request_update.php";
        // String url = Common.GetWebServiceURL() + "text/request_update.php";
        StringRequest sr = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("version",response);
                try
                {
                    if (!response.equals("App is up-to date"))
                    {
                        final Dialog dialog = new Dialog(MainActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.layout_update_app_version);
                        TextView text = (TextView) dialog.findViewById(R.id.headingTextView);
                        text.setText(response);
                        Button dialogButton = (Button) dialog.findViewById(R.id.dialog_button);
                        Button close_button = (Button) dialog.findViewById(R.id.close_button);

                        if (response.contains("maintenance"))
                        {
                            dialogButton.setVisibility(View.GONE);
                            close_button.setVisibility(View.VISIBLE);
                        }
                        close_button.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                finishAffinity();
                            }
                        });
                        dialogButton.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                final String my_package_name = "com.aspirepublicschool.gyanmanjari";
                                String url = "";
                                try
                                {
                                    //Check whether Google Play store is installed or not:
                                    getPackageManager().getPackageInfo("com.android.vending", 0);
                                    url = "market://details?id=" + my_package_name;
                                }
                                catch ( final Exception e )
                                {
                                    url = "https://play.google.com/store/apps/details?id=" + my_package_name;
                                }

                                //Open the app page in Google Play store:
                                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                                startActivity(intent);
                            }
                        });

                        dialog.show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> data = new HashMap<>();
                try
                {
                    PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    String version = pInfo.versionName;
                    int verCode = pInfo.versionCode;
                    data.put("version", String.valueOf(version));
                    data.put("version_code", String.valueOf(verCode));

                }
                catch (PackageManager.NameNotFoundException e)
                {
                    e.printStackTrace();
                }
                Log.d(TAG, "getParams: " + data);
                return data;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(MainActivity.this).add(sr);
    }
    private void Logoutactivedevice()
    {
        Common.progressDialogShow(MainActivity.this);
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        s_id = mPrefs.getString("stu_id", "none");
        sc_id = mPrefs.getString("sc_id", "none");
        String Webserviceurl = Common.GetWebServiceURL() + "Logoutdeviceid.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    Log.d("aaa", response);
                    Common.progressDialogDismiss(MainActivity.this);
                    JSONObject object = new JSONObject(response);
                    String messsage = object.getString("message");
                    if (messsage.equals("Submitted"))
                    {
                        startActivity(new Intent(MainActivity.this, Login.class));
                        finish();
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
                        editor.remove("number");
                        editor.apply();


                    } else {
                        Toast.makeText(MainActivity.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();

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
        Volley.newRequestQueue(MainActivity.this).add(request);
    }

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume()
    {

        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        timer.cancel();
    }

}
