package com.mas14llo.minsegapp;

import com.mas14llo.minsegapp.BluetoothTestService.LocalBinder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
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
import static android.content.ContentValues.TAG;

public class StartScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    BluetoothTestService btService;     // this is the connectionlink to the service
    boolean mBound = false;

    //Declare buttons
    Button onOff;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setup window (toolbar, buttons, textviews
        setupWindow();
        //Init the connection with the bluetoothService
        Intent intent = new Intent(this, BluetoothTestService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        //-------------------

        /**Endable or disable bluetooth on the device*/
        onOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btService.enableDisableBluetooth();
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

    private void setupWindow(){

        setContentView(R.layout.activity_start_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        onOff = (Button) findViewById(R.id.btnONOFF);
    }
    // ==================== NÄSTLAD KLASS  ======================

    /** Defines callbacks for service binding, passed to bindService()
     Creates the bound between activity and service*/
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
            mBound= false;
        }
    };


}
