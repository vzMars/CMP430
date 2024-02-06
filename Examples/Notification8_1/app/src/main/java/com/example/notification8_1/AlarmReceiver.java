package com.example.notification8_1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String TAG = "AlarmReceiver";
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel"; //API 26 and above each notification needs a channel id
    private static final String PRIMARY_CHANNEL_NAME = "my primary notification channel"; //API 26 and above each notification needs a channel name
    private static final int IMPORTANCE_LEVEL = NotificationManager.IMPORTANCE_DEFAULT; //priority level
    private static final int NOTIFICATION_ID_0 = 0; // unique allows us to update notification

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder;
        Intent intent1 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID_0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT >= 26){//API 26 or greater needs channel
            Log.d(TAG, "inside API >= 26 block of code");
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, PRIMARY_CHANNEL_NAME, IMPORTANCE_LEVEL);
            notificationChannel.setDescription("Reminders");

            notificationManager.createNotificationChannel(notificationChannel);

            notificationBuilder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                    .setContentTitle("MOVE AROUND Notification")
                    .setContentText("Get moving!")
                    .setSmallIcon(R.drawable.ic_action_run);

        }
        else{//API less than 26 set priority on notification... not using a channel
            notificationBuilder =
                    new NotificationCompat.Builder(context)
                            .setPriority(IMPORTANCE_LEVEL)
                            .setContentTitle("MOVE AROUND Notification").setContentText("Get moving!")
                            .setSmallIcon(R.drawable.ic_action_run);
        }

        notificationBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(NOTIFICATION_ID_0, notificationBuilder.build());


    }
}