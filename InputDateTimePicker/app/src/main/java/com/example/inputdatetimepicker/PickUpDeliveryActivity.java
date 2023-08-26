package com.example.inputdatetimepicker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PickUpDeliveryActivity extends AppCompatActivity {

    public static final String TAG = "PickUpDeliveryActivity";
    private ArrayList<String> checkedOptions = new ArrayList<>();
    private String dateAsString, timeAsString;
    private final String[] EMAIL_ADDRESSES = {"superman@heroes.org", "wonderwoman@heroes.org"};
    private final String EMAIL_SUBJECT = "Order details";
    private String orderDetailsAll = "Order Details:\n";
    private String orderOptions = "";
    private TextView textViewDisplayDate, textViewDisplayTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_delivery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG, "inside of onCreate method");

        Intent intent = getIntent();
        dateAsString = intent.getStringExtra(MainActivity.KEY_DATE);
        timeAsString = intent.getStringExtra(MainActivity.KEY_TIME);
        checkedOptions = intent.getStringArrayListExtra(PackagingActivity.KEY_PACKAGING_OPTIONS);

        Log.d(TAG, "date from intent\t"+dateAsString);
        Log.d(TAG, "time from intent\t"+timeAsString);
        Log.d(TAG, "packaging " + checkedOptions.toString());

        textViewDisplayDate = findViewById(R.id.tv_pickup_display_date_chosen);
        textViewDisplayTime = findViewById(R.id.tv_pickup_display_time_chosen);

        textViewDisplayDate.setText(dateAsString);
        textViewDisplayTime.setText(timeAsString);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                orderDetailsAll = String.format("order options are: %s %s %s %s", dateAsString, timeAsString, checkedOptions.toString(), orderOptions);
                Log.d(TAG, "fab clicked "+ orderDetailsAll);
                sendEmail();
            }
        });
        Log.d(TAG, "end of onCreate method");
    }

    public void onRadioButtonClicked(View view) {
        Log.d(TAG, "inside of onRadioButtonClicked method");

        boolean chosen = ((RadioButton)view).isChecked();
        orderOptions = ((RadioButton)view).getText().toString();

        Log.d(TAG, "order options : "+orderOptions);
        switch (view.getId()){
            case R.id.rb_cancel:
                goBackToMainActivity();
                break;
            case R.id.rb_delivery:
                displayDeliveryDetails();
                break;
            case R.id.rb_pickup:
                remindToPickup();
                break;
            default:
                Log.d(TAG, "no clue what radio button was chosen");
                break;
        }

        Log.d(TAG, "end of onRadioButtonClicked method");
    }

    private void displayDeliveryDetails(){
        Log.d(TAG, "inside of displayDeliveryDetails method");
        Toast.makeText(this, "Will deliver on " + dateAsString + " at " + timeAsString, Toast.LENGTH_LONG).show();

    }

    private void remindToPickup(){
        Log.d(TAG, "inside of remindToPickup method");
        Toast.makeText(this, "Pick up on " + dateAsString + " at " + timeAsString, Toast.LENGTH_LONG).show();
    }

    private void goBackToMainActivity(){
        Log.d(TAG, "inside of goBackToMainActivity method");
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Confirm Cancellation");
        alertBuilder.setMessage("Do you want to cancel your order?");
        alertBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG,"order cancelled go back to MainActivity");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        alertBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "not cancelling order... continue");
            }
        });
        alertBuilder.show();
    }

    private void sendEmail(){
        Log.d(TAG, "inside sendEmail method");
        Intent intent = ShareCompat.IntentBuilder.from(this).setType("text/plain")
                .addEmailTo(EMAIL_ADDRESSES)
                .setSubject(EMAIL_SUBJECT)
                .setText(orderDetailsAll)
                .createChooserIntent();
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
        Log.d(TAG, "end sendEmail method");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "inside of onCreateOptionsMenu method");

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "inside of onOptionsItemSelected method");

        switch(item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_favorites:
                Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_recent:
                Toast.makeText(this, "Recent", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Log.d("TAG", "I don't know what was selected from the menu");
        }

        return super.onOptionsItemSelected(item);
    }
}