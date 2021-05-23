
//Lots of imports

package com.example.firstapp;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.view.ViewGroup.LayoutParams;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;


public class MainActivity extends AppCompatActivity {

    BluetoothAdapter btAdapter;
    BluetoothDevice current_device;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.update_visual_logs();

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.d("BLUE",btAdapter.getBondedDevices().toString());



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

    public void update_bluetooth_settings(View v){
        Set<BluetoothDevice> a = btAdapter.getBondedDevices();
        Spinner spinner = findViewById(R.id.range);
        ArrayList<String> arraylist = new ArrayList<>();


        for (BluetoothDevice device_rn: a){
            Log.d("BLUE",device_rn.getName());
            arraylist.add(device_rn.getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraylist);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

    }
    public void open_settings(View v){
        setContentView(R.layout.settings);

    }

    public void open_home(View v){
        setContentView(R.layout.activity_main);
        View linearLayout = findViewById(R.id.log_scroll);
        this.update_visual_logs();
    }

    public void change_device_to_connect(View v){

        Spinner spinner = findViewById(R.id.range);
        String wewant = (String) spinner.getSelectedItem();
        Set<BluetoothDevice> a = btAdapter.getBondedDevices();

        for (BluetoothDevice device_rn: a){
            if (device_rn.getName().equals(wewant)){
                current_device = device_rn;
                break;
            }

        }


    }



}
