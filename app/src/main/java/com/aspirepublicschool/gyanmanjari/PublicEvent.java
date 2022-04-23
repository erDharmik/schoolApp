package com.aspirepublicschool.gyanmanjari;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
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
 * {@link PublicEvent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PublicEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublicEvent extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recpublic;
    Context ctx;
    ArrayList<PublicEventsModel> publicEventsList=new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PublicEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PublicEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static PublicEvent newInstance(String param1, String param2) {
        PublicEvent fragment = new PublicEvent();
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
        View v=inflater.inflate(R.layout.fragment_public_event, container, false);
        recpublic=v.findViewById(R.id.recpublic);
        ctx=getContext();
        loadAnnouncement();
       /* PublicEventsModel p1=new PublicEventsModel("maths","25/12/2019","Chapter1","asasdasdsassassassssasa","abc",1);
        publicEventsList.add(p1);
        PublicEventsModel p2=new PublicEventsModel("maths","25/12/2019","Chapter1","asasdasdsassassassssasa","abc",2);
        publicEventsList.add(p2);
        PublicEventsModel p3=new PublicEventsModel("maths","25/12/2019","Chapter1","asasdasdsassassassssasa","abc",3);
        publicEventsList.add(p3);
        PublicEventsAdapter adapter = new PublicEventsAdapter(getActivity().getApplicationContext(),publicEventsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recpublic.setLayoutManager(mLayoutManager);
        recpublic.setItemAnimator(new DefaultItemAnimator());
        recpublic.setAdapter(adapter);*/
        return v;
    }
    private void loadAnnouncement() {
        //Common.progressDialogShow(ctx);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String sc_id = preferences.getString("sc_id","none").toUpperCase();
        String URL_ANNOUNCEMENT = Common.GetWebServiceURL()+"announcement.php" ;
        // Log.v("announcement",URL_ANNOUNCEMENT);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_ANNOUNCEMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Common.progressDialogDismiss(ctx);
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject jsonObject = array.getJSONObject(i);

                        publicEventsList.add(new PublicEventsModel(
                                jsonObject.getString("img"),
                                jsonObject.getString("header"),
                                jsonObject.getString("date"),
                                jsonObject.getString("place"),
                                jsonObject.getString("weblink")


                        ));
                    }
                    PublicEventsAdapter adapter = new PublicEventsAdapter(ctx,publicEventsList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx);
                    recpublic.setLayoutManager(mLayoutManager);
                    recpublic.setItemAnimator(new DefaultItemAnimator());
                    recpublic.setAdapter(adapter);
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
                        loadAnnouncement();
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
