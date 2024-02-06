package com.example.inputcontrolspickers;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    public static final String TAG = "SecondActivity";
    private static final int MY_PERMISSION_REQUEST = 0;
    private static final String [] PERMISSIONS_REQUIRED = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private String telNum = "";
    private String telType = "";
    EditText editTextMessage;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG, "inside of onCreate method");

        Intent intent = getIntent();
        telNum = intent.getStringExtra(MainActivity.KEY_TEL_NUM);
        telType = intent.getStringExtra(MainActivity.KEY_TEL_TYPE);

        textView = findViewById(R.id.text_2nd_display_number);
        textView.setText(telNum);
        editTextMessage = findViewById(R.id.edit_text_second);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                sendSMS();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(TAG, "end of onCreate method");
    }

    private void sendSMS() {
        Log.d(TAG, "inside of sendSMS method");

        String text = editTextMessage.getText().toString();
        if(text.length() == 0) {
            text = "empty";
            Log.d(TAG, "Empty text");
            Toast.makeText(this, "empty message will not be sent", Toast.LENGTH_LONG).show();
        }
        if(checkPermissions()){
            Toast.makeText(this, "sending message", Toast.LENGTH_LONG).show();
            //go ahead and send the actual sms message using the default sms app on the device via the manager
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telNum, null, text, null, null);
        }

        Log.d(TAG, "end of sendSMS method");
    }

    private boolean checkPermissions(){
        Log.d(TAG, "inside of checkPermissions method");
        boolean permissionGranted = true;
        for(String permission : PERMISSIONS_REQUIRED){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                permissionGranted = false;
                Log.d(TAG, "PERMISSION DENIED" + permission);
                ActivityCompat.requestPermissions(this, new String[]{permission}, MY_PERMISSION_REQUEST);
            }
        }
        Log.d(TAG, "end of checkPermissions method");
        return permissionGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "inside of onRequestPermissionsResult method");

        switch (requestCode){
            case MY_PERMISSION_REQUEST:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.d(TAG, "Permission granted ");
                    sendSMS();
                }
                else{
                    Log.d(TAG, "Permission denied ");
                }
                break;
        }

        Log.d(TAG, "end of onRequestPermissionsResult method");
    }
}