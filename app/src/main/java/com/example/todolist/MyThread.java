package com.example.todolist;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MyThread extends Thread {
    public Handler handler;
    public Looper myLooper;

    @Override
    public void run() {
        super.run();
        myLooper = Looper.myLooper();
        Looper.prepare();
        Looper.loop();
        handler = new Handler() {
            @Override
            public void publish(LogRecord logRecord) {

            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {
                Log.d(TAG, "close: ");
            }
        };

    }
}
