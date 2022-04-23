package com.aspirepublicschool.gyanmanjari.internet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import com.aspirepublicschool.gyanmanjari.R;

public class TransparentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);

        final AlertDialog.Builder Checkbuilder = new AlertDialog.Builder(this);
        Checkbuilder.setIcon(R.drawable.nointernet);
        Checkbuilder.setTitle("Error!");
        Checkbuilder.setMessage("Check Your Internet Connection.");
        Checkbuilder.setCancelable(false);

        Checkbuilder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //closes dialog
//                onReceive(context, intent);
                finish();
            }
        });
        AlertDialog alert = Checkbuilder.create();
        alert.show();
    }
}