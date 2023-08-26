package com.example.mp2scorecounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    protected static final String KEY_EXTRA_WINNER = "com.example.mp2scorecounter.extra.WINNER";
    protected static final String KEY_EXTRA_FINAL_SCORE = "com.example.mp2scorecounter.extra.FINAL_SCORE";

    private static final String BLUE_SCORE = "BLUE_Score";
    private static final String RED_SCORE = "RED_Score";

    private TextView blueTeam;
    private TextView redTeam;

    private int blueScore = 0;
    private int redScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "inside onCreate method of MainActivity");

        blueTeam = findViewById(R.id.team_blue);
        redTeam = findViewById(R.id.team_red);

        Log.d(TAG, "end of onCreate method of MainActivity");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "inside of onSaveInstanceState");

        outState.putInt(BLUE_SCORE, blueScore);
        outState.putInt(RED_SCORE, redScore);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "inside of onRestoreInstanceState");

        blueScore = savedInstanceState.getInt(BLUE_SCORE,0);
        redScore = savedInstanceState.getInt(RED_SCORE,0);

        if(blueTeam != null || redTeam != null) {
            blueTeam.setText("" + blueScore);
            redTeam.setText("" + redScore);
        }
    }

    public void blueIncrement(View view) {
        Log.d(TAG, "inside blueIncrement");

        blueScore++;
        blueTeam.setText("" + blueScore);

        if(blueScore == 5) {
            Intent intent = new Intent(this,WinnerActivity.class);
            int finalScore =  blueScore - redScore;
            clearOutScores();

            intent.putExtra(KEY_EXTRA_WINNER, "Blue");
            intent.putExtra(KEY_EXTRA_FINAL_SCORE, finalScore);

            startActivity(intent);
        }
    }

    public void redIncrement(View view) {
        Log.d(TAG, "inside redIncrement");

        redScore++;
        redTeam.setText("" + redScore);

        if(redScore == 5) {
            Intent intent = new Intent(this,WinnerActivity.class);
            int finalScore = redScore - blueScore;
            clearOutScores();

            intent.putExtra(KEY_EXTRA_WINNER, "Red");
            intent.putExtra(KEY_EXTRA_FINAL_SCORE, finalScore);

            startActivity(intent);
        }
    }

    public void clearOutScores() {
        blueScore = 0;
        redScore = 0;

        blueTeam.setText("" + blueScore);
        redTeam.setText("" + redScore);
    }
}