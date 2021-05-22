
//Lots of imports


package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.view.View;
import android.os.Bundle;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.content.Intent;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);

        LinearLayout ll = (LinearLayout) findViewById(R.id.log_scroll);
        TextView tv1 = new TextView(this);
        tv1.setText("This is tv1");

        ll.addView(tv1);
    }

    @SuppressLint("SetTextI18n")
    public void change_box(View given_view){
        setContentView(R.layout.activity_main);

        LinearLayout ll = (LinearLayout) findViewById(R.id.log_scroll);
        TextView tv1 = new TextView(this);
        tv1.setText("This is tv1");

        ll.addView(tv1);






//        for (int i = 0; i < 5; i++) {
//            System.out.println(i);
//        }
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