package com.aspirepublicschool.gyanmanjari;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Common {
  // public final static String IP = "http://www.zocarro.net/school_mobile";


    //public final static String MAIN_URL = "https://gyanmanjarividyapith.net";

    //run on localhost
    public final static String MAIN_URL = "https://mrawideveloper.com/gyanmanfarividyapith.net";
//    public final static String MAIN_URL = "http://192.168.43.191";
//    public final static String  IP = "http://192.168.0.106";
    public final static String DEFAULT_ERROR_MESSAGE = "OOPS, Something went wrong we are trying to findout problem, " +
            "please come after sometime";
    public static ProgressDialog p1;
    public static String GetBaseURL()
    {
        return MAIN_URL+ "/zocarro_mobile_app/";
    }
    public static String GetWebServiceURL(){
        return GetBaseURL() + "ws/";
    }
    public static String GetTimeoutMessage()
    {
        return  "Attention!, your server is not responding. check following things and retry" +
                "\n 1) check wamp/ampp/xampp is running in background" +
                "\n 2) check your laptop/pc and mobile is in same network" +
                "\n 3) check ip address of your computer and match webservice url ip address, both ip must be same" +
                "\n 4) check your web service spelling, may be it is incorrect";
    }
    public static void ShowError(Context ctx, String error)
    {
        MyLog.p(error);
        AlertDialog.Builder b1 = new AlertDialog.Builder(ctx);
        b1.setTitle("Error ");
        if(MyLog.ISDEBUG==true)
            b1.setMessage(error);
        else
            b1.setMessage(DEFAULT_ERROR_MESSAGE);
        b1.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        b1.create().show();
    }

  /*  public static String GetBaseImageURL()
    {
        return MAIN_URL+"/school/images/";
    }*/
   public static String GetBaseImageURL()
   {
       return "https://mrawideveloper.com/gyanmanfarividyapith.net/zocarro/image/";
   }

    public static void progressDialogShow(Context context){
        p1 = new ProgressDialog(context);
        p1.setTitle("Processing, please wait");
        p1.setCancelable(false);
        p1.show();
    }
    public static void progressDialogDismiss(Context context){
        // p1.setTitle("Processing, please wait");
        if (p1.isShowing())
        {
            p1.dismiss();
        }

    }
    public static void checkvalidlogin(final Context context)
    {
        try {
            String Webserviceurl = Common.GetWebServiceURL() + "logincheck.php";
            final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            final String sc_id = mPrefs.getString("sc_id", "none");
            final String stu_id = mPrefs.getString("stu_id", "none");
            final String token = mPrefs.getString("token", "none");
            StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("thread", response);
                    try {
                        JSONObject object = new JSONObject(response);
                        String message = object.getString("message");
                        if (message.equals("Logout")) {
                            Logoutactivedevice(context);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("stu_id", stu_id);
                    data.put("token", token);
                    Log.d("threaddata", data.toString());
                    return data;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
            Volley.newRequestQueue(context).add(request);
        }
        catch (Exception e)
        {
            Toast.makeText(context, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
        }

    }
    public static void Logoutactivedevice(final Context context) {
        Common.progressDialogShow(context);
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        final String sc_id = mPrefs.getString("sc_id", "none");
        final String stu_id = mPrefs.getString("sc_id", "none");
        String Webserviceurl = Common.GetWebServiceURL() + "logoutthread.php";
        StringRequest request = new StringRequest(StringRequest.Method.POST, Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("aaa", response);
                    Common.progressDialogDismiss(context);
                    JSONObject object = new JSONObject(response);
                    String messsage = object.getString("message");
                    if (messsage.equals("Submitted")) {
                        MainActivity.timer.cancel();
                        context.startActivity(new Intent(context,Login.class));
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("stu_id");
                        editor.remove("status");
                        editor.remove("class_id");
                        editor.remove("stu_name");
                        editor.remove("std");
                        editor.remove("sc_id");
                        editor.remove("st_dp");
                        editor.remove("city");
                        editor.remove("address");
                        editor.remove("mobile");
                        editor.commit();


                    } else {
                        Toast.makeText(context, R.string.no_connection_toast, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, R.string.no_connection_toast, Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("stu_id", stu_id);
                data.put("sc_id", sc_id);
                return data;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
        Volley.newRequestQueue(context).add(request);
    }

}
