
//Lots of imports

package com.example.firstapp;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.UUID;

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

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        ConnectThread mConnectThread = new ConnectThread(current_device);
        mConnectThread.start();





    }




}


class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    Handler mHandler;
    public ConnectedThread(BluetoothSocket socket) {
        mHandler = new Handler() {

            public void handleMessage(Message msg) {
                Log.d("BLUE","creating");
                byte[] writeBuf = (byte[]) msg.obj;
                int begin = (int)msg.arg1;
                int end = (int)msg.arg2;
                Log.d("BLUE","creating1");
                switch(msg.what) {
                    case 1:
                        Log.d("BLUE","creating2");
                        String writeMessage = new String(writeBuf);
                        Log.d("BLUE","creating3");
                        writeMessage = writeMessage.substring(begin, end);
                        Log.d("BLUE","creating4");
                        Log.d("BLUE", "RECEIVED MESSAGE! " + writeMessage);
                        break;
                }
            }
        };
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { Log.d("BLUE", "AH SHIT1");};

        Log.d("BLUE", "yes? " + tmpIn.toString() + tmpOut.toString());
        mmInStream = tmpIn;
        mmOutStream = tmpOut;

        Log.d("BLUE","streams, " + mmInStream.toString()+ " " + mmOutStream.toString());



    }
    public void run() {
        byte[] buffer = new byte[1024];
        int begin = 0;
        int bytes = 0;
        Log.d("BLUE", "BENCHMARK 1");
//        this.write("*".getBytes());
//        Log.d("BLUE","wrote?");


        while (true) {
            try {
                Log.d("BLUE","INLOOP");
                bytes += mmInStream.read(buffer, bytes, buffer.length - bytes);
                Log.d("BLUE","gotbytes, " + bytes);

                for(int i = begin; i < bytes; i++) {
                    if(buffer[i] == "#".getBytes()[0]) {
                        mHandler.obtainMessage(1, begin, i, buffer).sendToTarget();
                        begin = i + 1;
                        if(i == bytes - 1) {
                            bytes = 0;
                            begin = 0;
                        }
                    }
                }
                Log.d("BLUE","here");
            } catch (IOException e) {
                Log.d("BLUE",e.toString());
            }
        }
    }
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
            Log.d("BLUE","wrote, " + bytes.toString());
        } catch (IOException e) { Log.d("BLUE","couldn't write: " + e.toString());};
    }
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}

class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    ConnectedThread mConnectedThread;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    public ConnectThread(BluetoothDevice device) {


        BluetoothSocket tmp = null;
        mmDevice = device;
        try {
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) { }
        mmSocket = tmp;
        mConnectedThread = new ConnectedThread(mmSocket);

    }
    public void run() {
        mBluetoothAdapter.cancelDiscovery();
        try {
            mmSocket.connect();
        } catch (IOException connectException) {
            try {
                mmSocket.close();
            } catch (IOException closeException) { }
            return;
        }
        Log.d("BLUE", "WE ARE HERE");
        mConnectedThread.start();

    }
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}
