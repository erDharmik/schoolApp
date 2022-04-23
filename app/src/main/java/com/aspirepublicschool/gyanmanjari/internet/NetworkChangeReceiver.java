package com.aspirepublicschool.gyanmanjari.internet;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.aspirepublicschool.gyanmanjari.R;

import static android.content.ContentValues.TAG;

public class NetworkChangeReceiver extends BroadcastReceiver
{
    public boolean isConnected = true;
    String status;
    Context Cnt;
    Activity activity;
    Activity parent;
    AlertDialog alert;

    public NetworkChangeReceiver(Activity a)
    {
        // TODO Auto-generated constructor stub
        parent = a;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {

        activity = (Activity) context;
        status = NetworkUtil.getConnectivityStatusString(context);
        if (status.equals("Not connected to Internet")) {
            //Toast.makeText(context, "Internet connection required", Toast.LENGTH_LONG).show();
        }
        ReturnStatus(status, context);
    }

    public void ReturnStatus(String s, final Context cnt)
    {
        if (s.equals("Mobile data enabled"))
        {
            isConnected = true;
            //Intent intent = new Intent(activity,activity.getClass());
            //activity.startActivity(intent);

        }
        else if (s.equals("Wifi enabled"))
        {
            isConnected = true;

        } else {
            isConnected = false;
            final AlertDialog.Builder builder = new AlertDialog.Builder(cnt);
            // Set the Alert Dialog Message

            builder.setIcon(R.drawable.nointernet);
            builder.setTitle("Error!");
            builder.setMessage("Please Check Your Internet Connection And Try Again.")
                    .setCancelable(false)
                    .setPositiveButton("Try again",
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog,int id)
                                {
                                    onReceive(activity,null);
                                }
                            });
            alert = builder.create();
            alert.show();
        }
    }

    public boolean is_connected()
    {
        return isConnected;
    }
}