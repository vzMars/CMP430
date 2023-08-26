package com.example.inputcontrolspickers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TextWatcher {

    public static final String TAG = "MainActivity";
    public static final int REQUEST_PERMISSION_PHONE_CALL = 1;
    protected static final String KEY_TEL_NUM = "com.example.inputcontrolspickers.tel_number";
    protected static final String KEY_TEL_TYPE = "com.example.inputcontrolspickers.tel_type";
    private String spinnerLabel = "";
    EditText editText;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    TextView textViewDisplaySelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "inside of onCreate method");

        textViewDisplaySelection = findViewById(R.id.text_phone_label);
        editText = findViewById(R.id.edit_text_main);
        spinner = findViewById(R.id.spinner);

        adapter = ArrayAdapter.createFromResource(this, R.array.phone_labels_array, android.R.layout.simple_spinner_item);

        if(spinner != null){
            spinner.setOnItemSelectedListener(this);
            spinner.setAdapter(adapter);
        }
        if(editText != null){
            editText.addTextChangedListener(this);
        }

        Log.d(TAG, "end of onCreate method");
    }

    private void showText() {
        Log.d(TAG, "inside of showText method");

        if(editText != null){
            String text = editText.getText().toString();
            if(text.length() > 0){
                textViewDisplaySelection.setText(spinnerLabel + ": " + text);
            }
            else{
                textViewDisplaySelection.setText("No Number");
            }
        }

        Log.d(TAG, "end of showText method");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Log.d(TAG, "inside of onItemSelected method");

        spinnerLabel = adapterView.getItemAtPosition(position).toString();
        textViewDisplaySelection.setText(spinnerLabel);

        Log.d(TAG, "end of onItemSelected method");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(TAG, "inside of onNothingSelected method");
        Log.d(TAG, "end of onNothingSelected method");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d(TAG, "inside of onTextChanged method");
        showText();
        Log.d(TAG, "end of onTextChanged method");
    }

    @Override
    public void afterTextChanged(Editable s) { }

    public void goToNextActivity(View view) {
        Log.d(TAG, "inside of goToNextActivity method");

        String numberAsString = editText.getText().toString();
        if (numberAsString.length() > 0) {
            Log.d(TAG, "editText not empty will proceed to next activity");
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(KEY_TEL_NUM, numberAsString);
            intent.putExtra(KEY_TEL_TYPE, spinnerLabel);
            startActivity(intent);
        }
        else{
            Log.d(TAG, "editText for the number has NO NUMBER");
            Toast.makeText(this, R.string.text_no_number, Toast.LENGTH_LONG).show();
            editText.setBackgroundColor(getColor(R.color.design_default_color_error));
        }

        Log.d(TAG, "end of goToNextActivity method");
    }

    public void callPhone(View view) {
        Log.d(TAG, "inside of callPhone method");
        callUsingImplicitIntent();
        Log.d(TAG, "end of callPhone method");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "inside of onRequestPermissionsResult method");

        switch (requestCode){
            case REQUEST_PERMISSION_PHONE_CALL:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.d(TAG, "Permission granted to place phone call");
                    callUsingImplicitIntent();
                }
                else{
                    Log.d(TAG, "Permission denied to place phone call");
                }
                break;
        }

        Log.d(TAG, "end of onRequestPermissionsResult method");
    }

    private void callUsingImplicitIntent() {
        Log.d(TAG, "inside of callUsingImplicitIntent method");


        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel: " + editText.getText().toString());
        intent.setData(uri);
        if(intent.resolveActivity(getPackageManager()) != null){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_PHONE_CALL);
            }
            else{//have the permission so we just start the implicit intent
                startActivity(intent);
            }
        }

        Log.d(TAG, "end of callUsingImplicitIntent method");
    }
}