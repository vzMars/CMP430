package com.example.mp5foodieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MealItemActivity extends AppCompatActivity {

    private static final String TAG = "MealItemActivity";
    private TextView tvTitle, tvDescription, tvIngredients, tvCalories, tvLink;
    private ImageView mealImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_item);

        getReferencesToWidgets();
        setMealItemText();
    }

    private void getReferencesToWidgets(){
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvIngredients = findViewById(R.id.tvIngredients);
        tvCalories = findViewById(R.id.tvCalories);
        tvLink = findViewById(R.id.tvLink);
        mealImage = findViewById(R.id.mealImage);
    }

    private void setMealItemText(){
        Intent intent = getIntent();

        String title = intent.getStringExtra(MealItemAdapter.KEY_EXTRA_TITLE);
        String description = intent.getStringExtra(MealItemAdapter.KEY_EXTRA_DESCRIPTION);
        String ingredients = intent.getStringExtra(MealItemAdapter.KEY_EXTRA_INGREDIENTS);
        String calories = intent.getStringExtra(MealItemAdapter.KEY_EXTRA_CALORIES);
        String link = intent.getStringExtra(MealItemAdapter.KEY_EXTRA_LINK);
        int image = intent.getIntExtra(MealItemAdapter.KEY_EXTRA_IMAGE, 0);

        tvTitle.setText(title);
        tvDescription.setText(description);
        tvIngredients.setText(ingredients);
        tvCalories.setText(calories);
        tvLink.setText(link);
        mealImage.setImageResource(image);
        Toast.makeText(this,"Happy Cooking " + title, Toast.LENGTH_SHORT).show();
    }
}