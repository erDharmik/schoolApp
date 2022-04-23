package com.aspirepublicschool.gyanmanjari.DoubtSolving;

import android.net.Uri;
import android.util.Log;

public class GenerateUrl {

    public static final String BASE_URL = "gyanmanjarividyapith.net/zocarro_mobile_app/ws/";
    public static final String SCHEMA = "https";

    public static String getPrivateFetchMsgUrl(String sender, String receiver){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEMA)
                .encodedAuthority(BASE_URL)
                .appendPath("fetch_chat.php")
                .appendQueryParameter("role", sender)
                .appendQueryParameter("target", receiver);
        return builder.build().toString();
    }

    public static String getPrivateSendMsgUrl(String sender, String receiver, String msg){
        Uri.Builder builder = new Uri.Builder();
        Log.d("Send", "getPrivateSendMsgUrl: role: "+sender+" target: "+receiver+" message: "+msg);
        builder.scheme(SCHEMA)
                .encodedAuthority(BASE_URL)
                .appendPath("insert_chat.php")
                .appendQueryParameter("role", sender)
                .appendQueryParameter("target", receiver)
                .appendQueryParameter("message", msg);
        return builder.build().toString();
    }

}
