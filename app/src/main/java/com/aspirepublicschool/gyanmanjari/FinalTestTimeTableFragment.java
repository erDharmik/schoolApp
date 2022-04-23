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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FinalTestTimeTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinalTestTimeTableFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    List<TestTimeTableModel> testTimeTableModelList = new ArrayList<>();
    TestTimeTableModel testTimeTableModel;
    Context context;
    RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;

    public FinalTestTimeTableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinalTestTimeTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinalTestTimeTableFragment newInstance(String param1, String param2) {
        FinalTestTimeTableFragment fragment = new FinalTestTimeTableFragment();
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
        v=inflater.inflate(R.layout.fragment_class_test_time_table, container, false);
        recyclerView = v.findViewById(R.id.timetable_test_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        context = getContext();
        loadTestTimeTable();
        return v;
    }
    private void loadTestTimeTable() {
        //Common.progressDialogShow(context);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        final String class_id = mPrefs.getString("class_id", "none");
        final String sc_id = mPrefs.getString("sc_id","none");
        Log.v("XYZ",class_id);
        Log.v("PQR",sc_id);
        String TEST_TIME_TABLE_URL = Common.GetWebServiceURL()+"TestTimeTable.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TEST_TIME_TABLE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(context);
                    JSONArray array = new JSONArray(response);
                    testTimeTableModelList.clear();
                    for (int i=0; i<array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                       /* testTimeTableModelList.add(new TestTimeTableModel(
                                object.getString("type"),
                                object.getString("subject"),
                                object.getString("total"),
                                object.getString("test_date"),
                                object.getString("test_time"),
                                object.getString("t_fname"),
                                object.getString("t_lname")
                        ));*/
                        testTimeTableModel = new TestTimeTableModel(
                                object.getString("type"),
                                object.getString("subject"),
                                object.getString("total"),
                                object.getString("test_date"),
                                object.getString("test_time"),
                                object.getString("t_fname"),
                                object.getString("t_lname")
                        );
                        if (testTimeTableModel.getTest_type().equals("Final Exam")){
                            testTimeTableModelList.add(testTimeTableModel);
                        }
                    }

                    TestTimeTableViewAdapter testTimeTableViewAdapter = new TestTimeTableViewAdapter(context,testTimeTableModelList);
                    recyclerView.setAdapter(testTimeTableViewAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("c_id",class_id);
                params.put("sc_id",sc_id);
                return params;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);
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
