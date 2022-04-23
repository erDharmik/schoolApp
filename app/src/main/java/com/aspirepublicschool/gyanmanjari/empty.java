package com.aspirepublicschool.gyanmanjari;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class empty extends AppCompatActivity {    @Override
protected void onCreate(@Nullable Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    Toast.makeText(getApplicationContext(), "LODU", Toast.LENGTH_SHORT).show();

}
}
