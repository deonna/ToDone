package com.deonna.todone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class TodoDataSource {

    private static TodoDataSource sInstance;
    private final TodoSQLiteHelper todoSQLiteHelper;

    private TodoDataSource(Context context) {

        todoSQLiteHelper = TodoSQLiteHelper.getInstance(context);
    }

    public static TodoDataSource getInstance(Context context) {

        if(sInstance == null) {
            sInstance = new TodoDataSource(context.getApplicationContext());
        }

        return sInstance;
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

    private String[] getColumnsForQuery() {

        String[] columns = new String[] {
                BaseColumns._ID,
                TodoSQLiteHelper.COLUMN_NAME,
                TodoSQLiteHelper.COLUMN_COMPLETED,
                TodoSQLiteHelper.COLUMN_DUE_DATE,
                TodoSQLiteHelper.COLUMN_PRIORITY
        };

        return columns;
    }

    public ArrayList<Todo> getAll() {

        SQLiteDatabase db = open();

        Cursor cursor = db.query(
                TodoSQLiteHelper.TABLE_TODOS,
                getColumnsForQuery(),
                null, null, null, null, null);

        ArrayList<Todo> todos = new ArrayList<Todo>();

        if(cursor.moveToFirst()) {
            do {

                Todo todo = initializeTodoFromDb(cursor);
                todos.add(todo);

            } while(cursor.moveToNext());
        }

        return todos;
    }

    private Todo initializeTodoFromDb(Cursor cursor) {

        Todo todo = new Todo(getStringFromColumnName(cursor, TodoSQLiteHelper.COLUMN_NAME));

        todo.setId(getIntFromColumnName(cursor, BaseColumns._ID));

        todo.setDueDateText(getStringFromColumnName(cursor, TodoSQLiteHelper.COLUMN_DUE_DATE));
        todo.setIsCompleted(getIntFromColumnName(cursor, TodoSQLiteHelper.COLUMN_COMPLETED));
        todo.setPriorityFromInt(getIntFromColumnName(cursor, TodoSQLiteHelper.COLUMN_PRIORITY));

        return todo;
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

        ContentValues todoValues = getContentValuesForTodo(todo);

        long todoId = database.insert(TodoSQLiteHelper.TABLE_TODOS, null, todoValues);
        todo.setId(todoId);

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    public void update(Todo todo) {

        SQLiteDatabase database = open();
        database.beginTransaction();

        ContentValues updateTodoValues = getContentValuesForTodo(todo);

        database.update(TodoSQLiteHelper.TABLE_TODOS,
                updateTodoValues,
                String.format("%s=%d", BaseColumns._ID, todo.getId()), null);

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    private ContentValues getContentValuesForTodo(Todo todo) {

        ContentValues todoValues = new ContentValues();
        todoValues.put(TodoSQLiteHelper.COLUMN_NAME, todo.getName());
        todoValues.put(TodoSQLiteHelper.COLUMN_COMPLETED, todo.getIsCompletedAsInt());
        todoValues.put(TodoSQLiteHelper.COLUMN_DUE_DATE, todo.getDueDateText());
        todoValues.put(TodoSQLiteHelper.COLUMN_PRIORITY, todo.getPriority().getValue());

        return todoValues;
    }
}
