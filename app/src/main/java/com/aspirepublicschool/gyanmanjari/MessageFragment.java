package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
 * {@link MessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText edttitle,edtmessage;
    Button Submit,sent_message;
    Context ctx;
    String target,target_id;

    Spinner spnTeacher;
    ArrayList<Teacher> teacherList =new ArrayList<>();
    ArrayList<String> teachList=new ArrayList<>();
    String tfname,tlname,tid;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
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
        View v= inflater.inflate(R.layout.fragment_message, container, false);
        spnTeacher=v.findViewById(R.id.spnTeacher);
        edttitle=v.findViewById(R.id.edttitle);
        ctx=getContext();
        edtmessage=v.findViewById(R.id.edtmessage);
        Submit=v.findViewById(R.id.Submit);
        sent_message = v.findViewById(R.id.sent_message);

        loadTeacher();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edttitle.getText().toString().isEmpty() || edtmessage.getText().toString().isEmpty()){
                    Toast.makeText(ctx,"Please Enter Valid Data",Toast.LENGTH_SHORT).show();
                }
                else {
                    SendRequest();
                }
            }
        });
        sent_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx,ViewSentMessage.class));
            }
        });
        spnTeacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                target= spnTeacher.getItemAtPosition(spnTeacher.getSelectedItemPosition()).toString();
                target_id = teacherList.get(position).getTid();
                Log.v("TID",target_id);
                Log.v("ROLE",target);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }


    private void loadTeacher() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String sc_id = preferences.getString("sc_id","none").toLowerCase();
        String WebServiceUrl=Common.GetWebServiceURL()+"getTeacher.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST, WebServiceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray res=new JSONArray(response);
                    String error=res.getJSONObject(0).getString("error");
                    if(error.equals("no error")==false)
                    {

                    }
                    else
                    {
                        int asize=res.length();
                        for(int i=1;i<asize;i++) {
                            JSONObject object=res.getJSONObject(i);
                            tid=object.getString("t_id");
                            tfname=object.getString("t_fname");
                            tlname=object.getString("t_lname");
                            Teacher t=new Teacher(tfname,tlname,tid);
                            teacherList.add(t);
                            teachList.add(tfname+" "+tlname);

                        }
                        spnTeacher.setAdapter(new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_dropdown_item,teachList));


                    }
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
                data.put("sc_id",sc_id.toUpperCase());
                return data;
            }
        };
        Volley.newRequestQueue(ctx).add(request);
    }



    private void SendRequest() {
        Common.progressDialogShow(ctx);
        String Webserviceurl=Common.GetWebServiceURL()+"communication.php";
        //   Log.d("web",Webserviceurl);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        final String stu_id = preferences.getString("stu_id","none");
        StringRequest stringRequest=new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Common.progressDialogDismiss(ctx);
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("success")){
                        Toast.makeText(ctx,"done",Toast.LENGTH_SHORT).show();

                        edttitle.setText("");
                        edtmessage.setText("");
                    }
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
                Map<String, String> data = new HashMap<>();
                /*role=USIDN1&target=abc@aaa.com&title=aaa&message=sdfdfasdff*/
                data.put("role", stu_id);
                data.put("target",target_id);
                data.put("title", edttitle.getText().toString());
                data.put("msg",edtmessage.getText().toString());
                Log.d("data",data.toString());
                return data;
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
