package com.aspirepublicschool.gyanmanjari;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TimeChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Date/Time changed ", Toast.LENGTH_LONG).show();
    }
}