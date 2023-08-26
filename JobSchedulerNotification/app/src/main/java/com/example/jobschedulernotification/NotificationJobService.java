package com.example.jobschedulernotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class NotificationJobService extends JobService {//API 21 and above, no compat library for prior versions
    private static final String  TAG = "NotificationJobService";
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final String  PRIMARY_CHANNEL_NAME = "Job Service Notification";
    private static final String  PRIMARY_CHANNEL_DESCRIPTION = "Notifications from Job Service ";
    private static final int IMPORTANCE_LEVEL = NotificationManager.IMPORTANCE_HIGH; //priority level

    private static final String  PRIMARY_NOTIFICATION_TITLE = "Job Service Notification";
    private static final int NOTIFICATION_ID_0 = 0; //unique allows us to update notification


    @Override
    public boolean onStartJob(JobParameters params) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= 26) {//API 26 or greater needs channel
            Log.d(TAG, "inside API >=26 block of code");
            NotificationChannel notificationChannel =
                    new NotificationChannel(PRIMARY_CHANNEL_ID, PRIMARY_CHANNEL_NAME, IMPORTANCE_LEVEL);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription(PRIMARY_CHANNEL_DESCRIPTION);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        //Pending Intent to launch the app
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);



        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder
                (this, PRIMARY_CHANNEL_ID)
                .setContentTitle(PRIMARY_NOTIFICATION_TITLE)
                .setContentText("Your Job ran to completion!")
                .setSmallIcon(R.drawable.ic_action_run)
                .setPriority(IMPORTANCE_LEVEL)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);


        notificationBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(NOTIFICATION_ID_0,notificationBuilder.build());

        return false;
    }


    public boolean onStopJob(JobParameters params) {
        return true;//return true to reschedule the job if it fails
    }
}
