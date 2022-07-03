package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
    ArrayList<ToDo> toDos;
    Context context;
    private setOnItemClickListener setOnItemClickListener;

    public RecyclerAdapter(ArrayList<ToDo> toDos, Context context) {
        this.toDos = toDos;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_to_do, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ToDo toDo = toDos.get(position);
        holder.checkBox.setChecked(toDo.isDone);
        holder.textViewSub.setText(toDo.subject);
        //********************set star's Visibility**************************
        int starVisibility;
        if (toDo.isMarked) starVisibility = View.VISIBLE;
        else starVisibility = View.INVISIBLE;
        holder.imageViewStar.setVisibility(starVisibility);
        //**********************************************

    }

    @Override
    public int getItemCount() {
        return toDos.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView textViewSub;
        ImageView imageViewStar;


        public Holder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.check_box);
            textViewSub = itemView.findViewById(R.id.text_view_subject);
            imageViewStar = itemView.findViewById(R.id.imageView_star);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(setOnItemClickListener!= null && getAdapterPosition()!= RecyclerView.NO_POSITION) {
                        setOnItemClickListener.OnClickItem(getAdapterPosition());
                    }
                }
            });
        }
    }

    interface setOnItemClickListener {
        void OnClickItem(int position);

    }
    void OnItemClickListener(setOnItemClickListener setOnItemClickListener){
        this.setOnItemClickListener = setOnItemClickListener;
    }
}

