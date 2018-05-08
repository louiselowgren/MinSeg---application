package com.mas14llo.minsegapp;

import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;
import static android.os.SystemClock.sleep;


public class PositionControll  extends AppCompatActivity{

    Boolean mBound;
    String red = "red";
    String green = "green";
    String blue = "blue";
    String readString;
    Button changeActivity;
    Button sendBtn;
    TextView sendTxt;
    TextView readTxt ;

    BluetoothSocket btSocket;
    BluetoothTestService btService;

    int nbrBytes;
    byte[] buffer = new byte[1024];


    public PositionControll(){

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position_control_layout);

        Intent intent = new Intent(this, BluetoothTestService.class);
        boolean bond = bindService(intent, connection, 0);
        sleep(1000);
        if(btService != null){
            Log.d(TAG, "Positioncontroll : binding complete ");
        }else{
            Log.d(TAG, "Positioncontroll : binding failed ");
        }


        if(btService != null){
            Log.d(TAG, "Positioncontroll : got the btService");
        }else{
            Log.d(TAG, "Positioncontroll : failed to get the btService");
        }
       // btSocket = btService.getBtSocket();

        changeActivity = (Button) findViewById(R.id.changeActivity);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        sendTxt = findViewById(R.id.sendTxt);
        readTxt = findViewById(R.id.readTxt);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                led_on_off(sendTxt.getText().toString());
                sendTxt.setText("");
                readTxt.setText("");

                //220 nope, 250 nope, 500 ok, 1000 ok
                sleep(1000);
                readTxt.setText(read());

            }
        });





        changeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), StartScreen.class);
                startActivity(startIntent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called . destroy service in Positioncontroll.");
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private void led_on_off(String i) {
        try {
            if (btSocket!=null){
                btSocket.getOutputStream().write(i.toString().getBytes());
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private String read(){
        try {
            if (btSocket!=null){
                nbrBytes= btSocket.getInputStream().read(buffer);
                readString = new String(buffer, 0,nbrBytes);
                Log.d(TAG, "Read : bytes read " + nbrBytes);
                Log.d(TAG, "Read : length of readstring " +readString.length());
                return readString;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return "error";
    }



    // ==================== NÄSTLAD KLASS  ======================

    /**
     * Defines callbacks for service binding, passed to bindService()
     * Creates the bound between activity and service
     */
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(TAG, "IN SERVICECONNECTION  ");
            BluetoothTestService.LocalBinder binder = (BluetoothTestService.LocalBinder) service;
            btService = binder.getService();
            mBound = true;


        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

}
