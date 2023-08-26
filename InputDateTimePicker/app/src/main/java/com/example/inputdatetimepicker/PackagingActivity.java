package com.example.inputdatetimepicker;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;

public class PackagingActivity extends AppCompatActivity {

    public static final String TAG = "PackagingActivity";
    protected static final String KEY_PACKAGING_OPTIONS = "com.example.inputdatetimepicker.packaging_options";
    private String packagingOptionsSelected = "";
    private ArrayList<String> checkedOptions = new ArrayList<>();
    private String dateAsString, timeAsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packaging);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG, "inside of onCreate method");

        Intent intent = getIntent();
        dateAsString = intent.getStringExtra(MainActivity.KEY_DATE);
        timeAsString = intent.getStringExtra(MainActivity.KEY_TIME);

        Log.d(TAG, "date from intent\t"+dateAsString);
        Log.d(TAG, "time from intent\t"+timeAsString);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToPickupDeliveryActivity();
            }
        });
        Log.d(TAG, "end of onCreate method");
    }

    public void onCheckboxClicked(View view) {
        Log.d(TAG,"Inside of onCheckboxClicked method");
        boolean isChecked = ((CheckBox)view).isChecked();
        switch(view.getId()){
            case R.id.checkbox_bag:
                if(isChecked){
                    Log.d(TAG, "Yes Bag");
                    checkedOptions.add(checkedOptions.size(), "Bag");
                }
                else{
                    checkedOptions.remove("Bag");
                }
                break;
            case R.id.checkbox_balloons:
                if(isChecked){
                    Log.d(TAG, "Yes Balloons");
                    checkedOptions.add(checkedOptions.size(), "Balloons");
                }
                else{
                    checkedOptions.remove("Balloons");
                }
                break;
            case R.id.checkbox_bow:
                if(isChecked){
                    Log.d(TAG, "Yes Bow");
                    checkedOptions.add(checkedOptions.size(), "Bow");
                }
                else{
                    checkedOptions.remove("Bow");
                }
                break;
            case R.id.checkbox_box:
                if(isChecked){
                    Log.d(TAG, "Yes Box");
                    checkedOptions.add(checkedOptions.size(), "Box");
                }
                else{
                    checkedOptions.remove("Box");
                }
                break;
            case R.id.checkbox_bubble_wrap:
                if(isChecked){
                    Log.d(TAG, "Yes Bubble Wrap");
                    checkedOptions.add(checkedOptions.size(), "Bubble Wrap");
                }
                else{
                    checkedOptions.remove("Bubble Wrap");
                }
                break;
            case R.id.checkbox_giftwrap:
                if(isChecked){
                    Log.d(TAG, "Yes Gift Wrap");
                    checkedOptions.add(checkedOptions.size(), "Gift Wrap");
                }
                else{
                    checkedOptions.remove("Gift Wrap");
                }
                break;
            default:
                Log.d(TAG, "Not sure what was checked/unchecked");
                break;
        }
        packagingOptionsSelected = "PACKAGING: " + checkedOptions.toString();
        Log.d(TAG, "All OPTIONS FOR PACKAGING " + packagingOptionsSelected);
        Log.d(TAG,"end of onCheckboxClicked method");
    }

    private void goToPickupDeliveryActivity() {
        Intent intent = new Intent(this, PickUpDeliveryActivity.class);
        intent.putExtra(MainActivity.KEY_DATE, dateAsString);
        intent.putExtra(MainActivity.KEY_TIME, timeAsString);
        intent.putExtra(KEY_PACKAGING_OPTIONS, checkedOptions);
        startActivity(intent);
    }

}