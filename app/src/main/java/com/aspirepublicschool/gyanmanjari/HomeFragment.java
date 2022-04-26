package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.PayTM.DuePaymentActivity;
import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    CircleImageView profile_image;
    boolean feedbackStatus;
    TextView txtevents, txtbreakfast, txtlunch, txtdinner, txtavggrade, profile_name, class_id, txtavgatt, rollno, txtsub, txtmarks, readmore;
    Context ctx;
    String stu_id, classid, name, std, roll_no;
    ArrayList<student_profileModel> student_profileModelList = new ArrayList();
    List<ResultModel> resultModelList = new ArrayList<>();
    ResultModel resultModel;
    int obtain = 0;
    int total = 0;
    int progress = 0;
    int present, absent, totaldays;
    List<AttendanceModel> attendanceModelList = new ArrayList<>();
    int result;
    RecyclerView recadv1, recadv2;
    ArrayList<Advertisement> advertisementlist = new ArrayList<>();
    List<Mainpage_design> lstMainpagedesign;
    String user,standard,div;

    String sc_id;
    String cid, number;
    TextView txtView;
    String status;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {

    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home2, container, false);
        ctx = getContext();
        profile_image = v.findViewById(R.id.profile_image);
        class_id = v.findViewById(R.id.class_id);
        profile_name = v.findViewById(R.id.profile_name);
        recadv1 = v.findViewById(R.id.recadv1);
        /*recadv2 = v.findViewById(R.id.recadv2);*/
        rollno = v.findViewById(R.id.rollno);
        txtView = v.findViewById(R.id.textView);
        txtView.setVisibility(View.INVISIBLE);

        statusGet();

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        sc_id= mPrefs.getString("sc_id", "none").toUpperCase();
        number= mPrefs.getString("number", "none");
//        status = mPrefs.getString("status", "none");
         stu_id = mPrefs.getString("stu_id", "none").toUpperCase();



        feedbackStatus();

        lstMainpagedesign = new ArrayList<>();
        lstMainpagedesign.add(new Mainpage_design("Notice", R.drawable.notice));
        lstMainpagedesign.add(new Mainpage_design("Video Lectures", R.drawable.ic_elearning));
        lstMainpagedesign.add(new Mainpage_design("Homework and \nStudy material", R.drawable.ic_homework));
        lstMainpagedesign.add(new Mainpage_design("Homework \nSubmission", R.drawable.ic_feedback));
        lstMainpagedesign.add(new Mainpage_design("Doubt Solving Material", R.drawable.ic_question));
        lstMainpagedesign.add(new Mainpage_design("Doubt Solving using Chat", R.drawable.ic_chat_desktop));
        lstMainpagedesign.add(new Mainpage_design("TimeTable", R.drawable.ic_schedule));
        lstMainpagedesign.add(new Mainpage_design("Teacher details", R.drawable.ic_staff));
        lstMainpagedesign.add(new Mainpage_design("Test/Key", R.drawable.ic_question));
        lstMainpagedesign.add(new Mainpage_design("WRT Test", R.drawable.ic_question));
        lstMainpagedesign.add(new Mainpage_design("Online/Offline Test", R.drawable.ic_question));
        lstMainpagedesign.add(new Mainpage_design("Result", R.drawable.ic_exam));
        lstMainpagedesign.add(new Mainpage_design("WRT Result", R.drawable.ic_exam));
        lstMainpagedesign.add(new Mainpage_design("Offline Test Result Display", R.drawable.ic_exam));


        //lstMainpagedesign.add(new Mainpage_design("Feedback", R.drawable.ic_feedback));
        /*lstMainpagedesign.add(new Mainpage_design("St.Package", R.drawable.ic_package));*/




        /*Advertisement advertisement1 = new Advertisement("1", R.drawable.ads1);
        advertisementlist.add(advertisement1);
        Advertisement advertisement = new Advertisement("2", R.drawable.ads2);
        advertisementlist.add(advertisement);
        AdvertisementAdapter adapter = new AdvertisementAdapter(ctx, advertisementlist);
        LinearLayoutManager horizontalLayoutManagaer1
                = new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false);
        recadv2.setLayoutManager(horizontalLayoutManagaer1);
        recadv2.setAdapter(adapter);*/
        loadStudentData();
        /*loadClassresult();

        //loadTodayMenu();
        loadAnnouncement();
        loadavgData();
        loadAttendanceStatus();
        readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctx.startActivity(new Intent(getActivity(),Result_Activity.class));

            }
        });*/
        return v;
    }

    private void statusGet() {
        String Webserviceurl = Common.GetWebServiceURL() + "getStatus.php";
        Log.d("@@@web", Webserviceurl);
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    status = array.getJSONObject(0).getString("status");

                    if (status.equals("demo") || status.equals("continue") | status.equals("fee")){
                        txtView.setVisibility(View.VISIBLE);
                    }

                    txtView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (status.equalsIgnoreCase("demo")) {
                                Toast.makeText(ctx, "Demo Request Pending", Toast.LENGTH_SHORT).show();
                            }else if (status.equalsIgnoreCase("continue")){
                                Toast.makeText(ctx, "We are adding you to the class shortly", Toast.LENGTH_SHORT).show();
                            }else if (status.equalsIgnoreCase("fee")){
                                startActivity(new Intent(ctx, DuePaymentActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("number", number);
                return data;
            }
        };
        Volley.newRequestQueue(ctx).add(request);
    }

    private void loadClassresult() {



        String Webserviceurl = Common.GetWebServiceURL() + "latestclasstest.php";
        Log.d("@@@web", Webserviceurl);
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject current = array.getJSONObject(i);
                        txtsub.setText(current.getString("subject"));
                        txtmarks.setText(current.getString("obtain") + "/" + current.getString("total"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("sc_id", sc_id.toLowerCase());
                data.put("cid", cid);
                data.put("stu_id", stu_id);
                return data;
            }
        };
        Volley.newRequestQueue(ctx).add(request);
    }

    public void feedbackStatus(){

        {

            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
            final String sc_id = mPrefs.getString("sc_id", "none").toUpperCase();
            final String cid = mPrefs.getString("class_id", "none").toUpperCase();
            final String stu_id = mPrefs.getString("stu_id", "none").toUpperCase();

            String Webserviceurl = Common.GetWebServiceURL() + "feedbackStatus.php";
            Log.d("@@@web", Webserviceurl);
            StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.equalsIgnoreCase("true")){
                        feedbackStatus = true;
                        if (feedbackStatus){
                            lstMainpagedesign.add(new Mainpage_design("Feedback", R.drawable.ic_feedback));
                        }

                    }else{
                        feedbackStatus = false;
                    }

                    RecyclerViewAdapter_Main adapter1 = new RecyclerViewAdapter_Main(ctx, lstMainpagedesign);
                    recadv1.setLayoutManager(new GridLayoutManager(ctx, 2));
                    recadv1.setAdapter(adapter1);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("sc_id", sc_id.toLowerCase());
                    data.put("cid", cid);
                    data.put("stu_id", stu_id);
                    return data;
                }
            };
            Volley.newRequestQueue(ctx).add(request);
        }


    }
    private void loadTodayMenu() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        final Date d = new Date();
        final String dayOfTheWeek = sdf.format(d);
        Log.v("TTTT", dayOfTheWeek);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String school_id = mPrefs.getString("sc_id", "none").toUpperCase();
        Log.v("LLL", school_id);
        String MENU_URL = Common.GetWebServiceURL() + "displayTodayMenu.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MENU_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(ctx);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i <= array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        txtbreakfast.setText(object.getString("breakfast"));
                        txtlunch.setText(object.getString("lunch"));
                        txtdinner.setText(object.getString("dinner"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("day", dayOfTheWeek);
                params.put("sc_id", school_id.toUpperCase());
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }

    private void loadAnnouncement() {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String school_id = mPrefs.getString("sc_id", "none").toUpperCase();
        Log.v("LLL", school_id);
        String MENU_URL = Common.GetWebServiceURL() + "recentAnnouncement.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MENU_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(ctx);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i <= array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        txtevents.setText(object.getString("header"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("sc_id", school_id.toUpperCase());
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }

    private void loadStudentData() {

        Common.progressDialogShow(ctx);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String s_id = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id", "none");
        String STUDENT_PROFILE_URL = Common.GetWebServiceURL() + "student_profile.php";
        Log.v("profile", STUDENT_PROFILE_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, STUDENT_PROFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(ctx);
                    Log.d("Stude", response);
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
                        Log.v("nameaaaa@@2", String.valueOf(name));
                        profile_name.setText(name);

                        stu_id = jsonObject.getString("std");
                        class_id.setText(jsonObject.getString("std") + "-" + jsonObject.getString("division"));
                        standard=jsonObject.getString("std");
                        std = jsonObject.getString("std") + "-" + jsonObject.getString("division");
                        roll_no = jsonObject.getString("roll_no");
                        rollno.setText(roll_no);
                        String DP_URL = "https://mrawideveloper.com/gyanmanfarividyapith.net/zocarro/image/student/" + student_profileModelList.get(i).getStu_img();
                        Log.d("DP_URL", DP_URL);
                        Glide.with(getActivity()).load(new URL(DP_URL)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                .into(profile_image);
                        classid = jsonObject.getString("cid");

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("class_id", classid);
                        editor.putString("stu_name", name);
                        editor.putString("f_name",  jsonObject.getString("f_name"));
                        editor.putString("stu_id", jsonObject.getString("stu_id"));
                        editor.putString("std", std);
                        editor.putString("standard", standard);
                        editor.putString("board",jsonObject.getString("board"));
                        editor.putString("med", jsonObject.getString("med"));
                        editor.putString("gender", jsonObject.getString("gender"));
                        editor.putString("mobile", jsonObject.getString("f_cno"));
                        editor.putString("address", jsonObject.getString("address"));
                        editor.putString("city", jsonObject.getString("city"));
                        editor.putString("mobile", jsonObject.getString("f_cno"));
                        editor.putString("st_dp", DP_URL);
                        editor.commit();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(ctx);
                Toast.makeText(ctx, R.string.no_connection_toast, Toast.LENGTH_LONG).show();

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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(ctx).add(stringRequest);
    }

    private void loadavgData() {
        // Common.progressDialogShow(Result_Activity.this);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stu_id = mPrefs.getString("stu_id", "none");
        final String school_id = mPrefs.getString("sc_id", "none");
        Log.v("XYZ", stu_id);
        Log.v("PQR", school_id);
        String URL = Common.GetWebServiceURL() + "result.php";
        Log.v("URL", URL);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //   Common.progressDialogDismiss(Result_Activity.this);
                    JSONArray array = new JSONArray(response);
                    // pieEntries.clear();
                    resultModelList.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject result = array.getJSONObject(i);
                        resultModelList.add(new ResultModel(
                                result.getString("stu_id"),
                                result.getString("cid"),
                                result.getString("t_id"),
                                result.getString("type"),
                                result.getString("subject"),
                                result.getString("total"),
                                result.getString("obtain"),
                                result.getString("test_date")
                        ));
                        resultModel = new ResultModel(result.getString("stu_id"),
                                result.getString("cid"),
                                result.getString("t_id"),
                                result.getString("type"),
                                result.getString("subject"),
                                result.getString("total"),
                                result.getString("obtain"),
                                result.getString("test_date"));

                        obtain = Integer.parseInt(resultModel.getObtain_marks());
                        Log.d("obtain", "" + obtain);

                        total = total + Integer.parseInt(resultModel.getTotal_marks());
                        progress = progress + obtain;

                    }
                    //resultModelList.clear();
                    if (total == 0) {
                        txtavggrade.setText("No Data Available");
                    } else {
                        int growth = 0;
                        growth = (progress * 100) / total;
                        Log.v("LLLL", String.valueOf(growth));
                        Log.v("TOTAL", String.valueOf(total));
                        //textView_percentage.setText(growth+"% with");
                        if (growth >= 96 && growth <= 100) {
                            txtavggrade.setText("A++");
                        }
                        if (growth >= 90 && growth <= 95) {
                            txtavggrade.setText("A+");
                        }
                        if (growth > 80 && growth < 90) {
                            txtavggrade.setText("A");
                        }
                        if (growth >= 70 && growth <= 80) {
                            txtavggrade.setText("B+");
                        }
                        if (growth > 60 && growth < 70) {
                            txtavggrade.setText("B+");
                        }
                        if (growth >= 50 && growth <= 60) {
                            txtavggrade.setText("B");
                        }
                        if (growth > 40 && growth < 50) {
                            txtavggrade.setText("C+");
                        }
                        if (growth >= 30 && growth <= 40) {
                            txtavggrade.setText("C");
                        }
                        if (growth >= 25 && growth <= 29) {
                            txtavggrade.setText("D+");
                        }
                        if (growth >= 20 && growth <= 24) {
                            txtavggrade.setText("D");
                        }
                        if (growth >= 10 && growth < 20) {
                            txtavggrade.setText("F");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("DDDD", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("stu_id", stu_id);
                params.put("sc_id", school_id);
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }

    private void loadAttendanceStatus() {
        // Common.progressDialogShow(ctx);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stu_id = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id", "none");
        //Log.v("STU_ID",stu_id);
        // String ATT_URL ="http://192.168.0.108:8080/school/ws/displayAttendance.php";
        String ATT_URL = Common.GetWebServiceURL() + "displayAttendance.php";
        Log.v("&&&", ATT_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ATT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Common.progressDialogDismiss(ctx);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i <= array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        attendanceModelList.add(new AttendanceModel(object.getString("attstatus"),
                                object.getString("date")
                        ));
                        AttendanceModel modelClass = new AttendanceModel(object.getString("attstatus"), object.getString("date"));
                        //   Log.v("DATA", modelClass.getDate());
                        if (modelClass.getStatus().equals("absent")) {
                            absent++;

                            String date = modelClass.getDate();
                            int Y = Integer.parseInt(date.substring(6, 10));
                            int M = Integer.parseInt(date.substring(3, 5));
                            int D = Integer.parseInt(date.substring(0, 2));
                            //calendarView.markDate(new DateData(Y, M, D).setMarkStyle(new MarkStyle(MarkStyle.DEFAULT, Color.RED)));
                        }
                        if (modelClass.getStatus().equals("present")) {
                            present++;
                            //  pieEntries.add(new PieEntry(present,modelClass.getStatus()));
                            //Log.v("P_COUNT",String.valueOf(present));
                            //Log.v("PRESENT",modelClass.getDate());
                            String date = modelClass.getDate();
                            int Y = Integer.parseInt(date.substring(6, 10));
                            //Log.v("P_YEAR",String.valueOf(Y));
                            int M = Integer.parseInt(date.substring(3, 5));
                            //Log.v("P_MONTH", String.valueOf(M));
                            int D = Integer.parseInt(date.substring(0, 2));
                            //Log.v("P_DDDD", String.valueOf(D));
                            //calendarView.markDate(new DateData(Y, M, D).setMarkStyle(new MarkStyle(MarkStyle.DEFAULT,Color.rgb(0,181,36))));
                        }
                        //txt_absent.setText(array.getJSONObject(i+1).getString("status"));
                        totaldays = absent + present;
                        Log.d("totaldays", "" + totaldays);
                        result = (present * 100) / totaldays;
                        Log.d("resultatt", "" + result);
                        txtavgatt.setText("" + result + "%");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("stu_id", stu_id);
                params.put("sc_id", sc_id);
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
