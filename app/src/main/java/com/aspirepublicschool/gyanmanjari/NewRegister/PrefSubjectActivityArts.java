package com.aspirepublicschool.gyanmanjari.NewRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.aspirepublicschool.gyanmanjari.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PrefSubjectActivityArts extends AppCompatActivity {

    CheckBox chAll ,chPoliticalScience, chHistory, chGeo, chEnglish, chSociology, chPsychology, chSanskrit;
    Button btnprevfromSub, btncontwithSub;
    TextView infotxt;
    Dialog dialog;
    List<String> sub = new ArrayList<String>();
    HashSet<String> hashSet = new HashSet<String>();
    TextView mediumtxt, standardtxt,boardtxt;
    String standard,medium,board,stream;
    Button dialogClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_subject_arts);

        allocateMemory();

        btnprevfromSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btncontwithSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueRegistration();
            }
        });

        medium = getIntent().getStringExtra("medium");
        standard = getIntent().getStringExtra("standard");
        board = getIntent().getStringExtra("board");
        stream = getIntent().getStringExtra("stream");

        // Retrieve Values from SharedPrefrences
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PrefSubjectActivityArts.this);
//        medium = sharedPreferences.getString("medium", "none");
//        standard = sharedPreferences.getString("standard", "none");
//        board = sharedPreferences.getString("board", "none");

        chAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chAll.isChecked()){
                    chHistory.setChecked(true);
                    chPoliticalScience.setChecked(true);
                    chGeo.setChecked(true);
                    chEnglish.setChecked(true);
                    chSanskrit.setChecked(true);
                    chSociology.setChecked(true);
                    chPsychology.setChecked(true);
                }else{
                    chHistory.setChecked(false);
                    chPoliticalScience.setChecked(false);
                    chGeo.setChecked(false);
                    chEnglish.setChecked(false);
                    chSociology.setChecked(false);
                    chPsychology.setChecked(false);
                    chSanskrit.setChecked(false);

                }
            }
        });
        infotxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(PrefSubjectActivityArts.this);
                dialog.setContentView(R.layout.selected_info_dialog);
//                dialog.getWindow().setLayout(800, 1200);
                dialog.show();

                mediumtxt = dialog.findViewById(R.id.mediumtxt);
                standardtxt = dialog.findViewById(R.id.standardtxt);
                boardtxt = dialog.findViewById(R.id.boardtxt);
                dialogClose = dialog.findViewById(R.id.dialogClose);
                mediumtxt.setText(medium);
                standardtxt.setText(standard + " " + stream);
                boardtxt.setText(board);

                dialogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    private void continueRegistration() {
        if(chPoliticalScience.isChecked()){
            sub.add("Political Studies");
        }
        if (chSanskrit.isChecked()){
            sub.add("Sanskrit");
        }
        if(chPsychology.isChecked()){
            sub.add("Psychology");
        }
        if(chEnglish.isChecked()){
            sub.add("English");
        }
        if(chSociology.isChecked()){
            sub.add("Sociology");
        }
        if(chGeo.isChecked()){
            sub.add("Geography");
        }
        if(chHistory.isChecked()){
            sub.add("History");
        }
        hashSet.addAll(sub);
        sub.clear();
        sub.addAll(hashSet);
        Intent intent = new Intent(PrefSubjectActivityArts.this, PrefTimeActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST",(Serializable)sub);
        intent.putExtra("BUNDLE",args);
        intent.putExtra("medium" ,medium);
        intent.putExtra("board" ,board);
        intent.putExtra("standard" ,standard);
        intent.putExtra("stream" ,stream);
        startActivity(intent);
    }

    private void allocateMemory() {
        chAll = findViewById(R.id.chAll);
        chEnglish = findViewById(R.id.chEnglish);
        chPoliticalScience = findViewById(R.id.chPoliticalScience);
        chHistory = findViewById(R.id.chHistory);
        chGeo = findViewById(R.id.chGeo);
        chSociology = findViewById(R.id.chSociology);
        chPsychology = findViewById(R.id.chPsychology);
        chSanskrit = findViewById(R.id.chSanskrit);
        btnprevfromSub =findViewById(R.id.btnprevfromSub);
        btncontwithSub = findViewById(R.id.btncontwithSub);
        infotxt = findViewById(R.id.infotxt);
    }
}