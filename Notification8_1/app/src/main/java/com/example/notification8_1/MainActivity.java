package com.example.notification8_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private NotificationManager notificationManager;
    private PendingIntent pendingNotificationIntent;

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel"; //API 26 and above each notification needs a channel id
    private static final String PRIMARY_CHANNEL_NAME = "my primary notification channel"; //API 26 and above each notification needs a channel name
    private static final int IMPORTANCE_LEVEL = NotificationManager.IMPORTANCE_DEFAULT; //priority level
    private static final int NOTIFICATION_ID_0 = 0; // unique allows us to update notification

    private Button btnCreateNotification, btnCancelNotification, btnUpdateNotification, btnAlarmSet, btnAlarmCancel;

    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        btnCreateNotification = findViewById(R.id.btn__create_notif);
        btnCancelNotification = findViewById(R.id.btn_cancel_notif);
        btnUpdateNotification = findViewById(R.id.btn_update_notif);
        btnAlarmSet = findViewById(R.id.btn_create_alarm);
        btnAlarmCancel = findViewById(R.id.btn_cancel_alarm);

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);

        pendingNotificationIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID_0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public void clickedButton(View view) {
        int btnClickedID = view.getId();
        switch (btnClickedID){
            case R.id.btn__create_notif:
                Log.d(TAG, "notify button clicked");
                sendNotification();
                break;
            case R.id.btn_cancel_notif:
                Log.d(TAG, "cancel button clicked");
                cancelNotification();
                break;
            case R.id.btn_update_notif:
                Log.d(TAG, "update button clicked");
                updateNotification();
                break;
            case R.id.btn_create_alarm:
                Log.d(TAG, "set alarm button clicked");
                setAlarm();
                break;
            case R.id.btn_cancel_alarm:
                Log.d(TAG, "cancel alarm button clicked");
                cancelAlarm();
                break;
            default:
                Log.d(TAG, "something clicked");
                break;
        }
    }//closing bracket for clickedButton method

    protected void sendNotification() {
        Log.d(TAG, "inside sendNotification method");
        NotificationCompat.Builder notificationBuilder;
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID_0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT >= 26){//API 26 or greater needs channel
            Log.d(TAG, "inside API >= 26 block of code");
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, PRIMARY_CHANNEL_NAME, IMPORTANCE_LEVEL);
            notificationChannel.setDescription("Reminders");

            notificationManager.createNotificationChannel(notificationChannel);

            notificationBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                    .setContentTitle("MOVE AROUND Notification")
                    .setContentText("Get moving!")
                    .setSmallIcon(R.drawable.ic_action_run);

        }
        else{//API less than 26 set priority on notification... not using a channel
            notificationBuilder =
                    new NotificationCompat.Builder(this)
                            .setPriority(IMPORTANCE_LEVEL)
                            .setContentTitle("MOVE AROUND Notification").setContentText("Get moving!")
                            .setSmallIcon(R.drawable.ic_action_run);
        }

        notificationBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(NOTIFICATION_ID_0, notificationBuilder.build());

        //disable the create new notification button


    }//closing bracket for sendNotification method

    protected void cancelNotification(){
        Log.d(TAG, "inside cancelNotification method");
        notificationManager.cancel(NOTIFICATION_ID_0);
    }

    protected void updateNotification() {
        Log.d(TAG, "inside updateNotification method");
        Bitmap bitmapLarge = BitmapFactory.decodeResource(getResources(), R.drawable.mascot);

        NotificationCompat.Builder notificationBuilder;

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID_0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        if(Build.VERSION.SDK_INT >= 26){//API 26 or greater needs channel
            Log.d(TAG, "inside API >= 26 block of code");
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, PRIMARY_CHANNEL_NAME, IMPORTANCE_LEVEL);
            notificationChannel.setDescription("Reminders");

            notificationManager.createNotificationChannel(notificationChannel);

            notificationBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                    .setContentTitle("MOVE AROUND Notification")
                    .setContentText("Get moving!")
                    .setSmallIcon(R.drawable.ic_action_run)
                    .setLargeIcon(bitmapLarge);

        }
        else{//API less than 26 set priority on notification... not using a channel
            notificationBuilder =
                    new NotificationCompat.Builder(this)
                            .setPriority(IMPORTANCE_LEVEL)
                            .setContentTitle("MOVE AROUND Notification").setContentText("Get moving!")
                            .setSmallIcon(R.drawable.ic_action_run)
                            .setLargeIcon(bitmapLarge);
        }

        notificationBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(NOTIFICATION_ID_0, notificationBuilder.build());

    }

    protected void setAlarm() {
        Log.d(TAG, "inside setAlarm method");
        long triggerTime = SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval, pendingNotificationIntent);
    }

    protected void cancelAlarm() {
        Log.d(TAG, "inside cancelAlarm method");
        if(alarmManager != null){
            alarmManager.cancel(pendingNotificationIntent);
        }
        notificationManager.cancelAll();
    }




}