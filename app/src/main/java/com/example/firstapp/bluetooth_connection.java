package com.example.firstapp;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

public class bluetooth_connection {

    private static final String TAG = "bluetooth_code";
    private static final String app_name = "Covid Radar";
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    private final BluetoothAdapter m_bluetooth_adapter;
    private AcceptThread m_insecure_accept_thread;
    private ConnectThread m_connect_thread;
    private BluetoothDevice mm_device;
    private UUID device_uuid;
    private connected_thread m_connected_thread;

    ProgressDialog m_progress_dialog;

    Context mcontext;

    public bluetooth_connection(Context context) {
        this.m_bluetooth_adapter = BluetoothAdapter.getDefaultAdapter();
        this.mcontext = context;
    }

    private class AcceptThread extends Thread {
        //waits for a connection
        private final BluetoothServerSocket mmserver_socket;

        public AcceptThread(){
            BluetoothServerSocket tmp = null;
            //create a new listening server socket

            try {
                tmp = m_bluetooth_adapter.listenUsingRfcommWithServiceRecord(app_name, MY_UUID_INSECURE);
            }
            catch (IOException e){

            }
            mmserver_socket = tmp;
        }

        public void run(){
            //either connection is returned or an exception
            BluetoothSocket socket = null;
            try {
                socket = mmserver_socket.accept();
                Log.d("BLUE","accepted connection!");

            }catch (IOException e){
                Log.d("BLUE","oh no: " + e.getMessage());
            }

            if(socket!=null){
                connected(socket, mm_device);
            }
        }

        public void cancel() {
            Log.d(TAG, "cancel: Canceling AcceptThread.");
            try {
                mmserver_socket.close();
            } catch (IOException e) {
                Log.e("BLUE", "cancel: Close of AcceptThread ServerSocket failed. " + e.getMessage() );
            }
        }
    }
    private class ConnectThread extends Thread{
        private BluetoothSocket mmsocket;
        public ConnectThread(BluetoothDevice device, UUID uuid){
            mm_device = device;
            device_uuid = uuid;

        }

        public void run(){
            BluetoothSocket tmp = null;
            try {
                tmp = mm_device.createRfcommSocketToServiceRecord(device_uuid);
            }
            catch (IOException e){
                Log.d("BLUE", "error place 1");
            }

            mmsocket = tmp;
            m_bluetooth_adapter.cancelDiscovery();

            try{
                mmsocket.connect();
            }
            catch(IOException e){
                try {
                    mmsocket.close();
                }catch (IOException ewe) {
                    Log.d("BLUE", "couldn't close connection");
                }
            }
            connected(mmsocket, mm_device);

        }




        public void cancel() {
            Log.d("BLUE", "cancel: Canceling AcceptThread.");
            try {
                mmsocket.close();
            } catch (IOException e) {
                Log.d("BLUE", "cancel: Close of AcceptThread ServerSocket failed. " + e.getMessage() );
            }
        }

        public synchronized void start(){

            if (m_connect_thread!=null){
                m_connect_thread.cancel();
                m_connect_thread = null;
            }
            if (m_insecure_accept_thread == null){
                m_insecure_accept_thread = new AcceptThread();
                m_insecure_accept_thread.start();
            }

        }

        public void startClient(BluetoothDevice device,UUID uuid){
            Log.d(TAG, "startClient: Started.");

            //initprogress dialog
            m_progress_dialog = ProgressDialog.show(mcontext,"Connecting Bluetooth"
                    ,"Please Wait...",true);

            m_connect_thread = new ConnectThread(device, uuid);
            m_connect_thread.start();
        }
    }

    private class connected_thread extends Thread{
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public connected_thread(BluetoothSocket socket){
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            //we know connection has been made

            m_progress_dialog.dismiss();
            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;

        }

        public void run(){
            byte[] buffer = new byte[1024];
            int bytes; //bytes we have read

            while (true){
                try {
                    bytes = mmInStream.read(buffer);
                    String incoming_message = new String(buffer, 0, bytes);
                    Log.d("BLUE", "MESSAGE: " + incoming_message);
                }
                catch (IOException e){
                    Log.d("BLUE","oh no. Closing connection");
                    break;

                }

            }

        }

        public void write(byte[] bytes){
            String text = new String(bytes, Charset.defaultCharset());
            Log.d("WRITING:",text);
            try {
                mmOutStream.write(bytes);
            }
            catch (IOException e){
                Log.d("BLUE", "couldn't write...");

            }

        }

        public void cancel(){
            try{
                mmSocket.close();
            }catch (IOException e){

            }
        }


    }

    private void connected (BluetoothSocket mmsocket, BluetoothDevice mm_device){
        m_connected_thread = new connected_thread(mmsocket);
        m_connected_thread.start();
    }

    public void write(byte[] bytes){
        m_connected_thread.write(bytes);
    }
}
