package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.DoubtSolving.ChatAdapter;
import com.aspirepublicschool.gyanmanjari.DoubtSolving.NewChatActivity;
import com.aspirepublicschool.gyanmanjari.DoubtSolving.TeacherDoubt;
import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Chat.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Chat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Chat extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recteacher;
//    FloatingActionButton newChat;
    ArrayList<TeacherDoubt> teacherDoubts=new ArrayList<>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Chat() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Chat.
     */
    // TODO: Rename and change types and number of parameters
    public static Chat newInstance(String param1, String param2) {
        Chat fragment = new Chat();
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
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recteacher=view.findViewById(R.id.recteacher);
//        newChat=view.findViewById(R.id.newChat);
        SendRequest();

//        newChat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    Intent intent = new Intent(getContext(), NewChatActivity.class);
//                    startActivity(intent);
//            }
//        });

        return view;
    }
    private void SendRequest() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String sc_id = preferences.getString("sc_id","none").toLowerCase();
        final String class_id = preferences.getString("class_id","none");
        String WebServiceUrl= Common.GetWebServiceURL()+"getTeacher.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("aa", response);
                    JSONArray res=new JSONArray(response);
                    int asize=res.length();
                    for(int i=0;i<asize;i++) {
                        JSONObject object=res.getJSONObject(i);
                        teacherDoubts.add(new TeacherDoubt(object.getString("t_id"),
                                object.getString("t_fname")+" "+object.getString("t_lname"),
                                object.getString("t_img"),
                                object.getString("t_cno"),
                                object.getString("subject")));


                    }
                    ChatAdapter adapter=new ChatAdapter(getContext(), teacherDoubts);
                    recteacher.setLayoutManager(new LinearLayoutManager(getContext()));
                    recteacher.addItemDecoration(new DividerItemDecoration(recteacher.getContext(), DividerItemDecoration.VERTICAL));
                    recteacher.setAdapter(adapter);



                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(getContext());
                Toast.makeText(getContext(), R.string.no_connection_toast, Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id",sc_id);
                data.put("c_id",class_id);
                Log.d("sss",data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
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
