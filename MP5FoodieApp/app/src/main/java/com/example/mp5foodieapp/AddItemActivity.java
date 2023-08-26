package com.example.mp5foodieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity";
    private EditText etTitle, etDescription, etIngredients, etCalories, etLink;
    protected static final String KEY_EXTRA_NEW_TITLE = "com.example.mp5foodieapp.extra.NEW_TITLE";
    protected static final String KEY_EXTRA_NEW_DESCRIPTION = "com.example.mp5foodieapp.extra.NEW_DESCRIPTION";
    protected static final String KEY_EXTRA_NEW_INGREDIENTS = "com.example.mp5foodieapp.extra.NEW_INGREDIENTS";
    protected static final String KEY_EXTRA_NEW_CALORIES = "com.example.mp5foodieapp.extra.NEW_CALORIES";
    protected static final String KEY_EXTRA_NEW_LINK = "com.example.mp5foodieapp.extra.NEW_LINK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        getReferencesToWidgets();
    }

    private void getReferencesToWidgets(){
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etIngredients = findViewById(R.id.etIngredients);
        etCalories = findViewById(R.id.etCalories);
        etLink = findViewById(R.id.etLink);
    }

    public void addMeal(View view) {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        String ingredients = etIngredients.getText().toString();
        String calories = etCalories.getText().toString();
        String link = etLink.getText().toString();

        Intent intentNewMeal = new Intent();

        intentNewMeal.putExtra(KEY_EXTRA_NEW_TITLE, title);
        intentNewMeal.putExtra(KEY_EXTRA_NEW_DESCRIPTION, description);
        intentNewMeal.putExtra(KEY_EXTRA_NEW_INGREDIENTS, ingredients);
        intentNewMeal.putExtra(KEY_EXTRA_NEW_CALORIES, calories);
        intentNewMeal.putExtra(KEY_EXTRA_NEW_LINK, link);

        setResult(RESULT_OK, intentNewMeal);
        finish();
    }
}