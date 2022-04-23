package com.aspirepublicschool.gyanmanjari;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoticeboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoticeboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoticeboardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recnoticeboard;
    ArrayList<NoticeBoard> noticeBoardsList=new ArrayList<>();
    Context ctx;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NoticeboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoticeboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoticeboardFragment newInstance(String param1, String param2) {
        NoticeboardFragment fragment = new NoticeboardFragment();
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
       View v= inflater.inflate(R.layout.fragment_noticeboard, container, false);
        recnoticeboard=v.findViewById(R.id.recnotice);
        recnoticeboard.addItemDecoration(new DividerItemDecoration(recnoticeboard.getContext(), DividerItemDecoration.VERTICAL));
        ctx=getContext();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        loadNoticeboard();
       return v;
    }
    private void loadNoticeboard() {
        Common.progressDialogShow(ctx);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String sc_id = mPrefs.getString("sc_id","none").toUpperCase();
        Log.v("VVVV",sc_id);
        String URL_NOTICEBOARD = Common.GetWebServiceURL()+"notice.php" ;
       // String URL_NOTICEBOARD = +"noticeboard.php" ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_NOTICEBOARD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(ctx);
                try {
                    Log.d("AAA", response);
                    JSONArray array = new JSONArray(response);
                    noticeBoardsList.clear();
                    for (int i = 0; i < array.length(); i++) {


                        JSONObject jsonObject = array.getJSONObject(i);

                        noticeBoardsList.add(new NoticeBoard(
                                jsonObject.getString("doc"),
                                jsonObject.getString("date"),
                                jsonObject.getString("notice_header"),jsonObject.getString("des")

                        ));
                        Log.d("abcaaa",noticeBoardsList.toString());
                    }

                    NoticeBoardAdapter adapter = new NoticeBoardAdapter(ctx, noticeBoardsList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx);
                    recnoticeboard.setLayoutManager(mLayoutManager);
                    recnoticeboard.setItemAnimator(new DefaultItemAnimator());
                    recnoticeboard.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(ctx);
                AlertDialog.Builder b1 = new AlertDialog.Builder(ctx);
                b1.setTitle("Error ");
                b1.setMessage(Common.GetTimeoutMessage());
                b1.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loadNoticeboard();
                    }
                });
                b1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                b1.create().show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sc_id",sc_id.toUpperCase());
                Log.d("AAA",params.toString());
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
