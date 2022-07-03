package com.example.todolist;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EditToDo extends AppCompatActivity implements View.OnClickListener {
    ToDo editingToDo, editedToDo;
    ImageView editImageView, deleteImageView, starImageView, offStarImageView;
    EditText dateEditTxt, titleEditTxt, detailEditTxt;
    Button submitBtn;
    TextView titleTxt, detailTxt, dateTxt;
    Toolbar toolbar;
    DBHelper dbHelper;
    int toDoEditingPosition;
    HelperAsyncTask helperAsyncTask;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_to_do);
        init();
        prepareHelpers();
        setVisibilityOfMarkedStar();
        prepareTexts();
        actionBarPrepares();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        DBHelper dbHelper = new DBHelper(this);

        if (view == deleteImageView) {
            //**deleting**
            helperAsyncTask = new HelperAsyncTask(this, editingToDo, editedToDo);
            helperAsyncTask.doInBackground(2, toDoEditingPosition);
            Intent intent = new Intent(EditToDo.this, MainActivity.class);
            startActivity(intent);
        } else if (view == editImageView) {
            submitBtn.setVisibility(View.VISIBLE);
            titleTxt.setVisibility(View.INVISIBLE);
            titleEditTxt.setVisibility(View.VISIBLE);
            titleEditTxt.setText(titleTxt.getText());
            dateTxt.setVisibility(View.INVISIBLE);
            dateEditTxt.setVisibility(View.VISIBLE);
            dateEditTxt.setText(dateTxt.getText());
            detailTxt.setVisibility(View.INVISIBLE);
            detailEditTxt.setVisibility(View.VISIBLE);
            detailEditTxt.setText(detailTxt.getText());

        } else if (view == submitBtn) {
            boolean isMarked;
            if (starImageView.getVisibility() == View.VISIBLE) isMarked = true;
            else isMarked = false;
            //**editing**
            editedToDo = new ToDo(String.valueOf(titleEditTxt.getText()), String.valueOf(dateEditTxt.getText()), false, isMarked, String.valueOf(detailEditTxt.getText()), editingToDo.ID);
            helperAsyncTask = new HelperAsyncTask(this, editingToDo, editedToDo);
            helperAsyncTask.doInBackground(3, toDoEditingPosition);
            Intent intent = new Intent(EditToDo.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (view == starImageView) {
            if (starImageView.getVisibility() == View.VISIBLE) {
                starImageView.setVisibility(View.INVISIBLE);
                editedToDo = editingToDo;
                editedToDo.isMarked = false;
            }
            /*else {
                Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!");
                starImageView.setVisibility(View.VISIBLE);
                editingToDo.isMarked = true;
            }*/
            HelperAsyncTask helperAsyncTask = new HelperAsyncTask(this, editingToDo, editedToDo);
            helperAsyncTask.doInBackground(3, toDoEditingPosition);
            /*
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dbHelper.updateToDo(toDoEditingPosition, editingToDo);
                    Log.d(TAG, "onClick: " + dbHelper.getTodos().get(toDoEditingPosition).isMarked);
                }
            }).start();*/

        } else if (view == offStarImageView) {
            Log.d(TAG, "onClick: offstar clicked");
            starImageView.setVisibility(View.VISIBLE);
            editedToDo = editingToDo;
            editedToDo.isMarked = true;
            HelperAsyncTask helperAsyncTask = new HelperAsyncTask(this, editingToDo, editedToDo);
            helperAsyncTask.doInBackground(3, toDoEditingPosition);
            /*
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dbHelper.updateToDo(toDoEditingPosition, editingToDo);
                    Log.d(TAG, "onClick: " + dbHelper.getTodos().get(toDoEditingPosition).isMarked);
                }
            }).start();*/
        }
    }

    void init() {
        editImageView = findViewById(R.id.imageView_edit);
        deleteImageView = findViewById(R.id.imageView_delete);
        detailTxt = findViewById(R.id.textView_detail);
        detailEditTxt = findViewById(R.id.editText_detail);
        titleTxt = findViewById(R.id.textView_title);
        titleEditTxt = findViewById(R.id.editText_title);
        dateTxt = findViewById(R.id.textView_Date);
        dateEditTxt = findViewById(R.id.editText_date);
        starImageView = findViewById(R.id.imageView_star);
        offStarImageView = findViewById(R.id.imageView_star_off);
        submitBtn = findViewById(R.id.button_insert);
        toolbar = findViewById(R.id.toolbar);
        editImageView.setOnClickListener(this);
        deleteImageView.setOnClickListener(this);
        starImageView.setOnClickListener(this);
        offStarImageView.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        intent = getIntent();
        toDoEditingPosition = intent.getIntExtra("position", 0);
        setSupportActionBar(toolbar);
        toolbar.setElevation(5);
    }

    void prepareTexts() {
        titleTxt.setText(editingToDo.subject);
        detailTxt.setText(editingToDo.description);
        dateTxt.setText(editingToDo.date);
    }

    void prepareHelpers() {
        dbHelper = new DBHelper(this);
        ArrayList<ToDo> toDos = (ArrayList<ToDo>) dbHelper.getTodos();
        editingToDo = toDos.get(toDoEditingPosition);
        toDoEditingPosition = intent.getIntExtra("position", -1);
    }

    private void setVisibilityOfMarkedStar() {
        if (editingToDo.isMarked) starImageView.setVisibility(View.VISIBLE);
        else starImageView.setVisibility(View.INVISIBLE);
    }
    void actionBarPrepares(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_delete);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return true;
    }
}