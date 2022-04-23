package com.aspirepublicschool.gyanmanjari.NewRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspirepublicschool.gyanmanjari.R;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrefSubjectActivity extends AppCompatActivity {

    CheckBox chAll, chMaths, chEnglish, chHindi, chSS, chGuj, chScience, chSanskrit;
    Boolean checkStatus = false;
    String totalsubject = "";
    Button dialogClose;
    List<String> sub = new ArrayList<String>();
    Button prev, cont;
    LinearLayout lnrsimple;
    HashSet<String> hashSet = new HashSet<String>();
    Dialog dialog;
    TextView infotext, mediumtxt ,standardtxt , boardtxt;
    String medium, standard, board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_subject);
        allocateMemory();

        medium = getIntent().getStringExtra("medium");
        standard = getIntent().getStringExtra("standard");
        board = getIntent().getStringExtra("board");

        // Retrieve Values from SharedPrefrences
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        medium = sharedPreferences.getString("medium", "none");
//        standard = sharedPreferences.getString("standard", "none");
//        board = sharedPreferences.getString("board", "none");


        infotext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(PrefSubjectActivity.this);
                dialog.setContentView(R.layout.selected_info_dialog);
//                dialog.getWindow().setLayout(800, 1200);
                dialog.show();

                mediumtxt = dialog.findViewById(R.id.mediumtxt);
                standardtxt = dialog.findViewById(R.id.standardtxt);
                boardtxt = dialog.findViewById(R.id.boardtxt);
                dialogClose = dialog.findViewById(R.id.dialogClose);
                mediumtxt.setText(medium);
                standardtxt.setText(standard);
                boardtxt.setText(board);

                dialogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });

        chAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chAll.isChecked()){
                    chMaths.setChecked(true);
                    chEnglish.setChecked(true);
                    chHindi.setChecked(true);
                    chSS.setChecked(true);
                    chGuj.setChecked(true);
                    chScience.setChecked(true);
                    chSanskrit.setChecked(true);
                    checkStatus = true;
                }else{
                    chMaths.setChecked(false);
                    chEnglish.setChecked(false);
                    chHindi.setChecked(false);
                    chSS.setChecked(false);
                    chGuj.setChecked(false);
                    chScience.setChecked(false);
                    chSanskrit.setChecked(false);
                    checkStatus = false;
                }
            }
        });

//        Intent i = getIntent();
//        medium = i.getStringExtra("medium");
//        standard = i.getStringExtra("standard");
//        board = i.getStringExtra("board");

        //To Set Values in SharedPrefrences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PrefSubjectActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("sub" , hashSet);
        editor.commit();

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chMaths.isChecked()){
                    sub.add("Maths");
                }
                if(chSanskrit.isChecked()){
                    sub.add("Sanskrit");
                }
                if(chEnglish.isChecked()){
                    sub.add("English");
                }
                if(chSS.isChecked()){
                    sub.add("Socialscience");
                }
                if(chHindi.isChecked()){
                    sub.add("Hindi");
                }
                if(chGuj.isChecked()){
                    sub.add("Gujarati");
                }
                if(chScience.isChecked()){
                    sub.add("Science");
                }
                hashSet.addAll(sub);
                sub.clear();
                sub.addAll(hashSet);
                Intent intent = new Intent(getApplicationContext(), PrefTimeActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)sub);
                intent.putExtra("BUNDLE",args);
                intent.putExtra("medium" ,medium);
                intent.putExtra("board" ,board);
                intent.putExtra("standard" ,standard);
                startActivity(intent);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefSubjectActivity.super.onBackPressed();
            }
        }
        );
    }

    private void allocateMemory() {
        chAll = findViewById(R.id.chAll);
        chMaths = findViewById(R.id.chMaths);
        chEnglish = findViewById(R.id.chEnglish);
        chHindi = findViewById(R.id.chHindi);
        chSS = findViewById(R.id.chSS);
        chGuj = findViewById(R.id.chGuj);
        chScience = findViewById(R.id.chScience);
        chSanskrit = findViewById(R.id.chSanskrit);
        totalsubject = null;

        cont = findViewById(R.id.btncontwithSub);
        prev = findViewById(R.id.btnprevfromSub);
        infotext = findViewById(R.id.infotxt);


    }


}