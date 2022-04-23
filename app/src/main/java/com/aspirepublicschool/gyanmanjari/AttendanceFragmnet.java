package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AttendanceFragmnet.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AttendanceFragmnet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttendanceFragmnet extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<AttendanceModel> attendanceModelList = new ArrayList<>();
    List<AttendanceModel> attendanceModels = new ArrayList<>();
    /*MCalendarView calendarView;*/
    int present,absent;
    TextView txtavgatt,text_month;
    int totaldays,result;
    final int[] pieColors = {
            Color.rgb(0,199,0), Color.rgb(199,0,0)
    };
    String date;
    int year,month,day;
    Context ctx;

    private OnFragmentInteractionListener mListener;

    public AttendanceFragmnet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttendanceFragmnet.
     */
    // TODO: Rename and change types and number of parameters
    public static AttendanceFragmnet newInstance(String param1, String param2) {
        AttendanceFragmnet fragment = new AttendanceFragmnet();
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
        // Inflate the layout for this fragment
     View v=inflater.inflate(R.layout.fragment_attendance_fragmnet, container, false);
     //calendarView=v.findViewById(R.id.attendance_calender);
     text_month=v.findViewById(R.id.text_month);
       /* calendarView.setMarkedStyle(MarkStyle.LEFTSIDEBAR);

        calendarView.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
              //  Toast.makeText(ctx, String.format("%d-%d", year, month), Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                calendar.set(Calendar.MONTH, month-1);
                String month_name = month_date.format(calendar.getTime());
                    text_month.setText(month_name);
                Log.e("LLL",""+month_name);
            }
        });*/
     txtavgatt=v.findViewById(R.id.txtavgatt);
      ctx=getContext();
      loadAttendanceStatus();
      loadAttendance();
     return v;
    }
    private void loadAttendance() {
        // Common.progressDialogShow(ctx);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stu_id = mPrefs.getString("stu_id", "none");
        final  String sc_id = mPrefs.getString("sc_id","none");
        //Log.v("STU_ID",stu_id);
        // String ATT_URL ="http://192.168.0.108:8080/school/ws/displayAttendance.php";
        String ATT_URL = Common.GetWebServiceURL()+"displayAttendance.php";
        Log.v("&&&",ATT_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ATT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Common.progressDialogDismiss(ctx);
                    JSONArray array = new JSONArray(response);
                    for (int i=0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        attendanceModels.add(new AttendanceModel(object.getString("attstatus"),
                                object.getString("date")
                        ));
                        AttendanceModel modelClass = new AttendanceModel(object.getString("attstatus"),object.getString("date"));
                        //   Log.v("DATA", modelClass.getDate());
                        if (modelClass.getStatus().equals("absent")){
                            absent++;

                            String date = modelClass.getDate();
                            int Y =Integer.parseInt(date.substring(6,10));
                            int M = Integer.parseInt(date.substring(3,5));
                            int D = Integer.parseInt(date.substring(0,2));
                            //calendarView.markDate(new DateData(Y, M, D).setMarkStyle(new MarkStyle(MarkStyle.DEFAULT, Color.RED)));
                        }
                        if (modelClass.getStatus().equals("present")){
                            present++;
                            //  pieEntries.add(new PieEntry(present,modelClass.getStatus()));
                            //Log.v("P_COUNT",String.valueOf(present));
                            //Log.v("PRESENT",modelClass.getDate());
                            String date = modelClass.getDate();
                            int Y =Integer.parseInt(date.substring(6,10));
                            //Log.v("P_YEAR",String.valueOf(Y));
                            int M = Integer.parseInt(date.substring(3,5));
                            //Log.v("P_MONTH", String.valueOf(M));
                            int D = Integer.parseInt(date.substring(0,2));
                            //Log.v("P_DDDD", String.valueOf(D));
                            //calendarView.markDate(new DateData(Y, M, D).setMarkStyle(new MarkStyle(MarkStyle.DEFAULT,Color.rgb(0,181,36))));
                        }
                        //txt_absent.setText(array.getJSONObject(i+1).getString("status"));
                        totaldays=absent+present;
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
                Toast.makeText(ctx,R.string.no_connection_toast,Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("stu_id",stu_id);
                params.put("sc_id",sc_id);
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }
    private void loadAttendanceStatus() {
        Common.progressDialogShow(ctx);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stu_id = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id","none");
        String ATT_URL = Common.GetWebServiceURL()+"displayAttendance.php";
        Log.v("&&&",ATT_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ATT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(ctx);
                    JSONArray array = new JSONArray(response);
                    for (int i=0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        attendanceModelList.add(new AttendanceModel(object.getString("attstatus"),
                                object.getString("date")
                        ));
                        AttendanceModel modelClass = new AttendanceModel(object.getString("attstatus"),object.getString("date"));
                        if (modelClass.getStatus().equals("absent")){
                            absent++;
                            String date = modelClass.getDate();
                            int Y = Integer.parseInt(date.substring(6,10));
                            int M = Integer.parseInt(date.substring(3,5));
                            int D = Integer.parseInt(date.substring(0,2));
                           // calendarView.markDate(new DateData(Y, M, D).setMarkStyle(new MarkStyle(MarkStyle.DEFAULT, Color.RED)));
                        }
                        if (modelClass.getStatus().equals("present")){
                            present++;
                            String date = modelClass.getDate();
                            int Y = Integer.parseInt(date.substring(6,10));
                            int M = Integer.parseInt(date.substring(3,5));
                            int D = Integer.parseInt(date.substring(0,2));
                           // calendarView.markDate(new DateData(Y, M, D).setMarkStyle(new MarkStyle(MarkStyle.DEFAULT, Color.rgb(0,181,36))));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx,R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("stu_id",stu_id);
                params.put("sc_id",sc_id);
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
