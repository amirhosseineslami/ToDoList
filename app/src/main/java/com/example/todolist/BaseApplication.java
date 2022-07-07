package com.example.todolist;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class BaseApplication extends Application {
    private String name;
    NotificationChannel notificationChannel;

    @Override
    public void onCreate() {
        // starts when our App start
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Insert Channel
            notificationChannel = new NotificationChannel(Constants.NOTIFICATION_INSERT_CHANNEL_ID, Constants.NOTIFICATION_INSERT_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationChannel.setLightColor(R.color.white);
            notificationChannel.setDescription("Todo is ready!");
            notificationChannel.setVibrationPattern(new long[]{100, 200, 100, 300});
            notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            // Music Channel
            NotificationChannel notificationChannel = new NotificationChannel(Constants.NOTIFICATION_MUSIC_CHANNEL_ID, Constants.NOTIFICATION_MUSIC_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            notificationChannel.setDescription(Constants.NOTIFICATION_MUSIC_CHANNEL_DESCRIPTION);
            notificationManager.createNotificationChannel(notificationChannel);
            
        }

        super.onCreate();
    }

    public NotificationChannel getNotificationChannel() {
        return notificationChannel;
    }
}
