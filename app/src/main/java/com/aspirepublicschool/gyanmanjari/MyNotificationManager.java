package com.aspirepublicschool.gyanmanjari;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.aspirepublicschool.gyanmanjari.R;

import java.util.GregorianCalendar;

public class MyNotificationManager
{
    public static void addNotification(String title, String message,
                                       Context ctx,PendingIntent pintent,int UniqueNumber, boolean isSound, boolean isVibrate,boolean isLight)
    {
        GregorianCalendar gc1 = new GregorianCalendar();
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(title)
                        .setWhen(gc1.getTimeInMillis())
                        .setContentText(message);

        builder.setContentIntent(pintent);
        int defaults = 0;
        if(isVibrate)
            defaults= NotificationCompat.DEFAULT_VIBRATE;
        if(isSound)
            defaults |= NotificationCompat.DEFAULT_SOUND;
        if(isLight)
            defaults |= NotificationCompat.DEFAULT_LIGHTS;

        builder.setDefaults(defaults);
        builder.setAutoCancel(true);
        // Add as notification
        NotificationManager manager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(UniqueNumber, builder.build());
    }
}