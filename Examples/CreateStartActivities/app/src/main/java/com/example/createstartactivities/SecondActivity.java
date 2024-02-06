package com.example.createstartactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SecondActivity extends AppCompatActivity {

    public static final String TAG = "SecondActivity";
    protected static final String KEY_EXTRA_RESPONSE = "com.example.createstartactivities.extra.EXTRA_RESPONSE";
    private TextView textViewMessageIncoming;
    private EditText editTextResponse;//declare variable for the editTextResponse

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG, "inside onCreate method of SecondActivity");

        Intent intent = getIntent();
        String messageReceived = intent.getStringExtra(MainActivity.KEY_EXTRA_MESSAGE);

        textViewMessageIncoming = findViewById(R.id.text_message2);
        textViewMessageIncoming.setText(messageReceived);

        editTextResponse = findViewById(R.id.editText_second);//gain reference to editTextResponse

        Log.d(TAG, "end of onCreate method of SecondActivity");
    }

    public void returnReplyToMain(View view) {
        Log.d(TAG, "inside returnReplyToMain method of SecondActivity");

        String replyMessage = editTextResponse.getText().toString();

        Intent intent = new Intent();
        intent.putExtra(KEY_EXTRA_RESPONSE, replyMessage);
        setResult(RESULT_OK, intent);
        finish();

        Log.d(TAG, "end of returnReplyToMain method of SecondActivity");
    }
}