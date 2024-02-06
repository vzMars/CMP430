package com.example.inputdatetimepicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";
    public static final String KEY_DATE = "com.example.inputdatetimepicker.date";
    public static final String KEY_TIME = "com.example.inputdatetimepicker.time";
    TextView textViewDisplayDate, textViewDisplayTime;
    Button buttonPickDate, buttonPickTime, buttonPackageOptions;
    private String dateAsString, timeAsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Inside of onCreate method");

        connectToViews();
        hideViws();
        setListenersOnButtons();

        Log.d(TAG, "end of onCreate method");
    }

    private void connectToViews(){
        Log.d(TAG, "Inside of connectToViews method");

        textViewDisplayDate = findViewById(R.id.tv_date);
        textViewDisplayTime = findViewById(R.id.tv_time);
        buttonPickDate = findViewById(R.id.button_date_pick);
        buttonPickTime = findViewById(R.id.button_time_pick);
        buttonPackageOptions = findViewById(R.id.button_goto_packaging_activity);

        Log.d(TAG, "end of connectToViews method");
    }

    private void hideViws(){
        Log.d(TAG, "Inside of hideViews method");

        textViewDisplayDate.setVisibility(View.INVISIBLE);
        textViewDisplayTime.setVisibility(View.INVISIBLE);
        buttonPackageOptions.setVisibility(View.INVISIBLE);

        Log.d(TAG, "end of hideViews method");
    }

    private void setListenersOnButtons(){
        Log.d(TAG, "Inside of setListenersOnButtons method");

        buttonPickDate.setOnClickListener(this);
        buttonPickTime.setOnClickListener(this);
        buttonPackageOptions.setOnClickListener(this);

        Log.d(TAG, "end of setListenersOnButtons method");
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "Inside of onClick method");

        int viewId = v.getId();
        switch (viewId){
            case R.id.button_date_pick:
                showDatePickerDialog();//show date picker dialog
                break;
            case R.id.button_time_pick:
                showTimePickerDialog();//show time picker dialog
                break;
            case R.id.button_goto_packaging_activity:
                goToPackagingActivity();//explicit intent to go to other activity
                break;
            default:
                Log.d(TAG, "unknown button clicked to trigger onClick method");
                break;
        }

        Log.d(TAG, "end of onClick method");
    }

    private void showDatePickerDialog(){
        Log.d(TAG, "Inside of showDatePickerDialog method");

        DialogFragment dialogFragment = new DatePickerFragment();//DialogFragment
        dialogFragment.show(getSupportFragmentManager(), "Date Picker");//show

        Log.d(TAG, "end of showDatePickerDialog method");
    }

    private void showTimePickerDialog(){
        Log.d(TAG, "Inside of showTimePickerDialog method");

        DialogFragment dialogFragment = new TimePickerFragment();//DialogFragment
        dialogFragment.show(getSupportFragmentManager(), "Time Picker");//show

        Log.d(TAG, "end of showTimePickerDialog method");
    }

    protected void processDatePickerResult(int year, int month, int day){
        Log.d(TAG, "Inside of processDatePickerResult method");

        dateAsString = String.format("%02d/%02d/%04d",month, day, year); // MM/DD/YYYY
        textViewDisplayDate.setText("DATE: " + dateAsString);
        textViewDisplayDate.setVisibility(View.VISIBLE);
        enablePackagingButton();

        Log.d(TAG, "end of processDatePickerResult method");
    }

    protected void processTimePickerResult(int hour, int minute){
        Log.d(TAG, "Inside of processTimePickerResult method");

        timeAsString = String.format("%02d:%02d",hour, minute); // hh:mm
        textViewDisplayTime.setText("TIME: " + timeAsString);
        textViewDisplayTime.setVisibility(View.VISIBLE);
        enablePackagingButton();

        Log.d(TAG, "Inside of processTimePickerResult method");
    }

    private void enablePackagingButton() {
        if(textViewDisplayDate.getText().length() > 0 && textViewDisplayTime.getText().length() > 0){
            buttonPackageOptions.setVisibility(View.VISIBLE);
        }
    }

    private void goToPackagingActivity(){
        Log.d(TAG, "Inside of goToPackagingActivity method");

        Intent intent = new Intent(this, PackagingActivity.class);
        intent.putExtra(KEY_DATE, dateAsString);
        intent.putExtra(KEY_TIME, timeAsString);
        startActivity(intent);

        Log.d(TAG, "end of goToPackagingActivity method");
    }
}