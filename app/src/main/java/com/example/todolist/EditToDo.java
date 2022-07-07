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
import android.widget.Toast;

import java.util.ArrayList;

public class EditToDo extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener {
    ToDo editingToDo, editedToDo;
    ImageView starImageView, offStarImageView;
    EditText dateEditTxt, titleEditTxt, detailEditTxt;
    Button submitBtn;
    TextView titleTxt, detailTxt, dateTxt;
    Toolbar toolbar;
    DBHelper dbHelper;
    int toDoEditingPosition;
    HelperAsyncTask helperAsyncTask;
    Intent intent;
    ArrayList<ToDo> toDos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_to_do);
        init();
        prepareHelpers();
        setVisibilityOfMarkedStar();
        prepareTexts();
        actionBarPrepares();
        toDos = dbHelper.getTodos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
/*
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

        }*/ if (view == submitBtn) {
            boolean isMarked;
            isMarked = starImageView.getVisibility() == View.VISIBLE;
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
        starImageView.setOnClickListener(this);
        offStarImageView.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        intent = getIntent();
        toDoEditingPosition = intent.getIntExtra("position", 0);
        setSupportActionBar(toolbar);
        toolbar.setElevation(5);
        toolbar.setOnMenuItemClickListener(this);
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
        if(getSupportActionBar()!=null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_delete);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            Intent intent = new Intent(EditToDo.this, MainActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if(menuItem.getItemId()==R.id.item_edit){
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
        }
        if(menuItem.getItemId()==R.id.item_delete){
            helperAsyncTask = new HelperAsyncTask(this, editingToDo, editedToDo);
            helperAsyncTask.doInBackground(2, toDoEditingPosition);
            Intent intent = new Intent(EditToDo.this, MainActivity.class);
            startActivity(intent);
        }
        return false;
    }
}