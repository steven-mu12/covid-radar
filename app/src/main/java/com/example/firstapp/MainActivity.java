
//Lots of imports

package com.example.firstapp;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.view.ViewGroup.LayoutParams;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //        Button buttontest = findViewById(R.id.tester_button);
//        buttontest.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view){
//
//            }
//        });

        setContentView(R.layout.home_screen);



    }

    @SuppressLint("SetTextI18n")
    public void add_log(String text){
        View linearLayout =  findViewById(R.id.log_scroll);
        //LinearLayout layout = (LinearLayout) findViewById(R.id.info);

        TextView valueTV = new TextView(this);
        valueTV.setText(text);

        valueTV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));

        ((LinearLayout) linearLayout).addView(valueTV);

    }


    public void save(View v) {
        String text = "YAS";
        String FILE_NAME = "example.txt";

        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d("READ","I HAVE SAVED THE FILE");

        View linearLayout =  findViewById(R.id.log_scroll);
        this.load(linearLayout);
    }
    public void load(View v) {
        FileInputStream fis = null;
        String FILE_NAME = "example.txt";
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            };
            Log.d("READ:", sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    public void change_box(View given_view){
//        setContentView(R.layout.activity_main);
//
//        LinearLayout ll = (LinearLayout) findViewById(R.id.log_scroll);
//        TextView tv1 = new TextView(this);
//        tv1.setText("This is tv1");
//
//        ll.addView(tv1);


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
//
//
//
//
//
//
//
//
//
//
//
//    }
    }
}