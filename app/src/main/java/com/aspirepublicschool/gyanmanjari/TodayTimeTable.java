package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TodayTimeTable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayTimeTable extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    /*TimeTableModel timeTableModel;
    RecyclerView recyclerView;

    private List<TimeTableModel> timeTableModelList = new ArrayList<>();*/
    static ExpandableListView expandableListView;
    ArrayList<TimeTableModel> expandableListDetail = new ArrayList<TimeTableModel>();
    static Context ctx;
    static TimeTableModel timeTableModel1,timeTableModel2,timeTableModel3,timeTableModel4,timeTableModel5,timeTableModel6,timeTableModel7;
    static DetailInfo detailInfo;
     ArrayList<DetailInfo> monday = new ArrayList<DetailInfo>();
    static ArrayList<DetailInfo> tuesday = new ArrayList<DetailInfo>();
    static ArrayList<DetailInfo> wednesday = new ArrayList<DetailInfo>();
    static ArrayList<DetailInfo> thursday = new ArrayList<DetailInfo>();
    static ArrayList<DetailInfo> friday = new ArrayList<DetailInfo>();
    static ArrayList<DetailInfo> saturday = new ArrayList<DetailInfo>();
    static ArrayList<DetailInfo> sunday = new ArrayList<DetailInfo>();
    static ExpandableAdapter expandableAdapter;
    DisplayMetrics metrics;
    int width;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TodayTimeTable() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodayTimeTable.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayTimeTable newInstance(String param1, String param2) {
        TodayTimeTable fragment = new TodayTimeTable();
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
       View v= inflater.inflate(R.layout.fragment_today_time_table, container, false);
       /* recyclerView = v.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));*/
        ctx=getContext();
        expandableListView=v.findViewById(R.id.expandableListView);
        metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        expandableListView.setIndicatorBounds(width - GetDipsFromPixel(50), width - GetDipsFromPixel(10));
        //loadData();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String class_id = preferences.getString("class_id","none");
        final String sc_id = preferences.getString("sc_id","none").toLowerCase();
        Date d = new Date();
        final String dayOfTheWeek = sdf.format(d);
        Log.v("TTTT",dayOfTheWeek);
        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        /*final String class_id = preferences.getString("class_id","none");
        final String sc_id = preferences.getString("sc_id","none").toLowerCase();
        Log.v("CCCCCC",class_id);*/
        //String URL = "http://192.168.43.65/schooltask/ws/displayTimeTable.php";
        //Common.progressDialogShow(ctx);
        String URL_TIMETABLE = Common.GetWebServiceURL()+"displayTimeTable.php";
        Log.v("URL_TIMETABLE",URL_TIMETABLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TIMETABLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Common.progressDialogDismiss(ctx);
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            //  ac_year.setText(String.valueOf(array));
                            //traversing through all the object
                            monday.clear();
                            tuesday.clear();
                            wednesday.clear();
                            thursday.clear();
                            friday.clear();
                            saturday.clear();
                            sunday.clear();
                            expandableListDetail.clear();
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject jsonObject = array.getJSONObject(i);

                                //adding the product to product list
                             /*timeTableModelList.add(new TimeTableModel(
                                       jsonObject.getString("day"),
                                       jsonObject.getString("lacture"),
                                       jsonObject.getString("time"),
                                       jsonObject.getString("t_fname"),
                                       jsonObject.getString("t_lname")
                               ));*/
                          /*   monday.add(new TimeTableModel(
                                               jsonObject.getString("day"),
                                               jsonObject.getString("lecture"),
                                               jsonObject.getString("time"),
                                               jsonObject.getString("t_fname"),
                                               jsonObject.getString("t_lname")));*/
                                detailInfo=new DetailInfo(
                                        jsonObject.getString("day"),
                                        jsonObject.getString("lecture"),
                                        jsonObject.getString("t_fname"),
                                        jsonObject.getString("t_lname"),
                                        jsonObject.getString("time"));
                                if (detailInfo.getDay().equals("Monday"))
                                {
                                    monday.add(detailInfo);
                                }
                                if (detailInfo.getDay().equals("Tuesday"))
                                {
                                    tuesday.add(detailInfo);
                                }
                                if (detailInfo.getDay().equals("Wednesday"))
                                {
                                    wednesday.add(detailInfo);
                                }
                                if (detailInfo.getDay().equals("Thursday"))
                                {
                                    thursday.add(detailInfo);
                                }
                                if (detailInfo.getDay().equals("Friday"))
                                {
                                    friday.add(detailInfo);
                                }
                                if (detailInfo.getDay().equals("Saturday"))
                                {
                                    saturday.add(detailInfo);
                                    Log.d("###", detailInfo.getDay());
                                    Log.d("###", saturday.toString());
                                }
                                if (detailInfo.getDay().equals("Sunday"))
                                {
                                    sunday.add(detailInfo);
                                    Log.d("###", detailInfo.getDay());
                                    Log.d("###", sunday.toString());
                                }


                            }

                            timeTableModel1=new TimeTableModel("Monday",monday);
                            timeTableModel2=new TimeTableModel("Tuesday",tuesday);
                            timeTableModel3=new TimeTableModel("Wednesday",wednesday);
                            timeTableModel4=new TimeTableModel("Thursday",thursday);
                            timeTableModel5=new TimeTableModel("Friday",friday);
                            timeTableModel6=new TimeTableModel("Saturday",saturday);
                            timeTableModel7=new TimeTableModel("Sunday",sunday);
                            expandableListDetail.add(timeTableModel1);
                            expandableListDetail.add(timeTableModel2);
                            expandableListDetail.add(timeTableModel3);
                            expandableListDetail.add(timeTableModel4);
                            expandableListDetail.add(timeTableModel5);
                            expandableListDetail.add(timeTableModel6);
                            expandableListDetail.add(timeTableModel7);
                            Collections.sort(monday, new Comparator<DetailInfo>() {
                                @Override
                                public int compare(DetailInfo o1, DetailInfo o2) {
                                    String s = String.valueOf(o1.getTime());
                                    String s1 = String.valueOf(o2.getTime());
                                    return s.compareTo(s1);
                                }
                            });
                            Collections.sort(tuesday, new Comparator<DetailInfo>() {
                                @Override
                                public int compare(DetailInfo o1, DetailInfo o2) {
                                    String s = String.valueOf(o1.getTime());
                                    String s1 = String.valueOf(o2.getTime());
                                    return s.compareTo(s1);
                                }
                            });
                            Collections.sort(wednesday, new Comparator<DetailInfo>() {
                                @Override
                                public int compare(DetailInfo o1, DetailInfo o2) {
                                    String s = String.valueOf(o1.getTime());
                                    String s1 = String.valueOf(o2.getTime());
                                    return s.compareTo(s1);
                                }
                            });
                            Collections.sort(thursday, new Comparator<DetailInfo>() {
                                @Override
                                public int compare(DetailInfo o1, DetailInfo o2) {
                                    String s = String.valueOf(o1.getTime());
                                    String s1 = String.valueOf(o2.getTime());
                                    return s.compareTo(s1);
                                }
                            });
                            Collections.sort(friday, new Comparator<DetailInfo>() {
                                @Override
                                public int compare(DetailInfo o1, DetailInfo o2) {
                                    String s = String.valueOf(o1.getTime());
                                    String s1 = String.valueOf(o2.getTime());
                                    return s.compareTo(s1);
                                }
                            });
                            Collections.sort(saturday, new Comparator<DetailInfo>() {
                                @Override
                                public int compare(DetailInfo o1, DetailInfo o2) {
                                    String s = String.valueOf(o1.getTime());
                                    String s1 = String.valueOf(o2.getTime());
                                    return s.compareTo(s1);
                                }
                            });
                            Collections.sort(sunday, new Comparator<DetailInfo>() {
                                @Override
                                public int compare(DetailInfo o1, DetailInfo o2) {
                                    String s = String.valueOf(o1.getTime());
                                    String s1 = String.valueOf(o2.getTime());
                                    return s.compareTo(s1);
                                }
                            });
                            Log.d("###", expandableListDetail.toString());
                          /*  Log.d("@@##",  jsonObject.getString("day") +jsonObject.getString("lecture")+
                                    jsonObject.getString("time")+
                                    jsonObject.getString("t_fname")+
                                    jsonObject.getString("t_lname"));*/

                            expandableAdapter=new ExpandableAdapter(ctx,expandableListDetail);
                            expandableListView.setAdapter(expandableAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("cid",class_id);
                params.put("sc_id",sc_id.toUpperCase());
                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(ctx).add(stringRequest);
        //Log.d("aaa",finalExpandableListDetail.toString());

        return v;
    }
    public int GetDipsFromPixel(float pixels)
    {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

  /*  public void loadData(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        final String dayOfTheWeek = sdf.format(d);
        Log.v("TTTT",dayOfTheWeek);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String class_id = preferences.getString("class_id","none");
        final String sc_id = preferences.getString("sc_id","none").toLowerCase();
        Log.v("CCCCCC",class_id);
        //String URL = "http://192.168.43.65/schooltask/ws/displayTimeTable.php";
        //Common.progressDialogShow(ctx);
        String URL_TIMETABLE = Common.GetWebServiceURL()+"displayTimeTable.php";
        // Log.v("URL_TIMETABLE",URL_TIMETABLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TIMETABLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Common.progressDialogDismiss(ctx);
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            //  ac_year.setText(String.valueOf(array));
                            //traversing through all the object
                            timeTableModelList.clear();
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject jsonObject = array.getJSONObject(i);

                                //adding the product to product list
                               *//*timeTableModelList.add(new TimeTableModel(
                                       jsonObject.getString("day"),
                                       jsonObject.getString("lacture"),
                                       jsonObject.getString("time"),
                                       jsonObject.getString("t_fname"),
                                       jsonObject.getString("t_lname")
                               ));*//*
                                timeTableModel=new TimeTableModel(
                                        jsonObject.getString("day"),
                                        jsonObject.getString("lecture"),
                                        jsonObject.getString("t_fname"),
                                        jsonObject.getString("t_lname"),
                                        jsonObject.getString("time"));
                                if (timeTableModel.getDay().equals(dayOfTheWeek))
                                {
                                    timeTableModelList.add(timeTableModel);
                                }

                            }
                            TimetableViewAdapter timetableViewAdapter = new TimetableViewAdapter(ctx,timeTableModelList);
                            LinearLayoutManager llm = new LinearLayoutManager(ctx);
                            llm.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(llm);
                            recyclerView.setAdapter(timetableViewAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("cid",class_id);
                params.put("sc_id",sc_id.toUpperCase());
                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(ctx).add(stringRequest);
    }*/

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
