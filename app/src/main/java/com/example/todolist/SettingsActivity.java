package com.example.todolist;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.SharedPreferencesCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    Switch musicSwitch;
    Button saveBtn;
    SharedPreferences sharedPreferences;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
        musicSwitch.setChecked(sharedPreferences.getBoolean("music", false));
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    void init() {
        musicSwitch = findViewById(R.id.switch_music);
        saveBtn = findViewById(R.id.button_save);
        saveBtn.setOnClickListener(this);
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save:
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("music", musicSwitch.isChecked());
                Log.d(TAG, "onClick: music" + musicSwitch.isChecked());
                editor.apply();
                startActivity(intent);
                break;
        }
        createSettingNotification();
    }
    void createSettingNotification(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.setting,options);
        Notification notification = new NotificationCompat.Builder(this,Constants.NOTIFICATION_SETTING_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_settings_24)
                .setLargeIcon(bitmap)
                .setChannelId(Constants.NOTIFICATION_SETTING_CHANNEL_ID)
                .build();
        notificationManager.notify(Constants.NOTIFICATION_SETTING_ID,notification);
    }
}