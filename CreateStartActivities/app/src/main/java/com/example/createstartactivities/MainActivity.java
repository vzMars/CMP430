package com.example.createstartactivities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    protected static final String KEY_EXTRA_MESSAGE = "com.example.createstartactivities.extra.MESSAGE";
    private static final int TEXT_REQUEST = 1; //request type
    private EditText editTextMain;
    private TextView textViewHeaderReply, textViewReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "inside onCreate method of MainActivity");

        editTextMain = findViewById(R.id.editText_main);
        textViewHeaderReply = findViewById(R.id.text_header_reply);
        textViewReply = findViewById(R.id.text_message_reply);


        Log.d(TAG, "end of onCreate method of MainActivity");
    }

    public void launchSecondActivity(View view) {
        Log.d(TAG, "inside launchSecond method of MainActivity");

        Intent intent = new Intent(this, SecondActivity.class);
        String message = editTextMain.getText().toString();

        intent.putExtra(KEY_EXTRA_MESSAGE, message);

        editTextMain.setText("");//clear out the editText by setting to ""
        //startActivity(intent); //start the activity but not expecting or perparing for receiving a result response

        startActivityForResult(intent, TEXT_REQUEST);

        Log.d(TAG, "end of launchSecond method of MainActivity");
    }

    //onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "inside of onActivityResult method of MainActivity");

        if(resultCode == RESULT_OK){
            if(requestCode == TEXT_REQUEST){
                String responseFromSecond = data.getStringExtra(SecondActivity.KEY_EXTRA_RESPONSE);
                textViewHeaderReply.setVisibility(View.VISIBLE);
                textViewReply.setText(responseFromSecond);
                textViewReply.setVisibility(View.VISIBLE);
            }

        }

        Log.d(TAG, "end of onActivityResult method of MainActivity");
    }
}