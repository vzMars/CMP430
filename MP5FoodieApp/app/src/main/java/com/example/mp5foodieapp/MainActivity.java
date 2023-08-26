package com.example.mp5foodieapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private MyReceiver myReceiver;
    protected static final String ACTION_CUSTOM_BROADCAST = "com.example.I_AM_HOME";
    protected static final String KEY_EXTRA_TITLE = "com.example.mp5foodieapp.extra.TITLE";
    protected static final String KEY_EXTRA_DESCRIPTION = "com.example.mp5foodieapp.extra.DESCRIPTION";
    protected static final String KEY_EXTRA_INGREDIENTS = "com.example.mp5foodieapp.extra.INGREDIENTS";
    protected static final String KEY_EXTRA_CALORIES = "com.example.mp5foodieapp.extra.CALORIES";
    protected static final String KEY_EXTRA_LINK = "com.example.mp5foodieapp.extra.LINK";
    protected static final String KEY_EXTRA_IMAGE = "com.example.mp5foodieapp.extra.IMAGE";

    private RecyclerView recyclerView;
    private ArrayList<MealItem> mealData;
    private MealItemAdapter mealItemAdapter;
    private static final int TEXT_REQUEST = 1;
    private MealItem newMeal;

    private int gridColumnCount; //use column number in integers.xml

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridColumnCount = getResources().getInteger(R.integer.grid_column_count);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));

        mealData = new ArrayList<>();
        mealItemAdapter = new MealItemAdapter(this, mealData);
        recyclerView.setAdapter(mealItemAdapter);

        loadMealData();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddItemActivity();
            }
        });

        IntentFilter intentFilter = new IntentFilter(ACTION_CUSTOM_BROADCAST);
        myReceiver = new MyReceiver();
        if(intentFilter != null){
            registerReceiver(myReceiver, intentFilter);
        }
    }

    private void loadMealData() {
        mealData.clear();

        String [] mealTitles = getResources().getStringArray(R.array.meal_titles);
        String [] mealDescriptions = getResources().getStringArray(R.array.meal_description);
        String [] mealIngredients = getResources().getStringArray(R.array.meal_ingredients);
        String [] mealCalories = getResources().getStringArray(R.array.meal_calories);
        String [] mealLinks = getResources().getStringArray(R.array.meal_links);
        TypedArray mealImages = getResources().obtainTypedArray(R.array.meal_images);

        for(int i = 0;i < mealTitles.length;i++){
            mealData.add(new MealItem(mealTitles[i],mealDescriptions[i],mealIngredients[i],mealCalories[i],mealLinks[i],mealImages.getResourceId(i, 0)));
        }

        mealItemAdapter.notifyDataSetChanged();
        mealImages.recycle();

    }

    private void launchAddItemActivity(){
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == TEXT_REQUEST){
            String title = data.getStringExtra(AddItemActivity.KEY_EXTRA_NEW_TITLE);
            String description = data.getStringExtra(AddItemActivity.KEY_EXTRA_NEW_DESCRIPTION);
            String ingredients = data.getStringExtra(AddItemActivity.KEY_EXTRA_NEW_INGREDIENTS);
            String calories = data.getStringExtra(AddItemActivity.KEY_EXTRA_NEW_CALORIES);
            String link = data.getStringExtra(AddItemActivity.KEY_EXTRA_NEW_LINK);

            Log.d(TAG, title + description + ingredients + calories + link);
            newMeal = new MealItem(title, description, ingredients, calories, link);

            mealData.add(mealData.size(), newMeal);
            mealItemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected class MyReceiver extends BroadcastReceiver{

        private Context context;

        @Override
        public void onReceive(Context context, Intent intent) {
            this.context = context;
            String intentAction = intent.getAction();
            intentAction(intentAction);
        }

        private void intentAction(String intentAction) {
            switch (intentAction) {
                case ACTION_CUSTOM_BROADCAST:
                    Log.d(TAG, "I am home broadcast");
                    chooseRandomMeal();
                    break;
                default:
                    Log.d(TAG, "Don't know what broadcast came in");
                    Toast.makeText(context, "Unknown broadcast??", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        private void chooseRandomMeal() {
            Random random = new Random();
            MealItem currentMeal = mealData.get(random.nextInt(mealData.size()));

            Intent intent = new Intent(context, MealItemActivity.class);

            intent.putExtra(KEY_EXTRA_TITLE, currentMeal.getTitle());
            intent.putExtra(KEY_EXTRA_DESCRIPTION, currentMeal.getDescription());
            intent.putExtra(KEY_EXTRA_INGREDIENTS, currentMeal.getIngredients());
            intent.putExtra(KEY_EXTRA_CALORIES, currentMeal.getCalories());
            intent.putExtra(KEY_EXTRA_LINK, currentMeal.getLink());
            intent.putExtra(KEY_EXTRA_IMAGE, currentMeal.getImageId());

            startActivity(intent);
        }

    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(myReceiver);
        super.onDestroy();
    }
}