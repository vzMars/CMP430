package com.example.receiver7_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "MainActivity";
    protected static final String ACTION_CUSTOM_BROADCAST = "com.example.receiver7_3.ACTION_CUSTOM";


    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myReceiver = new MyReceiver();
        registerReceiver(myReceiver, addIntentFilters()); //when not using LocalBroadcastManager

        //when using LocalBroadcastManager
    }

    private IntentFilter addIntentFilters() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CUSTOM_BROADCAST);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        return intentFilter;
    }

    public void sendTheBroadcast(View view) {
        Log.d(TAG, "Clicked the button for sending custom broadcast");
        Intent intent = new Intent();
        intent.setAction(ACTION_CUSTOM_BROADCAST);

        sendBroadcast(intent); //when not using LocalBroadcastManager

        //when using LocalBroadcastManager


    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(myReceiver);
        super.onDestroy();
    }
}