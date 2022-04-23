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
 * Use the {@link DinnerDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DinnerDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context ctx;
    View v;
    TextView day1_dinner,day2_dinner,day3_dinner,day4_dinner,day5_dinner,day6_dinner;
    private OnFragmentInteractionListener mListener;

    public DinnerDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DinnerDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static DinnerDetails newInstance(String param1, String param2) {
        DinnerDetails fragment = new DinnerDetails();
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
     v= inflater.inflate(R.layout.fragment_dinner_details, container, false);
        ctx=getContext();

        day1_dinner = v.findViewById(R.id.day1_dinner);
        day2_dinner = v.findViewById(R.id.day2_dinner);
        day3_dinner = v.findViewById(R.id.day3_dinner);
        day4_dinner = v.findViewById(R.id.day4_dinner);
        day5_dinner = v.findViewById(R.id.day5_dinner);
        day6_dinner = v.findViewById(R.id.day6_dinner);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String school_id = mPrefs.getString("school_id", "none");
        // Log.v("SSSSS",school_id);
       // Common.progressDialogShow(ctx);
        String DINNER_URL = Common.GetWebServiceURL()+"displayDinner.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DINNER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(ctx);
                try {
                    JSONArray array = new JSONArray(response);


                    day1_dinner.setText(array.getJSONObject(0).getString("dinner"));
                    day2_dinner.setText(array.getJSONObject(1).getString("dinner"));
                    day3_dinner.setText(array.getJSONObject(2).getString("dinner"));
                    day4_dinner.setText(array.getJSONObject(3).getString("dinner"));
                    day5_dinner.setText(array.getJSONObject(4).getString("dinner"));
                    day6_dinner.setText(array.getJSONObject(5).getString("dinner"));
                    day6_dinner.setText(array.getJSONObject(5).getString("dinner"));


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
