package com.aspirepublicschool.gyanmanjari.Lunch;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BreakfastDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BreakfastDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
   View v;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context ctx;
    TextView day1_breakfast,day2_breakfast,day3_breakfast,day4_breakfast,day5_breakfast,day6_breakfast;

    private OnFragmentInteractionListener mListener;

    public BreakfastDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BreakfastDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static BreakfastDetails newInstance(String param1, String param2) {
        BreakfastDetails fragment = new BreakfastDetails();
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
        v= inflater.inflate(R.layout.fragment_breakfast_details, container, false);
        ctx=getContext();

        day1_breakfast= v.findViewById(R.id.day1_breakFast);
        day2_breakfast = v.findViewById(R.id.day2_breakFast);
        day3_breakfast = v.findViewById(R.id.day3_breakFast);
        day4_breakfast = v.findViewById(R.id.day4_breakFast);
        day5_breakfast = v.findViewById(R.id.day5_breakFast);
        day6_breakfast = v.findViewById(R.id.day6_breakFast);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String school_id = mPrefs.getString("school_id", "none");
        // Log.v("SSSSS",school_id);
        Common.progressDialogShow(ctx);
        String LUNCH_URL = Common.GetWebServiceURL()+"displayBreakFast.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LUNCH_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(ctx);
                try {
                    JSONArray array = new JSONArray(response);


                    day1_breakfast.setText(array.getJSONObject(0).getString("breakfast"));
                    day2_breakfast.setText(array.getJSONObject(1).getString("breakfast"));
                    day3_breakfast.setText(array.getJSONObject(2).getString("breakfast"));
                    day4_breakfast.setText(array.getJSONObject(3).getString("breakfast"));
                    day5_breakfast.setText(array.getJSONObject(4).getString("breakfast"));
                    day6_breakfast.setText(array.getJSONObject(5).getString("breakfast"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Lunch_Activity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("school_id",school_id);
                return params;
            }
        };
        Volley.newRequestQueue(ctx).add(stringRequest);
        return v;
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
