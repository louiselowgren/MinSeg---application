package com.mas14llo.minsegapp;

import android.app.ProgressDialog;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Binder;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import static android.content.ContentValues.TAG;

public class BluetoothTestService extends Service {
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket btSocket;
    Setup setup;
    private final IBinder mBinder = new LocalBinder();
    public BluetoothTestService() {
        super();
    }

    //private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    // ==== Methods that needs to be overridden ==============
    // ==== for setup the socket and communication =========

    /** Binds with activities,
        this is done once, when the binding is established*/
    @Override
    public IBinder onBind(Intent intent){

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //Toast.makeText(this, "Binding is completed", Toast.LENGTH_LONG).show();
        setup = new Setup(getApplicationContext());
        //btSocket = setup.setUpBluetooth();
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("BluetoothService", "Received start id " + startId + ": " + intent);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }




    // =========== methods for clients ==========================
    // ======== to be called from activities ====================

    /** Turns bluetooth on or off depending on the devices current mode*/
    public int enableDisableBluetooth(){
        if(mBluetoothAdapter == null){
            Log.d(TAG, "enableDisableBT: Does not have BT capabilities.");
        }
        if(!mBluetoothAdapter.isEnabled()){
            Log.d(TAG, "enableDisableBT: enabling BT.");
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);
            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            //registerReceiver(mBroadcastReceiver1, BTIntent);
        }
        if(mBluetoothAdapter.isEnabled()){
            Log.d(TAG, "enableDisableBT: disabling BT.");
            mBluetoothAdapter.disable();

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            //registerReceiver(mBroadcastReceiver1, BTIntent);
        }

        return 1;
    }


    public String getAdress(){
        return setup.getAdress();
    }

    public String getName(){
        return setup.getName();
    }

    public BluetoothSocket getBtSocket(){
        return btSocket;
    }

    // ================== end of methods for clients ==========================

    //nästlad klass
    public class LocalBinder extends Binder {
        public BluetoothTestService getService() {
            return BluetoothTestService.this;
        }
    }



}
