package com.aspirepublicschool.gyanmanjari;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.VideoLectures.VideoTabbed;
import com.aspirepublicschool.gyanmanjari.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private Context mCtx=this;
    private static final String TAG = "MyFirebaseMsgService";


    @Override
    public void onNewToken(String token) {
        Log.d( "Gyan","Refreshed token: " + token);
        final Context ctx = getApplicationContext();
       // FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Gyan", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken(); //return firebase id
                        sendRegistrationToServer(token);

                        //FirebaseMessaging.getInstance().subscribeToTopic("global");
                        FirebaseInstanceId.getInstance().getToken();


                        Log.d("Gyan","firebase regid (token) " + token);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("regid",token);
                        editor.commit();
                        }
                });



    }
  /*  @Override
    public void onNewToken(String token) {
        Log.d("Gyan", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }*/
    private void sendRegistrationToServer(final String token) {
        // TODO: Implement this method to send token to your app server.
       // String Webserviceurl="http://www.zocarro.net/zocarro_mobile_app/ws/updateFirebasetoken.php";
        String Webserviceurl=Common.GetWebServiceURL()+"updateFirebasetoken.php";
        Log.d("AAA", Webserviceurl);
        final SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String s_id = mPrefs.getString("stu_id", "none");
        final String sc_id = mPrefs.getString("sc_id","none");
        StringRequest request=new StringRequest(StringRequest.Method.POST,Webserviceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, response);

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
                data.put("stu_id",s_id);
                data.put("token",token);
                return data;

            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(2000,3,1));
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        /*
        {data={"image":null,"is_background":'false',"payload":{"title":"test","message":"test 2"},
        "title":null,"message":null,"timestamp":"2019-03-10 4:41:55"}}
         */
       /* if (remoteMessage.getData().size() > 0) {
            Log.d("Gyan", "Message data payload: " + remoteMessage.getData());
            JSONObject level1 = new JSONObject(remoteMessage.getData());
            //Log.d(TAG, "level 1 " + level1.toString());
            try
            {
                JSONObject level2 = new JSONObject(level1.getString("data"));
                //Log.d(TAG, "level 2 " + level2.toString());
                JSONObject level3 = level2.getJSONObject("payload");
                String title,message;
                title = level3.getString("title");
                message = level3.getString("message");
                Log.d("Gyan", "title = " + title + " message = " + message);
                Context ctx = getApplicationContext();
                Intent intent = new Intent(ctx, Announcement.class);
                final PendingIntent pIntent = PendingIntent.getActivity(ctx, (int) System.currentTimeMillis(), intent, 0);


              *//*  Notification noti = new Notification.Builder(this)
                        .setContentTitle("FTest")
                        .setContentText("Firebabse notification test")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentIntent(pIntent)
                        .build();

*//*
             *//*   NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, noti);*//*
                Intent NotificationIntent = new Intent(ctx,Announcement.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(ctx,108,NotificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                MyNotificationManager.addNotification(title,message,ctx,pendingIntent,
                        1000,true,true,true);
               *//* NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, noti);*//*
            }
            catch (JSONException e)
            {
                Log.d("Gyan" + " error",e.getMessage());
            }*/
            if (remoteMessage.getData().size() > 0) {
                Log.d("Gyan", "Message data payload: " + remoteMessage.getData());
                JSONObject level1 = new JSONObject(remoteMessage.getData());
                JSONObject level2 = null;
                try {
                    level2 = new JSONObject(level1.getString("data"));
                    //Log.d(TAG, "level 2 " + level2.toString());
                    JSONObject level3 = level2.getJSONObject("payload");
                    String title,message,action;
                    title = level3.getString("title");
                    message = level3.getString("message");
                    action = level3.getString("action");
                    Log.d("Gyan", "title = " + title + " message = " + message+ " action = " + action);
                    sendNotification(title,message,action);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }




    }

    /*  private void sendNotification(String messageBody,String message) {
        Intent intent = new Intent(getApplicationContext(), Announcement.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0 , intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.id.action_settings)
                .setContentTitle(messageBody)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1 , notificationBuilder.build());





    }*/
    private void sendNotification(String messageBody,String message,String action) {
        Intent intent;
        if(action.equals("Notice")) {
             intent = new Intent(this, Announcement.class);
        }
        else
        {
             intent = new Intent(this, VideoTabbed.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/raw/guitar_tone");
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.gm)
                        .setContentTitle(messageBody)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(Uri.parse("android.resource://" + mCtx.getPackageName() + "/" + R.raw.guitar_tone))
                        .setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        MediaPlayer mp= MediaPlayer.create(mCtx, R.raw.guitar_tone);
        mp.start();
        manager.notify(73195, notificationBuilder.build());

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        //notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }


}
