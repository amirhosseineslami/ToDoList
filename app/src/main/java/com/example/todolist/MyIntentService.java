package com.example.todolist;

import static android.content.ContentValues.TAG;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyIntentService extends IntentService {
    private MediaPlayer mediaPlayer;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this,R.raw.music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=16;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.gl,options);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this,Constants.NOTIFICATION_MUSIC_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_music_note_24)
                .setLargeIcon(bitmap)
                .setContentTitle(Constants.NOTIFICATION_MUSIC_TITLE)
                .build();
        notificationManager.notify(Constants.NOTIFICATION_MUSIC_ID,notification);
        /*
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("IntentService")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            startForeground(Constants.NOTIFICATION_MUSIC_ID,notification);
        }*/
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Boolean playingMusic = intent.getBooleanExtra("playingMusic",true);
        Log.d(TAG, "onHandleIntent: "+playingMusic);
        while (playingMusic){
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        Log.d(TAG, "onDestroy: ");
    }

}
