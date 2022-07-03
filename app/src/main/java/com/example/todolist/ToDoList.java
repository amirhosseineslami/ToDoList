package com.example.todolist;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ToDoList {
    private ArrayList<ToDo> toDos = new ArrayList<>();

    public ArrayList<ToDo> getToDos() {
        return toDos;
    }
    public void addToDo(ToDo toDo){
        toDos.add(toDo);
    }
    public void removeToDo(int removingToDoPosition){
        toDos.remove(removingToDoPosition);
    }
    public void editToDo(int editingToDoPosition , ToDo newToDo){
        ToDo editingToDo = toDos.get(editingToDoPosition);
        editingToDo.date = newToDo.date;
        editingToDo.description = newToDo.description;
        editingToDo.isDone = newToDo.isDone;
        editingToDo.isMarked = newToDo.isMarked;
        editingToDo.subject = newToDo.subject;
    }

}

