package com.example.todolist;

import androidx.annotation.Nullable;

public class ToDo {
    String subject;
    String date;
    boolean isDone = false;
    boolean isMarked = false;
    String description;
    int ID;

    public ToDo(String subject, String date, boolean isDone, boolean marked, String description, @Nullable int ID) {
        this.subject = subject;
        this.date = date;
        this.isDone = isDone;
        this.isMarked = marked;
        this.description = description;
        this.ID = ID;
    }
}
