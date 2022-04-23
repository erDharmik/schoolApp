package com.aspirepublicschool.gyanmanjari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ViewSentMessage extends AppCompatActivity {
    RecyclerView recsendmsg;
    int total;
    String target,title,t_fname,t_lname,msg,time;
    ArrayList<ViewSent> SendList=new ArrayList<>();
    Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sent_message);
        allocatememory();

        if (CheckNetwork.isInternetAvailable(getApplicationContext())) {
            SendRequest();
        }
        else {
            Intent i = new Intent(ViewSentMessage.this, NoInternetActivity.class);
            startActivity(i);
            finish();
        }

    }
    private void SendRequest() {
        Common.progressDialogDismiss(this
        );
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String role=preferences.getString("stu_id","none");
        final String sc_id=preferences.getString("sc_id","none").toLowerCase();
        String Webserviceurl=Common.GetWebServiceURL()+"sendmessage.php";

        Log.d("abc",Webserviceurl);
        StringRequest request=new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("shubh", response.toString());
                    JSONArray res=new JSONArray(response);
                    String error = res.getJSONObject(0).getString("error");
                    if (error.equals("no error") == false) {
                    }//   Common.ShowDialog(ctx, error);
                    else //no error
                    {
                        total = res.getJSONObject(1).getInt("total");
                        if (total != 0) {
                            //   Common.ShowDialog(ctx, "No Product Found");
                            int asize = res.length();
                            for (int i = 2; i < asize; i++) {
                                        /*
                                         {

                                    "order_id": "ORDER1",
                                    "sin": "PSIN4",
                                    "img1": "341566541.jpg",
                                    "label": "Abc Color papers",
                                    "price": "10",
                                    "discount": "1"

                                  }
                                  */
                                JSONObject current = res.getJSONObject(i);
                                target = current.getString("target");
                                title = String.valueOf(Html.fromHtml(current.getString("title")));
                                msg = String.valueOf(Html.fromHtml(current.getString("message")));
                                time = current.getString("time");
                                t_fname = current.getString("t_fname");
                                t_lname = current.getString("t_lname");


                                ViewSent s = new ViewSent(target,title,t_fname,t_lname,msg,time);
                                SendList.add(s);

                            }
                            recsendmsg.setHasFixedSize(true);
                            recsendmsg.setLayoutManager(new LinearLayoutManager(ctx));
                            ViewSentAdapter adapter = new ViewSentAdapter(ctx ,SendList);
                            recsendmsg.setItemAnimator(new DefaultItemAnimator());
                            recsendmsg.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
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
                data.put("role",role);
                data.put("sc_id",sc_id);
                return data;
            }
        };
        /*JsonArrayRequest request = new JsonArrayRequest(Webserviceurl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Common.progressDialogDismiss(ViewSentMessage.this);
                try {
                    Log.d("shubh", response.toString());
                    String error = response.getJSONObject(0).getString("error");
                    if (error.equals("no error") == false) {
                    }//   Common.ShowDialog(ctx, error);
                    else //no error
                    {
                        total = response.getJSONObject(1).getInt("total");
                        if (total != 0) {
                            //   Common.ShowDialog(ctx, "No Product Found");
                            int asize = response.length();
                            for (int i = 2; i < asize; i++) {
                                        *//*
                                         {

                                    "order_id": "ORDER1",
                                    "sin": "PSIN4",
                                    "img1": "341566541.jpg",
                                    "label": "Abc Color papers",
                                    "price": "10",
                                    "discount": "1"

                                  }
                                  *//*
                                JSONObject current = response.getJSONObject(i);
                                target = current.getString("target");
                                title = current.getString("title");
                                t_fname = current.getString("t_fname");
                                t_lname = current.getString("t_lname");

                                ViewSent s = new ViewSent(target,title,t_fname,t_lname);
                                SendList.add(s);

                            }
                            recsendmsg.setHasFixedSize(true);
                            recsendmsg.setLayoutManager(new LinearLayoutManager(ctx));
                            ViewSentAdapter adapter = new ViewSentAdapter(ctx ,SendList);
                            recsendmsg.setItemAnimator(new DefaultItemAnimator());
                            recsendmsg.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Common.ShowDialog(ctx);
            }
        });*/
        Volley.newRequestQueue(ctx).add(request);
    }

    private void allocatememory() {
        recsendmsg=findViewById(R.id.recsendmsg);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recsendmsg.getContext(),
                DividerItemDecoration.VERTICAL);
        recsendmsg.addItemDecoration(dividerItemDecoration);
    }
}
