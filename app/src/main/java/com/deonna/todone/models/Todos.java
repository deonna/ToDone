package com.deonna.todone.models;

import com.deonna.todone.database.TodoDataSource;
import java.util.ArrayList;

public class Todos {

    private final ArrayList<Todo> items;
    private static TodoDataSource todosDataSource;

    public Todos() {

        todosDataSource = TodoDataSource.getInstance();
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
}
