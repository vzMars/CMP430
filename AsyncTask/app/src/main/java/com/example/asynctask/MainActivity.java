package com.example.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MyAsyncTask[] myAsyncTasks = new MyAsyncTask[3];
    private TextView[] textViews = new TextView[3];
    private Button[] buttons = new Button[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViews[0] = findViewById(R.id.textView_Status0);
        textViews[1] = findViewById(R.id.textView_Status1);
        textViews[2] = findViewById(R.id.textView_Status2);
        buttons[0] = findViewById(R.id.button_start_task0);
        buttons[1] = findViewById(R.id.button_start_task1);
        buttons[2] = findViewById(R.id.button_start_task2);
    }

    public void startTask(View view) {
        int viewId = view.getId();
        switch (viewId){
            case R.id.button_start_task0:
                textViews[0].setText("Started Task");
                myAsyncTasks[0] = new MyAsyncTask(textViews[0]);
                myAsyncTasks[0].execute();
                break;
            case R.id.button_start_task1:
                textViews[1].setText("Started Task");
                myAsyncTasks[1] = new MyAsyncTask(textViews[1]);
                myAsyncTasks[1].execute();
                break;
            case R.id.button_start_task2:
                textViews[2].setText("Started Task");
                myAsyncTasks[2] = new MyAsyncTask(textViews[2]);
                myAsyncTasks[2].execute();
                break;
        }

        /*
        the AsyncTask will continue to run in the background even after a roation and the Activity has been destroyed and recreated
         */

    }
}