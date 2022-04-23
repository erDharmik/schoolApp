package com.aspirepublicschool.gyanmanjari.Stationery.ReturnReplace;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.aspirepublicschool.gyanmanjari.R;
import com.aspirepublicschool.gyanmanjari.uniform.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReturnedStatus extends AppCompatActivity {
    ImageView imgproduct;
    TextView txtproductname,txtshopname,txtorderid,txtorderdate;
   // VerticalStepView step_view0;
    String order_id;
    //List<Step> stepcount = new ArrayList<>();
    String img_url;
    String cat;
    String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returned_status);
        order_id=getIntent().getExtras().getString("order_id");
        allocatememory();
        SendRequest();

    }

    private void SendRequest() {
        String WebserviceUrl= com.aspirepublicschool.gyanmanjari.Common.GetWebServiceURL()+"returnedPackageDetails.php";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String uid = preferences.getString("stu_id", "none");
        final String sc_id = preferences.getString("sc_id", "none").toUpperCase();
        final String sc_std = preferences.getString("std", "none");
        final String med = preferences.getString("med", "none");
        final String gender = preferences.getString("gender", "none");
        StringRequest request=new StringRequest(StringRequest.Method.POST,WebserviceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                /*{ "s_id": "ZCIN2",
                        "order_id": "ODM585",
                        "sin": "USIN20",
                        "quantity": "1",
                        "amount": "200",
                        "reason": "wrong book",
                        "r_status": "1",
                        "img1": "ZCIN2-USIN201.jpg",
                        "label": "Uniform male",
                        "user_id": "SCIDN14STIDN2",
                        "cat": "Full Uniform",
                        "date": "12-03-2020"
                }
            */
                Log.d("%%%",response);
                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        txtorderdate.setText(object.getString("date"));
                        txtorderid.setText(object.getString("order_id"));
                        txtproductname.setText(object.getString("label"));
                        txtshopname.setText(object.getString("ss_name"));
                         img_url= "https://www.aspirepublicschool.net/zocarro/image"+"/stationery/book/"+"textbook"+"/"+object.getString("img1");
                         cat=object.getString("cat");
                        image=object.getString("img1");

                       /* stepcount.add(new Step("Return Placed", Step.State.CURRENT));
                        stepcount.add(new Step("Return Confirmed"));
                        stepcount.add(new Step("Return Rejected"));
                        stepcount.add(new Step("Return Shipped"));
                        //stepcount.add(new Step("Order Return"));

                        step_view0.setSteps(stepcount);

                        step_view0 // Also applies to VerticalStepView
                                // Drawables
                                .setCompletedStepIcon(AppCompatResources.getDrawable(ReturnedStatus.this, R.drawable.ic_check))
                                .setNotCompletedStepIcon(AppCompatResources.getDrawable(ReturnedStatus.this, R.drawable.ic_uncheck))
                                .setCurrentStepIcon(AppCompatResources.getDrawable(ReturnedStatus.this, R.drawable.ic_attention))
                                // Text colors
                                .setCompletedStepTextColor(Color.DKGRAY) // Default: Color.WHITE
                                .setNotCompletedStepTextColor(Color.DKGRAY) // Default: Color.WHITE
                                .setCurrentStepTextColor(Color.BLACK) // Default: Color.WHITE
                                // Line colors
                                .setCompletedLineColor(Color.parseColor("#ea655c")) // Default: Color.WHITE
                                .setNotCompletedLineColor(Color.parseColor("#eaac5c")) // Default: Color.WHITE
                                // Text size (in sp)
                                .setTextSize(15) // Default: 14sp
                                // Drawable radius (in dp)
                                .setCircleRadius(15) // Default: ~11.2dp
                                // Length of lines separating steps (in dp)
                                .setLineLength(50).setReverse(false); // Default: ~34dp*/
                        String status=object.getString("r_status");
                        if (status.equals("0")) {
                           /* stepcount.get(0).setState(Step.State.COMPLETED);
                            stepcount.get(1).setState(Step.State.CURRENT);
                            step_view0.setSteps(stepcount);*/

                        } else if (status.equals("1")) {
                            /*stepcount.get(0).setState(Step.State.COMPLETED);
                            stepcount.get(1).setState(Step.State.COMPLETED);
                            stepcount.get(2).setState(Step.State.CURRENT);
                            step_view0.setSteps(stepcount);*/
                        } else if (status.equals("2")) {
                          /*  stepcount.get(0).setState(Step.State.COMPLETED);
                            stepcount.get(1).setState(Step.State.COMPLETED);
                            stepcount.get(2).setState(Step.State.COMPLETED);
                            stepcount.get(3).setState(Step.State.CURRENT);
                            step_view0.setSteps(stepcount);*/


                        } else if (status.equals("3")) {
                           /* stepcount.get(0).setState(Step.State.COMPLETED);
                            stepcount.get(1).setState(Step.State.COMPLETED);
                            stepcount.get(2).setState(Step.State.COMPLETED);
                            stepcount.get(3).setState(Step.State.COMPLETED);
                            step_view0.setSteps(stepcount);
*/

                        }

                    }
                    if(cat.equals("Full Uniform")) {
                        img_url = Common.GetBaseImageURL() + "/uniform/" + image;
                        Log.d("img",img_url);

                    }
                    else if(cat.equals("Shirt"))
                    {
                        img_url = Common.GetBaseImageURL() + "/shirt/" + image;
                        Log.d("img",img_url);

                    }
                    else if(cat.equals("Pant"))
                    {
                        img_url = Common.GetBaseImageURL() + "/pant/" + image;
                        Log.d("img",img_url);

                    }
                    else if(cat.equals("Other Items"))
                    {
                        img_url = Common.GetBaseImageURL() + "/otheritems/" + image;
                        Log.d("img",img_url);

                    }
                    Glide.with(ReturnedStatus.this).load(img_url).into(imgproduct);
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
                data.put("order_id",order_id);
                data.put("uid",uid);
                Log.d("^^",data.toString());
                return data;
            }
        };
        Volley.newRequestQueue(ReturnedStatus.this).add(request);
    }

    private void allocatememory() {
        imgproduct=findViewById(R.id.imgproduct);
        txtproductname=findViewById(R.id.txtproductname);
        txtshopname=findViewById(R.id.txtshopname);
        txtorderid=findViewById(R.id.txtorderid);
        txtorderdate=findViewById(R.id.txtorderdate);
       // step_view0=findViewById(R.id.step_view0);
    }
}
