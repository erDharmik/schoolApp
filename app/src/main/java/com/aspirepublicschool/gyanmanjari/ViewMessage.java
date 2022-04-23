package com.aspirepublicschool.gyanmanjari;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aspirepublicschool.gyanmanjari.R;

public class ViewMessage extends AppCompatActivity {
String Sender,Title,Message,Time;
TextView sender,title,message,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);
        getSupportActionBar().setTitle("Query");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Sender = getIntent().getExtras().getString("sender");
        Title = getIntent().getExtras().getString("title");
        Time = getIntent().getExtras().getString("time");
        Message = getIntent().getExtras().getString("message");
        Log.d("sss",Title+Message);
        Title= String.valueOf(Html.fromHtml(Title));
        Message= String.valueOf(Html.fromHtml(Message));


        sender = findViewById(R.id.sender);
        title = findViewById(R.id.title);
        time = findViewById(R.id.time);
        message = findViewById(R.id.message);

        sender.setText(Sender);
        title.setText(Title);
        time.setText(Time);
        message.setText(Message);
    }
}
