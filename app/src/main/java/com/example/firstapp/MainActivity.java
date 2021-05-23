
//Lots of imports

package com.example.firstapp;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.ApplicationErrorReport;
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

        this.update_visual_logs();

    }

    @SuppressLint("SetTextI18n")
    public void add_log(String text) {
        View linearLayout = findViewById(R.id.log_scroll);
        //LinearLayout layout = (LinearLayout) findViewById(R.id.info);

        TextView valueTV = new TextView(this);
        valueTV.setText(text);

        valueTV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        ((LinearLayout) linearLayout).addView(valueTV);

    }

    public void update_visual_logs(){
        setContentView(R.layout.activity_main);
        String now = this.load();
        String[] sdf = now.split("\n", 200);

        for (String s : sdf) {
            add_log(s);
        }

    }


    public void save(View v) {
        String distance = "6 feet";
        String datern = new Date().toString();


        String text = distance + " " + datern + "\n";
        String FILE_NAME = "example.txt";

        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_APPEND);
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
        Log.d("READ", "I HAVE SAVED THE FILE");

        View linearLayout = findViewById(R.id.log_scroll);
//        this.load();
    }

    public String load() {
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
            return sb.toString();

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
        return "";
    }
    public void open_bluetooth(View v){
        setContentView(R.layout.bluetooth);

    }

    public void open_settings(View v){
        setContentView(R.layout.settings);

    }

    public void open_home(View v){
        setContentView(R.layout.activity_main);
        this.update_visual_logs();
    }
}
