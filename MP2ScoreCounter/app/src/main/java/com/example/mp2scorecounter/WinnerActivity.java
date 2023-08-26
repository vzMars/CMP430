package com.example.mp2scorecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class WinnerActivity extends AppCompatActivity {

    public static final String TAG = "WinnerActivity";
    private TextView winningTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "inside onCreate method of WinnerActivity");
        setContentView(R.layout.activity_winner);

        Intent intent = getIntent();
        String winner = intent.getStringExtra(MainActivity.KEY_EXTRA_WINNER);
        int finalScore = intent.getIntExtra(MainActivity.KEY_EXTRA_FINAL_SCORE, 0);

        winningTeam = findViewById(R.id.winning_team);
        if(finalScore > 1) {
            winningTeam.setText(winner + " Team Won By " + finalScore + " Points!");
        }
        else {
            winningTeam.setText(winner + " Team Won By " + finalScore + " Point!");
        }


        Log.d(TAG, "end of onCreate method of WinnerActivity");
    }
}