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

public class PrefSubjectActivityCommerce extends AppCompatActivity {

    CheckBox chAll, chEconomy, chEnglish, chAccount, chStat, chBS, chSanskrit, chComputer;
    Boolean checkStatus = false;
    Button btnprevfromSub, cont, dialogClose;
    TextView infotxt;
    Dialog dialog;
    List<String> sub = new ArrayList<String>();
    HashSet<String> hashSet = new HashSet<String>();
    TextView mediumtxt, standardtxt,boardtxt;
    String standard,medium,board,stream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_subject_commerce);

        allocateMemory();

        medium = getIntent().getStringExtra("medium");
        standard = getIntent().getStringExtra("standard");
        board = getIntent().getStringExtra("board");
        stream = getIntent().getStringExtra("stream");

        // Retrieve Values from SharedPrefrences
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        medium = sharedPreferences.getString("medium", "none");
//        standard = sharedPreferences.getString("standard", "none");
//        board = sharedPreferences.getString("board", "none");

        infotxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(PrefSubjectActivityCommerce.this);
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

        chAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chAll.isChecked()){
                    chAccount.setChecked(true);
                    chStat.setChecked(true);
                    chBS.setChecked(true);
                    chEconomy.setChecked(true);
                    chComputer.setChecked(true);
                    chSanskrit.setChecked(true);
                    chEnglish.setChecked(true);
                } else{
                    chAccount.setChecked(false);
                    chStat.setChecked(false);
                    chBS.setChecked(false);
                    chEconomy.setChecked(false);
                    chComputer.setChecked(false);
                    chSanskrit.setChecked(false);
                    chEnglish.setChecked(false);
                }
            }
        });



        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chAccount.isChecked()){
                    sub.add("Accountancy");
                }
                if(chEconomy.isChecked()){
                    sub.add("Economics");
                }
                if(chEnglish.isChecked()){
                    sub.add("English");
                }
                if(chComputer.isChecked()){
                    sub.add("Computer");
                }
                if(chBS.isChecked()){
                    sub.add("Business Studies");
                }
                if(chSanskrit.isChecked()){
                    sub.add("Sanskrit");
                }
                if(chStat.isChecked()){
                    sub.add("Statastics");
                }
                hashSet.addAll(sub);
                sub.clear();
                sub.addAll(hashSet);
                Intent intent = new Intent(PrefSubjectActivityCommerce.this, PrefTimeActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)sub);
                intent.putExtra("BUNDLE",args);
                intent.putExtra("medium" ,medium);
                intent.putExtra("board" ,board);
                intent.putExtra("standard" ,standard);
                intent.putExtra("stream" ,stream);
                startActivity(intent);
            }
        });

        btnprevfromSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void allocateMemory() {
        chAll = findViewById(R.id.chAll);
        chComputer = findViewById(R.id.chComputer);
        chStat = findViewById(R.id.chStat);
        chSanskrit = findViewById(R.id.chSanskrit);
        chBS = findViewById(R.id.chBs);
        chAccount = findViewById(R.id.chAccount);
        chEnglish = findViewById(R.id.chEnglish);
        chEconomy = findViewById(R.id.chEconomy);
        btnprevfromSub = findViewById(R.id.btnprevfromSub);
        cont =findViewById(R.id.btncontwithSubCommerce);

        infotxt = findViewById(R.id.infotxt);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}