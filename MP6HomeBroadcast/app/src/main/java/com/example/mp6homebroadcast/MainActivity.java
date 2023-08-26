package com.example.mp6homebroadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    protected static final String ACTION_CUSTOM_BROADCAST = "com.example.I_AM_HOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendTheBroadcast(View view) {
        Intent intent = new Intent();
        intent.setAction(ACTION_CUSTOM_BROADCAST);
        sendBroadcast(intent);
    }


}