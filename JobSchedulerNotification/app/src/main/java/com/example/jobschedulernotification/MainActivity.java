package com.example.jobschedulernotification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private JobScheduler jobScheduler;
    private static final int JOB_ID = 0;

    private int selectedNetworkID, selectedNetworkOption;
    private RadioGroup networkOptions;
    private Switch switchDeviceIdle, switchDeviceCharging;
    private SeekBar seekBar;
    private TextView seekBarProgress;
    private int seekBarProgressValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "inside onCreate method");

        networkOptions = findViewById(R.id.networkOptions);
        switchDeviceCharging = findViewById(R.id.chargingSwitch);
        switchDeviceIdle = findViewById(R.id.idleSwitch);
        seekBar = findViewById(R.id.seekBar);
        seekBarProgress = findViewById(R.id.seekBarProgress);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarProgressValue = progress;
                seekBarProgress.setText(seekBarProgressValue + "s"); //number of seconds for Override Deadline by which job must run
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void scheduleJob(View view) {
        Log.d(TAG, "inside scheduleJobs method");

        selectedNetworkID = networkOptions.getCheckedRadioButtonId();

        switch(selectedNetworkID){
            case R.id.anyNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
                break;
            case R.id.wifiNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
            case R.id.cellularNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_CELLULAR;
                break;
            default:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
                break;
        }
        if(selectedNetworkOption != JobInfo.NETWORK_TYPE_NONE){
            Log.d(TAG, "will build and schedule job now. network constraint selected ="+selectedNetworkOption);

            jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

            ComponentName serviceName = new ComponentName(getPackageName(), NotificationJobService.class.getName());

            JobInfo.Builder jobBuilder = new JobInfo.Builder(JOB_ID, serviceName);
            jobBuilder.setRequiredNetworkType(selectedNetworkOption); //setNetworkOption (Any, Unmetered, None)
            jobBuilder.setRequiresDeviceIdle(switchDeviceIdle.isChecked()); //IDLE or Not
            jobBuilder.setRequiresCharging(switchDeviceCharging.isChecked());  //Charging or Not
            if(seekBarProgressValue > 0){
                jobBuilder.setOverrideDeadline(seekBarProgressValue * 1000); //set value in milliseconds by which job must run
            }

            JobInfo jobInfo = jobBuilder.build();

            jobScheduler.schedule(jobInfo);

            Toast.makeText(this, "Job Scheduled, job will run when constraints are met.", Toast.LENGTH_SHORT).show();

        }
        else{
            Log.d(TAG, "no network constraint selected");
            Toast.makeText(this, "Please select a network contstraint", Toast.LENGTH_SHORT).show();
        }





        Log.d(TAG, "end of scheduleJobs method");


    }




    public void cancelJobs(View view) {
        Log.d(TAG, "inside cancelJobs method");
        if(jobScheduler != null){
            jobScheduler.cancelAll();
            jobScheduler = null;

            Toast.makeText(this, "Jobs Cancelled", Toast.LENGTH_SHORT).show();

        }

    }
}