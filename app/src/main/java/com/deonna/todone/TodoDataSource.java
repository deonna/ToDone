package com.deonna.todone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class TodoDataSource {

    private Context context;
    private TodoSQLiteHelper todoSQLiteHelper;

    public TodoDataSource(Context context) {
        this.context = context;
        todoSQLiteHelper = TodoSQLiteHelper.getInstance(context);
    }

    private SQLiteDatabase open() {
        return todoSQLiteHelper.getWritableDatabase();
    }

    private void close(SQLiteDatabase db) {
        db.close();
    }

    public void delete(long todoId) {
        SQLiteDatabase db = open();

        db.beginTransaction();

        db.delete(TodoSQLiteHelper.TABLE_TODOS, String.format("%s=%s", BaseColumns._ID, todoId), null);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public ArrayList<Todo> getAll() {
        SQLiteDatabase db = open();

        Cursor cursor = db.query(
                TodoSQLiteHelper.TABLE_TODOS,
                new String[] { BaseColumns._ID, TodoSQLiteHelper.COLUMN_NAME },
                null, null, null, null, null);

        ArrayList<Todo> todos = new ArrayList<Todo>();

        if(cursor.moveToFirst()) {
            do {
                Todo todo = new Todo(getStringFromColumnName(cursor, TodoSQLiteHelper.COLUMN_NAME));
                todo.setId(getIntFromColumnName(cursor, BaseColumns._ID));

                todos.add(todo);
            } while(cursor.moveToNext());
        }

        return todos;
    }

    private int getIntFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(columnIndex);
    }

    private String getStringFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }

    public void create(Todo todo) {
        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues todoValues = new ContentValues();
        todoValues.put(TodoSQLiteHelper.COLUMN_NAME, todo.getName());
        long todoId = database.insert(TodoSQLiteHelper.TABLE_TODOS, null, todoValues);

        todo.setId(todoId);

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public void update(Todo todo) {
        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues updateTodoValues = new ContentValues();
        updateTodoValues.put(TodoSQLiteHelper.COLUMN_NAME, todo.getName());
        database.update(TodoSQLiteHelper.TABLE_TODOS,
                updateTodoValues,
                String.format("%s=%d", BaseColumns._ID, todo.getId()), null);

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }
}