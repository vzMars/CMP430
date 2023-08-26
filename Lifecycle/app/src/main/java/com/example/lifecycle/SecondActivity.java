package com.example.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {

    public static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "inside onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "inside onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "inside onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "inside onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "inside onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "inside onRestart");
    }
}