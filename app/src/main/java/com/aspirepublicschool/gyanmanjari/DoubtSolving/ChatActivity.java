package com.aspirepublicschool.gyanmanjari.DoubtSolving;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspirepublicschool.gyanmanjari.Common;
import com.aspirepublicschool.gyanmanjari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends AppCompatActivity {
    String fetch_url = Common.GetWebServiceURL() + "fetch_chat.php";
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    Button sendButton;
    EditText editText;
    AlphaAnimation sendClick = new AlphaAnimation(1F, 0.8F);
    ArrayAdapter<String> adapter;
    ArrayList<ChatData> data = new ArrayList<>();
    Context ctx=this;

    Timer timer;
    TimerTask timerTask;
    String title, time;
    static String senderId, sc_id, class_id, stu_id, tname,number;
    String SendingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        sc_id = preferences.getString("sc_id", "none").toUpperCase();
        class_id = preferences.getString("class_id", "none");
        SendingID = preferences.getString("stu_id", "none");

        senderId = getIntent().getExtras().getString("t_id");
        tname = getIntent().getExtras().getString("tname");
        number = getIntent().getExtras().getString("mno");

        Toast.makeText(getApplicationContext(), senderId, Toast.LENGTH_SHORT).show();

        setTitle(tname);
        allocatememory();
        //Disable soft keyboard from appearing by default
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        fetchMessage();

        //provide focus to editText
        editText.requestFocus();
//        repeat();
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                view.startAnimation(sendClick);

                //To avoid sending empty messages
                if (!editText.getText().toString().trim().equals("")) {
                    if (Utils.isNetworkAvailable(ChatActivity.this)) {
//                        sendMessage(SendingID, senderId, String.valueOf(Html.fromHtml(editText.getText().toString().trim())));

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                            time = sdf.format(new Date());
                            sendMessage();

//                        fetchMessage(SendingID, senderId);
                        fetchMessage();

                    } else
                        Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(), "Enter Message First", Toast.LENGTH_SHORT).show();
                }

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                try {
                    swipeRefreshLayout.setRefreshing(false);
                    if (Utils.isNetworkAvailable(ChatActivity.this)) {
                        fetchMessage();
//                        fetchMessage(SendingID, senderId);
                    } else {
                        Toast.makeText(getBaseContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (Utils.isNetworkAvailable(ChatActivity.this)) {
                    fetchMessage();
//                    fetchMessage(SendingID, senderId);
                } else {
                    Toast.makeText(getBaseContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void allocatememory() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        sendButton = (Button) findViewById(R.id.buttonSend);
        editText = (EditText) findViewById(R.id.chatText);
        recyclerView = (RecyclerView) findViewById(R.id.chatListView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void sendMessage(String sender, String receiver, String message) {
        RequestProcessor requestProcessor = new RequestProcessor(this, Request.Method.GET);

        requestProcessor.setListener(new RequestProcessorListener() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(ChatActivity.this, "Sent", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoading() {

            }

            @Override
            public void onError(VolleyError error) {
                String errorMessage = Utils.getErrorMessage(error);
                if (errorMessage != null) {
                    Log.d("Send", "onError: " + errorMessage);
                }
                Toast.makeText(ChatActivity.this, R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
            }
        });
        requestProcessor.execute(GenerateUrl.getPrivateSendMsgUrl(sender, receiver, message));
    }




    public void sendMessage(){
        String Webservice = Common.GetWebServiceURL() + "sendMessage.php";

        StringRequest request=new StringRequest(StringRequest.Method.POST, Webservice, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equalsIgnoreCase("true")){
                    editText.setText("");
                    Toast.makeText(getApplicationContext(), "Sent", Toast.LENGTH_SHORT).show();
                    fetchMessage();
//                    fetchMessage(SendingID, senderId);
                }else{
                    Toast.makeText(getApplicationContext(), R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(ChatActivity.this);
                Toast.makeText(ChatActivity.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("role",SendingID);
                data.put("target",senderId);
                data.put("message",editText.getText().toString().trim());
                data.put("time", time);
                data.put("title", tname);
                return data;
            }
        };
        Volley.newRequestQueue(ChatActivity.this).add(request);

    }

    public void fetchMessage(){
        String Webservice = "https://mrawideveloper.com/houseofknowledge.net/zocarro_mobile_app/ws/fetchMessage.php";

        StringRequest request=new StringRequest(StringRequest.Method.POST, Webservice, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                data.clear();
                String re = response;
                try {
                    JSONArray array = new JSONArray(re);
                    Log.d("$$$$$", re);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        data.add(new ChatData(object.getString("message"),
                                                object.getString("time"),
                                                object.getString("role"),
                                                object.getString("target")));

                    }
                    GMChatAdapter adapter = new GMChatAdapter(getApplicationContext(), data);

                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Common.progressDialogDismiss(ChatActivity.this);
                Toast.makeText(ChatActivity.this, R.string.no_connection_toast, Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data=new HashMap<>();
                data.put("role",SendingID);
                data.put("target",senderId);
                return data;
            }
        };
        Volley.newRequestQueue(ChatActivity.this).add(request);

    }





    private void repeat() {
        timer = new Timer();
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {

                    try {
                        if (Utils.isNetworkAvailable(ChatActivity.this)) {
                            fetchMessage();
//                            fetchMessage(SendingID, senderId);
                        } else {
                            Toast.makeText(getBaseContext(), "No network available", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        }
        timer.scheduleAtFixedRate(timerTask, 0, 5000);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (timer != null){
//            timer.cancel();
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (timer != null){
//            timer.cancel();
//            timer.scheduleAtFixedRate(timerTask, 0, 5000);
//        }
    }

//    private void fetchMessage(String sender, String reciver) {
//        String Webservice = fetch_url;
//        Log.d("aaa", Webservice);
//        JsonArrayRequest request = new JsonArrayRequest(Webservice,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        data.clear();
//                        String re = response.toString();
//                        try {
//                            JSONArray array = new JSONArray(re);
//                            Log.d("$$$$$", re);
//                            for (int i = 0; i < array.length(); i++) {
//
//                                JSONObject object = array.getJSONObject(i);
//                                data.add(new ChatData(object.getString("message"), object.getString("time"),
//                                        object.getString("role"), object.getString("target")));
//
//                            }
//                            GMChatAdapter adapter = new GMChatAdapter(getApplicationContext(), data);
//
//                            recyclerView.setAdapter(adapter);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> data = new HashMap<>();
//                data.put("role",SendingID);
//                data.put("target",senderId);
//                return data;
//            }
//        };
//        request.setRetryPolicy(new DefaultRetryPolicy(2000, 3, 1));
//        Volley.newRequestQueue(ChatActivity.this).add(request);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.attachment, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_attachment:
                intent = new Intent(ctx, Insert_Doubt.class);
                intent.putExtra("t_id", senderId);
                intent.putExtra("t_name", tname);
                startActivity(intent);
                // do something
                return true;
            case R.id.nav_call:
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                int checkPermission = ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE);
                startActivity(intent);
                // do something
                return true;
            case R.id.nav_addattach:
                intent = new Intent(ctx, DoubtSolving.class);
                intent.putExtra("t_id", senderId);
                intent.putExtra("t_name", tname);
                startActivity(intent);
                // do something
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
