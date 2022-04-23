package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WednesdayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WednesdayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View v;
    DetailInfo timeTableModel;
    RecyclerView recyclerView;
    Context ctx;
    private List<DetailInfo> timeTableModelList = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public WednesdayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WednesdayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WednesdayFragment newInstance(String param1, String param2) {
        WednesdayFragment fragment = new WednesdayFragment();
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
        v = inflater.inflate(R.layout.fragment_monday, container, false);
        recyclerView = v.findViewById(R.id.recycleView);
        ctx = getContext();
        loadData();
        return v;
    }

    public void loadData() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        final String dayOfTheWeek = sdf.format(d);
        Log.v("TTTT", dayOfTheWeek);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String class_id = preferences.getString("class_id", "none");
        final String sc_id = preferences.getString("sc_id", "none").toLowerCase();
        Log.v("CCCCCC", class_id);
        //String URL = "http://192.168.43.65/schooltask/ws/displayTimeTable.php";
        //Common.progressDialogShow(ctx);
        String URL_TIMETABLE = Common.GetWebServiceURL() + "displayTimeTable.php";
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
                               /*timeTableModelList.add(new TimeTableModel(
                                       jsonObject.getString("day"),
                                       jsonObject.getString("lacture"),
                                       jsonObject.getString("time"),
                                       jsonObject.getString("t_fname"),
                                       jsonObject.getString("t_lname")
                               ));*/
                                timeTableModel = new DetailInfo(
                                        jsonObject.getString("day"),
                                        jsonObject.getString("lecture"),
                                        jsonObject.getString("t_fname"),
                                        jsonObject.getString("t_lname"),
                                        jsonObject.getString("time"));
                                if (timeTableModel.getDay().equals("Wednesday")) {
                                    timeTableModelList.add(timeTableModel);
                                }

                            }
                            Collections.sort(timeTableModelList, new Comparator<DetailInfo>() {
                                @Override
                                public int compare(DetailInfo o1, DetailInfo o2) {
                                    String s = String.valueOf(o1.getTime());
                                    String s1 = String.valueOf(o2.getTime());
                                    return s.compareTo(s1);
                                }
                            });
                            TimetableViewAdapter timetableViewAdapter = new TimetableViewAdapter(ctx, timeTableModelList);
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cid", class_id);
                params.put("sc_id", sc_id);
                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(ctx).add(stringRequest);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
