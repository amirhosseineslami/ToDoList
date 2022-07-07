package com.example.todolist;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView toDoRecyclerView;
    ArrayList<ToDo> toDos = new ArrayList<>();
    RecyclerAdapter recyclerAdapter;
    DBHelper dbHelper;
    ImageView insertImageView;
    Intent intent;
    SharedPreferences sharedPreferences;
    ImageView settingImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        dbHelper = new DBHelper(this);
        toDos = dbHelper.getTodos();
        recyclerAdapter = new RecyclerAdapter(toDos, this);
        toDoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        toDoRecyclerView.setAdapter(recyclerAdapter);
        insertImageView.setOnClickListener(this);
        settingImageView.setOnClickListener(this);
        recyclerAdapter.OnItemClickListener(position -> {
            Intent intent = new Intent(MainActivity.this, EditToDo.class);
            intent.putExtra("position", position);
            startActivity(intent);
        });
        playMusicIntentService();
    }

    void init() {
        toDoRecyclerView = findViewById(R.id.recycler_view_toDo);
        insertImageView = findViewById(R.id.imageView_insert);
        settingImageView = findViewById(R.id.imageView_setting);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toast.makeText(this, "menu", Toast.LENGTH_SHORT).show();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public void onClick(View view) {
        if (insertImageView.equals(view)) {
            intent = new Intent(MainActivity.this, InsertActivity.class);
            startActivity(intent);
        } else if (settingImageView.equals(view)) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    }

    void playMusicIntentService() {
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        intent = new Intent(MainActivity.this, MyIntentService.class);
        if (sharedPreferences.getBoolean("music", false)) {
            startService(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, MyIntentService.class);
            intent.putExtra("playingMusic",false);
            stopService(intent);
        }
    }

}