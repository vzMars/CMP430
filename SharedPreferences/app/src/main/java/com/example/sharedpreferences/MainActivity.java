package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.BlendMode;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private int count = 0;
    private int color;//background color currently
    private TextView textViewShowCount;
    private final String KEY_COUNT = "count";
    private final String KEY_COLOR = "color";
    private SharedPreferences sharedPreferences;
    private String sharedPrefFileName = "com.example.examplesharedpreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewShowCount = findViewById(R.id.count_textview);
        color = ContextCompat.getColor(this, R.color.default_background);

        sharedPreferences = getSharedPreferences(sharedPrefFileName, MODE_PRIVATE);


        //restore the values from the preferences
        count = sharedPreferences.getInt(KEY_COUNT, 0);
        color = sharedPreferences.getInt(KEY_COLOR, color);

        textViewShowCount.setText(String.format("%s", count));
        textViewShowCount.setBackgroundColor(color);

    }




    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putInt(KEY_COUNT, count);
        preferencesEditor.putInt(KEY_COLOR, color);
        preferencesEditor.apply();

    }

    //reset the counter and the color
    public void resetValues(View view) {
        count = 0;
        color = ContextCompat.getColor(this, R.color.default_background);

        textViewShowCount.setText(String.format("%s", count));
        textViewShowCount.setBackgroundColor(color);

        //clear shared preferences
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();

    }


    //incrementing the counter
    public void incrementCounter(View view) {
        count++;
        textViewShowCount.setText(String.format("%s", count));
    }

    //changing the color based on the button clicked
    public void changeBackgroundColor(View view) {

        int bgColor = ((ColorDrawable)view.getBackground()).getColor();
        color = bgColor;
        textViewShowCount.setBackgroundColor(color);

    }
}