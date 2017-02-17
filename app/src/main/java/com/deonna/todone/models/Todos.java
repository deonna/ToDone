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
            todo.addToDataSource();
            return true;
        }

        return false;
    }

    public void edit(int position, String text) {

        if(!text.isEmpty()) {
            Todo todo = items.get(position);
            todo.setName(text);

            items.set(position, todo);
            todo.updateInDataSource();
        } else {
            remove(position);
        }
    }

    public void remove(int position) {

        Todo todo = items.get(position);
        todo.removeFromDataSource();
        items.remove(position);
    }

    public Todo get(int position) {

        return items.get(position);
    }
}
