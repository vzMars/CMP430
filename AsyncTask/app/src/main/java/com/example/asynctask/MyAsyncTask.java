package com.example.asynctask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class MyAsyncTask extends AsyncTask<Void, Integer, String> {

    private WeakReference<TextView> textView; //TextView textview

    public MyAsyncTask(TextView tv){
        this.textView = new WeakReference<TextView>(tv);
    }

    public static final String TAG = "MyAsyncTask";

    @Override
    protected String doInBackground(Void... voids) {
        Random random = new Random();
        int x = random.nextInt(1000);


            try {
                for(int i = 0;i < x;i++) {
                    Thread.sleep( 125);//sleep for a bit then log the number
                    Log.d(TAG, "out of sleep now " + i);
                    if(i % 10 == 0){
                        publishProgress(i);//calls onProgressUpdate
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        return "DONE counted up to " + x;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.d(TAG,"onProgressUpdate Method " + values[0]);
        textView.get().setText(String.valueOf(values[0]));
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d(TAG,"onPostExecute Method " + s);
        textView.get().setText(s);
    }
}
