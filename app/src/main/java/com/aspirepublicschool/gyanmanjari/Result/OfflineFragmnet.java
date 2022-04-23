package com.aspirepublicschool.gyanmanjari.Result;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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


public class OfflineFragmnet extends Fragment {

    RecyclerView result_recyclerView;
    ArrayList<Result> resultArrayList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_homework2, container, false);
        result_recyclerView = v.findViewById(R.id.rechomework);
        SendRequest();
        return  v;
    }
    private void SendRequest()
    {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String class_id = mPrefs.getString("class_id","none");
        final String sc_id = mPrefs.getString("sc_id","none");
        final String stu_id = mPrefs.getString("stu_id","none");
        String Webserviceurl= Common.GetWebServiceURL()+"classnewResult.php";
        // String Webserviceurl="https://www.zocarro.com/zocarro_mobile_app/ws/"+"classResult.php";
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                resultArrayList.clear();
                try
                {
                    Log.d("AAA1", response);
                    JSONArray array=new JSONArray(response);
                    for (int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        resultArrayList.add(new Result(object.getString("total"),
                                object.getString("obtain"),
                                object.getString("subject"),
                                object.getString("t_date"),
                                object.getString("tst_id"),
                                object.getString("status"),
                                object.getString("t_type"), "NA"));
                    }
                    OfflineResultAdapter adapter=new OfflineResultAdapter(getActivity(), resultArrayList);

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    result_recyclerView.setLayoutManager(mLayoutManager);
                    result_recyclerView.setAdapter(adapter);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> data=new HashMap<>();
                data.put("sc_id", sc_id);
                data.put("cid", class_id);
                data.put("stu_id",stu_id);
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(getContext()).add(request);
    }

}