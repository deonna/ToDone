package com.deonna.todone.models;

import android.content.Context;

import com.deonna.todone.database.TodoDataSource;
import com.deonna.todone.models.Todo;

import java.io.File;
import java.util.ArrayList;

public class Todos {

    private ArrayList<Todo> items;
    private final File filesDir;
    private static TodoDataSource todosDataSource;

    public Todos(Context context, File fileDir) {

        todosDataSource = TodoDataSource.getInstance(context);

        filesDir = fileDir;

        items = todosDataSource.getAll();
    }

    public ArrayList<Todo> getItems() {

        return items;
    }

    public static TodoDataSource getDataSource() {
        return todosDataSource;
    }

    public boolean add(String text) {

        if(!text.isEmpty()) {
            Todo todo = new Todo(text);

            items.add(todo);
            Todo.addToDataSource(todo);
            return true;
        }

        return false;
    }

    public void remove(int position) {

        Todo todo = items.get(position);
        Todo.removeFromDataSource(todo.getId());
        items.remove(position);
    }

    public Todo get(int position) {

        return items.get(position);
    }
}
