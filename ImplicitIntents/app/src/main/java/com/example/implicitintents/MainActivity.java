package com.example.implicitintents;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "MainActivity";
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_CALL_PHONE = 2;


    private EditText editTextWeb, editTextLoc, editTextPlainMsg;
    private Button buttonWeb, buttonLoc, buttonPlainMsg, buttonTakePic, buttonDial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "inside onCreate method");

        getReferencesToWidgets();
        setOnClickListenersForButtons();

        Log.d(TAG, "end of onCreate method");
    }

    private void getReferencesToWidgets(){
        Log.d(TAG, "inside getReferencesToWidgets method");

        editTextWeb = findViewById(R.id.editTextWeb);
        editTextLoc = findViewById(R.id.editTextLoc);
        editTextPlainMsg = findViewById(R.id.editTextPlainMessage);

        buttonWeb = findViewById(R.id.buttonWeb);
        buttonLoc = findViewById(R.id.buttonLoc);
        buttonPlainMsg = findViewById(R.id.buttonPlainMessage);
        buttonTakePic = findViewById(R.id.buttonTakePicture);
        buttonDial = findViewById(R.id.buttonDial);

        Log.d(TAG, "end of getReferencesToWidgets method");
    }

    private void setOnClickListenersForButtons(){
        Log.d(TAG, "inside setOnClickListenersForButtons method");

        buttonWeb.setOnClickListener(this);
        buttonLoc.setOnClickListener(this);
        buttonPlainMsg.setOnClickListener(this);
        buttonTakePic.setOnClickListener(this);
        buttonDial.setOnClickListener(this);

        Log.d(TAG, "end setOnClickListenersForButtons method");
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "inside onClick method");
        switch (v.getId()){
            case R.id.buttonWeb:
                Log.d(TAG, "clicked buttonWeb");
                openWebPage();
                break;
            case R.id.buttonLoc:
                Log.d(TAG, "clicked buttonLoc");
                findLocation();
                break;
            case R.id.buttonPlainMessage:
                Log.d(TAG, "clicked buttonPlainMessage");
                sendText();
                break;
            case R.id.buttonTakePicture:
                Log.d(TAG, "clicked buttonTakePicture");
                takePicture();
                break;
            case R.id.buttonDial:
                Log.d(TAG, "clicked buttonDial");
                dialNumber();
                break;
        }

        Log.d(TAG, "end onClick method");
    }

    private void openWebPage(){
        Log.d(TAG, "inside of openWebPage method");
        String url = editTextWeb.getText().toString();
        Uri webAddress = Uri.parse("http://" + url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webAddress);
        if(intent.resolveActivity(getPackageManager())   !=  null  ){
            startActivity(intent);
        }
        else{
            Log.d(TAG, "Cannot handle opening webpage "+url);
            Toast.makeText(this, "Cannot handle opening webpage "+url, Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "end of openWebPage method");
    }

    private void findLocation(){
        Log.d(TAG, "inside findLocation method");
        String loc = editTextLoc.getText().toString();
        Uri geoLoc = Uri.parse("geo:0,0?q="+loc);
        Intent intent = new Intent(Intent.ACTION_VIEW, geoLoc);
        if(intent.resolveActivity(getPackageManager())   !=  null  ){
            startActivity(intent);
        }
        else{
            Log.d(TAG, "Cannot find location "+loc);
            Toast.makeText(this, "Cannot find location "+loc, Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "end of findLocation method");
    }

    private void sendText(){
        Log.d(TAG, "inside sendText method");
        String textToSend = editTextPlainMsg.getText().toString();
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Pick an app")
                .setText(textToSend)
                .startChooser();
        Log.d(TAG, "end of sendText method");
    }

    private void takePicture(){
        Log.d(TAG, "inside takePicture method");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())   !=  null  ){
            startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);//need to override onActivityResult to handle response
            //startActivity(intent);//start but not wait to receive any picture back
        }
        else{
            Log.d(TAG, "Cannot take pic");
            Toast.makeText(this, "Cannot take pic", Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "end of takePicture method");
    }

    private void dialNumber(){
        Log.d(TAG, "inside dialNumber method");
        //Intent intent = new Intent(Intent.ACTION_DIAL);//doesn't need AndroidManifest permissions
        //startActivity(intent);

        Intent intent = new Intent(Intent.ACTION_CALL);// needs AndroidManifest permissions
        intent.setData(Uri.parse("tel:2128675309"));
        if(intent.resolveActivity(getPackageManager()) != null){
            //dialer app is available for usage
            //need to check  permissions
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "need to request permission to CALL_PHONE");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
                //need to override onRequestPermissionsResult method for when the REQUEST_CALL_PHONE triggers the callback method
            }
            else{// have permission to CALL_PHONE so we can start  the activity by passing in the intent
                startActivity(intent);
            }
        }
        else{
            Log.d(TAG, "Cannot place phone call");
            Toast.makeText(this, "Cannot place phone call", Toast.LENGTH_LONG).show();
        }


        Log.d(TAG, "end of dialNumber method");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "inside onActivityResult method");
        if(resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE){
            Intent intentShowImage = new Intent(this, ShowImageActivity.class);// explicit intent to trigger the second activity in this app
            intentShowImage.putExtras(data);// pass the data received (image captured) along to the second activity in this app
            startActivity(intentShowImage);
        }

        Log.d(TAG, "end of onActivityResult method");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "inside onRequestPermissionsResult method");
        switch (requestCode){
            case REQUEST_CALL_PHONE:{
                if( (grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED) ){
                    dialNumber(); //place call by calling our method again now that we have permission
                }
                else{
                    Log.d(TAG, "Permission to call not granted");
                }
            }
        }


        Log.d(TAG, "end of onRequestPermissionsResult method");

    }
}