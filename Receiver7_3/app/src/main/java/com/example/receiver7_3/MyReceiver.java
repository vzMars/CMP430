package com.example.receiver7_3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private Context context;
    protected static final String ACTION_CUSTOM_BROADCAST = "com.example.receiver7_3.ACTION_CUSTOM";

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String intentAction = intent.getAction();
        displayToastForIntentAction(intentAction);
    }

    private void displayToastForIntentAction(String intentAction) {
        switch (intentAction){
            case ACTION_CUSTOM_BROADCAST:
                Log.d("Broadcast Received", "Custom Broadcast");
                Toast.makeText(context, "Custom Broadcast", Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_POWER_CONNECTED:
                Log.d("Broadcast Received", "POWER CONNECTED");
                Toast.makeText(context, "POWER CONNECTED", Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                Log.d("Broadcast Received", "POWER DISCONNECTED");
                Toast.makeText(context, "POWER DISCONNECTED", Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                Log.d("Broadcast Received", "AIRPLANE MODE");
                Toast.makeText(context, "AIRPLANE MODE", Toast.LENGTH_SHORT).show();
            default:
                Log.d("Broadcast Received","DUNNO what Broadcast came in... confused");
                Toast.makeText(context, "SCOOBY???", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
