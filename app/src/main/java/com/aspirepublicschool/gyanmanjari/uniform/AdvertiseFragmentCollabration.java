package com.aspirepublicschool.gyanmanjari.uniform;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
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
 * {@link AdvertiseFragmentCollabration.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AdvertiseFragmentCollabration#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdvertiseFragmentCollabration extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recadvertisecoll;
    Context ctx;
    ArrayList<ShopBanner> shopBannerArrayList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AdvertiseFragmentCollabration() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdvertiseFragmentCollabration.
     */
    // TODO: Rename and change types and number of parameters
    public static AdvertiseFragmentCollabration newInstance(String param1, String param2) {
        AdvertiseFragmentCollabration fragment = new AdvertiseFragmentCollabration();
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
        View view = inflater.inflate(R.layout.fragment_advertise_fragment_collabration, container, false);
        recadvertisecoll=view.findViewById(R.id.recadvertisecoll);
        ctx=getContext();
        shopBannerArrayList=new ArrayList<>();
        LinearLayoutManager horizontalLayoutManagaer1
                = new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false);
        recadvertisecoll.setLayoutManager(horizontalLayoutManagaer1);
        SendRequest();
        return view;
    }

    private void SendRequest() {
        String Webserviceurl=Common.GetWebServiceURL()+"displaytopratedshop.php";
        Log.d("yrrr",Webserviceurl);
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array=new JSONArray(response);
                    Log.d("shop",response);
                    for(int i=0;i<array.length();i++)
                    {
                       /* {
                            "s_id": "ZCIN4",
                                "ss_name": "Ghogha Circle Clothing Center",
                                "addr": "ghogha circle",
                                "s_img": "90819596971.png"
                        }*/
                        JSONObject object=array.getJSONObject(i);
                        shopBannerArrayList.add(new ShopBanner(object.getString("ss_name"),object.getString("addr"),object.getString("s_id"),object.getString("s_img"),object.getString("s_cont"),object.getDouble("rating")));
                    }
                    AdvertiseShopBannerAdapter adapter=new AdvertiseShopBannerAdapter(ctx,shopBannerArrayList);
                    recadvertisecoll.setAdapter(adapter);
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
                Map<String,String> data=new HashMap<>();
                return data;
            }
        };
        Volley.newRequestQueue(ctx).add(request);
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
