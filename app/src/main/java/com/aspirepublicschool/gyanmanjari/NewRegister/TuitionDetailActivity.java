package com.aspirepublicschool.gyanmanjari.NewRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.aspirepublicschool.gyanmanjari.Login;
import com.aspirepublicschool.gyanmanjari.R;

import java.util.List;

public class TuitionDetailActivity extends AppCompatActivity {

    Spinner medium, board, standard, stream, group;
    Button cont, prev;
    String[] smedium = {"Select Medium","Gujarati", "Hindi", "English"} ;
    String[] sboard = {"Select Board","GSEB", "CBSE", "ICSE"} ;
    String[] sstandard = {"Select Standard","1","2","3","4","5","6","7","8","9","10","11","12"} ;
    String[] sstream = {"Select Stream","Science", "Commerce", "Arts"} ;
    String[] sgroup = {"Select Group","A", "B", "Both(A + B)"} ;
    ArrayAdapter<String> mediumadapter, boardadapter, standardadapter,streamadapter, groupadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_detail);
        getSupportActionBar().hide();

        allocateMemory();
        setAdapter();

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //942022



//                if (standard.getSelectedItem().equals("Select Standard")){
//                    Toast.makeText(getApplicationContext(), "Please Select Standard", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    sharedPref();
//                    registration();
//                }

                Intent intent = new Intent(TuitionDetailActivity.this,NewTimePrefActivity.class);
                intent.putExtra("medium", String.valueOf(medium.getSelectedItem()));
                intent.putExtra("board", String.valueOf(board.getSelectedItem()));
                intent.putExtra("standard", String.valueOf(standard.getSelectedItem()));
                intent.putExtra("stream", String.valueOf(stream.getSelectedItem()));
                intent.putExtra("group", String.valueOf(group.getSelectedItem()));
                startActivity(intent);
            }
        });


        standard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String temp = String.valueOf(parent.getItemAtPosition(position));
                stream.setSelection(0);
                group.setSelection(0);
                if (temp.equals(String.valueOf(11))) {
                    stream.setVisibility(View.VISIBLE);
                }
                else if(temp.equals(String.valueOf(12))){
                    stream.setVisibility(View.VISIBLE);
                }
                else{
                    stream.setVisibility(View.INVISIBLE);
                    group.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        stream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String temp = String.valueOf(parent.getItemAtPosition(position));
                group.setSelection(0);
                if (temp.equals("Science")) {
                    group.setVisibility(View.VISIBLE);
                }
                else{
                    group.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void sharedPref() {

        String standardfinal;
        if (stream.getSelectedItem().toString().equals("Select Stream") ){
            standardfinal = standard.getSelectedItem().toString();
            Toast.makeText(getApplicationContext(), standardfinal, Toast.LENGTH_SHORT).show();

        }else if(group.getSelectedItem().toString().equals("Select Group")){
            standardfinal = standard.getSelectedItem().toString() + " " + stream.getSelectedItem().toString();
            Toast.makeText(getApplicationContext(), standardfinal, Toast.LENGTH_SHORT).show();
        }
        else{
        standardfinal = standard.getSelectedItem().toString() + " " + stream.getSelectedItem() + " " + group.getSelectedItem() + " Group";
        Toast.makeText(getApplicationContext(), standardfinal, Toast.LENGTH_SHORT).show();
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TuitionDetailActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("medium" , medium.getSelectedItem().toString());
        editor.putString("board" , board.getSelectedItem().toString());
        editor.putString("standard" , standardfinal);
        editor.commit();
    }

    private void registration() {
        int Standard1 = Integer.parseInt(String.valueOf(standard.getSelectedItem()));
        String Group1 = String.valueOf(group.getSelectedItem());
        String Stream1 = String.valueOf(stream.getSelectedItem());

        if(Standard1 <= 10 ){
            continueRegistration();
        }
        else if (Standard1 > 10){
           if (Stream1.equals("Science")){
               if (Group1.equals("B") || Group1.equals("A") || Group1.equals("Both(A + B)")){
               scienceRegistration();
               }
               else{
                   Toast.makeText(getApplicationContext(), "Please Select Group", Toast.LENGTH_SHORT).show();
               }
           }
           else if(Stream1.equals("Commerce")){
                CommerceRegistration();
           }
           else if(Stream1.equals("Arts")){
               ArtsRegistration();
           }
           else{
               Toast.makeText(getApplicationContext(), "Please Select Stream", Toast.LENGTH_SHORT).show();
           }
        }
    }

    private void ArtsRegistration() {
        Intent intent = new Intent(getApplicationContext(), PrefSubjectActivityArts.class);
        intent.putExtra("medium", String.valueOf(medium.getSelectedItem()));
        intent.putExtra("board", String.valueOf(board.getSelectedItem()));
        intent.putExtra("standard", String.valueOf(standard.getSelectedItem()));
        intent.putExtra("stream", String.valueOf(stream.getSelectedItem()));
        startActivity(intent);
    }

    private void CommerceRegistration() {
        Intent intent = new Intent(getApplicationContext(), PrefSubjectActivityCommerce.class);
        intent.putExtra("medium", String.valueOf(medium.getSelectedItem()));
        intent.putExtra("board", String.valueOf(board.getSelectedItem()));
        intent.putExtra("standard", String.valueOf(standard.getSelectedItem()));
        intent.putExtra("stream", String.valueOf(stream.getSelectedItem()));
        startActivity(intent);

    }

    private void scienceRegistration() {
        Intent intent = new Intent(getApplicationContext(), PrefSubjectActivityScience.class);
        intent.putExtra("medium", String.valueOf(medium.getSelectedItem()));
        intent.putExtra("board", String.valueOf(board.getSelectedItem()));
        intent.putExtra("standard", String.valueOf(standard.getSelectedItem()));
        intent.putExtra("stream", String.valueOf(stream.getSelectedItem()));
        intent.putExtra("group", String.valueOf(group.getSelectedItem()));
        startActivity(intent);

    }

    private void continueRegistration() {

        // 942022

        Intent intent = new Intent(getApplicationContext(), PrefSubjectActivity.class);
        intent.putExtra("medium", String.valueOf(medium.getSelectedItem()));
        intent.putExtra("board", String.valueOf(board.getSelectedItem()));
        intent.putExtra("standard", String.valueOf(standard.getSelectedItem()));
        startActivity(intent);






    }

    private void allocateMemory() {
        medium = findViewById(R.id.mediumSpinner);
        board = findViewById(R.id.boardSpinner);
        standard = findViewById(R.id.standardSpinner);
        stream = findViewById(R.id.streamSpinner);
        group = findViewById(R.id.groupSpinner);
        stream.setVisibility(View.INVISIBLE);
        group.setVisibility(View.INVISIBLE);
        cont = findViewById(R.id.btncont);
    }

    private void setAdapter() {

        ArrayAdapter<String> mediumadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, smedium);
        mediumadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        medium.setAdapter(mediumadapter);

        ArrayAdapter<String> boardadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sboard);
        boardadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        board.setAdapter(boardadapter);

        ArrayAdapter<String> standardadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sstandard);
        standardadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        standard.setAdapter(standardadapter);

        ArrayAdapter<String> streamadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sstream);
        streamadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stream.setAdapter(streamadapter);

        ArrayAdapter<String> groupadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sgroup);
        groupadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        group.setAdapter(groupadapter);

    }
}