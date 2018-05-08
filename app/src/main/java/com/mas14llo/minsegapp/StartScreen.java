package com.mas14llo.minsegapp;

import com.mas14llo.minsegapp.BluetoothTestService.LocalBinder;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.ServiceConnection;
import android.content.ComponentName;


import java.util.UUID;
import static android.content.ContentValues.TAG;



public class StartScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    // this is the connectionlink to the service
    BluetoothTestService btService;
    boolean mBound = false;

    // ----------------------------------
    BluetoothAdapter mBluetoothAdapter;
    private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // ---------------------------------

    //Declare buttons
    Button onOff;
    Button changeActivity;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Init the connection with the bluetoothService

        Intent intent = new Intent(this, BluetoothTestService.class);

        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "Startscreen : binding complete ");
        //-------------------
        setContentView(R.layout.activity_start_screen);
        // Define buttons, textview etc
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        onOff = (Button) findViewById(R.id.btnONOFF);
        changeActivity = (Button) findViewById(R.id.changeActivity);
        //connectionInfo = findViewById(R.id.textView1);

        // the devices bluetoothadapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // set address and name
        //connectionInfo.setText("BT Name: "+ btService.getName() +"\nBT Address: "+btService.getAdress());



        /**Endable or disable bluetooth on the device*/
        onOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btService.enableDisableBluetooth();
            }
        });

        changeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), PositionControll.class);


                startActivity(startIntent);
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called . destroy service in startscreen.");
        super.onDestroy();
        btService.onDestroy();
        //mBluetoothAdapter.cancelDiscovery();
    }

    @Override
    protected void onStop(){
        super.onStop();
        //Log.d(TAG, "onStop: called - destroy service in startscreen");
        //btService.onDestroy();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // ==================== NÄSTLAD KLASS  ======================

    /**
     * Defines callbacks for service binding, passed to bindService()
     * Creates the bound between activity and service
     */
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {

            LocalBinder binder = (LocalBinder) service;
            btService = binder.getService();

            mBound = true;
            Log.d(TAG, "OnServiceConnected: Is bound!");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}


