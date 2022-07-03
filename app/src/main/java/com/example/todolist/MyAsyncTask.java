package com.example.todolist;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<Integer, Integer, String> {
    private static final String TAG = "AsyncTask";
    private Context context;

    public MyAsyncTask(Context context){
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context, "started" , Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(Integer... integers) {
        for (int i = 0; i <integers[0] ; i++) {
            Log.d(TAG, "doInBackground: "+i);

            SystemClock.sleep(1000);

            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        Toast.makeText(context, String.valueOf(values[0]), Toast.LENGTH_SHORT).show();
    }

}
