package com.example.todolist;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.webkit.DateSorter;

import androidx.annotation.Nullable;

import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + Constants.TABLE_NAME + " ( "
                + Constants.COLUMN_ID + " INTEGER PRIMARY KEY , "
                + Constants.COLUMN_TITLE + " TEXT ,"
                + Constants.COLUMN_DATE + " DATE , "
                + Constants.COLUMN_MARK + " INTEGER ,"
                + Constants.COLUMN_DETAIL + " TEXT )";
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + Constants.TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void insertToDo(ToDo toDo) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.COLUMN_TITLE, toDo.subject);
        contentValues.put(Constants.COLUMN_DATE, toDo.date);
        contentValues.put(Constants.COLUMN_DETAIL, toDo.description);
        // ****put isMarked as an Integer
        int isMarked;
        if (toDo.isMarked) isMarked = 1;
        else isMarked = 0;
        contentValues.put(Constants.COLUMN_MARK, isMarked);

        sqLiteDatabase.insert(Constants.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public ArrayList<ToDo> getTodos() {
        String GET_TODOS = "SELECT * FROM " + Constants.TABLE_NAME;
        ArrayList<ToDo> toDos = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(GET_TODOS, null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            boolean isMarked = false;
            for (int i = 0; i < cursor.getCount(); i++) {
                if (cursor.getInt(3) == 1) isMarked = true;
                else isMarked = false;
                toDos.add(new ToDo(cursor.getString(1), cursor.getString(2), false,
                        isMarked, cursor.getString(4), cursor.getInt(0)));

                cursor.moveToNext();
            }
            
        }
        cursor.close();

        // Sort the Todos topMarked
        ToDo testToDo;
        for (int i=0 ; i<toDos.size(); i++){
            if(toDos.get(i).isMarked) {
                for (int j = 0; j < toDos.size(); j++) {
                    if((!toDos.get(j).isMarked)&&(j<i)){
                        testToDo = toDos.get(j);
                        toDos.set(j,toDos.get(i));
                        toDos.set(i,testToDo);
                    }
                }
            }
        }
sqLiteDatabase.close();
        return toDos;
    }

    public void updateToDo(int position, ToDo editedToDo) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.COLUMN_TITLE, editedToDo.subject);
        contentValues.put(Constants.COLUMN_DATE, editedToDo.date);
        int ID = editedToDo.ID;
        int mark;
        Log.d(TAG, "updateToDo: input mark " + editedToDo.isMarked);
        if (editedToDo.isMarked) mark = 1;
        else mark = 0;
        contentValues.put(Constants.COLUMN_MARK, mark);
        Log.d(TAG, "updateToDo: mark value " + mark);
        contentValues.put(Constants.COLUMN_DETAIL, editedToDo.description);
        sqLiteDatabase.update(Constants.TABLE_NAME, contentValues, Constants.COLUMN_ID + "= ?", new String[]{String.valueOf(ID)});
        sqLiteDatabase.close();

    }

    public void removeToDo(int ID) {
        Log.d(TAG, "removeToDo: removing: "+ ID);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(Constants.TABLE_NAME, Constants.COLUMN_ID + "= ?", new String[]{String.valueOf(ID)});
        sqLiteDatabase.close();
    }

}
