package com.example.todolist;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

public class HelperAsyncTask extends AsyncTask<Integer, ToDo, Integer> {
    private Context context;
    private ToDo toDo,editedToDo;
    public HelperAsyncTask(Context context, ToDo toDo, @Nullable ToDo editedToDo){
        this.context = context;
        this.toDo = toDo;
        this.editedToDo=editedToDo;
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        DBHelper dbHelper = new DBHelper(context);

        // workType= |1:insert |2:remove |3:update
        int workType = integers[0];
        int toDoPosition = integers[1];

        switch (workType){
            case 1:
                //insert
                dbHelper.insertToDo(toDo);
                break;

            case 2:
                //remove
                dbHelper.removeToDo(toDo.ID);
                break;

            case 3:
                //update
                dbHelper.updateToDo(toDoPosition, editedToDo);
                break;
        }

        return 0;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute: started");
    }

    @Override
    protected void onPostExecute(Integer i) {
        super.onPostExecute(i);
    }

    @Override
    protected void onProgressUpdate(ToDo... values) {
        super.onProgressUpdate(values);
    }

}
