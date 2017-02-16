package com.deonna.todone;

import android.content.Context;
import java.io.File;
import java.util.ArrayList;

public class Todos {

    private ArrayList<Todo> items;
    private final File filesDir;
    private final TodoDataSource todosDataSource;

    public Todos(Context context, File fileDir) {

        todosDataSource = new TodoDataSource(context);
        filesDir = fileDir;

        items = todosDataSource.getAll();
    }

    public ArrayList<Todo> getItems() {

        return items;
    }

    public boolean add(String text) {

        if(!text.isEmpty()) {
            Todo todo = new Todo(text);

            items.add(todo);
            addToDataSource(todo);
            return true;
        }

        return false;
    }

    public void edit(int position, String text) {

        if(!text.isEmpty()) {
            Todo todo = items.get(position);
            todo.setName(text);

            items.set(position, todo);
            updateInDataSource(todo);
        } else {
            remove(position);
        }
    }

    public void remove(int position) {

        Todo todo = items.get(position);
        removeFromDataSource(todo);
        items.remove(position);
    }

    public Todo get(int position) {

        return items.get(position);
    }

    private void addToDataSource(Todo todo) {
        todosDataSource.create(todo);
    }

    private void updateInDataSource(Todo todo) {
        todosDataSource.update(todo);
    }

    private void removeFromDataSource(Todo todo) {
        todosDataSource.delete(todo.getId());
    }
}
