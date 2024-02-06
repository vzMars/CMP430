package com.example.showtoast;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private int myCounter = 0;
    private TextView tvShowCount = null;
//    private  Button btnShowToast = null;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Inside onCreate method");

        tvShowCount = findViewById(R.id.tv_show_count);

        /*other way to set a listener and use it instead of
        * using the onClick from the xml file
        *
        */
//        btnShowToast = findViewById(R.id.button_show_toast);
//        btnShowToast.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showToast(v);
////                Toast.makeText(getApplicationContext(), "Hellooooo " + myCounter, Toast.LENGTH_SHORT).show();
//            }
//        });

        Log.d(TAG, "End of onCreate method");
    }

    public void incrementCount(View view) {
        Log.d(TAG, "Inside incrementCount method");

        Log.d(TAG, "Counter is " + ++myCounter);

        tvShowCount.setText("Count is " + myCounter);

        Log.d(TAG, "End of incrementCount method");
    }

    public void showToast(View view) {
        Log.d(TAG, "Hi I will show a toast!");
//        incrementCount(view);// example if we want to also increment the count from the show toast button being clicked
//        Toast.makeText(this, "Hellooooo " + myCounter,Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "Hellooooo " + myCounter, Toast.LENGTH_SHORT).show();

        Log.d(TAG, "End of showToast method");
    }
}