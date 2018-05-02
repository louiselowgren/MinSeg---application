package com.mas14llo.minsegapp;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Binder;
import android.util.Log;
import android.widget.Toast;



import static android.content.ContentValues.TAG;

public class BluetoothTestService extends Service {
    BluetoothAdapter mBluetoothAdapter;
    private final IBinder mBinder = new LocalBinder();


    public BluetoothTestService() {
        super();
    }

    // ==== Methods that needs to be overridden ==============
    // ==== for setup the socket and communication =========

    /** Binds with activities,
        this is done once, when the binding is established*/
    @Override
    public IBinder onBind(Intent intent){
        Toast.makeText(this, "Binding is completed", Toast.LENGTH_LONG).show();
        //declare the devices Bluetoothadapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBinder;
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    // =========== methods for clients =====================
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
    // ================== end of methods for clients ==========================

    //nästlad klass
    public class LocalBinder extends Binder {
        public BluetoothTestService getService() {
            return BluetoothTestService.this;
        }
    }

}
