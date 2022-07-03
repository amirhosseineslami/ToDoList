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
            notificationChannel = new NotificationChannel(Constants.NOTIFICATION_INSERT_CHANNEL_ID, Constants.NOTIFICATION_INSERT_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationChannel.setLightColor(R.color.white);
            notificationChannel.setDescription("Todo is ready!");
            notificationChannel.setVibrationPattern(new long[]{100,200,100,300});
            notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        }
        super.onCreate();
    }
    public NotificationChannel getNotificationChannel(){
        return notificationChannel;
    }
}
