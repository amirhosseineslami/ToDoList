package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView toDoRecyclerView;
    ArrayList<ToDo> toDos = new ArrayList<>();
    RecyclerAdapter recyclerAdapter;
    DBHelper dbHelper;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        dbHelper = new DBHelper(this);
        toDos=dbHelper.getTodos();
        recyclerAdapter = new RecyclerAdapter(toDos, this);
        toDoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        toDoRecyclerView.setAdapter(recyclerAdapter);
        imageView.setOnClickListener(this);
        recyclerAdapter.OnItemClickListener(new RecyclerAdapter.setOnItemClickListener() {
            @Override
            public void OnClickItem(int position) {
                Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, EditToDo.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    void init() {
        toDoRecyclerView = findViewById(R.id.recycler_view_toDo);
        imageView = findViewById(R.id.imageView_insert);
    }

    @Override
    public void onClick(View view) {
        if (view==imageView){
            Intent intent = new Intent(MainActivity.this, InsertActivity.class);
            startActivity(intent);
        }
    }
}