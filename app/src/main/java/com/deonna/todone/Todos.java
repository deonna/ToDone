package com.deonna.todone;

import android.content.Context;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Todos {

    private static final String TAG = Todos.class.getSimpleName();
    private static final String FILENAME = "todo.txt";
    private static final String ENCODING = "UTF-8";

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

    private void writeToFile() {

        File file =  new File(filesDir, FILENAME);

        ArrayList<String> names = new ArrayList<String>();

        for (Todo todo : items) {
            names.add(todo.getName());
        }

        try {
            FileUtils.writeLines(file, names);
        } catch (IOException e) {
            Log.e(TAG, "Exception caught while writing to " + FILENAME + " ", e);
        }
    }

    private void readFromFile() {

        File file =  new File(filesDir, FILENAME);

        try {
            ArrayList<String> names = new ArrayList<String>(FileUtils.readLines(file, ENCODING));

            items = new ArrayList<Todo>();

            for (String name : names) {
                items.add(new Todo(name));
            }

        } catch (IOException e) {
            Log.e(TAG, "Exception caught while reading " + FILENAME + " ", e);
        }
    }
}
