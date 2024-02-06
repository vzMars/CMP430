package com.example.lifecycleandstate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final int TEXT_REQUEST = 1;//request code
    public static final String KEY_FOOD_LIST = "food_list";
    private TextView textViewFoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "inside of onCreate method");

        textViewFoodList = findViewById(R.id.textView_food_list);

        Log.d(TAG, "end of onCreate method");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "inside onSaveInstanceState method");
        if(textViewFoodList != null){
            String foodList = textViewFoodList.getText().toString();
            Log.d(TAG, "textViewFoodList is not null... it has \n" + foodList + "\n\n");
            outState.putString(KEY_FOOD_LIST, foodList);
        }
        Log.d(TAG, "end of onSaveInstanceState method");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "inside onRestoreInstanceState method");
        String foodList = savedInstanceState.getString(KEY_FOOD_LIST);
        if(textViewFoodList != null){
            textViewFoodList.setText(foodList);
            Log.d(TAG, "textViewFoodList is not null... it has \n" + foodList + "\n\n");
        }
        Log.d(TAG, "inside onRestoreInstanceState method");
    }

    public void launchFoodChooserActivity(View view) {
        Log.d(TAG, "inside of launchFoodChooserActivity method");
        Intent intentLaunchFoodChooserActivity = new Intent(this, FoodChooserActivity.class);
        startActivityForResult(intentLaunchFoodChooserActivity, TEXT_REQUEST);
        Log.d(TAG, "end of launchFoodChooserActivity method");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "inside of onActivityResult method");
        if(resultCode == RESULT_OK && requestCode == TEXT_REQUEST){
            String foodChosen = data.getStringExtra(FoodChooserActivity.KEY_FOOD_CHOICE);
            if(textViewFoodList.getText().equals(getString(R.string.no_food_chosen_yet))){
                textViewFoodList.setText(foodChosen+"\n");
            }
            else{
                textViewFoodList.append(foodChosen+"\n");
            }
        }
        else {
            Log.d(TAG, "Problem");
        }

        Log.d(TAG, "end of onActivityResult method");


    }

    public void launchSendList(View view) {
        Log.d(TAG, "inside of launchSendList method");
        Intent intentSendMail = new Intent(Intent.ACTION_SEND);
        intentSendMail.putExtra(Intent.EXTRA_TEXT, textViewFoodList.getText().toString());
        intentSendMail.setType("text/plain");

        if(intentSendMail.resolveActivity(getPackageManager()) != null){
            startActivity(intentSendMail);
        }

        Log.d(TAG, "end of launchSendList method");
    }
}