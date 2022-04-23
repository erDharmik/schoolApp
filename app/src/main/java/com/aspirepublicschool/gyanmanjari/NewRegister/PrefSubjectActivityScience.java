package com.aspirepublicschool.gyanmanjari.NewRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspirepublicschool.gyanmanjari.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PrefSubjectActivityScience extends AppCompatActivity {

    CheckBox chAll, chMaths, chEnglish, chChemistry, chComputer, chPhysics, chBiology, chSanskrit;
    Boolean checkStatus = false;
    String totalsubject = "";
    List<String> sub = new ArrayList<String>();
    Button prev, cont;
    LinearLayout lnrsimple;
    HashSet<String> hashSet = new HashSet<String>();
    TextView infotext;
    Dialog dialog;
    TextView mediumtxt, standardtxt,boardtxt;
    String standard,medium,board,stream,group;
    Button dialogClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_subject_science);
        allocateMemory();

        getSupportActionBar().hide();

        medium = getIntent().getStringExtra("medium");
        standard = getIntent().getStringExtra("standard");
        board = getIntent().getStringExtra("board");
        stream = getIntent().getStringExtra("stream");
        group = getIntent().getStringExtra("group");

        setAccordingtoGroup();

////      Retrieve Values from SharedPrefrences
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        medium = sharedPreferences.getString("medium", "none");
//        standard = sharedPreferences.getString("standard", "none");
//        board = sharedPreferences.getString("board", "none");

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrefTimeActivity.class);
                startActivity(intent);
            }
        });

        chAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chAll.isChecked()){
                    if (group.equals("A")){
                        chMaths.setChecked(true);
                        chChemistry.setChecked(true);
                        chPhysics.setChecked(true);
                        chComputer.setChecked(true);
                        chSanskrit.setChecked(true);
                        chEnglish.setChecked(true);
                    }else if(group.equals("B")){
                        chBiology.setChecked(true);
                        chChemistry.setChecked(true);
                        chPhysics.setChecked(true);
                        chComputer.setChecked(true);
                        chSanskrit.setChecked(true);
                        chEnglish.setChecked(true);
                    }else if (group.equals("Both(A + B)")){
                        chBiology.setChecked(true);
                        chMaths.setChecked(true);
                        chChemistry.setChecked(true);
                        chPhysics.setChecked(true);
                        chComputer.setChecked(true);
                        chSanskrit.setChecked(true);
                        chEnglish.setChecked(true);
                    }
                }
                else{
                    if (group.equals("A")) {
                        chMaths.setChecked(false);
                        chChemistry.setChecked(false);
                        chPhysics.setChecked(false);
                        chComputer.setChecked(false);
                        chSanskrit.setChecked(false);
                        chEnglish.setChecked(false);
                    }else if (group.equals("B")) {
                        chBiology.setChecked(false);
                        chChemistry.setChecked(false);
                        chPhysics.setChecked(false);
                        chComputer.setChecked(false);
                        chSanskrit.setChecked(false);
                        chEnglish.setChecked(false);
                    }else if (group.equals("Both(A + B)")){
                        chBiology.setChecked(false);
                        chMaths.setChecked(false);
                        chChemistry.setChecked(false);
                        chPhysics.setChecked(false);
                        chComputer.setChecked(false);
                        chSanskrit.setChecked(false);
                        chEnglish.setChecked(false);
                    }
                }
            }
        });
        infotext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog = new Dialog(PrefSubjectActivityScience.this);
               dialog.setContentView(R.layout.selected_info_dialog);
               dialog.show();

               mediumtxt = dialog.findViewById(R.id.mediumtxt);
               standardtxt = dialog.findViewById(R.id.standardtxt);
               boardtxt = dialog.findViewById(R.id.boardtxt);
               dialogClose = dialog.findViewById(R.id.dialogClose);
               mediumtxt.setText(medium);
               standardtxt.setText(standard + " " + stream + " "+ group + " Group");
               boardtxt.setText(board);

               dialogClose.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dialog.dismiss();
                   }
               });
           }
       });

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
                if(chPhysics.isChecked()){
                    sub.add("Physics");
                }
                if(chChemistry.isChecked()){
                    sub.add("Chemistry");
                }
                if(chComputer.isChecked()){
                    sub.add("Computer");
                }
                if(chBiology.isChecked()){
                    sub.add("Biology");
                }
                hashSet.addAll(sub);
                sub.clear();
                sub.addAll(hashSet);
                Toast.makeText(getApplicationContext(), String.valueOf(sub), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PrefTimeActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)sub);
                intent.putExtra("BUNDLE",args);
                intent.putExtra("medium" ,medium);
                intent.putExtra("board" ,board);
                intent.putExtra("standard" ,standard);
                intent.putExtra("stream" ,stream);
                intent.putExtra("group" ,group);
                startActivity(intent);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                PrefSubjectActivityScience.super.onBackPressed();
            }
        }
        );
    }

    private void setAccordingtoGroup() {
        if (group.equals("A")){
            chBiology.setVisibility(View.INVISIBLE);
        }else if(group.equals("B")){
            chMaths.setVisibility(View.INVISIBLE);
        }else if(group.equals("Both(A + B")){
            group = "AB";
        }

    }

    private void allocateMemory() {
        chAll = findViewById(R.id.chAll);
        chMaths = findViewById(R.id.chMaths);
        chEnglish = findViewById(R.id.chEnglish);
        chChemistry = findViewById(R.id.chChemistry);
        chPhysics = findViewById(R.id.chPhysics);
        chComputer = findViewById(R.id.chComputer);
        chBiology = findViewById(R.id.chBiology);
        chSanskrit = findViewById(R.id.chSanskrit);
        totalsubject = null;

        cont = findViewById(R.id.btncontwithSub);
        prev = findViewById(R.id.btnprevfromSub);
        infotext = findViewById(R.id.infotext);
    }
}