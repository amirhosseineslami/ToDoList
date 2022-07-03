package com.example.todolist;

import static android.content.ContentValues.TAG;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class InsertActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    Button insertBtn;
    EditText titleEditTxt, dateEditTxt, detailEditTxt;
    ImageView starImageView, offStarImageView;
    boolean insertingToDoIsMarked = false;
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        init();
        insertBtn.setOnClickListener(this);
        starImageView.setOnClickListener(this);
        offStarImageView.setOnClickListener(this);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }


    void init() {
        insertBtn = findViewById(R.id.button_insert);
        titleEditTxt = findViewById(R.id.editText_title);
        dateEditTxt = findViewById(R.id.editText_date);
        detailEditTxt = findViewById(R.id.editText_detail);
        starImageView = findViewById(R.id.imageView_star);
        offStarImageView = findViewById(R.id.imageView_star_off);

    }

    @Override
    public void onClick(View view) {

        if (view == insertBtn) {
            ToDo insertingToDo = new ToDo(String.valueOf(titleEditTxt.getText()), String.valueOf(dateEditTxt.getText()), false, insertingToDoIsMarked, String.valueOf(detailEditTxt.getText()), 0);
            HelperAsyncTask helperAsyncTask = new HelperAsyncTask(this, insertingToDo, insertingToDo);
            helperAsyncTask.doInBackground(1, -1);
            Intent intent = new Intent(InsertActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            publishNewToDoNotification();

        } else if (view == starImageView) {
            Log.d(TAG, "onClick: star clicked!!");
            if (starImageView.getVisibility() == View.VISIBLE) {
                starImageView.setVisibility(View.INVISIBLE);
                insertingToDoIsMarked = false;
            }
        } else if (view == offStarImageView) {
            Log.d(TAG, "onClick: offstar clicked!!");
            starImageView.setVisibility(View.VISIBLE);
            insertingToDoIsMarked = true;
        }
    }

    void publishNewToDoNotification() {
        BaseApplication baseApplication = (BaseApplication) getApplication();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.matrix_screen);

        Notification insertNotification = new NotificationCompat.Builder(this, Constants.NOTIFICATION_INSERT_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.btn_radio)
                .setContentTitle(Constants.NOTIFICATION_INSERT_TITLE)
                .setContentText(titleEditTxt.getText() + " is added to your todo list so no need to keep in your mind ;-) ")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setLargeIcon(bitmap)
                //.setLights(getColor(R.color.purple_200), 0, 2)
                .build();
        notificationManager.notify(Constants.NOTIFICATION_INSERT_ID, insertNotification);
    }

}