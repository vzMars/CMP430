package com.example.lifecycleandstate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

public class FoodChooserActivity extends AppCompatActivity implements View.OnClickListener{

    protected static final String KEY_FOOD_CHOICE = "food_choice";
    public static final String TAG = "FoodChooserActivity";
    ImageButton [] imageButtons;
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_chooser);
        Log.d(TAG, "inside of onCreate");

        gridLayout = findViewById(R.id.gridLayout_Food_Images);

        int numFoods = gridLayout.getChildCount();
        imageButtons = new ImageButton[numFoods];

        for(int i = 0;i < imageButtons.length;i++){
            imageButtons[i] = (ImageButton)gridLayout.getChildAt(i);
            imageButtons[i].setOnClickListener(this);
        }

        Log.d(TAG, "end of onCreate");
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "inside of onCreate");
        int viewId = v.getId();
        String textFoodChoice = findViewById(viewId).getContentDescription().toString();
        Log.d(TAG, "food chosen is " + textFoodChoice);
        Intent intentFoodChoice = new Intent();
        intentFoodChoice.putExtra(KEY_FOOD_CHOICE, textFoodChoice);
        setResult(RESULT_OK, intentFoodChoice);
        finish();
        Log.d(TAG, "end of onClick");
    }
}