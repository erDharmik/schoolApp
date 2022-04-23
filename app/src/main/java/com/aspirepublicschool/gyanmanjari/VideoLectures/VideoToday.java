package com.aspirepublicschool.gyanmanjari.VideoLectures;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoToday.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VideoToday#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoToday extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView rechomework;
    ArrayList<VideoLectureModel> homeWorkDetailsList = new ArrayList<>();
    Context ctx;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public VideoToday() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoToday.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoToday newInstance(String param1, String param2) {
        VideoToday fragment = new VideoToday();
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
        View view = inflater.inflate(R.layout.fragment_video_today, container, false);
        rechomework = view.findViewById(R.id.rechomework);
        ctx=getContext();
        String timeSettings = android.provider.Settings.Global.getString(
                ctx.getContentResolver(),
                android.provider.Settings.Global.AUTO_TIME);
        if (timeSettings.contentEquals("0")) {
            Log.d("Set", "True");
             /*   android.provider.Settings.Global.putString(
                        this.getContentResolver(),
                        android.provider.Settings.Global.AUTO_TIME, "1");*/
            Toast.makeText(ctx, "Sorry date and time changed.Please change in automatic to continue with Video for Today.", Toast.LENGTH_LONG).show();

        }
        else
        {
            Log.d("Set", "False");
            loadAssignment();

        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        return view;
    }
    private void loadAssignment() {
        Common.progressDialogShow(ctx);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        String HOMEWORK_URL = Common.GetWebServiceURL()+"videolectures.php";
        //String HOMEWORK_URL = "http://www.zocarro.net/zocarro_mobile_app/ws/videolectures.php";

        Log.v("LINK",HOMEWORK_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOMEWORK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(ctx);
                    Log.d("@@@",response );
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        homeWorkDetailsList.add(new VideoLectureModel(
                                jsonObject.getString("sc_id"),
                                jsonObject.getString("cid"),
                                jsonObject.getString("date"),
                                jsonObject.getString("subject"),
                                jsonObject.getString("link"),
                                jsonObject.getString("stime"),
                                jsonObject.getString("etime"),
                                jsonObject.getString("des"),
                                jsonObject.getString("status"),
                                jsonObject.getString("status1"),
                                jsonObject.getString("linkyt"),
                                jsonObject.getString("id")

                        ));
                    }
                        // loadTeacherData(t_id);
                        VideoLectureAdapter adapter = new VideoLectureAdapter(ctx, homeWorkDetailsList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx.getApplicationContext());
                        rechomework.setLayoutManager(mLayoutManager);
                        rechomework.setItemAnimator(new DefaultItemAnimator());
                        rechomework.setAdapter(adapter);


                } catch (JSONException e) {
                    Common.progressDialogDismiss(ctx);
                    Toast.makeText(ctx, "Something went wrong please try again later", Toast.LENGTH_LONG).show();
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
                params.put("sc_id",sc_id.toUpperCase());
                params.put("cid",class_id);
                Log.d("##",params.toString());

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
