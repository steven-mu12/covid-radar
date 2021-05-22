
//Lots of imports


package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.view.View;
import android.os.Bundle;

import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("SetTextI18n")
    public void change_box(View given_view){

//        EditText name_given = findViewById(R.id.person_name);
//        String name_text = name_given.getText().toString();
//
//        TextView button = findViewById(R.id.button);
//
        Date datern = new Date();
//        String display = datern.toString();
//
//        String message = "Hello there! How's it going " + name_text + "?\nRight now, the date is " + display;
//
//        ;
//        try{
//            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//            startActivity(intent);
//            button.setText(message);
//        }
//        catch (ActivityNotFoundException e) {
//            button.setText("There seems to be a problem with the picture taking function.");
//        }











    }
}